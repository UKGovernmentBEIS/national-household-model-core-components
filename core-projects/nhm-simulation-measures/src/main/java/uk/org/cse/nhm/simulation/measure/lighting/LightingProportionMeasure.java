package uk.org.cse.nhm.simulation.measure.lighting;

import org.eclipse.emf.common.util.EList;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class LightingProportionMeasure extends AbstractMeasure {

    private final IComponentsFunction<Number> proportionOfCfl;
    private final IComponentsFunction<Number> proportionOfIcandescent;
    private final IComponentsFunction<Number> propotionOfHAL;
    private final IComponentsFunction<Number> proportionOfLED;
    private final IDimension<ITechnologyModel> techDimension;
    private IComponentsFunction<Number> proportionOfLVHAL;
    private IComponentsFunction<Number> proportionOfLApp;

    @AssistedInject
    public LightingProportionMeasure(
            @Assisted("proportionOfCfl") final IComponentsFunction<Number> proportionOfCfl,
            @Assisted("proportionOfIcandescent") final IComponentsFunction<Number> proportionOfIcandescent,
            @Assisted("propotionOfHAL") final IComponentsFunction<Number> propotionOfHAL,
            @Assisted("proportionOfLED") final IComponentsFunction<Number> proportionOfLED,
            @Assisted("proportionOfLVHAL") final IComponentsFunction<Number> proportionOfLVHAL,
            @Assisted("proportionOfLA++") final IComponentsFunction<Number> proportionOfLApp,
            final IDimension<ITechnologyModel> techDimension
    ) {
        this.proportionOfCfl = proportionOfCfl;
        this.proportionOfIcandescent = proportionOfIcandescent;
        this.propotionOfHAL = propotionOfHAL;
        this.proportionOfLED = proportionOfLED;
        this.proportionOfLVHAL = proportionOfLVHAL;
        this.proportionOfLApp = proportionOfLApp;
        this.techDimension = techDimension;
    }

    protected final ILight createLight(LightType type, double proportion) {
        ILight light = ITechnologiesFactory.eINSTANCE.createLight();
        light.setType(type);
        light.setProportion(proportion);

        return light;
    }

    @Override
    public boolean doApply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        final double incandescent = proportionOfIcandescent.compute(scope, lets).doubleValue();
        final double cfl = proportionOfCfl.compute(scope, lets).doubleValue();
        final double halogen = propotionOfHAL.compute(scope, lets).doubleValue();
        final double led = proportionOfLED.compute(scope, lets).doubleValue();
        final double lvhalogen = proportionOfLVHAL.compute(scope, lets).doubleValue();
        final double aplusplus = proportionOfLApp.compute(scope, lets).doubleValue();

        final double total = incandescent + cfl + halogen + led + lvhalogen + aplusplus;

        scope.modify(techDimension, new IModifier<ITechnologyModel>() {
            @Override
            public boolean modify(ITechnologyModel modifiable) {
                final EList<ILight> lights = modifiable.getLights();
                //Remove all existing lights
                lights.clear();

                insert(lights, LightType.Incandescent, incandescent);
                insert(lights, LightType.CFL, cfl);
                insert(lights, LightType.Halogen, halogen);
                insert(lights, LightType.LED, led);
                insert(lights, LightType.LVHalogen, lvhalogen);
                insert(lights, LightType.APlusPlus, aplusplus);

                return true;
            }

            protected void insert(final EList<ILight> lights, final LightType type, final double prop) {
                if (prop > 0) {
                    final ILight light = ITechnologiesFactory.eINSTANCE.createLight();
                    light.setType(type);
                    light.setProportion(prop / total);
                    lights.add(light);
                }
            }
        });

        return true;
    }

    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        final double totalProportion
                = proportionOfIcandescent.compute(scope, lets).doubleValue()
                + proportionOfCfl.compute(scope, lets).doubleValue()
                + propotionOfHAL.compute(scope, lets).doubleValue()
                + proportionOfLED.compute(scope, lets).doubleValue()
                + proportionOfLVHAL.compute(scope, lets).doubleValue()
                + proportionOfLApp.compute(scope, lets).doubleValue();

        if (totalProportion < 0.99 || totalProportion > 1.01) {
            return false;
        }
        return true;
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
