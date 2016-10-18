package com.larkery.jasb.sexp;

import com.larkery.jasb.sexp.errors.IErrorHandler;

public interface ISExpressionSource<T> {
	public ISExpression get(final T address, final IErrorHandler errors);
}
