package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

/**
 * The interface for a heating system (which may have some things inside it)
 *
 * @author hinton
 *
 */
public interface IHeatingSystem {
	/**
	 * The amount in degrees c to increase the demand temperature
	 * @return
	 */
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters);

	/*
	BEISDOC
	NAME: Zone 2 control parameter
	DESCRIPTION: The zone two control parameter, indicating how controllable heat demand in zone two is
	TYPE: value
	UNIT: 0 or 1
	SAP: Table 4e (Control)
	NOTES: BREDEM instead uses Zone 2 Control Fraction, which we do not implement because of lack of information.
	CONVERSION: Type 1 maps to 0, while Types 2 and 3 map to 1
	ID: zone-2-control-parameter
	CODSIEB
	*/
	/**
	 * The zone two control parameter, indicating how controllable heat demand in zone two is
	 * @return
	 */
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters);

	/**
	 * The responsiveness, which controls how quickly the heating system causes the house to
	 * switch between the demand and background temperatures.
	 * @param energyCalculatorType
	 * @return
	 */
	public double getResponsiveness(final IConstants parameters, final EnergyCalculatorType energyCalculatorType, final ElectricityTariffType electricityTariffType);
}
