package uk.org.cse.nhm.simulator.let;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

final class Lets {
	final static class Empty implements ILets {
		@Override
		public ILets withBinding(final Object key, final Object value) {
			return Normal.create(this, ImmutableMap.of(key, value));
		}

		@Override
		public ILets withBindings(final Map<Object, Object> additions) {
			return Normal.create(this, ImmutableMap.copyOf(additions));
		}

		@Override
		public ILets assignableTo(final Class<?> clazz) {
			return this;
		}

		@Override
		public <T> Optional<T> get(final Object name, final Class<T> clazz) {
			return Optional.absent();
		}

		@Override
		public ILets withLets(final ILets letParams) {
			return letParams;
        }

        @Override
        public Optional<ILets> binderOf(final Object name) {
            return Optional.absent();
        }
	}

	final static class Normal implements ILets {
		private final ILets parent;
		private final ImmutableMap<Object, Object> contents;
		
		private Normal(final ILets parent, final ImmutableMap<Object, Object> contents) {
			this.parent = parent;
			this.contents = contents;
		}

		public static ILets create(final ILets parent, final ImmutableMap<Object, Object> contents) {
			if (contents.isEmpty()) {
				return parent;
			} else {
				return new Normal(parent, contents);
			}
		}

		@Override
		public ILets withBinding(final Object key, final Object value) {
			return Normal.create(this, ImmutableMap.of(key, value));
		}

		@Override
		public ILets withBindings(final Map<Object, Object> additions) {
			return Normal.create(this, ImmutableMap.copyOf(additions));
		}
		
		@Override
		public ILets assignableTo(final Class<?> clazz) {
			final ImmutableMap<Object, Object> matchingContents = filter(clazz);
			if (matchingContents.isEmpty()) {
				return parent.assignableTo(clazz);
			} else {
				return Normal.create(
						parent.assignableTo(clazz),
						matchingContents);
			}
		}

		private ImmutableMap<Object, Object> filter(final Class<?> clazz) {
			final ImmutableMap.Builder<Object, Object> b = ImmutableMap.builder();
			
			for (final Map.Entry<Object, Object> e : contents.entrySet()) {
				if (clazz.isInstance(e.getValue())) {
					b.put(e);
				}
			}
			
			return b.build();
		}

		@Override
		public <T> Optional<T> get(final Object name, final Class<T> clazz) {
			final Object o = contents.get(name);
			if (o == null) {
				if (parent == null) {
					return Optional.absent();
				} else {
					return parent.get(name, clazz);
				}
			} else {
				if (clazz.isInstance(o)) {
					return Optional.of(clazz.cast(o));
				} else {
					throw new IllegalArgumentException(String.format("Let binding %s is not of type %s", name, clazz));
				}
			}
		}
		
		@Override
		public ILets withLets(final ILets letParams) {
			if (letParams instanceof Empty) return this;
			return Dual.create(letParams, this);
        }

        @Override
        public Optional<ILets> binderOf(final Object name) {
            if (contents.containsKey(name)) {
                return Optional.<ILets>of(this);
            } else {
                return parent.binderOf(name);
            }
        }
	}

	private static class Dual implements ILets {
		private final ILets first;
		private final ILets second;
		
		private static ILets create(final ILets first, final ILets second) {
			if (first instanceof Empty) {
				return second;
			} else if (second instanceof Empty) {
				return first;
			} else {
				return new Dual(first, second);
			}
		}
		
		private Dual(final ILets first, final ILets second) {
			super();
			this.first = first;
			this.second = second;
		}

		@Override
		public ILets withBinding(final Object key, final Object value) {
			return Normal.create(this, ImmutableMap.of(key, value));
		}

		@Override
		public ILets withBindings(final Map<Object, Object> additions) {
			return Normal.create(this, ImmutableMap.copyOf(additions));
		}

		@Override
		public ILets assignableTo(final Class<?> clazz) {
			return create(
					first.assignableTo(clazz),
					second.assignableTo(clazz));
		}

		@Override
		public <T> Optional<T> get(final Object name, final Class<T> clazz) {
			final Optional<T> a = first.get(name, clazz);
			if (a.isPresent()) return a;
			return second.get(name, clazz);
		}

		@Override
		public ILets withLets(final ILets letParams) {
			return create(letParams, this);
        }

        @Override
        public Optional<ILets> binderOf(final Object name) {
            final Optional<ILets> firstBinder = first.binderOf(name);
            if (firstBinder.isPresent()) return firstBinder;
            return second.binderOf(name);
        }
	}
}
