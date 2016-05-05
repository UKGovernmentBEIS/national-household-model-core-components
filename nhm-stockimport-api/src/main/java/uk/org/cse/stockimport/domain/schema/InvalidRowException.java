package uk.org.cse.stockimport.domain.schema;

public class InvalidRowException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRowException(final String message, final IllegalArgumentException iae) {
		super(message, iae);
	}

}
