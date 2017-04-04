package uk.org.cse.nhm.language.builder.function;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.function.num.basic.XRound.XRoundDirection;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Maths {
	abstract static class SingleArgument extends AbstractNamed implements IComponentsFunction<Double> {
		final IComponentsFunction<? extends Number> delegate;

		private SingleArgument(final IComponentsFunction<? extends Number> delegate) {
			super();
			this.delegate = delegate;
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
	
	abstract static class MultipleArguments<T> extends AbstractNamed implements IComponentsFunction<T> {
		final List<IComponentsFunction<? extends Number>> arguments;
		private final Set<IDimension<?>> dependencies;
		
		public MultipleArguments(
				final List<IComponentsFunction<? extends Number>> arguments) {
			this.arguments = arguments;
			final ImmutableSet.Builder<IDimension<?>> b = ImmutableSet.builder();
			
			for (final IComponentsFunction<? extends Number> cf : arguments) {
				b.addAll(cf.getDependencies());
			}
			
			this.dependencies = b.build();
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return dependencies;
		}

		@Override
		public Set<DateTime> getChangeDates() {
			final ImmutableSet.Builder<DateTime> b = ImmutableSet.builder();
			
			for (final IComponentsFunction<? extends Number> cf : arguments) {
				b.addAll(cf.getChangeDates());
			}
			
			return b.build();
		}
	}

    abstract static class TwoArguments extends AbstractNamed implements IComponentsFunction<Double> {
        final IComponentsFunction<? extends Number> first, second;
        private final Set<IDimension<?>> dependencies;

        public TwoArguments(final IComponentsFunction<? extends Number> first,
                            final IComponentsFunction<? extends Number> second) {
            final ImmutableSet.Builder<IDimension<?>> b = ImmutableSet.builder();

            this.first = first;
            this.second = second;

            b.addAll(first.getDependencies());
            b.addAll(second.getDependencies());

            this.dependencies = b.build();
        }

        @Override
        public Set<IDimension<?>> getDependencies() {
            return dependencies;
        }

        @Override
        public Set<DateTime> getChangeDates() {
            final ImmutableSet.Builder<DateTime> b = ImmutableSet.builder();

            b.addAll(first.getChangeDates());
            b.addAll(second.getChangeDates());

            return b.build();
        }

        @Override
        public Double compute(final IComponentsScope scope, final ILets lets) {
            return compute(first.compute(scope, lets).doubleValue(),
                           second.compute(scope, lets).doubleValue());
        }

        protected abstract double compute(final double a, final double b);
    }


	static class Difference extends MultipleArguments<Double> {
		public Difference(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			if (arguments.isEmpty()) {
				return Double.NaN;
			} else {
                double value = arguments.get(0).compute(scope, lets).doubleValue();
                for (int i = 1; i<arguments.size(); i++) {
					value = (value - arguments.get(i).compute(scope, lets).doubleValue());
				}
				return value;
			}
		}
	}

    static class Difference2 extends TwoArguments {
        public Difference2(final IComponentsFunction<? extends Number> first,
                           final IComponentsFunction<? extends Number> second) {
            super(first, second);
        }
        @Override protected double compute(final double a, final double b) { return a - b; }
    }

	static class Log extends SingleArgument {
		private final double logBase;

		public Log(final IComponentsFunction<? extends Number> delegate, final double base) {
			super(delegate);
			this.logBase = Math.log(base);
		}
		
		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			return Math.log(delegate.compute(scope, lets).doubleValue()) / logBase;
		}
		
	}
	
	static class Negation extends SingleArgument {
		public Negation(final IComponentsFunction<? extends Number> delegate) {
			super(delegate);
		}		

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			return -delegate.compute(scope, lets).doubleValue();
		}
	}
	
	static class Max extends MultipleArguments<Double> {
		public Max(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			double max = Double.NEGATIVE_INFINITY;
			for (final IComponentsFunction<? extends Number> c : arguments) {
				max = Math.max(max, c.compute(scope, lets).doubleValue());
			}
			return max;
		}
	}
	
	static class Min extends MultipleArguments<Double> {
		public Min(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			double min = Double.POSITIVE_INFINITY;
			for (final IComponentsFunction<? extends Number> c : arguments) {
				min = Math.min(min, c.compute(scope, lets).doubleValue());
			}
			return min;
		}
	}
	
	static class Product extends MultipleArguments<Double> {
		public Product(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			double acc = 1;
			for (final IComponentsFunction<? extends Number> c : arguments) {
				acc *= c.compute(scope, lets).doubleValue();
			}
			return acc;
		}
	}
	
	static class Ratio extends MultipleArguments<Double> {
		public Ratio(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			if (arguments.isEmpty()) {
				return Double.NaN;
			} else {
				double value = arguments.get(0).compute(scope, lets).doubleValue();
				for (int i = 1; i<arguments.size(); i++) {
					value = (value / arguments.get(i).compute(scope, lets).doubleValue());
				}
				return value;
			}
		}
	}
	
	static class Sum extends MultipleArguments<Double> {
		public Sum(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			double acc = 0;
			
			for (final IComponentsFunction<? extends Number> d : arguments) {
				acc += d.compute(scope, lets).doubleValue();
			}
			
			return acc;
		}
	}
	
	static class Pow extends MultipleArguments<Double> {

		public Pow(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			if (arguments.isEmpty()) {
				return Double.NaN;
			} else {
				double accum = arguments.get(0).compute(scope, lets).doubleValue();
				for (int i = 1; i < arguments.size(); i++) {
					accum = Math.pow(accum, arguments.get(i).compute(scope, lets).doubleValue());
				}
				return accum;
			}
		}
	}
	
	static class Exp extends SingleArgument {
		public Exp(final IComponentsFunction<? extends Number> delegate) {
			super(delegate);
		}
		
		@Override
		public Double compute(final IComponentsScope scope, final ILets lets) {
			return Math.exp(delegate.compute(scope, lets).doubleValue());
		}
	}
	
	abstract static class Roller extends MultipleArguments<Boolean> implements IComponentsFunction<Boolean> {
		public Roller(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}
		
		@Override
		public Boolean compute(final IComponentsScope scope, final ILets lets) {
			if (arguments.size() >= 2) {
                double value = arguments.get(0).compute(scope, lets).doubleValue();
                if (Double.isNaN(value)) return false;
				for (int i = 1; i<arguments.size(); i++) {
                    final double next = arguments.get(i).compute(scope, lets).doubleValue();
                    if (Double.isNaN(next)) return false;
					if (invalid(value, next)) {
						return false;
					}
					value = next;
				}
			}
			return true;
		}
		
		protected abstract boolean invalid(final double a, final double b);
	}
	
	static class Greater extends Roller {
		public Greater(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		protected boolean invalid(final double a, final double b) {
			// we require > a b ==> a > b ===> !(a<=b)
			return a<=b;
		}
	}
	
	static class GreaterEq extends Roller {
		public GreaterEq(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		protected boolean invalid(final double a, final double b) {
			// >= a b ===> a >= b ===> ! (a < b)
			return a<b;
		}
	}
	
	static class Less extends Roller {
		public Less(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		protected boolean invalid(final double a, final double b) {
			// < a b ===> a < b ===> ! (a >=b)
			return a>=b;
		}
	}
	
	static class LessEq extends Roller {
		public LessEq(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		protected boolean invalid(final double a, final double b) {
			// <= a b ==> a <= b ==> ! (a>b) 
			return a>b;
		}
	}
	
	static class AllEq extends Roller {
		private static final double SMALL_NUMBER = 1e-5;

		public AllEq(final List<IComponentsFunction<? extends Number>> arguments) {
			super(arguments);
		}

		@Override
		protected boolean invalid(final double a, final double b) {
			return Math.abs(a - b) > SMALL_NUMBER;
		}
	}
	
	static class Round extends AbstractNamed implements IComponentsFunction<Number> {
		private final IComponentsFunction<Number> value;
		private final IComponentsFunction<Number> precision;
		private final XRoundDirection direction;
		
		public Round(
				IComponentsFunction<Number> value,
				IComponentsFunction<Number> precision, 
				XRoundDirection direction) {
			this.value = value;
			this.precision = precision;
			this.direction = direction;
		}

		@Override
		public Number compute(IComponentsScope scope, ILets lets) {
			final double precision = this.precision.compute(scope, lets).doubleValue();
			final double value = this.value.compute(scope, lets).doubleValue();
			
			switch (direction) {
			case Lower:
				return precision* Math.floor(value / precision);
			case Nearest:
				return precision* Math.round(value / precision);
			case Upper:
				return precision* Math.ceil(value / precision);
			default:
				return value;
			}
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return ImmutableSet.<IDimension<?>>builder()
					.addAll(value.getDependencies())
					.addAll(precision.getDependencies())
					.build();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return ImmutableSet.<DateTime>builder().addAll(value.getChangeDates()).addAll(precision.getChangeDates()).build();
		}
		
		
	}
	
	public static IComponentsFunction<? extends Number> exp(final IComponentsFunction<? extends Number> value) {
		return new Exp(value);
	}

    public static IComponentsFunction<Double> difference(final IComponentsFunction<? extends Number> A, final IComponentsFunction<? extends Number> B) {
        return new Difference2(A, B);
    }

	public static IComponentsFunction<? extends Number> difference(
            final List<IComponentsFunction<? extends Number>> children) {
		if (children.size() == 1) {
            return new Negation(children.get(0));
        } else if (children.size() == 2) {
            return new Difference2(children.get(0), children.get(1));
		} else {
			return new Difference(children);
		}
	}

	public static IComponentsFunction<? extends Number> max(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Max(children);
	}

	public static IComponentsFunction<? extends Number> min(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Min(children);
	}

	public static IComponentsFunction<? extends Number> product(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Product(children);
	}

	public static IComponentsFunction<? extends Number> ratio(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Ratio(children);
	}

	public static IComponentsFunction<? extends Number> sum(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Sum(children);
	}
	
	public static IComponentsFunction<? extends Number> pow(
			final List<IComponentsFunction<? extends Number>> children) {
		return new Pow(children);
	}
	
	public static IComponentsFunction<? extends Number> log(
			final IComponentsFunction<? extends Number> argument,
			final double base) {
		return new Log(argument, base);
	}
	
	public static IComponentsFunction<Boolean> greater(final List<IComponentsFunction<? extends Number>> children) {
		return new Greater(children);
	}
	
	public static IComponentsFunction<Boolean> greaterEq(final List<IComponentsFunction<? extends Number>> children) {
		return new GreaterEq(children);
	}

	public static IComponentsFunction<Boolean> less(final List<IComponentsFunction<? extends Number>> children) {
		return new Less(children);
	}

	public static IComponentsFunction<Boolean> lessEq(final List<IComponentsFunction<? extends Number>> children) {
		return new LessEq(children);
	}

	public static IComponentsFunction<Boolean> eq(final List<IComponentsFunction<? extends Number>> children) {
		return new AllEq(children);
	}
	
	public static IComponentsFunction<Number> round(final IComponentsFunction<Number> value,
			final IComponentsFunction<Number> precision, final XRoundDirection direction) {
		return new Round(value, precision, direction);
	}
}
