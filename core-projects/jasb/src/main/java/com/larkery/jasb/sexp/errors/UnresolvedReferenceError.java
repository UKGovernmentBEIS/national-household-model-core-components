package com.larkery.jasb.sexp.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Location;

public class UnresolvedReferenceError extends BasicError {

	public UnresolvedReferenceError(final Atom reference) {
		super(reference.getLocation(), String
				.format("could not find any element for reference "
						+ reference.getValue()), Type.ERROR);
	}

	@JsonCreator
	public UnresolvedReferenceError(
			@JsonProperty("location") final Location location, 
			@JsonProperty("message") final String message,
			@JsonProperty("type") final Type type) {
		super(location, message, type);
	}
}
