package uk.org.cse.nhm.simulator.reset.opex;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResetOpexAction extends AbstractNamed implements IComponentsAction {
	private final IComponentsFunction<? extends Number> value;
	private final IDimension<ITechnologyModel> technology;
	protected static final Object CURRENT_OPCOST = new Object();
	@AssistedInject
	ResetOpexAction(
			final IDimension<ITechnologyModel> technology,
			@Assisted final IComponentsFunction<? extends Number> value) {
		super();
		this.technology = technology;
		this.value = value;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		scope.modify(technology, new IModifier<ITechnologyModel>() {
			@Override
			public boolean modify(final ITechnologyModel modifiable) {
				final TreeIterator<EObject> iterator = modifiable.eAllContents();
				
				boolean changed = false;
				
				while (iterator.hasNext()) {
					final EObject e = iterator.next();
					if (e instanceof IOperationalCost) {
						final IOperationalCost o = (IOperationalCost) e;
						final Number number = value.compute(scope, 
								lets.withBinding(CURRENT_OPCOST, o));
						if (number.doubleValue() != o.getAnnualOperationalCost()) {
							changed = true;
							o.setAnnualOperationalCost(number.doubleValue());
						}
					}
				}
				
				return changed;
			}
		});
		return true;
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return true;
	}
	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
}
