package com.larkery.jasb.sexp.errors;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

public class JasbErrorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final List<IError> errors;

	public JasbErrorException(final IError error) {
		super(error.toString());
		this.errors = ImmutableList.of(error);
	}
	
	public JasbErrorException(final Collection<? extends IError> error) {
		super(error.toString());
		this.errors = ImmutableList.copyOf(error);
	}

	public List<IError> getErrors() {
		return errors;
	}
}
