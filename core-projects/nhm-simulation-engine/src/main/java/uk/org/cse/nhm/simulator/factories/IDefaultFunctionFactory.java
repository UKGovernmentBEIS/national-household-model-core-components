package uk.org.cse.nhm.simulator.factories;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.DefaultPricingFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.DefaultSizingFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.WarningFunction;

public interface IDefaultFunctionFactory {

    public DefaultPricingFunction createPricingFunction(final Name identity);

    public DefaultSizingFunction createSizingFunction(final Name identity);

    public WarningFunction createWarningFunction(@Assisted final Name owner,
            @Assisted final String description,
            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted("lowerBound") final double lowerBound,
            @Assisted("upperBound") final double upperBound,
            @Assisted("lowerClamp") final boolean lowerClamp,
            @Assisted("upperClamp") final boolean upperClamp);
}
