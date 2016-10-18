package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

/**
 * Cookers, as defined in BREDEM chapter 5 MODULO assumption 11.
 * 
 * This includes its own demand generator, which is a bit against the split where the calculator knows how to generate
 * demands, and the house elements know how to satisfy them, but there is no way to break BREDEM down other than like this.
 * 
 * @author hinton
 *
 */
public class SimpleCooker extends AbstractCooker {
	private EnergyType sourceEnergyType;

	public SimpleCooker(final EnergyType energyType) {
		setSourceEnergyType(energyType);
	}

	public SimpleCooker() {
	}

	/**
	 * @return The {@link EnergyType} which power this cooker.
	 */
	public EnergyType getSourceEnergyType() {
		return sourceEnergyType;
	}

	public void setSourceEnergyType(final EnergyType sourceEnergyType) {
		this.sourceEnergyType = sourceEnergyType;
	}

	@Override
	protected void increaseFuelDemand(final IInternalParameters parameters, final IEnergyState state, final double demand) {
		state.increaseDemand(sourceEnergyType, demand);
	}
	
	@Override
	public String toString() {
		return sourceEnergyType + " cooker";
	}
}
