package com.larkery.jasb.io.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.io.IAtomReader;
import com.larkery.jasb.io.IModel;

public class Model implements IModel {
	private final ImmutableSet<IElement> elements;
	private final ImmutableSet<IInvocationModel> invocations;
	private final ImmutableSet<IAtomModel> atoms;
	
	public Model(final Set<Class<?>> classes, final Set<? extends IAtomReader> atoms) {
		final ImmutableSet.Builder<IElement> elements = ImmutableSet.builder();
		final ImmutableSet.Builder<IAtomModel> atomModels = ImmutableSet.builder();
		final ImmutableSet.Builder<IInvocationModel> invocations = ImmutableSet.builder();
		
        final Map<String, AtomModel> atomModels_ = new HashMap<>();
        
		for (final Class<?> clazz : classes) {
			final InvocationModel inv = new InvocationModel(clazz);
			elements.add(inv);
			invocations.add(inv);
			for (final IArgument a : inv.getArguments()) {
				for (final IAtomReader r : atoms) {
					if (r.canReadTo(a.getJavaType())) {
						final String displayName = r.getDisplayName(a.getJavaType());

                        if (atomModels_.containsKey(displayName)) {
                            final AtomModel am = atomModels_.get(displayName);
                            am.addLegalValues(r.getLegalValues(a.getJavaType()));
                            am.addJavaType(a.getJavaType());
                        } else {
                            final AtomModel am = new AtomModel(
								displayName,
								a.getJavaType(),
								r.getLegalValues(a.getJavaType()),
								r.isBounded()
								);
                            elements.add(am);
                            atomModels.add(am);
                            atomModels_.put(displayName, am);
                        }
                    }
				}
			}
		}
		this.elements = elements.build();
		this.atoms = atomModels.build();
		this.invocations = invocations.build();
	}
	
	@Override
	public Set<IAtomModel> getAtoms() {
		return atoms;
	}
	
	@Override
	public Set<IInvocationModel> getInvocations() {
		return invocations;
	}
	
	@Override
	public Set<IElement> getElements() {
		return elements;
	}
	
	class Argument implements IArgument {
		private final JasbPropertyDescriptor pd;
		private Set<IElement> legalValues;
		private Optional<Object> defaultValue;
		private boolean exhaustive = true;
		
		public Argument(final Object val, final JasbPropertyDescriptor pd) {
			this.pd = pd;
			try {
				this.defaultValue = 
					Optional.fromNullable(pd.readMethod.invoke(val));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new IllegalArgumentException("Couldn't get default value for " + pd);
			}
		}
		
		@Override
		public boolean isIdentity() {
			return pd.isIdentifier;
		}
		
		@Override
		public Optional<Object> getDefaultValue() {
			return defaultValue;
		}

		@Override
		public boolean isNamedArgument() {
			return pd.key.isPresent();
		}

		@Override
		public Optional<String> getName() {
			return pd.key;
		}

		@Override
		public boolean isPositionalArgument() {
			return pd.position.isPresent();
		}

		@Override
		public Optional<Integer> getPosition() {
			return pd.position;
		}

		@Override
		public boolean isMultiple() {
			return pd.isMultiple;
		}
		
		@Override
		public boolean isRemainderArgument() {
			return !(isPositionalArgument() || isNamedArgument());
		}
		
		@Override
		public Method getReadMethod() {
			return pd.readMethod;
		}

		@Override
		public Set<IElement> getLegalValues() {
			if (legalValues == null) {
				final ImmutableSet.Builder<IElement> legalValues = ImmutableSet.builder();
				
				for (final IElement e : Model.this.elements) {
					if (e instanceof IAtomModel) {
						exhaustive = exhaustive && (((IAtomModel) e).isBounded());
					}
                    if (e.isAssignableTo(pd.boxedPropertyType)) {
						legalValues.add(e);
					}
				}
				
				this.legalValues = legalValues.build();
			}
			return legalValues;
		}
		
		@Override
		public Class<?> getJavaType() {
			return pd.boxedPropertyType;
		}

		@Override
		public boolean isListOfLists() {
			return pd.isListOfLists;
		}

		@Override
		public boolean isMandatory() {
			return pd.isMandatory && !defaultValue.isPresent();
		}
		
		@Override
		public boolean isBounded() {
			return exhaustive;
		}
	}
	
