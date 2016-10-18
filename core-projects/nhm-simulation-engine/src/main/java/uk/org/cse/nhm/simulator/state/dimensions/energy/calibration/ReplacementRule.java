package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This is the more expensive kind of rule, which fully computes an
 * NHM function to work out energy use.
 * 
 * Consequently the calibration stored is just the actual result.
 * @author hinton
 *
 */
public class ReplacementRule extends AbstractNamed implements ICalibrationRule {
	private static final ILets TIME_BINDING_LETS = ILets.EMPTY.withBinding(ITime.TIME_KEY, XForesightLevel.Calibration);
	private final IComponentsFunction<Number> function;
	
	public ReplacementRule(final IComponentsFunction<Number> function) {
		super();
		this.function = function;
	}

	@Override
	public int getGeneration(final IState state, final IDwelling dwelling) {
		int acc = 0;
		for (final IDimension<?> dim : function.getDependencies()) {
			acc += state.getGeneration(dim, dwelling);
		}
		return acc;
	}

	@Override
	public ICalibration getCalibration(final IState state, final IDwelling dwelling) {
		return new Cal(getIdentifier(), function.compute(state.detachedScope(dwelling), TIME_BINDING_LETS).floatValue());
	}
	
	static class Cal implements ICalibration, IIdentified {
		private final float value;
        final Name name;
        

		Cal(final Name name, final float value) {
			super();
			this.value = value;
            this.name = name;
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
			return value;
		}
		
		@Override
		public int hashCode() {
			return ((Float) value).hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			return (obj instanceof Cal) && ((Cal) obj).value == value;
		}
	}
}
