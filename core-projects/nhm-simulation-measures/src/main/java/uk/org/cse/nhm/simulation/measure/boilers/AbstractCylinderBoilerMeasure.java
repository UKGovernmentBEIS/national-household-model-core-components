package uk.org.cse.nhm.simulation.measure.boilers;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class AbstractCylinderBoilerMeasure extends AbstractBoilerMeasure {

    private final IComponentsFunction<Number> cylinderVolume;
    protected final double cylinderInsulationThickness;

    public AbstractCylinderBoilerMeasure(
            final ITimeDimension time,
            final IDimension<ITechnologyModel> technologies,
            final ITechnologyOperations operations,
            final IWetHeatingMeasureFactory factory,
            final TechnologyType technology,
            final ISizingFunction sizingFunction,
            final IComponentsFunction<Number> capitalCostFunction,
            final IComponentsFunction<Number> operationalCostFunction,
            final IComponentsFunction<Number> whCapex,
            final IComponentsFunction<Number> winterEfficiency,
            final IComponentsFunction<Number> summerEfficiency,
            final FuelType fuelType,
            final IComponentsFunction<Number> cylinderVolume,
            final double cylinderInsulationThickness
    ) {
        super(time,
                technologies,
                operations,
                factory,
                technology,
                sizingFunction,
                capitalCostFunction,
                operationalCostFunction,
                whCapex,
                winterEfficiency,
                summerEfficiency,
                fuelType);
        this.cylinderVolume = cylinderVolume;
        this.cylinderInsulationThickness = cylinderInsulationThickness;
    }

    protected double getCylinderVolume(final IComponentsScope scope, final ILets lets) {
        return Math.max(0, this.cylinderVolume.compute(scope, lets).doubleValue());
    }
}
