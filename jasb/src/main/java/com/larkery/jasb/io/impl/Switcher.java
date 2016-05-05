package com.larkery.jasb.io.impl;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.io.INodeReader;
import com.larkery.jasb.io.IReadContext;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.UnexpectedTermError;


/**
 * Subtypes switch on different options.
 * @author hinton
 *
 * @param <T>
 */
class Switcher<T> implements INodeReader<T> {
	private static final String CROSS_REFERENCE_PREFIX = "#";
	private final Map<String, InvocationReader<? extends T>> readersByName;
	private final Class<T> clazz;
	private final MultiAtomReader<T> atoms;
	
	protected Switcher(final Class<T> clazz, final Set<InvocationReader<? extends T>> readers, final MultiAtomReader<T> atoms) {
		this.clazz = clazz;
		this.atoms = atoms;
		final ImmutableMap.Builder<String, InvocationReader<? extends T>> builder = ImmutableMap.builder();
		for (final InvocationReader<? extends T> reader : readers) {
			builder.put(reader.getName(), reader);
		}
		readersByName = builder.build();
	}
	
	@Override
	public ListenableFuture<T> read(final IReadContext context, final Node node) {
		if (node instanceof Seq) {
			// this ought to be an invocation
			final Invocation invocation = Invocation.of(node, context);
			if (invocation != null) {
				return readInvocation(context, node, invocation);
			} else {
				return death("Malformed invocation");
			}
		} else if (node instanceof Atom) {
			// this is a value or a reference
			final Atom atom = (Atom) node;
			if (atom.getValue().startsWith(CROSS_REFERENCE_PREFIX)) {
				return context.getCrossReference(
						clazz,
						atom,
						atom.getValue().substring(CROSS_REFERENCE_PREFIX.length()),
                        ImmutableSet.<String>of());
			} else {
				return atoms.read(context, atom);
			}
		} else {
			return death("Unexpected node type " + node.getClass().getSimpleName());
		}
	}

	private ListenableFuture<T> death(final String string) {
		return Futures.immediateFailedFuture(new IllegalArgumentException(string));
	}

	private ListenableFuture<T> readInvocation(final IReadContext context, final Node node, final Invocation invocation) {
		if (readersByName.containsKey(invocation.name)) {
			return Futures.immediateFuture(clazz.cast(readersByName.get(invocation.name).read(context, invocation)));
		} else {
            // at this point it would be nice to work out what things can contain the given name
			context.handle(new UnexpectedTermError(node, "command", readersByName.keySet(), invocation.name));
			return death("Expected " + readersByName.keySet() + ", not " + invocation.name +" - maybe your Bind annotated class is not in the set presented to the Reader?");
		}
	}
}
