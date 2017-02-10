package cse.nhm.ide.runner.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implement some of RFC1867
 */
class MultipartHelper implements AutoCloseable {
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	private static final CharSequence CRLF = "\r\n";
	private final HttpURLConnection connection;
	private final String boundary;
	private PrintWriter writer;
	private OutputStream stream;

	public MultipartHelper(HttpURLConnection connection) throws IOException {
		super();
		this.connection = connection;
		this.boundary = "A" + System.currentTimeMillis();
		this.connection.setRequestProperty(CONTENT_TYPE,
                "multipart/form-data; boundary=" + boundary);
		this.stream = connection.getOutputStream();
		this.writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8),
                                              true);
	}
	
	void addBoundary() {
		writer.append("--");
		writer.append(boundary);
		writer.append(CRLF);
	}
	
	void addHeader(String name, String value, String... values) {
		writer.append(name);
		writer.append(": ");
		writer.append(value);
		for (int i = 0; i<values.length; i+=2) {
			writer.append("; ");
			writer.append(values[i]);
			writer.append("=\"");
			writer.append(values[i+1]);
			writer.append('"');
		}
		writer.append(CRLF);	
	}
	
	public void addField(final String name, final String value) {
		addBoundary();
		addHeader(CONTENT_DISPOSITION, "form-data",
                          "name", name,
                          CONTENT_TYPE, "text/plain; charset=utf-8");
		writer.append(CRLF);
		writer.append(value);
		writer.append(CRLF);
		writer.flush();
	}

	@Override
	public void close() throws IOException {
		writer.append(CRLF);
		writer.append("--");
		writer.append(boundary);
		writer.append("--");
		writer.append(CRLF);
		writer.flush();
		writer.close();
		
		final int result = connection.getResponseCode();
		switch (result) {
		case HttpURLConnection.HTTP_ACCEPTED:
		case HttpURLConnection.HTTP_CREATED:
		case HttpURLConnection.HTTP_OK:
		case HttpURLConnection.HTTP_NO_CONTENT:
			return;
		default:
			throw new IOException("HTTP Code " + result + " unexpected from " +
						connection.getURL());
		}
	}

	public void addFile(String string, Path path) throws IOException {
		addBoundary();
		addHeader(CONTENT_DISPOSITION, "form-data", 
				"name", string, 
				"filename", string);
		addHeader(CONTENT_TYPE, "application/octet-stream");
		writer.append(CRLF);
		//addHeader("Content-Transfer-Encoding", "binary");
		// stream path onto output stream and be done with it
		writer.flush();
		byte[] bytes = new byte[4096];
		try (final InputStream is = Files.newInputStream(path)) {
			int read = -1;
			while ((read = is.read(bytes)) != -1) {
				stream.write(bytes, 0, read);
			}
		}
		stream.flush();
		writer.append(CRLF);
		writer.flush();
	}
	
	public void addFile(String string, String content) throws IOException {
		addBoundary();
		addHeader(CONTENT_DISPOSITION, "form-data", 
				"name", string, 
				"filename", string);
		addHeader(CONTENT_TYPE, "application/octet-stream");
		writer.append(CRLF);
		//addHeader("Content-Transfer-Encoding", "binary");
		// stream path onto output stream and be done with it
		writer.flush();
		stream.write(content.getBytes(StandardCharsets.UTF_8));
		stream.flush();
		writer.append(CRLF);
		writer.flush();
	}
}
