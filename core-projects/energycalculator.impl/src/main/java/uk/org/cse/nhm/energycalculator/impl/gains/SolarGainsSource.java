package uk.org.cse.nhm.energycalculator.impl.gains;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/**
 * The energy transducer which provides solar gains; it sees {@link IFabricElement}s through {@link #addTransparentElement(IFabricElement)},
 * and accumulates their solar gains; then when {@link #generate(IEnergyState, IEnergyCalculatorParameters, ISpecificHeatLosses)} is invoked the
 * the accumulated gains are provided into the energy state.  
 * 
 * @author hinton
 *
 */
public class SolarGainsSource implements IEnergyTransducer {
	private final double REFLECTION_FACTOR;
	private final double[] OVERSHADING_FACTOR;
	/**
	 * The kind of gains this is producing from incident solar radiation. Typically {@link EnergyType#BACKGROUND_GAINS_1} or two.
	 */
	private final EnergyType gainsType;

	private static class Plane {
		public final double angleFromHorizontal;
		public final double angleFromNorth;
		
		public Plane(final double angleFromHorizontal, final double angleFromNorth) {
			this.angleFromHorizontal = angleFromHorizontal;
			this.angleFromNorth = angleFromNorth;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(angleFromHorizontal);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(angleFromNorth);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Plane other = (Plane) obj;
			if (Double.doubleToLongBits(angleFromHorizontal) != Double
					.doubleToLongBits(other.angleFromHorizontal))
				return false;
			if (Double.doubleToLongBits(angleFromNorth) != Double
					.doubleToLongBits(other.angleFromNorth))
				return false;
			return true;
		}
	}

	final Map<Plane, Double> totalFluxOnPlanes = new HashMap<Plane, Double>();
	
	/**
	 * @param constants the constants for this EnergyCalc impl.
	 * @param gainsType the gains to produce
	 * @param latitude the latitude we are at
	 * @param declination the solar declination at the moment
	 * @param horizontalSolarFlux the average horizontal solar flux
	 * @param verticalSolarFlux the average vertical solar flux
	 */
	public SolarGainsSource(
			final IConstants constants,
			final EnergyType gainsType) {
		this.gainsType = gainsType;
		OVERSHADING_FACTOR = constants.get(EnergyCalculatorConstants.SOLAR_GAINS_OVERSHADING, double[].class);
		REFLECTION_FACTOR = constants.get(EnergyCalculatorConstants.SOLAR_GAINS_REFLECTION_FACTOR);
	}
	
	public void addTransparentElement(final double visibleLightTransmittivity,
			final double solarGainTransmissivity,
			final double angleFromHorizontal,
			final double angleFromNorth,
			final OvershadingType overshading) {
		/*
		BEISDOC
		NAME: Solar gains effective transmission area 2
		DESCRIPTION: The effective solar gains transmission area after reflection and overshading are accounted for.
		TYPE: formula
		UNIT: m2
		SAP: (74-82)
		BREDEM: 5A
		DEPS: solar-gains-effective-transmission-area,overshading-factor,solar-reflection
		ID: solar-gains-effective-transmission-area-2
		CODSIEB
		*/
		final double t = solarGainTransmissivity *
				OVERSHADING_FACTOR[overshading.ordinal()] *
				REFLECTION_FACTOR;
		if (t > 0) {
			final Plane p = new Plane(angleFromHorizontal, angleFromNorth);
			
			if (totalFluxOnPlanes.containsKey(p)) {
				totalFluxOnPlanes.put(p, t + totalFluxOnPlanes.get(p));
			} else {
				totalFluxOnPlanes.put(p, t);
			}
		}
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.SOLAR_GAINS;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		/*
		BEISDOC
		NAME: Monthly solar gains
		DESCRIPTION: Total solar gains each month
		TYPE: type
		UNIT: W
		SAP: (74-83)
		BREDEM: 5A
		DEPS: solar-gains-effective-transmission-area-2, effective-solar-flux
		ID: monthly-solar-gains
		CODSIEB
		*/
		double solarGain = 0;
		
		final ISeasonalParameters climate = parameters.getClimate();
		
		for (final Map.Entry<Plane, Double> e : totalFluxOnPlanes.entrySet()) {
			final double flux = climate.getSolarFlux(e.getKey().angleFromHorizontal, e.getKey().angleFromNorth);
			solarGain += e.getValue() * flux;
		}
		
		state.increaseSupply(gainsType, solarGain);
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "The Sun";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
