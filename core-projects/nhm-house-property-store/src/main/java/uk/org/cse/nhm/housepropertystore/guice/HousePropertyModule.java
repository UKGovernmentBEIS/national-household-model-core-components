package uk.org.cse.nhm.housepropertystore.guice;

import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import uk.org.cse.nhm.housepropertystore.build.HousePropertyAdapter;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.builder.SimulationParameter;
import uk.org.cse.nhm.logging.logentry.BasicAttributesLogEntry;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;
import uk.org.cse.nhm.simulator.impl.RequestedHouseProperties;

/**
 * A module which sets up the house property store related simulation logic.
 * 
 * If you use this module, you need to pass in a {@link SimulationParameter} to the builder
 * which will seed the binding for {@link HousePropertyStoreRepository} when you run a sim.
 * 
 * @author hinton
 *
 */
public class HousePropertyModule extends AbstractModule {
	Set<String> defaultProperties = BasicAttributesLogEntry.REQUIRED_ADDITIONAL_PROPERTIES;
	
	@Override
	protected void configure() {
		Multibinder.newSetBinder(binder(), IAdapter.class).addBinding().to(HousePropertyAdapter.class);
		bind(RequestedHouseProperties.class).in(SimulationScoped.class);
		bind(new TypeLiteral<Set<String>>(){})
			.annotatedWith(Names.named(RequestedHouseProperties.DEFAULT_PROPERTIES))
			.toInstance(defaultProperties);
		install(new FactoryModuleBuilder().build(IHousePropertyFunctionFactory.class));
	}
}
