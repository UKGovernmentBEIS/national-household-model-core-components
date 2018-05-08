package uk.org.cse.nhm.simulator.measure.sizing;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;

public interface ISizingFunction {

    public ISizingResult computeSize(final IComponentsScope scope,
            ILets lets, final Units units);
}
