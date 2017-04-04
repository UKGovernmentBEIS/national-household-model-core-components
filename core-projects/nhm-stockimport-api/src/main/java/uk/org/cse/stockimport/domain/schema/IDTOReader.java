package uk.org.cse.stockimport.domain.schema;

import java.util.List;

public interface IDTOReader<Q> {
	public String getFilename();
	public void checkHeaders(final List<String> headers) throws InvalidHeaderException;
	public Q read(final IKeyValue row) throws InvalidRowException;
	public boolean isRequired();
}
