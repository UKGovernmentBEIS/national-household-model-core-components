package uk.org.cse.nhm.language.builder.action.measure.wetheating;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulation.measure.HeatingMeasureApplication;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

/**
 * This measure DOES NOT install central wet heating itself. Instead, it is used
 * to record that it has been installed as a side effect of another measure.
 *
 * See {@link ITechnologyOperations} for the various parts that actually do this
 * job.
 *
 */
public class WetHeatingMeasure extends AbstractMeasure {

    private final IComponentsFunction<? extends Number> capex;
    private final IDimension<ITechnologyModel> technology;

    @AssistedInject
    public WetHeatingMeasure(
            final IDimension<ITechnologyModel> technology,
            @Assisted final IComponentsFunction<? extends Number> capex) {
        this.technology = technology;
        this.capex = capex;
        setIdentifier(Name.of("Wet Central Heating"));
    }

    @Override
    public boolean doApply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        final double capex = this.capex.compute(scope, lets).doubleValue();
        scope.addNote(new TechnologyInstallationDetails(this, TechnologyType.wetCentralHeating(), 0, Units.KILOWATTS, capex, 0));

        scope.addTransaction(Payment.capexToMarket(capex));

        return true;
    }

    /**
     * Applies the given heating measure, checks if central wet heating was
     * installed and, if so, makes a subscope and writes down technology
     * installation details and capex for that wet heating system.
     *
     * We have no concept of removing wet central heating at this point.
     */
    public boolean applyMayInstallWetHeating(final ISettableComponentsScope scopeOfHeatingMeasure, final ILets lets, final HeatingMeasureApplication measure) {
        if (isInstalled(scopeOfHeatingMeasure)) {
            return measure.apply(scopeOfHeatingMeasure, lets);
        } else {
            final boolean result = measure.apply(scopeOfHeatingMeasure, lets);
            if (result && isInstalled(scopeOfHeatingMeasure)) {
                return scopeOfHeatingMeasure.apply(this, lets);
            } else {
                return result;
            }
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return !isInstalled(scope);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    public boolean isInstalled(final IComponentsScope scope) {
        return scope.get(technology).getPrimarySpaceHeater() instanceof ICentralHeatingSystem;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }
}
