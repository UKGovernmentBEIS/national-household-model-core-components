package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

public class ElectricCooker extends AbstractCooker {
	public ElectricCooker() {
	
	}

	@Override
	protected void increaseFuelDemand(final IInternalParameters parameters, final IEnergyState state, final double demand) {
		state.increaseElectricityDemand(
				parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class)
				[parameters.getTarrifType().ordinal()]
				, demand);
	}
	
	@Override
	public String toString() {
		return "Electric cooker";
	}
}
