package com.larkery.jasb.io.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.cglib.asm.ClassWriter;
import net.sf.cglib.asm.FieldVisitor;
import net.sf.cglib.asm.Label;
import net.sf.cglib.asm.MethodVisitor;
import net.sf.cglib.asm.Opcodes;
import net.sf.cglib.asm.Type;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.bind.AfterReading;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.io.IReadContext;
import com.larkery.jasb.io.impl.JasbPropertyDescriptor.BoundTo;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

/**
 * This is a class which generates subclasses of {@link InvocationReader} which handle
 * individual mapped types. What each generated class does is implement the abstract read
 * method and (a) new up the mapped type and then (b) generate callbacks for all its fields
 * so that they get set in the end.
 * 
 * The consequences of doing this with codegeneration rather than reflection are good and bad;
 * 
 * good: it's much faster, and stack traces are a lot easier to follow because you can see
 * 		 what kind of thing is being constructed rather than getting a lot of recursive calls to 
 *		 apparently identical methods which have different runtime behaviour
 *  bad: it's a bit harder to maintain, and it makes various methods not refactor-safe in some
 *  	 other classes, specifically {@link Invocation}, {@link IReadContext} and {@link InvocationReader} 
 * 
 * @author hinton
 *
 * @param <T>
 */
class InvocationReaderLoader<T> extends ClassLoader implements Opcodes {
	private static final String THIS = "this";
	private static final String GET = "get";
	private static final String TARGET = "target";
	private static final String ADD_CALLBACK = "addCallback";
	private static final String READ = "read";
	private static final String READ_ONE_OR_MANY = "readOneOrMany";
	private static final String ARGUMENTS = "arguments";
	private static final String INIT = "<init>";
	private static final int SELF_SLOT = 0;
	private static final int CONTEXT_SLOT = 1;
	private static final int INVOCATION_SLOT = 2;
	private static final int RESULT_SLOT = 3;

	private final Class<T> typeToRead;
	private final String generatedClassName;
	private final String generatedClassInternalName;
	private final String generatedClassDescriptor;
	private final String readTypeDescriptor;
	private final String name;
	private final Class<? extends InvocationReader<T>> constructorClass;
	
	private final Map<String, byte[]> unloadedClasses = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public InvocationReaderLoader(final Class<T> typeToRead) {
		super(typeToRead.getClassLoader());
		this.typeToRead = typeToRead;
		
		final Bind bind = typeToRead.getAnnotation(Bind.class);
		if (bind == null) {
			throw new IllegalArgumentException(typeToRead + " has no Bind annotation");
		}
		this.name = bind.value();
		
		this.generatedClassName = "ReaderFor" + 
		CharMatcher.JAVA_LETTER_OR_DIGIT.retainFrom(
				bind.value() )
		
		+ "A" + typeToRead.getSimpleName();
		this.generatedClassInternalName = 
				getClass().getPackage().getName().replace('.', '/') +
				"/" + generatedClassName;
		this.generatedClassDescriptor = "L" + this.generatedClassInternalName + ";";
		
		this.readTypeDescriptor = Type.getDescriptor(typeToRead);
		
		final Set<JasbPropertyDescriptor> properties = JasbPropertyDescriptor.getDescriptors(typeToRead);
		
		final ImmutableMap.Builder<JasbPropertyDescriptor, String> listenerClasses = ImmutableMap.builder();
		for (final JasbPropertyDescriptor property : properties) {
			final String futureListenerClass = declareFutureListenerClass(property);
			listenerClasses.put(property, futureListenerClass);
		}
		
		this.constructorClass = (Class<? extends InvocationReader<T>>) declareConstructorClass(properties, listenerClasses.build());
	}

