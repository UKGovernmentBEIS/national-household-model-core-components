package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetHouseLightingProportion extends TechnologyFunction<Double> {
	
	private Set<LightType> types;

	@AssistedInject
	public GetHouseLightingProportion(
			@Assisted List<LightType> types,
			IDimension<ITechnologyModel> technologies) {
		
		super(technologies);
		this.types = EnumSet.copyOf(types);
	}

	@SuppressWarnings("unchecked")
    @Override
	public Double compute(IComponentsScope scope, ILets lets) {
		double totalProportion = 0;
		for (final ILight light : getTechnologies(scope).getLights()) {
			if (types.contains(light.getType())) totalProportion += light.getProportion();
		}
		return totalProportion;
	}
}
