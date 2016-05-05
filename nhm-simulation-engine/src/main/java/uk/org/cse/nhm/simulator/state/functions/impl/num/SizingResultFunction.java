package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A function which gets the last {@link ISizingResult}'s size, where the result
 * matches a specified unit.
 * 
 * @author hinton
 *
 */
public class SizingResultFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final Units unit;
	
	@Inject
	public SizingResultFunction(@Assisted final Units unit) {
		this.unit = unit;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
        return findTotalSizingResult(scope, lets);
	}

    private double findTotalSizingResult(final IComponentsScope scope, final ILets lets) {
        double acc = 0;
        for (final ISizingResult size : scope.getAllNotes(ISizingResult.class)) {
            if (size.getUnits().equals(unit)) {
                acc += size.getSize();
            }
        }
        return acc;
    }

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
