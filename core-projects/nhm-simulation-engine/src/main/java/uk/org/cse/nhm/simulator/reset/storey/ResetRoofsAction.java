package uk.org.cse.nhm.simulator.reset.storey;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResetRoofsAction extends AbstractNamed implements IComponentsAction {
	private final IDimension<StructureModel> structureDimension;
	private final IComponentsFunction<Number> uValue;
	
	private final RoofPropertyFunction floorProperties;
	
	/**
	 * A helper to work out the new properties for a given storey / floor.
	 * @author hinton
	 *
	 */
	class RoofPropertyFunction extends AbstractNamed implements IComponentsFunction<Number> {
		@Override
		public Number compute(final IComponentsScope scope, final ILets lets) {
			final Storey s = lets.get(ResetFloorsAction.STOREY_SCOPE_KEY, Storey.class).get();
			
			return uValue == null ? s.getCeilingUValue() : uValue.compute(scope, lets);
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
	
	@AssistedInject
	public ResetRoofsAction(
			final IDimension<StructureModel> structureDimension,
			@Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue
			) {
		this.structureDimension = structureDimension;
		this.uValue = uValue.orNull();
		this.floorProperties = new RoofPropertyFunction();
	}	
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		scope.modify(structureDimension, new IModifier<StructureModel>(){
			@Override
			public boolean modify(final StructureModel modifiable) {
				for (final Storey storey : modifiable.getStoreys()) {
					final Number result = 
							floorProperties.compute(scope, 
									lets.withBinding(ResetFloorsAction.STOREY_SCOPE_KEY, storey));
					storey.setCeilingUValue(result.doubleValue());
				}
				return true;
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
