package uk.org.cse.nhm.simulator.action.hypothetical;

import javax.inject.Inject;
import javax.inject.Named;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public class UseUncalibratedEnergyAction extends HypotheticalAction {
	private final IDimension<IPowerTable> uncalibratedPowerDimension;
	private final IDimension<IPowerTable> calibratedPowerDimension;
		
	@Inject
	UseUncalibratedEnergyAction(
			@Named("uncalibrated") 
			final IDimension<IPowerTable> uncalibratedPowerDimension,
			final IDimension<IPowerTable> calibratedPowerDimension) {
		super();
		this.uncalibratedPowerDimension = uncalibratedPowerDimension;
		this.calibratedPowerDimension = calibratedPowerDimension;
	}
	
	@Override
	protected boolean doApply(final IHypotheticalComponentsScope scope, final ILets lets) {
		scope.replace(calibratedPowerDimension, 
				new UncalibratedPowerShim(
						scope.getHypotheticalBranch(), 
						uncalibratedPowerDimension)
				);
		return true;
	}
}
