package uk.org.cse.stockimport.domain.schema;

import java.util.List;

import com.google.common.base.Joiner;

public class InvalidHeaderException extends Exception {
	private static final long serialVersionUID = 1L;
	private final List<String> errors;

	public InvalidHeaderException(final List<String> errors) {
		super(Joiner.on(", ").join(errors));
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
}
