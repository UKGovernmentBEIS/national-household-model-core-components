package uk.org.cse.nhm.stockimport.simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class Metadata {
	public static final String PATH = "metadata.csv";
	
	public final Map<String, String> values;
	
	Metadata(final Map<String, String> values) {
		super();
		this.values = values;
	}

	public static Metadata load(final BufferedReader br) throws IOException {
		final CSV.Reader reader = CSV.trimmedReader(br);
		String[] row;
		
		final ImmutableMap.Builder<String, String> values = ImmutableMap.builder(); 
		
		while ((row = reader.read()) != null) {
			if (row.length >= 2) {
				values.put(row[0], row[1]);
			}
		}
		
		return new Metadata(values.build());
	}
	
	public static Metadata load(final Path resolve) throws IOException {
		try (final BufferedReader br = Util.open(resolve)) {
			return load(br);
		}
	}

	public void save(final Path resolve) throws IOException {
		try (final BufferedWriter bw = Files.newBufferedWriter(resolve, StandardCharsets.UTF_8)) {
			final CSV.Writer w = CSV.writer(bw);
			for (final Map.Entry<String, String> e : values.entrySet()) {
				w.write(new String[] {e.getKey(), e.getValue()});
			}
		}
	}

	public Metadata replace(final String prefix, final String string, final String string2) {
		final Map<String, String> newValues = new HashMap<String, String>(values);
		
		shift(newValues, prefix, string, string2);
		
		return new Metadata(ImmutableMap.copyOf(newValues));
	}

	private static void shift(final Map<String, String> newValues, final String prefix, final String key, final String value) {
		if (newValues.containsKey(key)) {
			final String oldValue = newValues.get(key);
			shift(newValues, prefix, prefix + key, oldValue);
		}
		newValues.put(key, value);
	}
}
