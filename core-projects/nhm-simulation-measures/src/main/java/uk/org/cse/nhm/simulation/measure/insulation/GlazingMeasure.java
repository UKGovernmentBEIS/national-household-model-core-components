package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.mode.SAPTables;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * A measure for installing double & triple glazing onto a proportion of the
 * windows in a house.
 *
 * @author tomw
 * @since 4.2.0
 */
public class GlazingMeasure extends AbstractMeasure implements
        IModifier<StructureModel> {

    private final IDimension<StructureModel> structureDimension;
    private final IComponentsFunction<Number> capitalCostFunction;
    private final Glazing glazing;
    private double quantityInstalled;

    @AssistedInject
    public GlazingMeasure(
            final IDimension<StructureModel> structureDimension,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("uvalue") final double uValue,
            @Assisted("light") final double lightTransmittance,
            @Assisted("gains") final double gainsTransmittance,
            @Assisted("framefactor") final double frameFactor,
            @Assisted final FrameType frameType,
            @Assisted final GlazingType glazingType,
            @Assisted final WindowInsulationType insulationType,
            @Assisted final WindowGlazingAirGap airGap
    ) {
        super();
        this.structureDimension = structureDimension;
        this.capitalCostFunction = capitalCostFunction;
        this.glazing = new Glazing(1d, glazingType, frameType);
        this.glazing.setFrameFactor(frameFactor);
        this.glazing.setUValue(uValue);
        this.glazing.setFrameFactor(frameFactor);
        this.glazing.setLightTransmissionFactor(lightTransmittance);
        this.glazing.setGainsTransmissionFactor(gainsTransmittance);
        this.glazing.setInsulationType(insulationType);
        this.glazing.setWindowGlazingAirGap(airGap);

        //BREDEMVisitor expects a u-value to be present and so we must try and set-one
        if (uValue == 0 && frameType != null && insulationType != null && airGap != null) {
            this.glazing.setUValue(SAPTables.Windows.uValue(frameType, glazingType, insulationType, airGap));
        }
    }

    /**
     * Modifies the given household structure to add the glazing to every
     * elevation of the given structure.
     *
     * @see
     * uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
     * @Assumption Here we assume that all windows are suitable for glazing.
     * @Assumption Here we assume that all houses have at least one elevation,
     * but the method will return false if not.
     * @return successfullyModified true if the given structure model had
     * glazings set for at least one elevation, false otherwise (i.e. there are
     * no elevations )
     */
    @Override
    public boolean modify(final StructureModel modifiable) {
        boolean modified = false;

        for (final Map.Entry<ElevationType, Elevation> e : modifiable.getElevations().entrySet()) {
            final Elevation elevation = e.getValue();

            elevation.setGlazings(ImmutableList.<Glazing>of(this.glazing.copy()));

            modified = true;
        }

        if (modified) {
            final AreaAccumulator a = new AreaAccumulator(EnumSet.of(AreaType.GlazingMetal, AreaType.GlazingUPVC, AreaType.GlazingWood));
            modifiable.accept(a);
            quantityInstalled = a.getTotalArea();
        }
        return modified;
    }

    @Override
    public boolean doApply(final ISettableComponentsScope components, final ILets lets)
            throws NHMException {

        this.quantityInstalled = 0;
        components.modify(structureDimension, this);
        addCapitalCosts(components, lets, this.quantityInstalled);

        return true;
    }

    /**
     * Returns true if the given household components are suitable for this
     * measure, false otherwsie
     *
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * ILets)
     * @Assumption We assume that all households have at least one glazable
     * opening
     */
    @Override
    public boolean isSuitable(final IComponentsScope components, final ILets lets) {
        // TODO: Does it have something to glaze e.g. windows?

        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    /**
     * Adds to the capex for this measure. Assumes no opex cost.
     *
     * @since 4.2.0
     * @param components
     * @param lets TODO
     * @param quantityInstalled
     * @Assumption Operational expenditure of installation measures is zero.
     */
    protected void addCapitalCosts(final ISettableComponentsScope components,
            final ILets lets, final double quantityInstalled) {
        final double opex = 0;
        components.addNote(SizingResult.suitable(quantityInstalled,
                Units.SQUARE_METRES));
        final double capex = capitalCostFunction.compute(components, lets).doubleValue();
        components.addNote(new TechnologyInstallationDetails(this,
                TechnologyType.glazing(glazing.getGlazingType()), quantityInstalled, Units.SQUARE_METRES, capex,
                opex));
        components.addTransaction(Payment.capexToMarket(capex));
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    /**
     * Returns the glazing associated with this measure for testing purposes.
     *
     * @since 4.2.0
     * @return
     */
    protected Glazing getGlazing() {
        return this.glazing;
    }
}