	/**
	 * Defines the class which will actually construct a thing of the desired type
	 * 
	 * @param properties
	 * @param listenerClassesByProperty
	 * @return
	 */
	private Class<?> declareConstructorClass(
			final Set<JasbPropertyDescriptor> properties, 
			final ImmutableMap<JasbPropertyDescriptor, String> listenerClassesByProperty) {
		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
		
		cw.visit(V1_7, 
				ACC_PUBLIC+ACC_SUPER,
				generatedClassInternalName, // our internal name
				// our signature, which is InvocationReader<ReadType>
				"L" + Type.getInternalName(InvocationReader.class) + "<"
						+ readTypeDescriptor+">;",
				// our superclass
				Type.getInternalName(InvocationReader.class),
				null);
		
		cw.visitSource(getClass().getSimpleName()+ ".java", null);
		
		addInnerClasses(cw, listenerClassesByProperty.values());
		addConstructor(cw, properties);
		addReadMethod(cw, properties, listenerClassesByProperty);
		
		cw.visitEnd();
		
		final byte[] byteArray = cw.toByteArray();
		unloadedClasses.put(generatedClassInternalName.replace('/', '.'), byteArray);
		
		try {
			return loadClass(generatedClassInternalName.replace('/', '.'));
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		if (unloadedClasses.containsKey(name)) {
			final byte[] bytes = unloadedClasses.get(name);
			return defineClass(name, bytes, 0, bytes.length);
		} else {
			return super.findClass(name);
		}
	}

	/**
	 * Adds the default constructor, which is anonymous
	 * @param cw
	 * @param properties
	 */
	private void addConstructor(final ClassWriter cw, final Set<JasbPropertyDescriptor> properties) {
		final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, INIT, "()V", null, null);
		mv.visitCode();
		
		// what we want to do first is invoke our super constructor
		
		final Label start = new Label();
		mv.visitLabel(start);

		// empty stack
		mv.visitVarInsn(ALOAD, 0);
		// this
		mv.visitLdcInsn(Type.getType(typeToRead));
		// this, type
		mv.visitLdcInsn(name);
		// this, type, name
		final Set<String> keys = new HashSet<>();
		for (final JasbPropertyDescriptor pd : getPropertiesBoundTo(BoundTo.Name, properties)) {
			keys.add(pd.key.get());
		}
		
		mv.visitIntInsn(BIPUSH, keys.size());
		// this, type, name, keys.size()
		mv.visitTypeInsn(ANEWARRAY, Type.getInternalName(String.class));
		// this, type, name, String[keys.size()]
		int i = 0;
		for (final String s : keys) {
			mv.visitInsn(DUP);
			// this type name, string[], string[]
			mv.visitIntInsn(BIPUSH, i);
			// this type name string[] string[] i
			mv.visitLdcInsn(s);
			// this type name string[] string[] i s
			mv.visitInsn(AASTORE);
			// this type name string[]
			i++;
		}
		
		// invoke super constructor (InvocationReader.<init>(this, type, name, keywords))
		final String initSignature = 
				Type.getMethodDescriptor(Type.getType(Void.TYPE), 
						new Type[] {Type.getType(Class.class), Type.getType(String.class), Type.getType(String[].class)});
		
		mv.visitMethodInsn(
				INVOKESPECIAL,
				Type.getInternalName(InvocationReader.class), 
				INIT,
				initSignature);
		// done
		mv.visitInsn(RETURN);
		final Label end = new Label();
		mv.visitLabel(end);

		// declare that we have a local variable called this with type this' type
		mv.visitLocalVariable(THIS, generatedClassDescriptor, null, start, end, 0);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
	}

	/**
	 * If the type we are reading is an inner class, we need to register it as an inner class.
	 * @param cw
	 */
	private void addInnerClassForTypeToRead(final ClassWriter cw) {
		// first of all, if our friend is himself an inner class, here we go
		if (typeToRead.getEnclosingClass() != null) {
			if (!(Modifier.isPublic(typeToRead.getModifiers()) 
					&& Modifier.isStatic(typeToRead.getModifiers()))) {
				throw new IllegalArgumentException(typeToRead + " is a non-static inner class, which I cannot construct");
			} else {
				cw.visitInnerClass(
						Type.getInternalName(typeToRead),
						generatedClassInternalName, 
						typeToRead.getSimpleName(), 
						ACC_PUBLIC + ACC_STATIC);
			}
		}
	}
	
	private void addInnerClasses(final ClassWriter cw, final ImmutableCollection<String> listenerClasses) {
		addInnerClassForTypeToRead(cw);
		// next, we need to visit any inner classes that we created for use
		// when setting properties with a callback.
		for (final String clazz : listenerClasses) {
			cw.visitInnerClass(
					this.generatedClassInternalName + "$" + clazz,
					generatedClassInternalName,
					clazz,
					ACC_STATIC
					);
		}
	}
	
	private void addReadMethod(
			final ClassWriter cw, 
			final Set<JasbPropertyDescriptor> properties,
			final ImmutableMap<JasbPropertyDescriptor, String> listenerClassesByProperty) {		
		final MethodVisitor mv = 
				cw.visitMethod(ACC_PROTECTED, 
						READ, 
						Type.getMethodDescriptor(
								Type.getType(typeToRead),
								new Type[] {
									Type.getType(IReadContext.class),
									Type.getType(Invocation.class)
								}),						
						null, null);

		
		mv.visitCode();
		
		final Label methodStart = new Label();
		mv.visitLabel(methodStart);
		// construct a new [type]
		mv.visitTypeInsn(NEW, Type.getInternalName(typeToRead));
		// dup it
		mv.visitInsn(DUP);
		// invoke constuctor (pops a dup)
		mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(typeToRead), INIT, "()V");
		// store constructed thing into local variable
		mv.visitVarInsn(ASTORE, RESULT_SLOT);
		
