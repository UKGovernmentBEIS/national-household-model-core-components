package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Miscellaneous constants to do with various heating systems. Some of these could do with tidying up into other places.")
public enum HeatingSystemConstants implements IConstant {

    /*
	BEISDOC
	NAME: Primary pipework coefficient
	DESCRIPTION: The number '14' which is used as a multiplier in the primary pipework losses calculation.
	TYPE: value
	UNIT: W
	SAP: Table 3
        SAP_COMPLIANT: Yes
	BREDEM: 2.2D
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/day to W (1000 / 24).
	ID: primary-pipework-coefficient
	CODSIEB
     */
    @ConstantDescription("The number '14' which is used as a multiplier in the primary pipework losses calculation (converted to W from kWh/day).")
    PRIMARY_PIPEWORK_COEFFICIENT(583.33),
    /*
	BEISDOC
	NAME: Primary pipework insulated multiplier
	DESCRIPTION: The value which is multiplied by pipework insulated fraction in the primary pipework losses calculation.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 3
        SAP_COMPLIANT: Yes
	BREDEM: 2.2D
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: primary-pipework-insulated-multiplier
	CODSIEB
     */
    @ConstantDescription("The value which is multiplied by pipework insulated fraction in the primary pipework losses calculation.")
    PRIMARY_PIPEWORK_INSULATED_MULTIPLIER(0.0091),
    /*
	BEISDOC
	NAME: Primary pipework uninsulated multiplier
	DESCRIPTION: The value which is multiplied by (1 - pipework insulated fraction) in the primary pipework losses calculation.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 3
        SAP_COMPLIANT: Yes
	BREDEM: 2.2D
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: primary-pipework-uninsulated-multiplier
	CODSIEB
     */
    @ConstantDescription("The value which is multiplied by (1 - pipework insulated fraction) in the primary pipework losses calculation.")
    PRIMARY_PIPEWORK_UNINSULATED_MULTIPLIER(0.0245),
    /*
	BEISDOC
	NAME: Primary pipework constant
	DESCRIPTION: The final constant term in the primary pipework losses calculation.
	TYPE: value
	UNIT: Dimensionless
	SAP: Table 3
        SAP_COMPLIANT: Yes
	BREDEM: 2.2D
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: primary-pipework-constant
	CODSIEB
     */
    @ConstantDescription("The final constant term in the primary pipework losses calculation.")
    PRIMARY_PIPEWORK_CONSTANT(0.0263),
    /*
	BEISDOC
	NAME: Hours per day primary hot lookup
	DESCRIPTION: The number of hours per day the primary pipework is hot (a) in winter without a cylinder thermostat, (b) in winter without a separate water heating timer and (c) otherwise.
	TYPE: value
	UNIT: Hours
	SAP: Table 3
        SAP_COMPLIANT: Yes
	BREDEM: Table 11
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: hours-per-day-primary-hot-lookup
	CODSIEB
     */
    @ConstantDescription("The number of hours per day the primary pipework is hot (a) in winter without a cylinder thermostat, (b) in winter without a separate water heating timer and (c) otherwise.")
    HOURS_PIPEWORK_HOT(11, 5, 3),
    /*
	BEISDOC
	NAME: Distribution Loss Factor
	DESCRIPTION: Distribution losses from a centrally heated system, as a proportion of hot water energy
	TYPE: value
	UNIT: Dimensionless
	SAP: (46)
        SAP_COMPLIANT: Yes
	BREDEM: 2.2A
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: Scaled up by dividing by 0.85. This is necessary because we are doing this calculation in reverse here.
	NOTES: In SAP and BREDEM, we subtract 15% distribution losses from an amount which already includes them.
	NOTES: In the NHM, we add (15% / 85%) = 17.65% distribution losses to an amount which does not include them.
	ID: distribution-loss-factor
	CODSIEB
     */
    @ConstantDescription("Distribution losses from a centrally heated system, as a proportion of hot water energy")
    CENTRAL_HEATING_DISTRIBUTION_LOSSES(0.15 / 0.85),
    /*
	BEISDOC
	NAME: Solar Primary Pipework Correction Factor
	DESCRIPTION: Solar primary pipework correction factor (by month)
	TYPE: value
	UNIT: Dimensionless
	SAP: (59), Table H4
        SAP_COMPLIANT: Yes
	BREDEM: 2.2D, Table 12
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: solar-primary-pipework-correction
	CODSIEB
     */
    @ConstantDescription("Solar primary pipework correction factor (by month)")
    CENTRAL_HEATING_SOLAR_PPCF(1, 1, 0.94, 0.70, 0.45, 0.44, 0.44, 0.48, 0.76, 0.94, 1, 1),
    @ConstantDescription("Electric CPSU low-rate heat constant")
    ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT(0.1456),
    @ConstantDescription("Electric CPSU temperature offset (SAP appendix F)")
    ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET(48),
    /*
	BEISDOC
	NAME: Combi loss factor hot water usage limit
	DESCRIPTION: Combi additional loss factor daily hot water usage limit
	TYPE: value
	UNIT: Litres/day
	SAP: Table 3a second section
        SAP_COMPLIANT: Yes
	BREDEM: Table 13 footnote a
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: combi-loss-water-usage-limit
	CODSIEB
     */
    @ConstantDescription("Combi additional loss factor daily hot water usage limit (SAP 2009 table 3a)")
    COMBI_HOT_WATER_USAGE_LIMIT(100),
    /*
	BEISDOC
	NAME: Instantaneous combi factor
	DESCRIPTION: Instantaneous combi without keep-hot facility loss factor
	TYPE: value
	UNIT: W
	SAP: Table 3a, row 1
        SAP_COMPLIANT: Yes
	BREDEM: Table 13, row 1
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (24 * 365.25)).
	ID: instantaneous-factor
	CODSIEB
     */
    @ConstantDescription("Instantaneous combi without KHF loss factor (SAP 2009 table 3a, row 1, converted from kWh/year to watts)")
    INSTANTANEOUS_COMBI_FACTOR(68.4477),
    /*
	BEISDOC
	NAME: Storage combi volume threshold
	DESCRIPTION: The threshold volume above which a storage combi boiler has no additional losses
	TYPE: value
	UNIT: Litres
	SAP: Table 3a, row 5
        SAP_COMPLIANT: Yes
	BREDEM: Table 13, row 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	ID: storage-combi-volume-threshold
	CODSIEB
     */
    @ConstantDescription("The threshold volume above which a storage combi boiler has no additional losses (SAP 2009 table 3a)")
    STORAGE_COMBI_VOLUME_THRESHOLD(55),
    /*
	BEISDOC
	NAME: Instantaneous keep-hot factor with timeclock
	DESCRIPTION: The additional wattage of combi losses when using a combi with a keep hot facility with a timeclock.
	TYPE: value
	UNIT: W
	SAP: Table 3a, row 2
        SAP_COMPLIANT: Yes
	BREDEM: Table 13, row 2
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (24 * 365.25)).
	ID: instantaneous-keep-hot-timeclock-factor
	CODSIEB
     */
    @ConstantDescription("The additional wattage of combi losses when using a combi with a keep hot facility with a timeclock (SAP 2009 table 3a, converted to watts)")
    INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK(68.4477),
    /*
	BEISDOC
	NAME: Instantaneous keep-hot factor without timeclock
	DESCRIPTION: The additional wattage of combi losses when using a combi with a keep hot facility without a timeclock.
	TYPE: value
	UNIT: W
	SAP: Table 3a, row 3
        SAP_COMPLIANT: Yes
	BREDEM: Table 13, row 3
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (24 * 365.25)).
	ID: instantaneous-keep-hot-factor
	CODSIEB
     */
    @ConstantDescription("The additional wattage of combi losses when using a combi with a keep hot facility without a timeclock (SAP 2009 table 3a, converted to watts)")
    INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK(102.671),
    /*
	BEISDOC
	NAME: Storage combi loss factor
	DESCRIPTION: The terms from the additional wattage equation for storage combis with store volume under 55l in SAP 2012 table 3a
	TYPE: value
	UNIT: W, Dimensionless, W
	SAP: Table 3a, row 5
        SAP_COMPLIANT: Yes
	BREDEM: Table 13, row 4
        BREDEM_COMPLIANT: Yes
	SET: context.energy-constants
	CONVERSION: From kWh/year to W (1000 / (24 * 365.25)) (does not apply to dimensionless 2nd term).
	ID: storage-combi-storage-loss-factor
	CODSIEB
     */
    @ConstantDescription("The terms from the additional wattage equation for storage combis with store volume under 55l in SAP 2009 table 3a (in SAP this is fu * (600 - (volume - 15) * 15) - these values are in the same order here)")
    STORAGE_COMBI_LOSS_TERMS(68.4477, 15, 1.711);

    private final double[] values;

    HeatingSystemConstants(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if (clazz.isAssignableFrom(double[].class)) {
            if (values == null) {
                throw new RuntimeException(this + " cannot be read as a double[]");
            }
            return clazz.cast(values);
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (values.length != 1) {
                throw new RuntimeException(this + " cannot be read as a double");
            }
            return clazz.cast(values[0]);
        } else {
            throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
        }
    }
}
