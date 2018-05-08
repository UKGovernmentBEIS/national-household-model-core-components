package uk.org.cse.nhm.simulation.measure.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.builder.action.measure.HeatingMeasureAdapter;
import uk.org.cse.nhm.language.builder.action.measure.InsulationMeasureAdapter;
import uk.org.cse.nhm.language.builder.action.measure.LightingAndApplianceMeasureAdapter;
import uk.org.cse.nhm.language.builder.action.measure.RenewablesMeasureAdapter;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulation.measure.factory.IMeasureFactory;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;

public class MeasuresModule extends AbstractModule {

    @Override
    protected void configure() {
        final Multibinder<IAdapter> adapters = Multibinder.newSetBinder(binder(), IAdapter.class);
        final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();

        install(factoryModuleBuilder.build(IMeasureFactory.class));

        adapters.addBinding().to(HeatingMeasureAdapter.class);
        adapters.addBinding().to(InsulationMeasureAdapter.class);
        adapters.addBinding().to(RenewablesMeasureAdapter.class);
        adapters.addBinding().to(LightingAndApplianceMeasureAdapter.class);

        bind(ITechnologyOperations.class).to(TechnologyOperations.class).in(SimulationScoped.class);

        install(new FactoryModuleBuilder().build(IWetHeatingMeasureFactory.class));
    }
}
