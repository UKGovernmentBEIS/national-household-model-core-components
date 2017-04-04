package uk.org.cse.nhm.stockimport.simple.dto;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import com.btaz.util.files.SortController;

import uk.org.cse.nhm.stockimport.simple.CSV;
import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;
import uk.org.cse.nhm.stockimport.simple.Util;

public class Deduplicator {
	public static boolean deduplicate(final Path dir, final ImportErrorHandler errors) throws IOException {
		final File sortDirectory = Files.createTempDirectory("-deduplicate-stock-files").toFile();
		try {
			for (final Path p : Files.newDirectoryStream(dir, "*.csv")) {
				final Set<String> singularCodes = new HashSet<>();
				final String[] header;
				final String firstLine;
				
				// for each file
				// sort the file by everything, but aacode and weights last
				// iterate over the file and collect sets of aacodes that are identical % aacode, weight

                final char separator;
                
				try (final BufferedReader read = Util.open(p)) {
					firstLine = read.readLine();
					if (firstLine == null) {
						continue;
					}
                    separator = CSV.guessSeparator(firstLine);
					header = CSV.parse(firstLine, true, separator);
				}
								
				final int[] sortOrder = new int[header.length];
				for (int i = 0; i<sortOrder.length; i++) {
					sortOrder[i] = i;
				}
				
				int key = 0;
				
				for (int i = 0; i<header.length; i++) {
					if (header[i].equalsIgnoreCase("aacode")) {
						final int j = sortOrder[sortOrder.length-1];
						sortOrder[sortOrder.length - 1] = i;
						sortOrder[i] = j;
						key = i;
					} else if (header[i].equalsIgnoreCase("householdcaseweight")) {
						sortOrder[i] = -1;
					} else if (header[i].equalsIgnoreCase("dwellingcaseweight")) {
						sortOrder[i] = -1;
					}
				}
				
				final Path sorted = Files.createTempFile(p.getFileName().toString(), "csv");
				
				SortController.sortFile(sortDirectory, p.toFile(), 
						sorted.toFile()
						, 
						new Comparator<String>() {
							
							@Override
							public int compare(final String arg0, final String arg1) {
								final String [] r0 = CSV.parse(arg0, true, separator);
								final String [] r1 = CSV.parse(arg1, true, separator);
								// lexically compare by field, in the desired order
								// skipping stupid fields
								for (int i_ = 0; i_<sortOrder.length-1; i_++) {
									final int i = sortOrder[i_];
									if (i < 0) continue;
									final String k0 = r0[i];
									final String k1 = r1[i];
									
									final int cmp = k0.compareTo(k1);
									if (cmp != 0) {
										return cmp;
									}
								}
								
								return 0;
							}
						}
						, true);
				
				// scan the sorted copy looking for rows which are the same
				
				try (final CSV.Reader reader = CSV.trimmedReader(Util.open(sorted))) {
					final Set<String> codes = new HashSet<>();
					String[] prev = null;
					String[] row;
					while ((row = reader.read()) != null) {
						if (prev != null) {
							boolean matching = true;
							if (prev.length == row.length && prev.length > key) {
								for (int i = 0; i<prev.length; i++) {
									if (i == key) continue;
									if (!prev[i].equals(row[i])) {
										matching = false;
										break;
									}
								}
							} else {
								matching = false;
							}
							if (!matching) {
								if (codes.size() == 1) {
									singularCodes.addAll(codes);
								}
								codes.clear();
							}
							codes.add(row[key]);
						}
						
						prev = row;
					}
					
					if (codes.size() == 1) {
						singularCodes.addAll(codes);
					}
					codes.clear();
				}
				System.out.println("After " + sorted + ": " + singularCodes.size());
				Files.delete(sorted);
			}
			
		} finally {
			Util.destroy(sortDirectory.toPath());
		}
		
		
		return true;
	}
}
