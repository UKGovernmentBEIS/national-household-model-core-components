package uk.org.cse.nhm.simulator.action.hypothetical;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;

public class EnergyCalculatorAction extends HypotheticalAction {

    private final IDimension<IHeatingBehaviour> heatingBehaviour;
    private IModifier<IHeatingBehaviour> modifier;

    @Inject
    public EnergyCalculatorAction(
            @Assisted final EnergyCalculatorType calculatorType,
            final IDimension<IHeatingBehaviour> heatingBehaviour) {
        this.heatingBehaviour = heatingBehaviour;

        modifier = new IModifier<IHeatingBehaviour>() {

            @Override
            public boolean modify(IHeatingBehaviour modifiable) {
                modifiable.setEnergyCalculatorType(calculatorType);
                return true;
            }
        };
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    protected boolean doApply(IHypotheticalComponentsScope scope, ILets lets) {
        scope.modify(heatingBehaviour, modifier);
        return true;
    }
}
