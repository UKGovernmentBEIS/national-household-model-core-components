package uk.org.cse.nhm.simulation.measure.boilers;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.definition.enums.XChangeDirection;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;

public class BoilerEfficiencyMeasure extends AbstractMeasure {

	private final IDimension<ITechnologyModel> techDimension;
	protected final ITechnologyOperations operations;
	
	private final Optional<IComponentsFunction<Number>> targetWinterEfficiency;
	private final Optional<IComponentsFunction<Number>> targetSummerEfficiency;
	private final XChangeDirection direction;
	private Optional<HeatingEfficiencyFunction> getWinterEfficiency;
	private Optional<HeatingEfficiencyFunction> getSummerEfficiency;

	@AssistedInject
	public BoilerEfficiencyMeasure(
			final IDimension<ITechnologyModel> techDimension,
			final ITechnologyOperations operations,
			@Assisted("winterEfficiency") final Optional<IComponentsFunction<Number>> winterEfficiency,
			@Assisted("summerEfficiency") final Optional<IComponentsFunction<Number>> summerEfficiency,
			@Assisted final XChangeDirection direction,
			@Assisted("getWinterEfficiency") final Optional<HeatingEfficiencyFunction> getWinterEfficiency,
			@Assisted("getSummerEfficiency") final Optional<HeatingEfficiencyFunction> getSummerEfficiency) {
		this.techDimension = techDimension;
		this.operations = operations;
		targetWinterEfficiency = winterEfficiency;
		targetSummerEfficiency = summerEfficiency;
		this.direction = direction;
		this.getWinterEfficiency = getWinterEfficiency;
		this.getSummerEfficiency = getSummerEfficiency;
		
		if (!(targetWinterEfficiency.isPresent() || getWinterEfficiency.isPresent())) {
			throw new RuntimeException("Neither targetWinterEfficiency nor a way to get the current summer efficiency were present. This is a programming error, please contact support.");
		}
		
		if (!(targetSummerEfficiency.isPresent() || getSummerEfficiency.isPresent())) {
			throw new RuntimeException("Neither targetSummerEfficiency nor a way to get the current summer efficiency were present. This is a programming error, please contact support.");
		}
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		double winterValue = targetWinterEfficiency.isPresent() ? 
				targetWinterEfficiency.get().compute(scope, lets).doubleValue() :
					getWinterEfficiency.get().compute(scope, lets);
				
		double summerValue = targetSummerEfficiency.isPresent() ?
				targetSummerEfficiency.get().compute(scope, lets).doubleValue() :
					getSummerEfficiency.get().compute(scope, lets);
				
		if (summerValue <= 0) {
			summerValue = winterValue + summerValue;
		}
		
		scope.modify(techDimension, new Mod(
				winterValue,
				summerValue
			));
		return true;
	}
	
	private class Mod implements IModifier<ITechnologyModel> {
		final double targetWinterEfficiency;
		final double targetSummerEfficiency;
		
		public Mod(double winterValue, double summerValue) {
			super();
			this.targetWinterEfficiency = winterValue;
			this.targetSummerEfficiency = summerValue;
		}

		@Override
		public boolean modify(final ITechnologyModel modifiable) {
			return operations.changeHeatingEfficiency(modifiable, targetWinterEfficiency, targetSummerEfficiency, 
					direction == XChangeDirection.Increase || direction == XChangeDirection.Set, 
					direction == XChangeDirection.Decrease || direction == XChangeDirection.Set);
		}
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return true;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
