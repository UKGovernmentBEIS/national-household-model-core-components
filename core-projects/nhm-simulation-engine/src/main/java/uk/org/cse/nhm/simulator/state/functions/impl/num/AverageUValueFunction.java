package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AverageUValueFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IDimension<StructureModel> structure;
    private final IDimension<BasicCaseAttributes> basic;
    private final IDimension<IHeatingBehaviour> heavingBehaviour;
    private final Set<AreaType> includedAreas;

    @Inject
    public AverageUValueFunction(
            final IDimension<StructureModel> structure,
            final IDimension<BasicCaseAttributes> basic,
            final IDimension<IHeatingBehaviour> heavingBehaviour,
            @Assisted final Set<AreaType> includedAreas) {
        this.structure = structure;
        this.basic = basic;
        this.heavingBehaviour = heavingBehaviour;
        this.includedAreas = includedAreas;
    }

    private static class Visitor implements IEnergyCalculatorVisitor {

        private double totalU = 0;
        private double totalA = 0;
        private final Set<AreaType> includedAreas;
        private final EnergyCalculatorType calculatorType;
        private final Country country;
        private final Band ageBand;

        private RoofConstructionType roofConstructionType;
        private Double roofInsulationThickness;
        private FloorConstructionType groundFloorConstructionType;
        private double insulationThickness;

        public Visitor(final Set<AreaType> includedAreas, final EnergyCalculatorType calculatorType, final Country country, final Band ageBand) {
            this.includedAreas = includedAreas;
            this.calculatorType = calculatorType;
            this.country = country;
            this.ageBand = ageBand;
        }

        @Override
        public void visitHeatingSystem(final IHeatingSystem system, final double proportion) {
        }

        @Override
        public double heatSystemProportion(final IHeatingSystem system) {
            return 0.0;
        }

        @Override
        public void visitEnergyTransducer(final IEnergyTransducer transducer) {
        }

        @Override
        public void visitVentilationSystem(final IVentilationSystem ventilation) {
        }

        @Override
        public void addWallInfiltration(final double wallArea, final WallConstructionType wallConstructionType, final double airChangeRate) {
        }

        @Override
        public void addFanInfiltration(final int fans) {
        }

        public double getAverageUValue() {
            return totalU / totalA;
        }

        @Override
        public void addVentInfiltration(final int vents) {
        }

        @Override
        public void addFlueInfiltration() {
        }

        @Override
        public void addChimneyInfiltration() {
        }

        @Override
        public void visitTransparentElement(final GlazingType glazingType, final WindowInsulationType insulationType,
                final double visibleLightTransmittivity, final double solarGainTransmissivity, final double area, final FrameType frameType,
                final double frameFactor, final double horizontalOrientation, final double verticalOrientation,
                final OvershadingType overshading) {
            // TODO Auto-generated method stub
        }

        @Override
        public void addGroundFloorInfiltration(final FloorConstructionType floorType) {
            // TODO Auto-generated method stub
        }

        @Override
        public void visitWall(
                final WallConstructionType constructionType,
                final double externalOrExternalInsulationThickness,
                final boolean hasCavityInsulation,
                final double area,
                final double uValue,
                final double thickness,
                final Optional<ThermalMassLevel> thermalMassLevel
        ) {

            if (includedAreas.contains(constructionType.getWallType().getAreaType())) {
                totalA += area;

                final double overrideU = calculatorType.uvalues.getWall(uValue, country, constructionType, externalOrExternalInsulationThickness, hasCavityInsulation, ageBand, thickness);

                totalU += overrideU * area;
            }
        }

        @Override
        public void visitDoor(final DoorType doorType, final double area, final double uValue) {
            if (includedAreas.contains(doorType.getAreaType())) {
                totalA += area;

                final double overrideU = calculatorType.uvalues.getOutsideDoor(uValue, ageBand, country);

                totalU += overrideU * area;
            }
        }

        @Override
        public void setRoofType(final RoofConstructionType constructionType, final double insulationThickness) {
            this.roofConstructionType = constructionType;
            this.roofInsulationThickness = insulationThickness;
        }

        @Override
        public void visitCeiling(final RoofType type, final double area, final double uValue) {
            if (includedAreas.contains(type.getAreaType())) {
                totalA += area;

                final double overrideU
                        = calculatorType.uvalues.getCeiling(uValue,
                                type,
                                roofConstructionType, roofInsulationThickness, country, ageBand
                        );

                totalU += overrideU * area;
            }
        }

        @Override
        public void visitWindow(final double area, final double uValue, final FrameType frameType, final GlazingType glazingType,
                final WindowInsulationType insulationType, final WindowGlazingAirGap airGap) {

            if (includedAreas.contains(frameType.getAreaType())) {
                totalA += area;

                final double overrideU = calculatorType.uvalues.getWindow(uValue, frameType, glazingType, insulationType, airGap);

                totalU += overrideU * area;
            }
        }

        @Override
        public void setFloorType(final FloorConstructionType groundFloorConstructionType, final double insulationThickness) {
            this.groundFloorConstructionType = groundFloorConstructionType;
            this.insulationThickness = insulationThickness;
        }

        @Override
        public void visitFloor(final AreaType type, final double area, final double uValue,
                final double exposedPerimeter, final double wallThickness) {

            if (includedAreas.contains(type)) {
                totalA += area;

                final double overrideU = calculatorType.uvalues.
                        getFloor(uValue,
                                type == AreaType.PartyFloor,
                                type == AreaType.BasementFloor || type == AreaType.GroundFloor,
                                area,
                                exposedPerimeter,
                                wallThickness,
                                groundFloorConstructionType,
                                insulationThickness,
                                ageBand,
                                country);

                totalU += overrideU * area;

            }
        }

        /**
         * @param name
         * @param proportion
         * @param efficiency
         * @param splitRate
         * @see
         * uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor#visitLight(java.lang.String,
         * double, double, double[])
         */
        @Override
        public void visitLight(String name, double proportion, LightType efficiency, double[] splitRate) {
            // NoOp
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final StructureModel structure = scope.get(this.structure);
        final BasicCaseAttributes basicAttributes = scope.get(this.basic);
        final Visitor v = new Visitor(
                includedAreas,
                scope.get(heavingBehaviour).getEnergyCalculatorType(),
                basicAttributes.getRegionType().getCountry(),
                SAPAgeBandValue.fromYear(basicAttributes.getBuildYear(), basicAttributes.getRegionType()).getName()
        );
        structure.accept(v);
        return v.getAverageUValue();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(structure);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
