package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.libraries.ILibrariesOutput;

public class TemporaryFileStreamFactory implements IOutputStreamFactory, Closeable {
	private final Path temporary;
	private final Map<Path, TrackedStream> streams = new HashMap<>();
	private final Map<Path, IReportDescriptor> descriptors = new HashMap<>();

	private static final Logger log = LoggerFactory.getLogger(TemporaryFileStreamFactory.class);
	
	public TemporaryFileStreamFactory() {
		try {
			temporary = createNewTemporaryDirectory();
			log.debug("Creating report in {}", temporary);
		} catch (final IOException e) {
			throw new RuntimeException(
					"Unable to create temporary directory for report construction",
					e);
		}
	}
	
	/**
	 * A helper which tracks bytes written and whether the delegate stream has been closed.
	 *
	 */
	static class TrackedStream extends FilterOutputStream {
		private long count;
		private boolean closed = false;

		/**
		 * Wraps another output stream, counting the number of bytes written.
		 * 
		 * @param out
		 *            the output stream to be wrapped
		 */
		public TrackedStream(@Nullable final OutputStream out) {
			super(out);
		}

		/** Returns the number of bytes written. */
		public long getCount() {
			return count;
		}

		@Override
		public void write(final byte[] b, final int off, final int len) throws IOException {
			out.write(b, off, len);
			count += len;
		}

		@Override
		public void write(final int b) throws IOException {
			out.write(b);
			count++;
		}
		
		@Override
		public void close() throws IOException {
			closed = true;
			super.close();
		}

		public boolean isClosed() {
			return closed;
		}
	}

	protected Path createNewTemporaryDirectory() throws IOException {
		return Files.createTempDirectory("nhm-report");
	}
	
	protected Path getTemporaryDirectory() {
		return temporary;
	}
	
	@Override
	public OutputStream createReportFile(final String name, final Optional<IReportDescriptor> descriptor) {
		final Path outputFile = temporary.resolve(removeInvalidCharacters(name));

		if (streams.containsKey(outputFile))
			throw new IllegalArgumentException("The report output " + name
					+ " has already been created by another reporter");

		if (descriptor.isPresent()) {
			descriptors.put(outputFile, descriptor.get());
		}

		try {
			mkdirs(outputFile);
			streams.put(outputFile, createStream(outputFile));
		} catch (final IOException e) {
			throw new RuntimeException("IO Exception creating report output "
					+ name, e);
		}

		return streams.get(outputFile);
	}

	private static String removeInvalidCharacters(final String name) {
		return name.replaceAll("[<>:\"\\|\\?\\*]", "-");
	}

	private TrackedStream createStream(final Path outputFile) throws IOException {
		log.debug("Creating stream for {}", outputFile);
		return new TrackedStream(Files.newOutputStream(outputFile));
	}

	private void mkdirs(final Path outputFile) throws IOException {
		if (!Files.exists(outputFile)) {
			Files.createDirectories(outputFile.getParent());
		}
	}

	private void eraseTemporaryFiles() {
		final SimpleFileVisitor<Path> deleter = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				log.debug("delete {}", file);
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(final Path dir,final IOException exc) throws IOException {
				if (exc != null) throw exc;
				log.debug("delete {}", dir);
				try {
					Files.delete(dir);
				} catch (final IOException ex) {
					log.debug("Could not delete temporary directory {}", dir);
				}
				return FileVisitResult.CONTINUE;
			}
		};
		
		log.debug("erasing {}", temporary);
		
		try {
			Files.walkFileTree(temporary, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, deleter);
		} catch (final IOException e) {
			//log.error("unable to clean up temp dir {}", temporary, e);
		}
	}

	@Override
	public void close() throws IOException {
		eraseTemporaryFiles();
	}

	protected List<CompletedOutput> getOutputs() throws IOException {
		final ArrayList<CompletedOutput> result = new ArrayList<>();

		for (final Map.Entry<Path, TrackedStream> entry : streams
				.entrySet()) {
			
			if (!entry.getValue().isClosed()) {
				throw new IOException(
						String.format("The report outputstream at %s has not been closed, but the report is being built", entry.getKey()));
			}
			
			final IReportDescriptor descriptor = descriptors.get(entry.getKey());
			final Set<ILibrariesOutput> libraries;
			if (descriptor == null) {
				libraries = Collections.emptySet();
			} else {
				libraries = descriptor.getLibraries();
			}
			
			final Path path = entry.getKey();
			
			result.add(new CompletedOutput(
						path,
						temporary.relativize(path).toString().replace('\\', '/'),
						descriptor,
						entry.getValue().getCount(),
						libraries
					));
		}

		return result;
	}

	public static class CompletedOutput {
		public final Path path;
		public final String name;
		public final IReportDescriptor descriptor;
		public final long size;
		public final Set<ILibrariesOutput> dependencies;

		public CompletedOutput(final Path path, final String name,
				final IReportDescriptor descriptor, final long size,
				final Set<ILibrariesOutput> dependencies) {
			super();
			this.path = path;
			this.name = name;
			this.descriptor = descriptor;
			this.size = size;
			this.dependencies = ImmutableSet.copyOf(dependencies);
		}

		public Path getPath() {
			return path;
		}

		public String getName() {
			return name;
		}

		public IReportDescriptor getDescriptor() {
			return descriptor;
		}

		public long getSize() {
			return size;
		}

		public Set<ILibrariesOutput> getDependencies() {
			return dependencies;
		}
		
		public Set<ILibrariesOutput> getDependencyClosure() {
			final HashSet<ILibrariesOutput> sum = new HashSet<>();
			for (final ILibrariesOutput d : dependencies) {
				sum.add(d);
				sum.addAll(d.getDependencies());
			}
			return sum;
		}
		
		public String getHumanReadableSize() {
			return FileUtils.byteCountToDisplaySize(size);
		}
	}
}
