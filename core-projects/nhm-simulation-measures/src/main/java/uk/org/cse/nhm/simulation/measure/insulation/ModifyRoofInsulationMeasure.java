package uk.org.cse.nhm.simulation.measure.insulation;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Allows the user to set the loft insulation thickness and ceiling u-value
 * (optional)
 *
 * @since 4.2.0
 */
public class ModifyRoofInsulationMeasure extends AbstractModifyInsulationMeasure {

    private final Optional<IComponentsFunction<? extends Number>> uValue;

    @Inject
    public ModifyRoofInsulationMeasure(
            final IDimension<StructureModel> structureDimension,
            @Assisted("insulationthickness") final IComponentsFunction<? extends Number> insulationThickness,
            @Assisted("uvalue") final Optional<IComponentsFunction<? extends Number>> uValue
    ) {
        super(structureDimension, insulationThickness);
        this.uValue = uValue;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return scope.get(structureDimension).getHasLoft();
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    @Override
    protected IModifier<StructureModel> modifier(
            final ISettableComponentsScope scope, final ILets lets) {
        final double insulationThickness = getInsulationThickness(scope, lets);
        final Number n = uValue.isPresent() ? uValue.get().compute(scope, lets) : null;

        return new IModifier<StructureModel>() {
            @Override
            public boolean modify(final StructureModel modifiable) {
                boolean modified = false;

                if (modifiable.getRoofInsulationThickness() != insulationThickness) {
                    modifiable.setRoofInsulationThickness(insulationThickness);
                    modified = true;
                }
                if (n != null) {
                    for (final Storey s : modifiable.getStoreys()) {
                        s.setCeilingUValue(n.doubleValue());
                    }
                    modified = true;
                }
                return modified;
            }
        };
    }
}