		// now we want to generate the logic which does all the properties.
		final List<Local> locals = addReadMethodBody(mv, properties, listenerClassesByProperty);
		
		mv.visitVarInsn(ALOAD, RESULT_SLOT);
		
		mv.visitInsn(ARETURN);
		final Label methodEnd = new Label();
		mv.visitLabel(methodEnd);
		mv.visitLocalVariable(THIS, generatedClassDescriptor, null, methodStart, methodEnd, SELF_SLOT);
		mv.visitLocalVariable("context", Type.getDescriptor(IReadContext.class), null, methodStart, methodEnd, CONTEXT_SLOT);
		mv.visitLocalVariable("invocation", Type.getDescriptor(Invocation.class), null, methodStart, methodEnd, INVOCATION_SLOT);
		mv.visitLocalVariable("result", Type.getDescriptor(typeToRead), null, methodStart, methodEnd, RESULT_SLOT);
		for (final Local local : locals) {
			mv.visitLocalVariable(local.name, 
					Type.getDescriptor(local.type),
					null,
					local.from,
					local.to,
					local.position);
		}
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		// generate bridging method
		final MethodVisitor mv2 = cw.visitMethod(
				ACC_PROTECTED + ACC_BRIDGE + ACC_SYNTHETIC,
				READ,
				Type.getMethodDescriptor(Type.getType(Object.class), 
						new Type[] {
							Type.getType(IReadContext.class),
							Type.getType(Invocation.class)
						}),
				null, null);
		
		mv2.visitVarInsn(ALOAD, 0);
		mv2.visitVarInsn(ALOAD, 1);
		mv2.visitVarInsn(ALOAD, 2);
		mv2.visitMethodInsn(INVOKEVIRTUAL, 
				this.generatedClassInternalName,
				READ,
				Type.getMethodDescriptor(
						Type.getType(typeToRead),
						new Type[] {
							Type.getType(IReadContext.class),
							Type.getType(Invocation.class)
						}));
		
