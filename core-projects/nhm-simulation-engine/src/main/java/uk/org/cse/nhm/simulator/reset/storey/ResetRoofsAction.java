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
	private final IComponentsFunction<Number> kValue;
	private final IComponentsFunction<Number> partykValue;
	
	private final RoofPropertyFunction floorProperties;
	
	/**
	 * A helper to work out the new properties for a given storey / floor.
	 * @author hinton
	 *
	 */
	class RoofPropertyFunction extends AbstractNamed implements IComponentsFunction<double[]> {
		@Override
		public double[] compute(final IComponentsScope scope, final ILets lets) {
			final Storey s = lets.get(ResetFloorsAction.STOREY_SCOPE_KEY, Storey.class).get();
			
			return new double[] {
					uValue == null ? s.getCeilingUValue():uValue.compute(scope, lets).doubleValue(),
					kValue == null ? s.getCeilingKValue():kValue.compute(scope, lets).doubleValue(),
					partykValue == null ? s.getPartyCeilingKValue() : partykValue.compute(scope, lets).doubleValue()
			};
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
			@Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue,
			@Assisted("kValue") final Optional<IComponentsFunction<Number>> kValue,
			@Assisted("partyKValue") final Optional<IComponentsFunction<Number>> partyKValue
			) {
		this.structureDimension = structureDimension;
		this.uValue = uValue.orNull();
		this.kValue = kValue.orNull();
		this.partykValue = partyKValue.orNull();
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
					final double[] result = 
							floorProperties.compute(scope, 
									lets.withBinding(ResetFloorsAction.STOREY_SCOPE_KEY, storey));
					storey.setCeilingUValue(result[0]);
					storey.setCeilingKValue(result[1]);
					storey.setPartyCeilingKValue(result[2]);
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
