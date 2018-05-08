package uk.org.cse.nhm.language.builder.context;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext;
import uk.org.cse.nhm.simulator.main.Initializable;

/**
 * Adapter which reads {@link XEnergyConstantsContext}, and does nothing with
 * it. This is just to allow the simulation to work.
 *
 * @author hinton
 *
 */
public class ParametersAdapter extends ReflectingAdapter {

    @Inject
    public ParametersAdapter(Set<IConverter> delegates, Set<IAdapterInterceptor> interceptors) {
        super(delegates, interceptors);
    }

    @Adapt(XEnergyConstantsContext.class)
    public Initializable ignoreParametersContext() {
        return Initializable.NOP;
    }
}
