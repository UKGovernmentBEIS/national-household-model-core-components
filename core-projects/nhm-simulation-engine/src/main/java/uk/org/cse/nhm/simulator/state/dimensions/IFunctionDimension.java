package uk.org.cse.nhm.simulator.state.dimensions;

import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IFunctionDimension<T> extends IDimension<T> {

    public void setFunction(final IComponentsFunction<T> function);
}
