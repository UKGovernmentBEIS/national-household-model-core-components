package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Constants relevant to community heating systems.")
public enum CommunityHeatingConstants implements IConstant {
    @ConstantDescription("Demand temperature adjustment for 2301, 2302")
    DEMAND_TEMPERATURE_ADJUSTMENT(0.3),
    /*
	BEISDOC
	NAME: Community Space Heating Efficiency Multipliers
	DESCRIPTION: The energy use multipliers for community space heating
	TYPE: value
	UNIT: Dimensionless
	SAP: (305), Table 4c(3)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: No
	SET: context.energy.constants
	ID: community-space-heating-energy-multipliers
	CODSIEB
     */
    @ConstantDescription("The space energy multiplier in SAP table 4c(3) for system codes 2301, 2302")
    HIGH_SPACE_USAGE_MULTIPLER(1.1),
    @ConstantDescription("The space energy multiplier in SAP table 4c(3) for system codes 2303, 2304, 2307, 2305, 2308, 2309")
    MEDIUM_SPACE_USAGE_MULTIPLER(1.05),
    @ConstantDescription("The space energy multiplier in SAP table 4c(3) for system code 2310, 2306")
    LOW_SPACE_USAGE_MULTIPLIER(1),
    /*
	BEISDOC
	NAME: Community Water Heating Efficiency Multipliers
	DESCRIPTION: The energy use multipliers for community water heating
	TYPE: value
	UNIT: Dimensionless
	SAP: (305a), Table 4c(3)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: No
	SET: context.energy.constants
	ID: community-water-heating-energy-multipliers
	CODSIEB
     */
    @ConstantDescription("The hot water energy multiplier in SAP table 4c(3) for systems 2301, 2302, 2303, 2304, 2307, 2305, and community DHW only with flat-rate charging")
    HIGH_WATER_USAGE_MULTIPLIER(1.05),
    @ConstantDescription("The hot water energy multiplier in SAP table 4c(3) for systems 2308, 2309, 2310, 2306")
    LOW_WATER_USAGE_MULTIPLIER(1),
    /*
	BEISDOC
	NAME: Community Dsitribution Loss Factor
	DESCRIPTION: The community heat distribution loss factor to be used if there is no information
	TYPE: value
	UNIT: Dimensionless
	SAP: (306), Appendix C (C3.1, final sentence), Table 12c
        SAP_COMPLIANT: No, see note
        BREDEM_COMPLIANT: No
	SET: context.energy-constants
	NOTES: We don't have the information to perform the lookup in Table 12c.
	NOTES: Adjusted to match CHM.
	ID: community-distribution-loss-factor
	CODSIEB
     */
    @ConstantDescription("The community heat distribution loss factor to be used if there is no information (appendix C3.1) - adjusted to match CHM's 1.1")
    DEFAULT_DISTRIBUTION_LOSS_FACTOR(1.1);

    private final double[] values;

    CommunityHeatingConstants(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(final Class<T> clazz) {
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
