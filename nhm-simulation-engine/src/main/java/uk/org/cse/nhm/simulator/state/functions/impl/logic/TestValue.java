package uk.org.cse.nhm.simulator.state.functions.impl.logic;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.larkery.jasb.io.atom.NumberAtomIO;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class TestValue  {
	private static final double epsilon = 1E-5;

	public static final IComponentsFunction<Boolean> create(final IComponentsFunction<?> value, final List<String> match) {
		if (match.isEmpty()) {
			return new NeverMatches();
		} else if (match.size() == 1) {
			return new MatchOneThing(value, createSingleMatch(value, match.get(0)));
		} else {
			final ImmutableList.Builder<Predicate<Object>> ps = ImmutableList.builder();
			for (final String s : match) {
				ps.add(createSingleMatch(value, s));
			}
			return new MatchAtLeastOneThing(value, ps.build());
		}
	}
	
	private static Predicate<Object> createSingleMatch(final IComponentsFunction<?> value, final String string) {
		final Class<?> t = getType(value);
		if (t.isEnum()) {
			final Object val = asEnumValue(t, string);
			return new Predicate<Object>() {
				@Override
				public boolean apply(final Object arg0) {
					return arg0 == val;
				}
			};
		} else if (Number.class.isAssignableFrom(t)) {
			final Optional<Double> d = new NumberAtomIO().read(string, Double.class);
			if (d.isPresent()) {
				final double q = d.get();
				return new Predicate<Object>() {
					@Override
					public boolean apply(final Object arg0) {
						if (arg0 instanceof Number) {
							return Math.abs(((Number) arg0).doubleValue() - q) < epsilon; 
						}
						return false;
					}
				};
			} else {
				return Predicates.alwaysFalse();
			}
		} else if (IIdentified.class.isAssignableFrom(t)) {
			return new Predicate<Object>() {
				@Override
				public boolean apply(final Object arg0) {
					if (arg0 instanceof IIdentified) {
						return ((((IIdentified) arg0).getIdentifier().getName()).equalsIgnoreCase(string));
					}
					return false;
				}
			};
		} else {
			return new Predicate<Object>() {
				@Override
				public boolean apply(final Object arg0) {
					return String.valueOf(arg0).equalsIgnoreCase(string);
				}
			};
		}
	}
	
	private static Object asEnumValue(final Class<?> t, final String s) {
		try {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			final Object o = Enum.valueOf((Class) t, s);
			return o;
		} catch (final IllegalArgumentException nse) {
			for (final Object o : t.getEnumConstants()) {
				if (String.valueOf(o).equalsIgnoreCase(s)) {
					return o;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static Class<?> getType(final IComponentsFunction<?> fn) {
		final TypeToken<? extends IComponentsFunction> tt = TypeToken.of(fn.getClass());
		final Class<?> resolvedType = tt.resolveType(IComponentsFunction.class.getTypeParameters()[0]).getRawType();
		return resolvedType;
	}
	
	static class MatchOneThing extends AbstractNamed implements IComponentsFunction<Boolean> {
		final IComponentsFunction<?> delegate;
		private final Predicate<Object> p;
		
		public MatchOneThing(final IComponentsFunction<?> delegate, final Predicate<Object> p) {
			super();
			this.delegate = delegate;
			this.p = p;
		}

		@Override
		public Boolean compute(final IComponentsScope scope, final ILets lets) {
			return p.apply(delegate.compute(scope, lets));
		}
		
		@Override
		public Set<IDimension<?>> getDependencies() {
			return delegate.getDependencies();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return delegate.getChangeDates();
		}
	}
	
	static class MatchAtLeastOneThing extends AbstractNamed implements IComponentsFunction<Boolean> {
		final IComponentsFunction<?> delegate;
		private final List<Predicate<Object>> p;
		
		public MatchAtLeastOneThing(final IComponentsFunction<?> delegate, final List<Predicate<Object>> p) {
			super();
			this.delegate = delegate;
			this.p = p;
		}

		@Override
		public Boolean compute(final IComponentsScope scope, final ILets lets) {
			final Object o = delegate.compute(scope, lets);
			for (final Predicate<Object> p2  : p) {
				if (p2.apply(o)) return true;
			}
			return false;
		}
		
		@Override
		public Set<IDimension<?>> getDependencies() {
			return delegate.getDependencies();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return delegate.getChangeDates();
		}
		
	}
	
	static class NeverMatches extends AbstractNamed implements IComponentsFunction<Boolean> {
		@Override
		public Boolean compute(final IComponentsScope scope, final ILets lets) {
			return false;
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return Collections.emptySet();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return Collections.emptySet();
		}
	}
}
