package uk.org.cse.nhm.reporting.standard.flat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import au.com.bytecode.opencsv.CSVWriter;

public class TSVWriter implements AutoCloseable {
	private CSVWriter csvWriter;
	
	public TSVWriter(Writer writer) {
		this.csvWriter = new CSVWriter(writer, '\t', CSVWriter.NO_QUOTE_CHARACTER);
	}
	
	public TSVWriter(OutputStream outputStream) {
		this(new PrintWriter(outputStream));
	}

	public void writeNext(String[] buildHeader) {
		csvWriter.writeNext(buildHeader);
	}

	public void flush() throws IOException {
		csvWriter.flush();
	}

	public void close() throws IOException {
		csvWriter.close();
	}
}
