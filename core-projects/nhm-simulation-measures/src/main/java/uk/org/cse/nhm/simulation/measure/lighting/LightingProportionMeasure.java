package uk.org.cse.nhm.simulation.measure.lighting;

import org.eclipse.emf.common.util.EList;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
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
	
	@AssistedInject
	public LightingProportionMeasure(
			@Assisted("proportionOfCfl") final IComponentsFunction<Number> proportionOfCfl,
			@Assisted("proportionOfIcandescent") final IComponentsFunction<Number> proportionOfIcandescent,
			@Assisted("propotionOfHAL") final IComponentsFunction<Number> propotionOfHAL,
			@Assisted("proportionOfLED") final IComponentsFunction<Number> proportionOfLED,
			final IDimension<ITechnologyModel> techDimension
			) {
		this.proportionOfCfl = proportionOfCfl;
		this.proportionOfIcandescent = proportionOfIcandescent;
		this.propotionOfHAL = propotionOfHAL;
		this.proportionOfLED = proportionOfLED;
		this.techDimension = techDimension;
	}
	
	protected final ILight createLight(double efficiency, double proportion){
		ILight light = ITechnologiesFactory.eINSTANCE.createLight();
		light.setEfficiency(efficiency);
		light.setProportion(proportion);
		
		return light;
	}
		
	@Override
	public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
		if (!isSuitable(scope, lets)) {
			return false;
		}
		
		scope.modify(techDimension, new IModifier<ITechnologyModel>(){
			@Override
			public boolean modify(ITechnologyModel modifiable) {
				final EList<ILight> lights = modifiable.getLights();
				//Remove all existing lights
				lights.clear();
				
				//Add new lights in given proportion
				lights.add(createLight(ILight.INCANDESCENT_EFFICIENCY, proportionOfIcandescent.compute(scope, lets).doubleValue()));
				lights.add(createLight(ILight.BRE_CFL_EFFICIENCY, proportionOfCfl.compute(scope, lets).doubleValue()));
				lights.add(createLight(ILight.HAL_EFFICIENCY, propotionOfHAL.compute(scope, lets).doubleValue()));
				lights.add(createLight(ILight.LED_EFFICIENCY, proportionOfLED.compute(scope, lets).doubleValue()));
				
				return true;
			}
		});
		
		return true;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		double actualProportionOfCfl = proportionOfCfl.compute(scope, lets).doubleValue();
		double actualProportionOfIcandescent = proportionOfIcandescent.compute(scope, lets).doubleValue();
		double actualpropotionOfHAL = propotionOfHAL.compute(scope, lets).doubleValue();
		double actualProportionOfLED = proportionOfLED.compute(scope, lets).doubleValue();
		
		double proportionBeingReplaced = actualProportionOfCfl + actualProportionOfIcandescent + actualpropotionOfHAL + actualProportionOfLED;
		
		return proportionBeingReplaced == 1d;
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
