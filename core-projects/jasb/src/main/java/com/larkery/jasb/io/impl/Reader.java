package com.larkery.jasb.io.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.bind.AfterReading;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.io.IAtomReader;
import com.larkery.jasb.io.IReadContext;
import com.larkery.jasb.io.IReader;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.ILocated;
import com.larkery.jasb.sexp.errors.UnexpectedTermError;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

class Reader implements IReader {
	private final Map<Class<?>, Switcher<?>> switchers = new HashMap<>();
	private final Map<Class<?>, InvocationReader<?>> specificReaders = new HashMap<>();
	private final Set<Class<?>> boundClasses;
	private final Set<? extends IAtomReader> atomReaders;
	private final Set<String> allBoundNames;
	
	public Reader(final Set<Class<?>> concrete, final Set<? extends IAtomReader> atomReaders) {
		super();
		
		checkConsistency(concrete, atomReaders);
		
		this.boundClasses = concrete;
		this.atomReaders = atomReaders;
		
		final ImmutableSet.Builder<String> strings = ImmutableSet.builder();
		for (final Class<?> clazz : this.boundClasses) {
			if (clazz.isAnnotationPresent(Bind.class)) {
				strings.add(clazz.getAnnotation(Bind.class).value());
			}
		}
		allBoundNames = strings.build();
	}
	
	public IReadContext getContext(final IErrorHandler delegate) {
		return new Context(delegate);
	}

	private static class Result<T> implements IResult<T> {
		private final Node node;
		private final Optional<T> value;
		private final Map<String, Object> crossReferences;
		
		public Result(final Node node, final Optional<T> value, final Map<String, Object> crossReferences) {
			super();
			this.node = node;
			this.value = value;
			this.crossReferences = crossReferences;
		}

		@Override
		public Node getNode() {
			return node;
		}
		
		@Override
		public Optional<T> getValue() {
			return value;
		}
		
