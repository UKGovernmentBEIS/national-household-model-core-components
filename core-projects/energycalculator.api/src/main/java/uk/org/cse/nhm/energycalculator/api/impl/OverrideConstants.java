package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.IConstant;
import uk.org.cse.nhm.energycalculator.api.IConstants;

/**
 * An {@link IConstants} which wraps another {@link IConstants}, overriding enum
 * value lookups from a map from {@link Enum#name()} to a value. The values in
 * the map must either be doubles, or sequences of doubles separated by commas.
 * <p>
 * Because the class doesn't know about what enums might be used with it, it
 * can't tell you when the override value keys have spelling mistakes in them.
 * Similarly it can't fail-fast on construction, but will throw illegal argument
 * exceptions if there is a problem with the bound data when it it requested.
 * </p>
 * <p>
 * Consequently, it's probably a good idea to do some extra sanity-checking of
 * the inputs before you use the class
 * </p>
 * 
 * @author hinton
 * 
 */
public class OverrideConstants implements IConstants {
	private final IConstants delegate;
	private final Map<Enum<?>, Object> overrides = new HashMap<Enum<?>, Object>();

	public OverrideConstants(IConstants delegate,
			final Map<Enum<?>, Object> seededValues) {
		this.delegate = delegate;
		this.overrides.putAll(seededValues);
	}

	@Override
	public <T, Q extends Enum<Q> & IConstant> T get(Q key, Class<T> clazz) {
		if (overrides.containsKey(key)) {
			try {
				final Object o = overrides.get(key);
				if (clazz.isInstance(o)) {
					return clazz.cast(overrides.get(key));
				} else if (Double.class.isInstance(o) && clazz.isAssignableFrom(double[].class)) {
					return clazz.cast(new double[] {(Double) o});
				} else {
					throw new RuntimeException("Can only override double constant values");
				}
			} catch (final ClassCastException cce) {
				throw new RuntimeException("Key " + key + " overridden with the wrong type of value", cce);
			}
		} else {
			return delegate.get(key, clazz);
		}
	}

	@Override
	public <Q extends Enum<Q> & IConstant> double get(Q key) {
		return get(key, Double.class);
	}

	@Override
	public <Q extends Enum<Q> & IConstant> double get(Q key, int index) {
		return get(key, double[].class)[index];
	}

	@Override
	public <Q extends Enum<Q> & IConstant> double get(Q key, Enum<?> index) {
		return get(key, index.ordinal());
	}
}
