package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;

/**
 * Helper methods for things which can be connected to an {@link ICentralWaterSystem} - because there are a few things
 * like this which have common logic ({@link IHeatPump}, {@link IWarmAirCirculator} and {@link IBoiler}, for example) but 
 * should not share inheritance, I have moved some methods to here.
 * 
 * @author hinton
 *
 */
public class HotWaterUtilities {
	/**
	 * Get standard primary pipework losses for different kinds of {@link IHeatSource} and {@link IWarmAirSystem}
	 * used for DHW.
	 * 
	 * @param parameters
	 * @param primaryPipeworkIsInsulated
	 * @param tankPresentAndThermostatic
	 * @param primaryCorrectionFactor
	 * @return
	 */
	public static final double getPrimaryPipeworkLosses(IInternalParameters parameters,
			boolean primaryPipeworkIsInsulated,
			boolean tankPresentAndThermostatic, double primaryCorrectionFactor) {
		// primary pipework insulation is a property of the heating system
		// so to be honest maybe it belongs elsewhere.
		
		//TODO this does not cover the case where the tank is in the primary circuit, and so is a thermal store.
		
		/*
		BEISDOC
		NAME: Primary Pipework Losses
		DESCRIPTION: Losses due to the primary hot water pipework
		TYPE: formula
		UNIT: W
		SAP: (59)
		BREDEM: 2.2D 
		NOTES: TODO 
		ID: primary-pipework-losses
		CODSIEB
		*/
		if (primaryPipeworkIsInsulated && tankPresentAndThermostatic) {
			return parameters.getConstants().get(HeatingSystemConstants.GOOD_PRIMARY_PIPEWORK_LOSSES) *
				   primaryCorrectionFactor;
		} else if (!(primaryPipeworkIsInsulated || tankPresentAndThermostatic)) {
			return parameters.getConstants().get(HeatingSystemConstants.BAD_PRIMARY_PIPEWORK_LOSSES) *
					primaryCorrectionFactor;
		} else {
			return parameters.getConstants().get(HeatingSystemConstants.MED_PRIMARY_PIPEWORK_LOSSES) *
					primaryCorrectionFactor;
		}
	}
	
	/**
	 * Get the standard storage temperature factors for hot water tanks; this is defined by SAP 2009 table 2b
	 * 
	 * This doesn't cover special cases from that table like combis (the combi implementation does that).
	 * 
	 * @param parameters
	 * @param store
	 * @param storeInPrimaryCircuit
	 * @return
	 */
	public static final double getStorageTemperatureFactor(IInternalParameters parameters,
			IWaterTank store, boolean storeInPrimaryCircuit, boolean systemIsSeparatelyTimeControlled) {
		/*
		BEISDOC
		NAME: Storage Temperature Factor
		DESCRIPTION: A factor which depends on the temperature of the hot water store. It is a multiplier to storage losses.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (53), Table 2b
		BREDEM: 2.2B.C, Table 9
		DEPS: basic-temperature-factor,temperature-factor-no-thermostat-multiplier,temperature-factor-separate-timer-multiplier
		NOTES: TODO Handle case when store in primary circuit 
		ID: storage-temperature-factor
		CODSIEB
		*/
		final IConstants constants = parameters.getConstants();
		if (storeInPrimaryCircuit) {
			return 1; //TODO don't return 1 - this is probably a hot water only thermal store
		} else {
			double factor = constants.get(CylinderConstants.TEMPERATURE_FACTOR_BASIC);
			
			if (store.isThermostatFitted() == false) {
				factor *= constants.get(CylinderConstants.TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER);
			}
			
			if (systemIsSeparatelyTimeControlled) {
				factor *= constants.get(CylinderConstants.TEMPERATURE_FACTOR_SEPARATE_HW_TIMER);
			}
			
			return factor;
		}
	}
}
