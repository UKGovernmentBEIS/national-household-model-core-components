package uk.org.cse.nhm.simulator.integration.tests.guice;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.integration.tests.xml.XAssertion;
import uk.org.cse.nhm.simulator.integration.tests.xml.XAssertion.XAssertionType;
import uk.org.cse.nhm.simulator.integration.tests.xml.XMLMeasureProbe;
import uk.org.cse.nhm.simulator.integration.tests.xml.XNumberDebugger;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class IntegrationAdapter extends ReflectingAdapter {

    final ITestingFactory factory;

    @Inject
    public IntegrationAdapter(Set<IConverter> delegates, final ITestingFactory factory, Set<IAdapterInterceptor> interceptors) {
        super(delegates, interceptors);
        this.factory = factory;
    }

    @Adapt(XMLMeasureProbe.class)
    public IStateAction addProbe(final XMLMeasureProbe probe) {
        return factory.createProbe(probe.getName());
    }

    @Adapt(XAssertion.class)
    public Initializable buildAssertion(
            @Prop("name") final String name,
            @Prop("group") final IDwellingGroup group,
            @Prop("aggregation") final IAggregationFunction aggregation,
            @Prop("type") final XAssertionType type,
            @Prop("continuous") final boolean continuous,
            @Prop("bound") final double bound
    ) {
        return factory.createAssertion(name == null ? "unnamed" : name, type.ordinal() - 1, bound, continuous, group, aggregation);
    }

    @Adapt(XNumberDebugger.class)
    public IComponentsFunction<Number> addDebugger(
            @Prop("name") final String name,
            @Prop("delegate") final IComponentsFunction<Number> delegate
    ) {
        return factory.createFunctionAssertion(name, delegate);
    }
}
