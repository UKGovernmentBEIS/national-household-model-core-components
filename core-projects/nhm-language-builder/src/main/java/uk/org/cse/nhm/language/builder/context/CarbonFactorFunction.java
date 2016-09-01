package uk.org.cse.nhm.language.builder.context;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.CarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

class CarbonFactorFunction extends AbstractNamed implements IComponentsFunction<ICarbonFactors> {
	final Multimap<IComponentsFunction<Number>, FuelType> mm = HashMultimap.create();
	final Set<IDimension<?>> deps;
	final Set<DateTime> changeDates;
	
	CarbonFactorFunction(final Multimap<IComponentsFunction<Number>, FuelType> mm) {
		this.mm.putAll(mm);
		final ImmutableSet.Builder<IDimension<?>> dims = ImmutableSet.builder();
		final ImmutableSet.Builder<DateTime> dates = ImmutableSet.builder();
		
		for (final IComponentsFunction<Number> cf : mm.keySet()) {
			dims.addAll(cf.getDependencies());
			dates.addAll(cf.getChangeDates());
		}
		
		this.deps = dims.build();
		this.changeDates = dates.build();
	}
	
	@Override
	public ICarbonFactors compute(final IComponentsScope scope, final ILets lets) {
		/*
		BEISDOC
		NAME: Carbon Factors
		DESCRIPTION: A table mapping fuel types to functions which calculate the carbon factors for those fuels. Includes CH4 and N2O emissions.
		TYPE: Lookup table
		UNIT: Fuel Type (categorical) -> Function (no arguments, return value is quantity of carbon / kWh fuel)
		SAP: Table 12, Section 12a (Emission factor column)
		SET: context.carbon-factors,counterfactual.carbon
		NOTES: The SAP emissions factors will be used for fuels for which no carbon factor has been specified.
		NOTES: Additionally, the default values of counterfactual.carbon are the SAP numbers.
		ID: carbon-factors
		CODSIEB
		*/
		final CarbonFactors cf = CarbonFactors.of(ICarbonFactors.SapFactors.factors);
		for (final IComponentsFunction<Number> f : mm.keySet()) {
			final Double value = f.compute(scope, lets).doubleValue();
			
			if (value != null) {
				for (final FuelType ft : mm.get(f)) {
					cf.setCarbonFactor(ft, value);
				}
			}
		}
		return cf;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return deps;
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return changeDates;
	}
}