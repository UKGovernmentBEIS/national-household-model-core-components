package uk.org.cse.nhm.language.builder.action.measure;

import javax.inject.Inject;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.factories.IDefaultFunctionFactory;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A class which provides decorators for functions which should be bounded
 */
public class BoundedFunctions {

    private final IDefaultFunctionFactory functions;

    @Inject
    public BoundedFunctions(final IDefaultFunctionFactory functions) {
        this.functions = functions;
    }

    public IComponentsFunction<Number> capex(final Name identifier, final IComponentsFunction<Number> in) {
        return functions.createWarningFunction(identifier,
                "capex",
                in,
                0,
                1000000,
                true, true);
    }

    public IComponentsFunction<Number> capex(final Name identifier, final Optional<IComponentsFunction<Number>> in) {
        return capex(identifier, in.or(functions.createPricingFunction(identifier)));
    }

    public IComponentsFunction<Number> systemCapex(final Name identifier, final IComponentsFunction<Number> in) {
        return functions.createWarningFunction(identifier,
                "system capex",
                in,
                0,
                1000000,
                true, true);
    }

    public IComponentsFunction<Number> systemCapex(final Name identifier, final Optional<IComponentsFunction<Number>> in) {
        return systemCapex(identifier, in.or(functions.createPricingFunction(identifier)));
    }

    public IComponentsFunction<Number> opex(final Name identifier, final IComponentsFunction<Number> in) {
        return functions.createWarningFunction(identifier,
                "opex",
                in,
                0,
                1000000,
                true, true);
    }

    public IComponentsFunction<Number> opex(final Name identifier, final Optional<IComponentsFunction<Number>> in) {
        return opex(identifier, in.or(functions.createPricingFunction(identifier)));
    }

    public IComponentsFunction<Number> efficiency(final Name identifier, final IComponentsFunction<Number> efficiency, final String description) {
        return functions.createWarningFunction(identifier,
                description,
                efficiency,
                0.01, 1,
                true, true);
    }

    public IComponentsFunction<Number> efficiency(final Name identifier, final IComponentsFunction<Number> efficiency) {
        return efficiency(identifier, efficiency, "efficiency");
    }

    public IComponentsFunction<Number> winterEfficiency(final Name identifier, final IComponentsFunction<Number> efficiency) {
        return efficiency(identifier, efficiency, "winterEfficiency");
    }

    public IComponentsFunction<Number> summerEfficiency(final Name identifier, final IComponentsFunction<Number> efficiency) {
        return functions.createWarningFunction(identifier,
                "summerEfficiency",
                efficiency,
                -0.99, 1,
                true, true);
    }

    public IComponentsFunction<Number> cop(final Name identifier, final IComponentsFunction<Number> efficiency) {
        return functions.createWarningFunction(identifier,
                "coefficient of performance",
                efficiency,
                0.01, 10,
                true, false);
    }

    public IComponentsFunction<Number> tank(final Name identifier, final IComponentsFunction<Number> tank) {
        return functions.createWarningFunction(identifier,
                "hot water tank volume",
                tank,
                10, 1000,
                true, true);
    }

    public ISizingFunction sizing(final Name identifier, final Optional<ISizingFunction> sizingFunction) {
        return sizingFunction.or(functions.createSizingFunction(identifier));
    }
}
