package uk.org.cse.nhm.simulator.action;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SetThermalBridgingFactorAction extends AbstractNamed implements IComponentsAction {

	private final IDimension<StructureModel> structureDimension;
	private final IComponentsFunction<Number> thermalBridgingFactorFunction;

	@AssistedInject
	public SetThermalBridgingFactorAction(
			IDimension<StructureModel> structureDimension,
			@Assisted IComponentsFunction<Number> thermalBridgingFactorFunction
			) {
				this.structureDimension = structureDimension;
				this.thermalBridgingFactorFunction = thermalBridgingFactorFunction;
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
		scope.modify(
			structureDimension, 
			new Modifier(
				thermalBridgingFactorFunction.compute(scope, lets).doubleValue()
			)
		);
		
		return true;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}

	static class Modifier implements IModifier<StructureModel> {
		private final double thermalBridgingFactor;

		Modifier(final double thermalBridgingFactor) {
			this.thermalBridgingFactor = thermalBridgingFactor;
		}
		
		@Override
		public boolean modify(StructureModel modifiable) {
			modifiable.setThermalBridigingCoefficient(thermalBridgingFactor);
			return true;
		}
	}
}