		mv2.visitInsn(ARETURN);
		mv2.visitMaxs(0, 0);
		mv2.visitEnd();
	}
	
	static class Local {
		public final String name;
		public final Label from;
		public final Label to;
		public final Class<?> type;
		public final int position;
		Local(final String name, final Label from, final Label to, final Class<?> type, final AtomicInteger position) {
			super();
			this.name = name;
			this.from = from;
			this.to = to;
			this.type = type;
			this.position = position.getAndIncrement();
		}
	}
	
	private Set<JasbPropertyDescriptor> getPropertiesBoundTo(final JasbPropertyDescriptor.BoundTo t, final Iterable<JasbPropertyDescriptor> properties) {
		final ImmutableSet.Builder<JasbPropertyDescriptor> out = ImmutableSet.builder();
		for (final JasbPropertyDescriptor p : properties) {
			if (p.boundTo == t) out.add(p);
		}
		return out.build();
	}
	
	private List<Local> addReadMethodBody(
			final MethodVisitor mv,
			final Set<JasbPropertyDescriptor> properties, 
			final ImmutableMap<JasbPropertyDescriptor, String> listenerClassesByProperty) {
		final AtomicInteger localCounter = new AtomicInteger(RESULT_SLOT + 1);
		final ImmutableList.Builder<Local> locals = ImmutableList.builder();
		
		// next we should invoke the special information method if there is one
		
		for (final Method method : typeToRead.getMethods()) {
			if (method.isAnnotationPresent(AfterReading.class)) {
				// what do we pass to this? the node which made it?
				// that seems reasonable.
				mv.visitVarInsn(ALOAD, RESULT_SLOT);
				// S: result
				mv.visitVarInsn(ALOAD, INVOCATION_SLOT);
				// S: result invocation
				mv.visitFieldInsn(GETFIELD, 
						Type.getInternalName(Invocation.class),
						"node",
						Type.getDescriptor(Seq.class));
				
				// S: result invocation.node
				mv.visitMethodInsn(
						INVOKEVIRTUAL, 
						Type.getInternalName(typeToRead),
						method.getName(), 
						Type.getMethodDescriptor(
								Type.getType(Void.TYPE),
								new Type[] {Type.getType(Node.class)}));
				// S: empty
				
				break;
			}
		}
		
		// next we want to do all the bound properties; if one of these is an identity it will be handled in there
		
		final Set<JasbPropertyDescriptor> namedProperties = getPropertiesBoundTo(BoundTo.Name, properties);
		final Set<JasbPropertyDescriptor> indexedProperties = getPropertiesBoundTo(BoundTo.Position, properties);
		final Set<JasbPropertyDescriptor> overflow = getPropertiesBoundTo(BoundTo.Remainder, properties);
		
		createCallbacksForNamedProperties(mv, listenerClassesByProperty, localCounter, locals, namedProperties);
		
		createCallbacksForIndexedProperties(mv, listenerClassesByProperty, localCounter, locals, indexedProperties, overflow);
		
		return locals.build();
	}

	private void createCallbacksForIndexedProperties(
			final MethodVisitor mv,
			final ImmutableMap<JasbPropertyDescriptor, String> listenerClassesByProperty,
			final AtomicInteger localCounter,
			final ImmutableList.Builder<Local> locals,
			final Set<JasbPropertyDescriptor> indexedProperties,
			final Set<JasbPropertyDescriptor> overflow) {
		if (indexedProperties.isEmpty() == false || overflow.isEmpty() == false) {
			final Label startIndexed = new Label();
			
			final Label endIndexed = new Label();
			final Local remainder = new Local("remainder", startIndexed, endIndexed, List.class, localCounter);
			locals.add(remainder);
			
			mv.visitLabel(startIndexed);
			// remainder = invocation.remainder
			mv.visitVarInsn(ALOAD, INVOCATION_SLOT);
			mv.visitFieldInsn(GETFIELD, Type.getInternalName(Invocation.class), "remainder", Type.getDescriptor(List.class));
			mv.visitVarInsn(ASTORE, remainder.position);
			
			int lastIndexed = 0;
			for (final JasbPropertyDescriptor indexed : indexedProperties) {
				final Label startIndexedLocal = new Label();
				final Label endIndexedLocal = new Label();
				
				final Local indexedLocal = new Local("indexed" + indexed.position.get(), startIndexedLocal, endIndexedLocal, Node.class, localCounter);
				locals.add(indexedLocal);
				
				lastIndexed = Math.max(lastIndexed, indexed.position.get() + 1);
				
				mv.visitVarInsn(ALOAD, remainder.position);	
				// remainder
				mv.visitIntInsn(BIPUSH, indexed.position.get());
				// remainder, index
				mv.visitMethodInsn(INVOKESTATIC, 
						Type.getInternalName(InvocationReader.class),
						"getNodeOrNull",
						Type.getMethodDescriptor(Type.getType(Node.class), 
								new Type[] {
									Type.getType(List.class),
									Type.getType(int.class)
									}
								));
				// [node|null] on stack
				mv.visitLabel(startIndexedLocal);
				mv.visitVarInsn(ASTORE, indexedLocal.position); // indexedLocal = node || null
				mv.visitVarInsn(ALOAD, indexedLocal.position);
				// indexedLocal
				final Label skip = new Label();
				mv.visitJumpInsn(IFNULL, skip);
				
				// create the future for it
				readSingleOrMultiValued(mv, indexed, indexedLocal);
				connectIdentifierProperty(mv, indexed, indexedLocal);
				mv.visitLabel(endIndexedLocal);
				// create the callback
				constructNewListener(mv, listenerClassesByProperty.get(indexed));
				
				// hook up the callback with the future
				connectFutureToCallback(mv);
				
				mv.visitLabel(skip);
			}
			
			// so we have done all the indexed properties
			if (overflow.size() > 0) {
				final JasbPropertyDescriptor varargs = overflow.iterator().next();
				// to handle the remaining args, we use a superclass method
				if (varargs.isListOfLists) {
					mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
					// S: context
					mv.visitLdcInsn(Type.getType(varargs.propertyType));
					// S: context, , property type
					mv.visitVarInsn(ALOAD, remainder.position);	
					// load remainder, which is the list of remaining unused arguments to read
					// S: context, , property, remainder list
					mv.visitIntInsn(BIPUSH, lastIndexed);
					// load position of the last indexed argument,
					// i.e. where in the remainder list to start
					// reading from
					mv.visitMethodInsn(INVOKESTATIC, 
									   Type.getInternalName(InvocationReader.class), 
									   "readListsRemainder", 
									   Type.getMethodDescriptor(Type.getType(ListenableFuture.class), 
																new Type[]{
																	Type.getType(IReadContext.class),
																	Type.getType(Class.class),
																	Type.getType(List.class),
																	Type.getType(int.class)
																}
																));
				} else {
					// S: 
					mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
					//  context
					mv.visitLdcInsn(Type.getType(varargs.propertyType));
					// context, type
					mv.visitVarInsn(ALOAD, remainder.position);	
					//S:  context, type, remainder
					mv.visitIntInsn(BIPUSH, lastIndexed);
					//S: context, type, remainder, maxindex
					mv.visitMethodInsn(INVOKESTATIC, 
									   Type.getInternalName(InvocationReader.class), 
									   "readRemainder", 
									   Type.getMethodDescriptor(Type.getType(ListenableFuture.class), 
																new Type[]{
																	Type.getType(IReadContext.class),
																	Type.getType(Class.class),
																	Type.getType(List.class),
																	Type.getType(int.class)
																}
																));
				}
				
				// S: future
				constructNewListener(mv, listenerClassesByProperty.get(varargs));
				// S: future, callback
				connectFutureToCallback(mv);
				// S: 
			} else {
				// we need to check that there are no extra things
				// beyond what we hoped
				mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
				mv.visitVarInsn(ALOAD, remainder.position);
				mv.visitIntInsn(BIPUSH, lastIndexed);
				mv.visitMethodInsn(INVOKESTATIC, 
						Type.getInternalName(InvocationReader.class), 
						"warnOnUnusedPositions", 
						Type.getMethodDescriptor(Type.getType(Void.TYPE), 
								new Type[]{
								Type.getType(IReadContext.class),
								Type.getType(List.class),
								Type.getType(int.class)
							}
						));
			}
			
			mv.visitLabel(endIndexed);
		} else {
			final Label startIndexed = new Label();
			final Label endIndexed = new Label();
			final Local remainder = new Local("remainder", startIndexed, endIndexed, List.class, localCounter);
			locals.add(remainder);
			
			mv.visitLabel(startIndexed);
			// remainder = invocation.remainder
			mv.visitVarInsn(ALOAD, INVOCATION_SLOT);
			mv.visitFieldInsn(GETFIELD, Type.getInternalName(Invocation.class), "remainder", Type.getDescriptor(List.class));
			mv.visitVarInsn(ASTORE, remainder.position);
			
			// we need to check that there are no extra things
			// beyond what we hoped
			mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
			mv.visitVarInsn(ALOAD, remainder.position);
			mv.visitIntInsn(BIPUSH, 0);
			mv.visitMethodInsn(INVOKESTATIC, 
					Type.getInternalName(InvocationReader.class), 
					"warnOnUnusedPositions", 
					Type.getMethodDescriptor(Type.getType(Void.TYPE), 
							new Type[]{
							Type.getType(IReadContext.class),
							Type.getType(List.class),
							Type.getType(int.class)
						}
					));
			
			mv.visitLabel(endIndexed);
		}
	}

	private void createCallbacksForNamedProperties(
			final MethodVisitor mv,
			final ImmutableMap<JasbPropertyDescriptor, String> listenerClassesByProperty,
			final AtomicInteger localCounter,
			final ImmutableList.Builder<Local> locals,
			final Set<JasbPropertyDescriptor> namedProperties) {
		if (!namedProperties.isEmpty()) {
			final Label startKeyVal = new Label();
			final Label endKeyVal = new Label();

			mv.visitLabel(startKeyVal);
			final Local map = new Local(ARGUMENTS, startKeyVal, endKeyVal, Map.class, localCounter);
			locals.add(map);
			
			mv.visitVarInsn(ALOAD, INVOCATION_SLOT);
			mv.visitFieldInsn(GETFIELD, Type.getInternalName(Invocation.class), ARGUMENTS, Type.getDescriptor(Map.class));

			mv.visitVarInsn(ASTORE, map.position);
			
			for (final JasbPropertyDescriptor property : namedProperties) {
				final Label startEntry = new Label();
				final Label endEntry = new Label();
				final Local node = new Local(
						property.key.get(), startEntry, endEntry, 
						Node.class, localCounter);
				locals.add(node);
				
				mv.visitLabel(startEntry);
				// empty stack
				mv.visitVarInsn(ALOAD, map.position);
				// map
				mv.visitLdcInsn(property.key.get());
				// map key
				mv.visitMethodInsn(INVOKEINTERFACE, 
						Type.getInternalName(Map.class), 
						GET, 
						Type.getMethodDescriptor(Type.getType(Object.class), new Type[]{Type.getType(Object.class)}));
				
				// map[key]
				mv.visitTypeInsn(CHECKCAST, Type.getInternalName(Node.class));
				// map[key]
				mv.visitVarInsn(ASTORE, node.position);
				// empty stack
				
				mv.visitVarInsn(ALOAD, node.position);
				// node
				mv.visitJumpInsn(IFNULL, endEntry);
				// empty stack
				
				final String listenerClassName = listenerClassesByProperty.get(property);
				final Label error = new Label();
				
				readSingleOrMultiValued(mv, property, node);
				
				connectIdentifierProperty(mv, property, node);
				
				constructNewListener(mv, listenerClassName);
				
				connectFutureToCallback(mv);
				
				mv.visitJumpInsn(GOTO, endEntry);
				// stack is empty and we can all go home
				mv.visitLabel(error);
				// make an error
				throwRuntimeException(mv, "A node was neither an atom nor a sequence - it was likely a comment, and comments should not be presented here.");
				mv.visitLabel(endEntry);
			}
			
			mv.visitVarInsn(ALOAD, SELF_SLOT);
			mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
			mv.visitVarInsn(ALOAD, INVOCATION_SLOT);
			
			mv.visitMethodInsn(INVOKESPECIAL, 
					Type.getInternalName(InvocationReader.class),
					"warnOnUnusedKeys", 
					Type.getMethodDescriptor(
							Type.getType(Void.TYPE),
							new Type[] {
								Type.getType(IReadContext.class),
								Type.getType(Invocation.class)
							}));
			
			mv.visitLabel(endKeyVal);
		}
	}

	private void connectIdentifierProperty(final MethodVisitor mv,
			final JasbPropertyDescriptor property, final Local node) {
		if (property.isIdentifier) {
			// if this is the name for this object, we want to tell 
			// the read context to listen to the future as well so 
			// it can find out our identifier.
			// S: future (we want to keep this)
			mv.visitInsn(DUP);
			// S: future, future
			mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
			// S: future, future, context
			mv.visitInsn(SWAP);
			// S: future, context, future
			mv.visitVarInsn(ALOAD, RESULT_SLOT);
			// S: future, context, future, result
			mv.visitInsn(SWAP);
			// S: future, context, result, future
			mv.visitVarInsn(ALOAD, node.position);
			// S: future, context, result, future, node
			mv.visitInsn(SWAP);
			// S: future, context, result, node, future
			
			// do context.registerIdentity(result, future)
			mv.visitMethodInsn(INVOKEINTERFACE, 
					Type.getInternalName(IReadContext.class),
					"registerIdentity",
					Type.getMethodDescriptor(
							Type.getType(Void.TYPE), 
							new Type[] {
								Type.getType(Object.class),
								Type.getType(Node.class),
								Type.getType(ListenableFuture.class)
							}));
			// S: future (i.e. back how we were)
		}
	}

	private void connectFutureToCallback(final MethodVisitor mv) {
		mv.visitMethodInsn(INVOKESTATIC, 
				Type.getInternalName(Futures.class),
				ADD_CALLBACK,
				Type.getMethodDescriptor(
						Type.getType(Void.TYPE),
						new Type[] {
							Type.getType(ListenableFuture.class),
							Type.getType(FutureCallback.class)
						}));
	}

	private void readSingleOrMultiValued(final MethodVisitor mv, final JasbPropertyDescriptor property, final Local node) {
		if (property.isMultiple) {	
			// call InvocationReader.readOneOrMany in base class, 
			// as we need to do something complex and the bytecode
			// is tedious to write out here.
			mv.visitVarInsn(ALOAD, SELF_SLOT);
			mv.visitVarInsn(ALOAD, CONTEXT_SLOT);
			mv.visitLdcInsn(Type.getType(property.propertyType));
			mv.visitVarInsn(ALOAD, node.position);
					
			mv.visitMethodInsn(INVOKESPECIAL, 
							   Type.getInternalName(InvocationReader.class),
							   READ_ONE_OR_MANY, 
							   Type.getMethodDescriptor(
														Type.getType(ListenableFuture.class),
														new Type[] {
															Type.getType(IReadContext.class),
															Type.getType(Class.class),
															Type.getType(Node.class)
														}));
		} else {
			readSingleValued(mv, property, node);
		}
	}

	private void readSingleValued(final MethodVisitor mv, final JasbPropertyDescriptor property, final Local node) {
		// if the node is not null, we need to hook a callback for it
		// first we need to ask the context to make a future
		// push the arguments to the method for that
		mv.visitVarInsn(ALOAD, CONTEXT_SLOT); // the context
		mv.visitLdcInsn(Type.getType(property.boxedPropertyType)); // the type
		mv.visitVarInsn(ALOAD, node.position); // the node
		// invoke the method; this will consume the three things we just stacked
		// and leave the resulting future on the stack
		mv.visitMethodInsn(INVOKEINTERFACE, 
				Type.getInternalName(IReadContext.class),
				READ , 
				Type.getMethodDescriptor(
						Type.getType(ListenableFuture.class),
						new Type[] {
							Type.getType(Class.class),
							Type.getType(Node.class)
						})
				);
	}

	private void constructNewListener(
			final MethodVisitor mv,
			final String listenerClassName) {
		final String listenerClassInternalName = this.generatedClassInternalName + "$" + listenerClassName;
		mv.visitTypeInsn(NEW, listenerClassInternalName);
		// stack: ..., callback listener
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, RESULT_SLOT);
		
		// stack : ..., listener, [listener, result]
		// invoke the constructor
		mv.visitMethodInsn(INVOKESPECIAL, 
				listenerClassInternalName, 
				INIT,
				Type.getMethodDescriptor(Type.getType(Void.TYPE), 
						new Type[] {Type.getType(typeToRead)}));
	}
	
	private void unboxIfPrimitive(final MethodVisitor mv, final JasbPropertyDescriptor property) {
		final Class<?> t = property.propertyType;
		if (t.isPrimitive()) {
			// we need to do the unboxing
			// top of the stack at this point is the boxed type,
			// so this basically consists of invoking intValue() or similar:
			if (t == int.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Integer.class), "intValue", "()" + Type.getDescriptor(int.class));
			} else if (t == double.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Double.class), "doubleValue", "()" + Type.getDescriptor(double.class));
			} else if (t == boolean.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Boolean.class), "booleanValue", "()" + Type.getDescriptor(boolean.class));
			} else if (t == long.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Long.class), "longValue", "()" + Type.getDescriptor(long.class));
			} else if (t == float.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Float.class), "floatValue", "()" + Type.getDescriptor(float.class));
			} else if (t == char.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Character.class), "charValue", "()" + Type.getDescriptor(char.class));
			} else if (t == byte.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Byte.class), "byteValue", "()" + Type.getDescriptor(byte.class));
			} else if (t == short.class) {
				mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(Short.class), "shortValue", "()" + Type.getDescriptor(short.class));
			} else {
				throw new IllegalArgumentException(t + " is a new kind of primitive type! Quite suprised.");
			}
		}
	}

	private String declareFutureListenerClass(final JasbPropertyDescriptor property) {
		// if property is multivalue, we want to make it work on a list using a getter
		// otherwise, we make it work directly using a setter.
		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
		
		final String listenerClassName = 
				"CallbackFor" + property.name + "In" + typeToRead.getSimpleName();
		
		final String listenerInternalName = generatedClassInternalName + "$"+ listenerClassName;
		final String listenerDescriptor = "L" + listenerInternalName + ";";
		
		cw.visit(
				V1_7,
				ACC_SUPER,
				listenerInternalName,
				Type.getDescriptor(Object.class) + "L" + Type.getInternalName(FutureCallback.class) +
				"<" + 
					( property.isMultiple ?
							("L" + Type.getInternalName(List.class) +"<" + Type.getDescriptor(property.propertyType) +">;") :
							Type.getDescriptor(property.boxedPropertyType)			
					)
					+ ">;",
				Type.getInternalName(Object.class),
				new String[] { 
					Type.getInternalName(FutureCallback.class)});
		
		cw.visitSource(getClass().getSimpleName()+ ".java", null);
		// we need to add ourself as an inner class, even in our own declaration
		cw.visitInnerClass(listenerInternalName, generatedClassInternalName, listenerClassName, ACC_STATIC);
		
		// once again, need to visit innerclass if we need one
		addInnerClassForTypeToRead(cw);
		
		// add field for storing target object
		{
			final FieldVisitor fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, 
					TARGET, Type.getDescriptor(typeToRead), null, null);
			fv.visitEnd();
		}

		// generate constructor
		
		{
			final MethodVisitor mv = cw.visitMethod(0, 
					INIT,
					Type.getMethodDescriptor(Type.getType(Void.TYPE), new Type[] {Type.getType(typeToRead)}),
					null, null);
			
			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object.class), INIT, "()V");
			mv.visitVarInsn(ALOAD, 0); // load this
			mv.visitVarInsn(ALOAD, 1); // load target
			// store target in this.target
			mv.visitFieldInsn(PUTFIELD, listenerInternalName, TARGET, Type.getDescriptor(typeToRead));
						
			mv.visitInsn(RETURN);
			final Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable(THIS, listenerDescriptor, null, l0, l3, 0);
			mv.visitLocalVariable(TARGET, Type.getDescriptor(typeToRead), null, l0, l3, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		
		if (property.isMultiple) {
			final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "onSuccess", "(Ljava/util/List;)V", "(" + 
					"L" + Type.getInternalName(List.class) +
					"<" + Type.getDescriptor(property.propertyType) + ">;", null);
			
			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			// stack = this
			mv.visitFieldInsn(GETFIELD, 
					listenerInternalName, 
					TARGET, 
					Type.getDescriptor(typeToRead));
			// stack = target
			
			mv.visitMethodInsn(INVOKEVIRTUAL, 
					Type.getInternalName(typeToRead),
					property.readMethod.getName(),
					Type.getMethodDescriptor(property.readMethod));
			//printTopOfStack(mv);
			mv.visitVarInsn(ASTORE, 2);
			mv.visitVarInsn(ALOAD, 2);
			
			final Label error = new Label();
			// if it is null, we want to give up
			mv.visitJumpInsn(IFNULL, error);
			// stack = list from target,

			mv.visitVarInsn(ALOAD, 2);
			// invoke list.clear()
			mv.visitMethodInsn(INVOKEINTERFACE, 
					Type.getInternalName(List.class),
					"clear", 
					Type.getMethodDescriptor(
							Type.getType(Void.TYPE),
							new Type[] {}
							));
			// stack = list from target
			
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 1);
			// stack = list from target, result list
			// listFromTarget.addAll(resultList)
			mv.visitMethodInsn(INVOKEINTERFACE, 
					Type.getInternalName(List.class),
					"addAll",
					Type.getMethodDescriptor(
							Type.getType(Boolean.TYPE),
							new Type[] {Type.getType(Collection.class)}
							));
			// dump the boolean that comes out
			mv.visitInsn(POP);
			// stack now empty and we are done
			mv.visitInsn(RETURN);
			mv.visitLabel(error);
			
			throwRuntimeException(mv, "List for property " + property.name + " was null in newly constructed " + this.typeToRead.getSimpleName());

			mv.visitInsn(RETURN);
			final Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable(THIS, listenerDescriptor, null, l0, l2, 0);
			mv.visitLocalVariable("result", Type.getDescriptor(List.class), "Ljava/util/List<" + Type.getDescriptor(property.propertyType) +">;", l0, l2, 1);
			mv.visitLocalVariable("output", Type.getDescriptor(List.class), "Ljava/util/List<" + Type.getDescriptor(property.propertyType) +">;", l0, l2, 2);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		} else {
			// simple setter behaviour is OK
			final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "onSuccess", "(" + Type.getDescriptor(property.boxedPropertyType)+ ")V", null, null);
			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			
			mv.visitFieldInsn(GETFIELD, 
					listenerInternalName, 
					TARGET, 
					Type.getDescriptor(typeToRead));
			mv.visitVarInsn(ALOAD, 1);
			// at this point we need to unbox any boxed types
			
			unboxIfPrimitive(mv, property);
			
			mv.visitMethodInsn(INVOKEVIRTUAL, 
					Type.getInternalName(typeToRead),
					property.writeMethod.getName(),
					Type.getMethodDescriptor(property.writeMethod));
			
			mv.visitInsn(RETURN);
			final Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable(THIS, listenerDescriptor, null, l0, l2, 0);
			mv.visitLocalVariable("result", Type.getDescriptor(property.boxedPropertyType), null, l0, l2, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		
		// generate bridging method
		{
			final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "onSuccess", "(Ljava/lang/Object;)V", null, null);
			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(1, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			// nope, this needs to think
			if (property.isMultiple) {
				mv.visitTypeInsn(CHECKCAST, Type.getInternalName(List.class));
				mv.visitMethodInsn(INVOKEVIRTUAL, 
						listenerInternalName, 
						"onSuccess", 
						"("+Type.getDescriptor(List.class)+")V");
			} else {
				mv.visitTypeInsn(CHECKCAST, Type.getInternalName(property.boxedPropertyType));
				mv.visitMethodInsn(INVOKEVIRTUAL, 
						listenerInternalName, 
						"onSuccess", 
						"("+Type.getDescriptor(property.boxedPropertyType)+")V");
			}
			mv.visitInsn(RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		
		// do nothing in our onerror method{
		{
			final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "onFailure", "(Ljava/lang/Throwable;)V", null, null);
			mv.visitCode();
			final Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(47, l0);
			mv.visitInsn(RETURN);
			final Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable(THIS, listenerDescriptor, null, l0, l1, 0);
			mv.visitLocalVariable("t", Type.getDescriptor(Throwable.class), null, l0, l1, 1);
			mv.visitMaxs(0, 2);
			mv.visitEnd();
		}
		
		cw.visitEnd();
		
		unloadedClasses.put(listenerInternalName.replace('/', '.'), cw.toByteArray());
		
		return listenerClassName;
	}

	private void throwRuntimeException(final MethodVisitor mv,final String error) {
		mv.visitTypeInsn(NEW, "java/lang/RuntimeException");
		mv.visitInsn(DUP);
		mv.visitLdcInsn(error);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", INIT, "(Ljava/lang/String;)V");
		mv.visitInsn(ATHROW);
	}

	public Class<? extends InvocationReader<T>> getReaderClass() {
		return constructorClass;
	}

	public InvocationReader<T> getReaderInstance() {
		try {
			return getReaderClass().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Generated class has no zero-argument constructor? That should not happen", e);
		}
	}
}
