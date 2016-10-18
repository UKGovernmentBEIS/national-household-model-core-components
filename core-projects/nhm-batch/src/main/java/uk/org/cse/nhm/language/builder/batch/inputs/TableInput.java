package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;

public class TableInput implements IBatchInputs {
	private final List<String> headers;
	private List<List<Object>> rows;

	public TableInput(List<String> headers, List<List<Object>> rows) {
		this.headers = headers;
		this.rows = rows;
	}
	
	@Override
	public Iterator<List<Object>> iterator() {
		return new Iterator<List<Object>>(){
			int row = 0;

			@Override
			public boolean hasNext() {
				return row < rows.size();
			}

			@Override
			public List<Object> next() {
				List<Object> result = rows.get(row);
				row += 1;
				return result;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not Implemented");
			}};
	}

	@Override
	public Optional<Integer> getBound() {
		return Optional.of(rows.size());
	}

	@Override
	public List<String> getPlaceholders() {
		return headers;
	}
}
