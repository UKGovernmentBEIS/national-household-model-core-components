package uk.org.cse.nhm.simulator.reset.storey;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
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

public class ResetFloorsAction extends AbstractNamed implements IComponentsAction {
	private final IDimension<StructureModel> structureDimension;
	private final IComponentsFunction<Number> uValue;
	private final IComponentsFunction<Number> infiltration;
	private final FloorPropertyFunction floorProperties;
	/**
	 * A lookup key in the ley bindings which refers to the storey in consideration.
	 */
	protected final static Object STOREY_SCOPE_KEY = new Object();
	/**
	 * A lookup key in the let bindings which refers to the area of the 
	 * current storey which is not in contact with the storey beneath.
	 */
	protected final static Object STOREY_GROUND_AREA_KEY = new Object();
	
	/**
	 * A helper to work out the new properties for a given storey / floor.
	 * @author hinton
	 *
	 */
	class FloorPropertyFunction extends AbstractNamed implements IComponentsFunction<double[]> {
		@Override
		public double[] compute(final IComponentsScope scope, final ILets lets) {
			final Storey s = lets.get(STOREY_SCOPE_KEY, Storey.class).get();
			
			return new double[] {
					uValue == null ? s.getFloorUValue():uValue.compute(scope, lets).doubleValue(),
					infiltration == null ? s.getFloorAirChangeRate():infiltration.compute(scope, lets).doubleValue()
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
	public ResetFloorsAction(
			final IDimension<StructureModel> structureDimension,
			@Assisted("uValue") final Optional<IComponentsFunction<Number>> uValue,
			@Assisted("infiltration") final Optional<IComponentsFunction<Number>> infiltration
			) {
		this.structureDimension = structureDimension;
		this.uValue = uValue.orNull();
		this.infiltration = infiltration.orNull();
		this.floorProperties = new FloorPropertyFunction();
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
				double areaBelow = 0;
				for (final Storey storey : modifiable.getStoreys()) {
					final double[] result = 
							floorProperties.compute(scope, lets.withBindings(
								ImmutableMap.of(
										STOREY_SCOPE_KEY, storey, 
										STOREY_GROUND_AREA_KEY, areaBelow)));
					
					storey.setFloorUValue(result[0]);
					storey.setFloorAirChangeRate(result[1]);
					
					areaBelow = storey.getArea();
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