	class InvocationModel implements IInvocationModel {
		private final Class<?> javaType;
		private final ImmutableSet<IArgument> arguments;
		private final String name;
		private final ImmutableSet<IArgument> named;
		private final ImmutableSet<IArgument> positional;
		private final Optional<IArgument> remainder;

		public InvocationModel(final Class<?> clazz) {
			this.javaType = clazz;
			if (!clazz.isAnnotationPresent(Bind.class)) {
				throw new IllegalArgumentException(""+clazz);
			}
			final Object val;
			try {
				val = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(clazz.getCanonicalName() + " couldn't be constructed", e);
			}
			
			this.name = clazz.getAnnotation(Bind.class).value();
			
			final ImmutableSet.Builder<IArgument> arguments = 
					ImmutableSet.builder();
			
			final ImmutableSet.Builder<IArgument> named = 
					ImmutableSet.builder();
			
			final ImmutableSet.Builder<IArgument> positional = 
					ImmutableSet.builder();
			
			IArgument remainder = null;
			for (final JasbPropertyDescriptor pd : JasbPropertyDescriptor.getDescriptors(javaType)) {
				final Argument argument = new Argument(val, pd);
				arguments.add(argument);
				if (argument.isNamedArgument()) named.add(argument);
				if (argument.isPositionalArgument()) positional.add(argument);
				if (argument.isRemainderArgument()) remainder = argument;
			}
			
			this.arguments = arguments.build();
			this.named = named.build();
			this.positional = positional.build();
			this.remainder = Optional.fromNullable(remainder);
		}

        @Override
        public boolean isAssignableTo(final Class<?> clazz) {
            return clazz.isAssignableFrom(getJavaType());
        }
        
		@Override
		public Class<?> getJavaType() {
			return this.javaType;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public Set<IArgument> getArguments() {
			return this.arguments;
		}
		
		@Override
		public Set<IArgument> getNamedArguments() {
			return named;
		}
		
		@Override
		public Set<IArgument> getPositionalArguments() {
			return positional;
		}
		
		@Override
		public Optional<IArgument> getRemainderArgument() {
			return remainder;
		}
		
		@Override
		public String toString() {
			return "invocation " + name;
		}
		
		@Override
		public int compareTo(final IElement o) {
			if (o instanceof IInvocationModel) {
				return name.compareTo(((IInvocationModel) o).getName());
			} else {
				return -1;
			}
		}
	}
	
	class AtomModel implements IAtomModel {
		private final String name;
		private final Set<Class<?>> javaTypes;
		private final Set<String> legalValues = new HashSet<>();
        private Class<?> mainType;
        private boolean bounded;

		public AtomModel(final String name, final Class<?> javaType, final Set<String> legalValues, boolean bounded) {
			this.name = name;
            this.mainType = null;
            this.javaTypes = new HashSet<>();
            this.javaTypes.add(javaType);
            this.legalValues.addAll(legalValues);
            this.bounded = bounded;
		}

        @Override
        public boolean isAssignableTo(final Class<?> clazz) {
            for (final Class<?> c2 : javaTypes) {
                if (clazz.isAssignableFrom(c2)) return true;
            }
            return false;
        }
        
        void addJavaType(final Class<?> type) {
            this.javaTypes.add(type);
        }

        void addLegalValues(final Set<String> legalValues) {
            this.legalValues.addAll(legalValues);
        }
        
		@Override
		public Class<?> getJavaType() {
			if (mainType == null) {
                if (javaTypes.size() == 1) {
                    mainType = javaTypes.iterator().next();
                } else {
                classes: for (final Class<?> clazz : javaTypes) {
                        // if there is no element for it, it's better
                        if (mainType == null) mainType = clazz;
                        else {
                            for (final IInvocationModel i : Model.this.invocations) {
                                if (i.isAssignableTo(clazz)) continue classes;
                            }
                            mainType = clazz;
                        }
                        
                    }
                }
            }
            return mainType;
		}

		@Override
		public Set<String> getLiterals() {
			return legalValues;
		}
		
		@Override
		public boolean isBounded() {
			return bounded;
		}
		
		@Override
		public String toString() {
			return "atom " + legalValues;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public int compareTo(final IElement o) {
			if (o instanceof IAtomModel) {
				return name.compareTo(o.getName());
			} else {
				return 1;
			}
		}
	}
}
