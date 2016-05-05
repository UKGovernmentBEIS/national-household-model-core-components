package uk.org.cse.nhm.stockimport.simple;

import java.nio.file.Path;

public interface ImportErrorHandler extends AutoCloseable {
	void handle(final Path file, final int line, String newParam, final String error);

	void update(final String message);
}