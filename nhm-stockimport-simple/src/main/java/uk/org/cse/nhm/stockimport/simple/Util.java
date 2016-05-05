package uk.org.cse.nhm.stockimport.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;

import com.btaz.util.files.SortController;
import com.google.common.base.Optional;

public class Util {
    public static BufferedReader open(final Path file) throws IOException {
        final BOMInputStream stream = new BOMInputStream(Files.newInputStream(file));
        final String charsetName = stream.getBOMCharsetName();
        return new BufferedReader(new InputStreamReader(stream,
                                                        charsetName == null ?
                                                        "UTF-8" : charsetName));
    }
    
	public static boolean sort(final Path dir, final ImportErrorHandler errors) throws IOException {
		final File sortDirectory = Files.createTempDirectory("-sort-stock-files").toFile();
		try {
			for (final Path p : Files.newDirectoryStream(dir, "*.csv")) {
				final String[] header;
				final String firstLine;
				final char separator;
				try (final BufferedReader read = open(p)) {
					firstLine = read.readLine();
					if (firstLine == null) {
						continue;
					}
                    separator = CSV.guessSeparator(firstLine);
					header = CSV.parse(firstLine, true, separator);
				}
				
				int key = 0;
				for (key = 0; key<header.length; key++) {
					if (header[key].equalsIgnoreCase("aacode")) break;
				}
				
				if (key == header.length) continue;
				
				errors.update("Sorting " + p + " on field " + key);
				
				final int key_ = key;
				final Path sorted = Files.createTempFile(p.getFileName().toString(), "csv");
				
				SortController.sortFile(sortDirectory, p.toFile(), 
						sorted.toFile()
						, 
						new Comparator<String>() {
							
							@Override
							public int compare(final String arg0, final String arg1) {
								final String k0 = CSV.parse(arg0, true, separator)[key_];
								final String k1 = CSV.parse(arg1, true, separator)[key_];
								
								return k0.compareTo(k1);
							}
						}
						, true);
				
				// replace original with sorted
				try (final BufferedWriter out = Files.newBufferedWriter(p, StandardCharsets.UTF_8)) {
					 try (final BufferedReader in = open(sorted)) {
						 out.append(firstLine + "\n");
						 IOUtils.copy(in, out);
					 }
				}
				
				Files.delete(sorted);
			}
		} finally {
			destroy(sortDirectory.toPath());
		}
		
		return true;
	}
	
	public static boolean unzip(final Path zipPath, final Path temporary, final ImportErrorHandler errors) {
		errors.update("Unzipping import bundle");
		try (final ZipFile zipfile = new ZipFile(zipPath.toFile())) {
			final Enumeration<? extends ZipEntry> entries = zipfile.entries();
			ZipEntry ze = null;
			
			while (entries.hasMoreElements()) {
				ze = entries.nextElement();
				
				final Path unzippedPath = temporary.resolve(ze.getName());
				
				if (ze.isDirectory()) {
					Files.createDirectories(unzippedPath);
				} else {
					Files.createDirectories(unzippedPath.getParent());
					
					try (final OutputStream out = Files.newOutputStream(unzippedPath)) {
						IOUtils.copyLarge(zipfile.getInputStream(ze), out);
					}
				}
			}
		} catch (final IOException e) {
			errors.handle(zipPath, 0, "n/a", "Error unzipping: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	
	private static boolean checkFor(final Path dir, final String file, final ImportErrorHandler errors) {
		errors.update("Checking that required file " + file + " is present...");
		final Path p = dir.resolve(file);
		if (!Files.exists(p)) {
			errors.handle(p, 0, "n/a", "Required input file is missing");
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkfiles(final Path temporary, final Set<String> files, final ImportErrorHandler errors) {
		boolean ok = true;
		
		for (final String s : files) {
			ok = ok && checkFor(temporary, s, errors);
		}
		
		return ok;
	}
	
	public static void destroy(final Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
				if (exc == null) {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				} else {
					throw exc;
				}
			}
		});
	}
	
	public static ImportErrorHandler logFile(final Path file) throws IOException {
		final BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
		return new ImportErrorHandler() {
			@Override
			public void close() throws Exception {
				out.close();
			}

			@Override
			public void update(final String message) {
				try {
					out.append(message + "\n");
				} catch (final IOException e) {
				}
			}
			
			@Override
			public void handle(final Path file, final int line, final String aacode, final String error) {
				try {
					out.append(String.format("%s:%d [%s] %s\n", file, line, aacode, error));
				} catch (final IOException e) {
					throw new RuntimeException("Error writing to error log: " + e.getMessage(), e);
				}
			}
		};
	}
	
	public static void createZipFile(final Path output, final Path resultPath) throws IOException {
		try (final ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(resultPath))) {
			Files.walkFileTree(output, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					zout.putNextEntry(new ZipEntry(output.relativize(file).toString()));
					try (final InputStream is = Files.newInputStream(file)) {
						IOUtils.copyLarge(is, zout);
					}
					return FileVisitResult.CONTINUE;
				}
			});
		}
	}

	public static Optional<String> getTypeOfZip(final Path file) {
		try (final ZipFile zf = new ZipFile(file.toFile())) {
			final ZipEntry ze = zf.getEntry(Metadata.PATH);
			final Metadata md = Metadata.load(new BufferedReader(new InputStreamReader(zf.getInputStream(ze))));
			return Optional.of(md.values.get("type"));
		} catch (final Exception e) {
		}
		return Optional.absent();
	}
}
