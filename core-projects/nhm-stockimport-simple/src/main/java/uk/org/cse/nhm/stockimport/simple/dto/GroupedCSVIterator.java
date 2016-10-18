package uk.org.cse.nhm.stockimport.simple.dto;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.stockimport.simple.CSV;

public class GroupedCSVIterator implements Iterator<List<String[]>>, java.io.Closeable {
	private final CSV.Reader reader;
	private final int keyColumn;
	private String[] rowBuffer;
	private String key = "_______NO_KEY";
	
	/**
	 * @param reader - this should do trimming if you want trimming
	 * @param keyColumn
	 * @throws IOException
	 */
	public GroupedCSVIterator(final CSV.Reader reader, final int keyColumn) throws IOException {
		super();
		this.reader = reader;
		this.keyColumn = keyColumn;
		this.rowBuffer = reader.read();
		Preconditions.checkElementIndex(keyColumn, rowBuffer.length, "Could not find the key column");
		this.key = rowBuffer[keyColumn];
	}

	@Override
	public boolean hasNext() {
		return rowBuffer != null;
	}

    @Override
    public void close() {
        try {
            reader.close();
        } catch (final Exception e) {}
    }

	@Override
	public List<String[]> next() {
		if (rowBuffer == null) {
			return Collections.emptyList();
		} else {
			final String keyField = rowBuffer[keyColumn];
			final ImmutableList.Builder<String[]> builder = ImmutableList.builder();
			
			builder.add(rowBuffer);
			
			try {
				while (null != (rowBuffer = reader.read())) {
					if (keyField.equals(rowBuffer[keyColumn])) {
						builder.add(rowBuffer);
					} else {
						break;
					}
				}
				if (rowBuffer != null) {
					Preconditions.checkElementIndex(keyColumn, rowBuffer.length);
				} else {
					this.key = null;
				}
				this.key = rowBuffer == null ? null : rowBuffer[keyColumn];
			} catch (final IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			
			return builder.build();
		}
	}

	public String getKey() {
		return key;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
