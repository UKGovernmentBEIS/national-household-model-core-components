package uk.org.cse.nhm.simulation.reporting.two;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Joiner;

import uk.org.cse.nhm.simulation.reporting.aggregates.NTile;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.utility.DeduplicatingMap.Deduplicator;

public abstract class Accumulator extends AbstractNamed {
	private final boolean hasExplicitName;
	private final String sname;
	String uniqueName;
	
	Accumulator(final String sname, boolean hasExplicitName) {
		super();
		this.hasExplicitName = hasExplicitName;
		this.sname = sname;
	}

	public interface IAccumulation {
		public Accumulator source();
		public void put(final double w, final Object v);
		public double get();
		public void reset();
	}
	
	static abstract class DoubleAccumulation implements IAccumulation {
		@Override
		public final void put(double w, Object d) {
			if (d instanceof Number) {
				doPut(w, ((Number) d).doubleValue());
			}
		}

		protected abstract void doPut(double w, double doubleValue);
	}
	
	public final IAccumulation start() {
		final IAccumulation a = doStart();
		a.reset();
		return a;
	}
	
	public void takeName(String columnName, Deduplicator<Object> names) {
		if (hasExplicitName) {
			this.uniqueName = names.add(this, getIdentifier().getName());
		} else {
			this.uniqueName = names.add(this, String.format("%s-of-%s", sname, columnName));
		}
	}
	
	protected abstract IAccumulation doStart();
	
	public static class Mean extends Accumulator {
		public Mean(boolean hasExplicitName) {
			super("mean", hasExplicitName);
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				double sx, sw;
				
				@Override
				public Accumulator source() { return Mean.this; }
				@Override
				public void reset() { sx = 0; sw = 0; }
				@Override
				public void doPut(double w, double d) { sx += (d*w); sw += w; }
				@Override
				public double get() { return sx == 0 ? 0 : sx / sw; }
			};
		}
	}
	
	public static class Min extends Accumulator {
		public Min(boolean hasExplicitName) {
			super("min", hasExplicitName);
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				double m;
				@Override
				public Accumulator source() { return Min.this; }
				@Override
				public void reset() { m = Double.POSITIVE_INFINITY; }
				@Override
				public void doPut(double w, double d) { m = Math.min(m, d); }
				@Override
				public double get() { return m; }
			};
		}
	}
	
	public static class Max extends Accumulator {
		public Max(boolean hasExplicitName) {
			super("max", hasExplicitName);
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				double m;
				@Override
				public Accumulator source() { return Max.this; }
				@Override
				public void reset() { m = Double.NEGATIVE_INFINITY; }
				@Override
				public void doPut(double w, double d) { m = Math.max(m, d); }
				@Override
				public double get() { return m; }
			};
		}
	}
	
	public static class Sum extends Accumulator {
		public Sum(boolean hasExplicitName) {
			super("sum", hasExplicitName);
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				double s;
				@Override
				public Accumulator source() { return Sum.this; }
				@Override
				public void reset() { s = 0; }
				@Override
				public void doPut(double w, double d) { s += w*d; }
				@Override
				public double get() { return s; }
			};
		}
	}
	
	/**
	 * medians cannot be computed in a streaming manner, so we have to store them all.
	 * a bit pants.
	 */
	public static class Tile extends Accumulator {
		protected final double p;
		
		public Tile(boolean b, double p) {
			super(String.format("%.2f-tile", p), b);
			this.p = p;
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				List<double[]> values = new ArrayList<>();
				@Override
				public Accumulator source() { return Tile.this; }
				@Override
				public void reset() { values.clear(); }
				@Override
				public void doPut(double w, double d) { values.add( new double[] {d, w} ); }
				@Override
				public double get() { return NTile.evaluate(p, values.toArray(new double[values.size()][])); }
			};
		}
	}
	
	/**
	 * Algorithm after West (1979), D. H. D. West (1979). Communications of the ACM, 22, 9, 532-535: Updating Mean and Variance Estimates: An Improved Method
	 */
	public static class Variance extends Accumulator {
		public Variance(boolean hasExplicitName) {
			super("variance", hasExplicitName);
		}

		@Override
		public IAccumulation doStart() {
			return new DoubleAccumulation() {
				double sumweight;
				double mean;
				double mean2;
				double count;
				
				@Override
				public Accumulator source() { return Variance.this; }
				@Override
				public void reset() { count = sumweight = mean = mean2 = 0; }
				@Override
				public void doPut(double weight, double x) { 
					double temp = weight + sumweight;
					double delta = x - mean;
					double R = delta * weight / temp;
					mean = mean + R;
					mean2 = mean2 + sumweight * delta * R;
					sumweight = temp;
					count++;
				}
				@Override
				public double get() { 
					final double variance_n = mean2 / sumweight;
					return variance_n * (count / (count-1));
				}
			};
		}
	}
	
	public static class Count extends Accumulator {
		public Count(boolean hasExplicitName) {
			super("count", hasExplicitName);
		}

		@Override
		protected IAccumulation doStart() {
			return new IAccumulation() {
				double swTrue;
				@Override
				public Accumulator source() {
					return Count.this;
				}
				
				@Override
				public void reset() {
					swTrue = 0;
				}
				
				@Override
				public void put(double w, Object d) {
					if (d instanceof Boolean) {
						if ((Boolean) d) {
							swTrue += w;
						}
					} else if (d instanceof Number) {
						if (((Number) d).doubleValue() != 0) {
							swTrue ++;
						}
					}
				}
				
				@Override
				public double get() {
					return swTrue;
				}
			};
		}
	}
	
	public static class CountIs extends Accumulator {
		protected final HashSet<String> compare;

		public CountIs(boolean hasExplicitName, List<String> list) {
			super(Joiner.on("-or-").join(list) + "-contains-", hasExplicitName);
			
			this.compare = new HashSet<String>();
			
			for (final String s : list) {
				compare.add(s.toLowerCase());
			}	
		}

		@Override
		protected IAccumulation doStart() {
			return new IAccumulation() {
				double swTrue;
				
				@Override
				public Accumulator source() {
					return CountIs.this;
				}
				
				@Override
				public void reset() {
					swTrue = 0;
				}
				
				@Override
				public void put(double w, Object d) {
					final String s = String.valueOf(d);
					if (compare.contains(s.toLowerCase())) {
						swTrue += w;
					}
				}
				
				@Override
				public double get() {
					return swTrue;
				}
			};
		}
	}

	
}
