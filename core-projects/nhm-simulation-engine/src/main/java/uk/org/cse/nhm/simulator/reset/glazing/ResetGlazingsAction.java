package uk.org.cse.nhm.simulator.reset.glazing;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResetGlazingsAction extends AbstractNamed implements IComponentsAction {

    private final IDimension<StructureModel> structureDimension;

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    static class GlazingPropsFunction extends AbstractNamed implements IComponentsFunction<double[]> {

        private final IComponentsFunction<Number> frameFactor;
        private final IComponentsFunction<Number> gainsTransmission;
        private final IComponentsFunction<Number> lightTransmission;
        private final IComponentsFunction<Number> uValue;

        GlazingPropsFunction(final IComponentsFunction<Number> frameFactor,
                final IComponentsFunction<Number> gainsTransmission,
                final IComponentsFunction<Number> lightTransmission,
                final IComponentsFunction<Number> uValue) {
            super();
            this.frameFactor = frameFactor;
            this.gainsTransmission = gainsTransmission;
            this.lightTransmission = lightTransmission;
            this.uValue = uValue;
        }

        @Override
        public double[] compute(final IComponentsScope scope, final ILets lets) {
            final Glazing g = lets.get(GLAZING_LET_KEY, Glazing.class).get();
            return new double[]{
                frameFactor == null ? g.getFrameFactor()
                : frameFactor.compute(scope, lets).doubleValue(),
                gainsTransmission == null ? g.getGainsTransmissionFactor()
                : gainsTransmission.compute(scope, lets).doubleValue(),
                lightTransmission == null ? g.getLightTransmissionFactor()
                : lightTransmission.compute(scope, lets).doubleValue(),
                uValue == null ? g.getuValue()
                : uValue.compute(scope, lets).doubleValue()
            };
        }

        @Override
        public Set<DateTime> getChangeDates() {
            return Collections.emptySet();
        }

        @Override
        public Set<IDimension<?>> getDependencies() {
            return Collections.emptySet();
        }
    }

    static final Object GLAZING_LET_KEY = new Object();

    private final GlazingPropsFunction glazingPropsFunction;

    @AssistedInject
    public ResetGlazingsAction(
            final IDimension<StructureModel> structureDimension,
            @Assisted("frameFactor") final Optional<IComponentsFunction<Number>> frameFactor,
            @Assisted("gainsTransmittance") final Optional<IComponentsFunction<Number>> gains,
            @Assisted("lightTransmittance") final Optional<IComponentsFunction<Number>> light,
            @Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue
    ) {
        this.structureDimension = structureDimension;
        this.glazingPropsFunction = new GlazingPropsFunction(
                frameFactor.orNull(),
                gains.orNull(),
                light.orNull(),
                uValue.orNull());
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {

        scope.modify(structureDimension, new IModifier<StructureModel>() {
            @Override
            public boolean modify(final StructureModel modifiable) {
                for (final Elevation e : modifiable.getElevations().values()) {
                    for (final Glazing g : e.getGlazings()) {
                        final double[] values
                                = glazingPropsFunction.compute(scope,
                                        lets.withBinding(GLAZING_LET_KEY, g));

                        g.setFrameFactor(values[0]);
                        g.setGainsTransmissionFactor(values[1]);
                        g.setLightTransmissionFactor(values[2]);
                        g.setUValue(values[3]);
                    }
                }

                return true;
            }

        });

        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }
}
