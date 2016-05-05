package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This is a rule which computes a simple polynomial; since this is likely to be used
 * for calibration more than the more generic option, it makes sense to special-case it 
 * out and make it fast.
 * 
 * @author hinton
 *
 */
public class PolynomialRule extends AbstractNamed implements ICalibrationRule {
	private static final ILets TIME_BINDING_LETS = ILets.EMPTY.withBinding(ITime.TIME_KEY, XForesightLevel.Calibration);
	private final IComponentsFunction<Number>[] terms;
	private final Set<IDimension<?>> dependencies = new HashSet<>();
	
	@SuppressWarnings("unchecked")
	public PolynomialRule(final List<IComponentsFunction<Number>> terms) {
		super();
		this.terms = new IComponentsFunction[terms.size()];
		for (int i = 0; i<this.terms.length; i++) {
			this.terms[i] = terms.get(i);
			this.dependencies.addAll(this.terms[i].getDependencies());
		}
	}

	@Override
	public int getGeneration(final IState state, final IDwelling dwelling) {
		int acc = 0;
		for (final IDimension<?> dim : dependencies) {
			acc += state.getGeneration(dim, dwelling);
		}
		return acc;
	}

	@Override
	public ICalibration getCalibration(final IState state, final IDwelling dwelling) {
		final IComponentsScope scope = state.detachedScope(dwelling);
		final double[] coefficients = new double[terms.length];
		for (int i = 0; i<terms.length; i++) {
			coefficients[i] = terms[i].compute(scope, TIME_BINDING_LETS).doubleValue();
		}
		return new Cal(getIdentifier(), coefficients);
	}

	static class Cal implements ICalibration, IIdentified {
		final double[] coefficients;
        final Name name;
        
		Cal(final Name name, final double[] coefficients) {
			super();
            this.name = name;
			this.coefficients = coefficients;
		}

        @Override
        public Name getIdentifier() {
            return this.name;
        }
        
		@Override
		public float compute(
				final IPowerTable uncalibrated, 
				final IState state,
				final IDwelling dwelling, 
				final FuelType fuelType) {
			float sum = 0;
			final float x = uncalibrated.getPowerByFuel(fuelType);
			float pow = 1;
			for (int i = 0; i<coefficients.length; i++) {
				sum += pow * coefficients[i];
				pow = pow * x;
			}
			return sum;
		}
	
		@Override
		public int hashCode() {
			return Arrays.hashCode(coefficients);
		}
		
		@Override
		public boolean equals(final Object obj) {
			return obj instanceof Cal && Arrays.equals(((Cal)obj).coefficients, coefficients);
		}
	}
}