		@Override
		public Map<String, Object> getCrossReferences() {
			return crossReferences;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.larkery.jasb.io.impl.IReader#read(java.lang.Class, com.larkery.jasb.sexp.ISExpression, com.larkery.jasb.sexp.errors.IErrorHandler)
	 */
	@Override
	public <T> IResult<T> read(final Class<T> output, final ISExpression input, final IErrorHandler errors) {
		Node node = null;
		try {
			node = Node.copyStructure(input);
		} catch (final UnfinishedExpressionException e) {
		}
		
		final Map<String, Object> xrefs = new HashMap<String, Object>();
		final Optional<T> readNode = readNode(output, node, errors, xrefs);
		return new Result<T>(node, readNode, xrefs);
	}
	
	public <T> Optional<T> readNode(final Class<T> output, final Node input, final IErrorHandler errors, final Map<String, Object> crossReferences) {
		if (input == null) return Optional.absent();
		
		final Context context = new Context(errors);
		
		final ListenableFuture<T> read = context.read(output, input);
		
		for (final Map.Entry<Atom, Set<String>> error : context.unresolved.entrySet()) {
            errors.handle(new UnexpectedTermError(error.getKey(),
                                                  "word",
                                                  Sets.union(error.getValue(), context.resolver.getDefinedNames()),
                                                  error.getKey().getValue()));
		}
		
		if (crossReferences != null) {
			crossReferences.putAll(context.resolver.getDefinitions());
		}
		
		if (read.isDone()) {
			try {
				return Optional.fromNullable(read.get());
			} catch (InterruptedException | ExecutionException e) {
				return Optional.absent();
			}
		} else {
			return Optional.absent();
		}
	}
	
	@Override
	public <T> Optional<T> readNode(final Class<T> output, final Node input, final IErrorHandler errors) {
		return readNode(output, input, errors, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void checkConsistency(final Set<Class<?>> classes, final Set<? extends IAtomReader> atomReaders) {
		for (final Class<?> clazz : classes) {
			if (Modifier.isAbstract(clazz.getModifiers())) {
				throw new IllegalArgumentException(clazz + " cannot be unmarshalled from s-expressions as it is abstract");
			}
			if (clazz.getEnclosingClass() != null) {
				if (!Modifier.isStatic(clazz.getModifiers())) {
					throw new IllegalArgumentException(clazz + " cannot be unmarshalled from s-expressions as it is a non-static inner class");
				}
			}
			if (!(clazz.isAnnotationPresent(Bind.class))) {
				throw new IllegalArgumentException(clazz + " has no bind annotation");
			}
			try {
				final Constructor<?> constructor = clazz.getConstructor();
				
				final Object o;
				try {
					o = constructor.newInstance();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					throw new IllegalArgumentException("Constructing " + clazz
							+ " causes an error", e);
				}
				
				for (final Method m : clazz.getMethods()) {
					if (m.isAnnotationPresent(AfterReading.class)) {
						if (m.getReturnType() != Void.TYPE) {
							throw new IllegalArgumentException("AfterReading method " + m + " is not void");
						}
						if (m.getParameterTypes().length != 1
								|| !m.getParameterTypes()[0].equals(Node.class)) {
							throw new IllegalArgumentException("AfterReading method " + m + " should take a single Node as its argument");
						}
						if (!Modifier.isPublic(m.getModifiers())) {
							throw new IllegalArgumentException("AfterReading method " + m + " is not public");
						}
						if (Modifier.isStatic(m.getModifiers())) {
							throw new IllegalArgumentException("AfterReading method " + m + " is static");
						}
						if (m.getDeclaringClass().isInterface()) {
							throw new IllegalArgumentException("AfterReading method " + m + " is in an interface");
						}
					}
				}
				
				final Set<JasbPropertyDescriptor> descriptors = JasbPropertyDescriptor.getDescriptors(clazz);
				for (final JasbPropertyDescriptor pd : descriptors) {
					if (pd.isMultiple) {
						Object initialValue;
						try {
							initialValue = pd.readMethod.invoke(o);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							throw new IllegalArgumentException("Invoking the read method for " + pd + " on clazz caused an error", e);
						}
						if (initialValue instanceof List) {
							try {
								((List) initialValue).add(null);
							} catch (final Throwable th) {
								throw new IllegalArgumentException("Had an error adding a null to the list produced by " + pd + " in " + clazz, th);
							}
						} else {
							throw new IllegalArgumentException(pd + " in " + clazz + " does not produce a list, but " + initialValue);
						}
					}
					
					boolean canRead = false;
					
					if (Node.class.isAssignableFrom(pd.boxedPropertyType)) {
						canRead = true;
					}
					
					for (final IAtomReader reader : atomReaders) {
						if (reader.canReadTo(pd.boxedPropertyType)) {
							canRead = true;
							break;
						}
					}
					
					if (!canRead && pd.boxedPropertyType.isAnnotationPresent(Bind.class)) {
						canRead = true;
					}
					
					if (!canRead) {
						for (final Class<?> possible : classes) {
							if (pd.boxedPropertyType.isAssignableFrom(possible)) {
								canRead = true;
								//TODO check for stupid wrapper types here
								break;
							}
						}
					}
					
					if (!canRead) {
						throw new IllegalArgumentException(pd + " in " + clazz + 
								" has no legal values in the set of input classes or supported atom types");
					}
				}
				
				
			} catch (NoSuchMethodException | SecurityException e) {
				throw new IllegalArgumentException(clazz + " does not have an accessible no-args constructor");
			}
		}
	}

	class Context implements IReadContext {
		private final IErrorHandler delegateErrorHandler;
		private final Resolver resolver = new Resolver();
		private final Map<Atom, Set<String>> unresolved = new IdentityHashMap<Atom, Set<String>>();
		
		Context(final IErrorHandler delegateErrorHandler) {
			super();
			this.delegateErrorHandler = delegateErrorHandler;
		}

		@Override
		public void handle(final IError error) {
			delegateErrorHandler.handle(error);
		}
		
		@Override
		public void error(final ILocated location, final String format, final Object... interpolate) {
			delegateErrorHandler.error(location, format, interpolate);
		}
		
		@Override
		public void warn(final ILocated location, final String format, final Object... interpolate) {
			delegateErrorHandler.warn(location, format, interpolate);
		}

		@Override
		public <T> ListenableFuture<T> getCrossReference(final Class<T> clazz, final Atom where, final String identity, final Set<String> legalValues) {
			final ListenableFuture<T> resolve = resolver.resolve(where, identity, clazz);
			
			unresolved.put(where, legalValues);
			
			Futures.addCallback(resolve, new FutureCallback<T>() {
				@Override
				public void onSuccess(final T result) {
					unresolved.remove(where);
				}

				@Override
				public void onFailure(final Throwable t) {
					handle(BasicError.at(where, t.getMessage()));
				}
			});
			
			return resolve;
		}

		@Override
		public <T> ListenableFuture<T> read(final Class<T> clazz, final Node node) {
			if (clazz.isInstance(node)) {
				return Futures.immediateFuture(clazz.cast(node));
			} else {
				return Reader.this.getSwitcher(clazz).read(this, node);
			}
		}
		
		@Override
		public <T> ListenableFuture<List<T>> readMany(final Class<T> clazz, final Iterable<Node> nodes) {
			final ImmutableList.Builder<ListenableFuture<T>> futures = ImmutableList.builder();
			for (final Node node : nodes) {
				if (node instanceof Comment) continue;
				futures.add(read(clazz, node));
			}
			return Futures.allAsList(futures.build());
		}
		
		@Override
		public void registerIdentity(final Object o, final Node definingNode, final ListenableFuture<String> future) {
			Futures.addCallback(future, new FutureCallback<String>() {
				@Override
				public void onSuccess(final String result) {
					try {
						resolver.define(result, o);
					} catch (final IllegalArgumentException exception) {
						// produce an error
						handle(BasicError.at(definingNode, exception.getMessage()));
					}
				}
				
				@Override
				public void onFailure(final Throwable t) {}
			});
		}
		
		@Override
		public boolean hasInvocationNamed(final Node head) {
			if (head instanceof Atom) {
				final String val = ((Atom) head).getValue();
				return allBoundNames.contains(val);
			} else {
				return false;
			}
		}
	}

	private <T> Switcher<T> getSwitcher(final Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Switcher<T> out = (Switcher<T>) switchers.get(clazz);
		
		if (out == null) {
			out = new Switcher<>(
					clazz, 
					createReaders(clazz), 
					createAtomReader(clazz)
					);
			switchers.put(clazz, out);
		}
		
		return out;
	}
	
	@SuppressWarnings("unchecked")
	private <T> Set<InvocationReader<? extends T>> createReaders(final Class<T> clazz) {
		final ImmutableSet.Builder<InvocationReader<? extends T>> builder = ImmutableSet.builder();
		for (final Class<?> sub : boundClasses) {
			if (clazz.isAssignableFrom(sub)) {
				//TODO check not abstract etc
				builder.add((InvocationReader<? extends T>) getOrCreateInvocationReader(sub));
			}
		}
		return builder.build();
	}
	
	@SuppressWarnings("unchecked")
	private <T> InvocationReader<T> getOrCreateInvocationReader(final Class<T> sub) {
		if (!specificReaders.containsKey(sub)) {
			final InvocationReader<T> reader = new InvocationReaderLoader<T>(sub).getReaderInstance();
			specificReaders.put(sub, reader);
		}
		return (InvocationReader<T>) specificReaders.get(sub);
	}

	private <T> MultiAtomReader<T> createAtomReader(final Class<T> clazz) {
		final ImmutableSet.Builder<IAtomReader> readers = ImmutableSet.builder();
		
		final ImmutableSet.Builder<Class<?>> fallbackClasses = ImmutableSet.builder();
		for (final Class<?> sub : boundClasses) {
			if (clazz.isAssignableFrom(sub)) {
				fallbackClasses.add(sub);
			}
		}
		
		for (final IAtomReader reader : atomReaders) {
			if (reader.canReadTo(clazz)) {
				readers.add(reader);
			}
		}
		return new MultiAtomReader<T>(clazz, readers.build(), fallbackClasses.build());
	}
}
