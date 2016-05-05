package uk.org.cse.nhm.stockimport.simple.dto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.stockimport.simple.CSV;
import uk.org.cse.stockimport.domain.schema.IKeyValue;

public class GroupedKeyValueIterator implements Iterator<List<IKeyValue>>, java.io.Closeable {
	final String[] header;
	final GroupedCSVIterator delegate;
	final Path file;
	private ImmutableList<IKeyValue> last;
	int line = 1;
	
	public String[] getHeader() {
		return header;
	}
	
	public String getKey() {
		return delegate.getKey();
	}

    private static BufferedReader openForReading(final Path file) throws IOException {
        final BOMInputStream stream = new BOMInputStream(Files.newInputStream(file));
        final String charsetName = stream.getBOMCharsetName();
        return new BufferedReader(new InputStreamReader(stream,
                                                        charsetName == null ?
                                                        "UTF-8" : charsetName));
    }

    @Override
    public void close() {
        try {
            delegate.close();
        } catch (final Exception e) {};
    }
    
	public GroupedKeyValueIterator(final Path file, final String key) throws IOException {
		this(file, openForReading(file), key);
	}
	
	public GroupedKeyValueIterator(final Path file, final BufferedReader reader, final String key) throws IOException {
		this.file = file;
		final CSV.Reader csv = CSV.trimmedReader(reader);
		header = csv.read();
		
		int keyPosition = -1;
		for (int i = 0; i<header.length; i++) {
			header[i] = header[i].toLowerCase();
			if (key.equals(header[i])) {
				if (keyPosition >= 0) {
					throw new IllegalArgumentException(String.format("In %s, key column %s repeated at index %d and %d", file, key, keyPosition, i));
				}                     
				keyPosition = i;      
			} else {                  
            }
		}
		// check the column index is OK
		Preconditions.checkElementIndex(keyPosition, header.length, 
			String.format("Column '%s' not found in %s (header was %s - %d items)", key, file, Arrays.toString(header), header.length));
		
		delegate = new GroupedCSVIterator(csv, keyPosition);
	}
	
	@Override
	public boolean hasNext() {
		return delegate.hasNext();
	}

	@Override
	public List<IKeyValue> next() {
		final List<String[]> rows = delegate.next();
		final ImmutableList.Builder<IKeyValue> builder = ImmutableList.builder();
		for (final String[] r : rows) {
			line++;
			builder.add(kv(file, header, r, line));
		}
		return this.last = builder.build();
	}

	public ImmutableList<IKeyValue> getLast() {
		return last;
	}
	
	@Override
	public void remove() {
		throw new IllegalArgumentException();
	}
	
	private static IKeyValue kv(final Path file, final String[] header, final String[] row, final int line) {
		final Map<String, String> kv = new HashMap<>();
		for (int i = 0; i<header.length && i < row.length; i++) {
			kv.put(header[i], row[i]);
		}
		
		for (int i = row.length; i<header.length; i++) {
			kv.put(header[i], "");
		}
		
		return new IKeyValue() {
			@Override
			public Set<String> getKeys() {
				return ImmutableSet.copyOf(header);
			}
			
			@Override
			public String get(final String key) {
				if (kv.containsKey(key)) {
					return kv.get(key);					
				} else {
                    return "";
				}
			}
			
			@Override
			public Path file() {
				return file;
			}
			
			@Override
			public int line() {
				return line;
			}
		};
	}
}
