package uk.org.cse.nhm.simulator.state.functions.impl.house;

import org.eclipse.emf.common.util.EList;

import com.google.common.collect.Range;
import com.google.common.util.concurrent.AtomicDouble;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetHouseLightingProportion extends TechnologyFunction<Double> {
	private final IComponentsFunction<Number> minEfficiency;
	private final IComponentsFunction<Number> maxEfficiency;
	
	@AssistedInject
	public GetHouseLightingProportion(
			@Assisted("maxEfficiency") final IComponentsFunction<Number> maxEfficiency,
			@Assisted("minEfficiency") final IComponentsFunction<Number> minEfficiency,
			IDimension<ITechnologyModel> technologies) {
		
		super(technologies);
		this.minEfficiency = minEfficiency;
		this.maxEfficiency = maxEfficiency;
	}

	@Override
	public Double compute(IComponentsScope scope, ILets lets) {
		return getProportionOfLightingOfGivenEfficiency(
				getTechnologies(scope).getLights(), 
				Range.openClosed(
						minEfficiency.compute(scope, lets).doubleValue(), 
						maxEfficiency.compute(scope, lets).doubleValue()));
	}
	
	protected Double getProportionOfLightingOfGivenEfficiency(EList<ILight> lights, Range<Double> efficiency){
		
		AtomicDouble proportion = new AtomicDouble();
		lights.forEach(l -> {
			if(efficiency.contains(l.getEfficiency())){
				proportion.addAndGet(l.getProportion());
			}
		});
		
		return proportion.getAndSet(0);
	}
}
