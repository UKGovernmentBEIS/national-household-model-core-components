package uk.org.cse.nhm.simulation.measure.insulation;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class WallInsulationMeasure extends InsulationMeasure {

    private final WallInsulationType insulationType;

    private final IDimension<StructureModel> structureDimension;
    private final IComponentsFunction<Number> thermalResistancePerMil;
    private final Optional<IComponentsFunction<Number>> uValueOverride;
    private final double thickness;

    private final Predicate<IWall> suitability;

    @Inject
    public WallInsulationMeasure(
            final IDimension<StructureModel> structureDimension,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("thickness") final double insulationThickness,
            @Assisted("rvalue") final IComponentsFunction<Number> thermalResistancePerMil,
            @Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValueOverride,
            @Assisted final Predicate<IWall> suitability,
            @Assisted final WallInsulationType insulationType
    ) {
        super(capitalCostFunction, TechnologyType.wallInsulation(insulationType));
        this.thickness = insulationThickness;
        this.thermalResistancePerMil = thermalResistancePerMil;
        this.uValueOverride = uValueOverride;
        this.insulationType = insulationType;
        this.structureDimension = structureDimension;
        this.suitability = suitability;
    }

    private static abstract class WallModifier implements IModifier<StructureModel> {

        private double quantityInstalled = 0;
        private final Predicate<IWall> suitability;

        private WallModifier(final Predicate<IWall> suitability) {
            this.suitability = suitability;
        }

        @Override
        public boolean modify(final StructureModel newCase) {
            for (final Storey storey : newCase.getStoreys()) {
                for (final IMutableWall wall : storey.getWalls()) {
                    if (suitability.apply(wall)) {
                        quantityInstalled += wall.getArea();
                        mangle(wall);
                    }
                }
            }
            return true;
        }

        abstract void mangle(final IMutableWall wall);

        public double getQuantityInstalled() {
            return quantityInstalled;
        }
    }

    private static class ResistanceModifier extends WallModifier {

        private final double resistancePerMil;
        private final double thickness;
        private final WallInsulationType insulationType;

        ResistanceModifier(
                final double resistancePerMil, final double thickness,
                final WallInsulationType insulationType,
                final Predicate<IWall> suitability) {
            super(suitability);
            this.resistancePerMil = resistancePerMil;
            this.thickness = thickness;
            this.insulationType = insulationType;
        }

        @Override
        void mangle(final IMutableWall wall) {
            wall.addInsulation(insulationType, thickness, resistancePerMil);
        }
    }

    private static class UValueModifier extends WallModifier {

        private final double uValue;
        private final double thickness;

        private final WallInsulationType insulationType;

        UValueModifier(
                final double uValue, final double thickness,
                final WallInsulationType insulationType,
                final Predicate<IWall> suitability) {
            super(suitability);
            this.uValue = uValue;
            this.thickness = thickness;
            this.insulationType = insulationType;
        }

        @Override
        void mangle(final IMutableWall wall) {
            wall.setWallInsulationThicknessAndAddOrRemoveInsulation(insulationType, thickness);
            wall.setUValue(uValue);
        }
    }

    @Override
    public boolean doApply(final ISettableComponentsScope components, final ILets lets) throws NHMException {
        if (isSuitable(components, lets)) {
            final WallModifier modifier;
            if (uValueOverride.isPresent()) {
                final double newUvalue = uValueOverride.get().compute(components, lets).doubleValue();
                modifier = new UValueModifier(newUvalue, thickness, insulationType, suitability);
            } else {
                final double rValuePerMil = thermalResistancePerMil.compute(components, lets).doubleValue();
                modifier = new ResistanceModifier(rValuePerMil, thickness, insulationType, suitability);
            }

            components.modify(structureDimension, modifier);

            super.addCapitalCosts(components, lets, modifier.getQuantityInstalled());

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope components, final ILets lets) {
        for (final Storey storey : components.get(structureDimension).getStoreys()) {
            for (final IWall wall : storey.getImmutableWalls()) {
                if (suitability.apply(wall)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }
}
