package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.EnumSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class SapScoreFunction extends FuelCostFunction {

    /*
	BEISDOC
	NAME: Floor Area Offset
	DESCRIPTION: This value is added to the floor area as part of the energy cost rating calculation
	TYPE: value
	UNIT: m^2
	SAP: Section 13 (equation 9)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: floor-area-offset
	CODSIEB
     */
 /*
     * @section energy cost rating
     * @item floor area offset
     * @reference SAP9.92 p35 equation (9)
     */
    private static final double FLOOR_AREA_OFFSET = 45;

    /*
	BEISDOC
	NAME: ECF Threshold
	DESCRIPTION: The ECF score under which we will use the alternative Energy Cost Rating formula.
	TYPE: value
	UNIT: ???
	SAP: Section 13 (equation 9)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: ecf-threshold
	CODSIEB
     */
 /*
     * @section energy cost rating
     * @item ECF threshold
     * @reference SAP9.92 p35 equations (10) and (11)
     */
    private static final double ECF_THRESHOLD = 3.5;

    /*
	BEISDOC
	NAME: High ECF Multiplier
	DESCRIPTION: The multiplier applied to log10(ECF) when we are calculating the Energy Cost Rating and the ECF is greater than or equal to the ECF threshold.
	TYPE: value
	UNIT: ???
	SAP: Section 13 (equation 10)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: high-ecf-multiplier
	CODSIEB
     */
 /*
     * @section energy cost rating
     * @item multiplier of log term for large ECF
     * @reference SAP2012 9.92 Section 13 equation (10)
     */
    private static final double HIGH_ECF_MULTIPLIER = 121d;

    /*
	BEISDOC
	NAME: High ECF First Term
	DESCRIPTION: The constant value at the start of the Energy Cost Rating formula when the ECF is greater than or equal to the ECF threshold.
	TYPE: value
	UNIT: ???
	SAP: Section 13 (equation 10)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: high-ecf-first-term
	CODSIEB
     */
 /*
     * @section energy cost rating
     * @item constant term for large ECF
     * @reference SAP9.92 p35 equation (10)
     */
    private static final double HIGH_ECF_FIRST_TERM = 117d;

    /*
	BEISDOC
	NAME: Low ECF First Term
	DESCRIPTION: The constant value at the start of the Energy Cost Rating formula when the ECF is less than the ECF threshold.
	TYPE: value
	UNIT: ???
	SAP: Section 13 (equation 11)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: low-ecf-first-term
	CODSIEB
     */
 /*
     * @section energy cost rating
     * @item constant term for small ECF
     * @reference SAP9.92 p35 equation (11)
     */
    private static final double LOW_ECF_FIRST_TERM = 100d;

    /*
	BEISDOC
	NAME: Low ECF Multiplier
	DESCRIPTION: The multiplier applied to ECF when we are calculating the Energy Cost Rating and the ECF is less than the ECF threshold.
	TYPE: value
	UNIT: ???
	SAP: Section 13 (equation 11)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
	ID: low-ecf-multiplier
	CODSIEB
    /*
     * @section energy cost rating
     * @item multiplicative term for small ECF
     * @reference SAP9.92 p35 equation (11)
     */
    private static final double LOW_ECF_MULTIPLIER = 13.95d;

    private final double deflator;

    final IDimension<StructureModel> structure;

    @AssistedInject
    public SapScoreFunction(
            final Set<IDimension<?>> allDimensions,
            final IDimension<IEnergyMeter> meterDimension,
            final IDimension<IPowerTable> powerDimension,
            final IDimension<StructureModel> structureDimension,
            final IDimension<ITariffs> tariffs,
            @Assisted final double deflator) {
        super(allDimensions,
                meterDimension,
                powerDimension,
                tariffs,
                Optional.<FuelType>absent(),
                EnumSet.of(ServiceType.APPLIANCES, ServiceType.COOKING)
        );
        structure = structureDimension;
        this.deflator = deflator;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final double fuelBill = super.compute(scope, lets);
        final double floorArea = scope.get(structure).getFloorArea();

        return clampedScore(fuelBill, floorArea, deflator);
    }

    protected static double clampedScore(final double fuelBill, final double floorArea, final double deflator) {
        return clamp(score(ecf(floorArea, fuelBill, deflator)));
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return super.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return super.getChangeDates();
    }

    protected static double ecf(final double tfa, final double totalCost, final double deflator) {
        /*
		BEISDOC
		NAME: ECF
		DESCRIPTION: The ECF from the Energy Cost Rating calculation/
		TYPE: formula
		UNIT: ???
		SAP: (257), Section 13 (equation 9)
                SAP_COMPLIANT: Yes
                BREDEM_COMPLIANT: N/A - out of scope
		DEPS: energy-cost-deflator,floor-area-offset,dwelling-floor-area,fuel-cost
		NOTES: The fuel cost used excludes the per-unit cost of energy for appliances and cooking.
		ID: ecf
		CODSIEB
         */
        return deflator * totalCost / (tfa + FLOOR_AREA_OFFSET);
    }

    /*
     * @section energy cost rating
     * @item implementation of equations (9) (10) (11)
     * @reference SAP9.92 p35
     */
    protected static double score(final double ecf) {
        /*
		BEISDOC
		NAME: Energy Cost Rating
		DESCRIPTION: The Energy Cost Rating calculation.
		TYPE: formula
		UNIT: ???
		SAP: (258), Section 13 (Equations 10 and 11)
                SAP_COMPLIANT: Yes
                BREDEM_COMPLIANT: N/A - out of scope
		DEPS: ecf,ecf-threshold,high-ecf-first-term,high-ecf-multiplier,low-ecf-first-term,low-ecf-multiplier
		GET: house.fuel-cost-index
		NOTES: We don't implement the SAP band function in the NHM. To find the SAP band for a dwelling, take this number and perform the lookup in Table 14.
		ID: energy-cost-rating
		CODSIEB
         */
        if (ecf >= ECF_THRESHOLD) {
            return HIGH_ECF_FIRST_TERM - HIGH_ECF_MULTIPLIER * Math.log10(ecf);
        } else {
            return LOW_ECF_FIRST_TERM - LOW_ECF_MULTIPLIER * ecf;
        }
    }

    /*
     * @section energy cost rating
     * @item rounding and clamping of sap score
     * @reference SAP9.92 p35 section 13 paragraph 4
     */
    protected static double clamp(final double score) {
        if (score < 1) {
            return 1;
        } else {
            return Math.round(score);
        }
    }
}
