package uk.org.cse.nhm.simulation.measure.heatpumps;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GroundSourceHeatPumpMeasure extends AbstractHeatPumpMeasure {

    private final double minimumSpaceRequired;
    private final IDimension<StructureModel> structure;

    @Inject
    public GroundSourceHeatPumpMeasure(
            final ITimeDimension time,
            final IWetHeatingMeasureFactory factory,
            final IDimension<ITechnologyModel> technologies,
            final IDimension<StructureModel> structure,
            final ITechnologyOperations operations,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency,
            @Assisted("insulation") final double cylinderInsulationThickness,
            @Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("space") final double minimumSpace,
            @Assisted("fuel") final FuelType mainFuel,
            @Assisted final Optional<Hybrid> hybrid) {
        super(time,
                factory,
                TechnologyType.groundSourceHeatPump(),
                sizingFunction,
                capitalCostFunction,
                operationalCostFunction,
                wetHeatingCostFunction,
                efficiency,
                cylinderInsulationThickness,
                cylinderVolume,
                HeatPumpSourceType.GROUND,
                operations,
                technologies,
                structure,
                mainFuel,
                hybrid);
        this.structure = structure;
        this.minimumSpaceRequired = minimumSpace;
    }

    @Override
    protected boolean doDoIsSuitable(final IComponents components) {
        final StructureModel structureModel = components.get(structure);
        final ITechnologyModel technologyModel = components.get(technologies);

        if (structureModel.getBackPlotArea() < minimumSpaceRequired && structureModel.getFrontPlotArea() < minimumSpaceRequired) {
            return false;
        }

        // not a flat
        if (structureModel.getBuiltFormType().isFlat()) {
            return false;
        }

        // not community heating
        if (operations.hasCommunitySpaceHeating(technologyModel)) {
            return false;
        }

        return true;
    }
}
