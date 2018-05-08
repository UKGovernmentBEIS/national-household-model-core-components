package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

/**
 * These constants are taken from SAP 2009 tables 2, 2a, 2b.
 *
 * @author hinton
 *
 */
@ConstantDescription("Constants pertaining to the cylinder loss model, drawn from SAP tables 2, 2a, 2b.")
public enum CylinderConstants implements IConstant {
    /*
	BEISDOC
	NAME: Cylinder loss constant
	DESCRIPTION: The constant term of the cylinder loss factor formula.
	TYPE: value
	UNIT: W
	SAP: Table 2 footnote 1
        SAP_COMPLIANT: Yes
	BREDEM: 2.2B.a
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	NOTES: Scaled by (1000/24) to convert from kWh/day to W.
	ID: cylinder-loss-constant
	CODSIEB
     */
    @ConstantDescription("The constant term in all loss factor equations")
    LOSS_FACTOR_CONSTANT_TERM(0.20833333),
    /*
	BEISDOC
	NAME: Cylinder loss loose jacket terms
	DESCRIPTION: The remaining two numeric terms of the cylinder loss factor formula for mineral wool jacket insulation.
	TYPE: value
	UNIT: ???
	SAP: Table 2 footnote 1
        SAP_COMPLIANT: Yes
	BREDEM: 2.2B.a
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	NOTES: The first term must be scaled by (1000/24) to convert from kWh/day to W.
	ID: cylinder-loss-loose-jacket-terms
	CODSIEB
     */
    @ConstantDescription("The loss factor equation terms for loose jacket cylinders")
    LOOSE_JACKET_FACTORS(73.33333, 12.8),
    /*
	BEISDOC
	NAME: Cylinder loss factor foam terms
	DESCRIPTION: The remaining two numeric terms of the cylinder loss factor formula for factory foam insulation.
	TYPE: value
	UNIT: ???
	SAP: Table 2 footnote 1
        SAP_COMPLIANT: Yes
	BREDEM: 2.2B.a
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	NOTES: The first term must be scaled by (1000/24) to convert from kWh/day to W.
	ID: cylinder-loss-factory-foam-terms
	CODSIEB
     */
    @ConstantDescription("The loss factor equation terms for factory jacket cylinders")
    FACTORY_JACKET_FACTORS(22.9167, 4.0),
    /*
	BEISDOC
	NAME: Volume factor terms
	DESCRIPTION: The terms of the volume loss factor formula.
	TYPE: value
	UNIT: Dimensionless
	SAP: (52), Table 2a footnote 2
        SAP_COMPLIANT: Yes
	BREDEM: 2.2B.b
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: volume-factor-terms
	CODSIEB
     */
    @ConstantDescription("The terms for the volume factor equation")
    VOLUME_FACTOR_TERMS(120, 1 / 3.0),
    /*
	BEISDOC
	NAME: Basic Temperature Factor
	DESCRIPTION: The storage temperature factor used in most cases
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b
        SAP_COMPLIANT: Yes
	BREDEM: Table 9
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: basic-temperature-factor
	CODSIEB
     */
    @ConstantDescription("The storage temperature factor used in most cases")
    TEMPERATURE_FACTOR_BASIC(0.6),
    /*
	BEISDOC
	NAME: Temperature Factor No Thermostat Multiplier
	DESCRIPTION: The storage temperature factor multiplier when there is no thermostat.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b (footnote a)
        SAP_COMPLIANT: Yes
	BREDEM: Table 9 (footnote a)
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: temperature-factor-no-thermostat-multiplier
	CODSIEB
     */
    @ConstantDescription("The storage temperature factor multiplier when there is no thermostat")
    TEMPERATURE_FACTOR_NO_THERMOSTAT_MULTIPLIER(1.3),
    /*
	BEISDOC
	NAME: Temperature Factor Separate Timer Multiplier
	DESCRIPTION: The storage temperature factor multiplier when there is a separate hot water timer
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b (footnote b)
        SAP_COMPLIANT: Yes
	BREDEM: Table 9 (footnote b)
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: temperature-factor-separate-timer-multiplier
	CODSIEB
     */
    @ConstantDescription("The storage temperature factor used when there is a separate hot water timer")
    TEMPERATURE_FACTOR_SEPARATE_HW_TIMER(0.9),
    /*
	BEISDOC
	NAME: Storage Combi Primary Store Temperature Factor
	DESCRIPTION: The storage temperature factor terms for a combi with store in primary circuit.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b (storage combi primary store)
        SAP_COMPLIANT: Yes
	BREDEM: Table 9 (storage combi primary store)
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants.
	ID: temperature-factor-storage-combi-primary-store-terms
	CODSIEB
     */
    @ConstantDescription("The storage temperature factor terms for a combi with store in primary circuit")
    TEMPERATURE_FACTOR_PRIMARY_STORAGE_COMBI(0.82, 0.0022, 115),
    /*
	BEISDOC
	NAME: Storage Combi Secondary Store Temperature Factor
	DESCRIPTION: The storage temperature factor terms for a combi with store in secondary circuit.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b (storage secondary store)
        SAP_COMPLIANT: Yes
	BREDEM: Table 9 (storage combi secondary store)
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants.
	ID: temperature-factor-storage-combi-secondary-store-terms
	CODSIEB
     */
    @ConstantDescription("The STF terms for a combi with store in secondary circuit")
    TEMPERATURE_FACTOR_SECONDARY_STORAGE_COMBI(0.6, 0.0016, 115),
    /*
	BEISDOC
	NAME: Gas CPSU Storage Temperature Factor
	DESCRIPTION: The STF for a gas CPSU
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b
        SAP_COMPLIANT: Yes
	BREDEM: Table 9
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: cpsu-gas-storage-temperature-factor
	CODSIEB
     */
    @ConstantDescription("The STF for a gas CPSU")
    TEMPERATURE_FACTOR_GAS_CPSU(1.08),
    /*
	BEISDOC
	NAME: Electric CPSU Storage Temperature Factor
	DESCRIPTION: The STF for an electric CPSU
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 2b
        SAP_COMPLIANT: Yes
	BREDEM: Table 9
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: cpsu-electric-storage-temperature-factor
	CODSIEB
     */
    @ConstantDescription("The storage temperature factor for an electric CPSU.")
    TEMPERATURE_FACTOR_ELECTRIC_CPSU(1.0);

    private final double[] values;

    CylinderConstants(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if (clazz.isAssignableFrom(double[].class)) {
            if (values == null) {
                throw new RuntimeException(this
                        + " cannot be read as a double[]");
            }
            return clazz.cast(values);
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (values.length != 1) {
                throw new RuntimeException(this + " cannot be read as a double");
            }
            return clazz.cast(values[0]);
        } else {
            throw new RuntimeException(this + " cannot be read as a "
                    + clazz.getSimpleName());
        }
    }
}
