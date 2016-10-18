package com.larkery.jasb.sexp.parse;

import java.io.IOException;
import java.io.Reader;

import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public interface IDataSource<T> {
	public T root();
	public T resolve(final Seq relation, IErrorHandler errors);
	public Reader open(final T resolved) throws IOException;
}
