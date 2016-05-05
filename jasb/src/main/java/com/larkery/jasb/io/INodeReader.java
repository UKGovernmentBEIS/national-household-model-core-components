package com.larkery.jasb.io;

import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.sexp.Node;

/**
 * An interface for making S-Expressions into things of type T
 * 
 * This will be code-generated when you set up a reader.
 * 
 * @param <T>
 */
public interface INodeReader<T> {
	public ListenableFuture<T> read(final IReadContext context, final Node node);
}
