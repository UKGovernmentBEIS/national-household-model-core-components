package uk.org.cse.nhm.simulation.measure.adjustment;

import java.util.Iterator;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class AdjustmentMeasure extends AbstractMeasure implements IModifier<ITechnologyModel> {
	private final IDimension<ITechnologyModel> technologies;
	private final IAdjuster prototype;
	private final boolean add;
	
	@AssistedInject
	public AdjustmentMeasure(
			final IDimension<ITechnologyModel> technologies, 
			@Assisted
			final IAdjuster prototype, 
			@Assisted
			final boolean add) {
		super();
		this.technologies = technologies;
		this.prototype = prototype;
		this.add = add;
	}

	@Override
	public boolean modify(final ITechnologyModel modifiable) {
		if (add) {
			modifiable.getAdjusters().add(EcoreUtil.copy(prototype));
		} else {
			final Iterator<IAdjuster> it = modifiable.getAdjusters().iterator();
			while (it.hasNext()) if (it.next().getName().equals(prototype.getName())) it.remove();
		}
		return true;
	}
	
	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		if (isSuitable(scope, lets)) {
			scope.modify(technologies, this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(technologies);
		for (final IAdjuster adj : tech.getAdjusters()) {
			if (adj.getName().equals(prototype.getName())) {
				if (add) return false;
				else return true;
			}
		}
		
		return add;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
