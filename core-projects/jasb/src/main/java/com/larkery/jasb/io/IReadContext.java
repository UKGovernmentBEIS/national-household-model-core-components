package com.larkery.jasb.io;

import java.util.List;
import java.util.Set;

import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public interface IReadContext extends IErrorHandler {

    <T> ListenableFuture<T> getCrossReference(final Class<T> clazz, Atom where, String identity, final Set<String> legalValues);

    <T> ListenableFuture<T> read(final Class<T> clazz, final Node node);

    <T> ListenableFuture<List<T>> readMany(final Class<T> clazz, final Iterable<Node> nodes);

    void registerIdentity(final Object o, final Node node, final ListenableFuture<String> future);

    boolean hasInvocationNamed(Node head);
}
