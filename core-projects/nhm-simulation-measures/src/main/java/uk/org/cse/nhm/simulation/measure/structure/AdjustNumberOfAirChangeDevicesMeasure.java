package uk.org.cse.nhm.simulation.measure.structure;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustNumberOfAirChangeDevices;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * Modifies the number of passive vents in a dwelling
 *
 * @author trickyBytes
 */
public class AdjustNumberOfAirChangeDevicesMeasure extends AbstractMeasure implements IModifier<StructureModel> {

    private final IDimension<StructureModel> structureDimension;
    private final int adjustment;
    private final XAdjustNumberOfAirChangeDevices.XAirChangeDevice device;

    @Inject
    public AdjustNumberOfAirChangeDevicesMeasure(final IDimension<StructureModel> structureDimension,
            @Assisted int adjustment,
            @Assisted XAdjustNumberOfAirChangeDevices.XAirChangeDevice device) {
        this.structureDimension = structureDimension;
        this.adjustment = adjustment;
        this.device = device;
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @throws NHMException
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#apply(uk.org.cse.nhm.simulator.scope.ISettableComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean doApply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        scope.modify(structureDimension, this);
        return true;
    }

    /**
     * Always suitable
     *
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return true;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.simulator.scope.IComponentsAction#isAlwaysSuitable()
     */
    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.simulator.state.IStateChangeSource#getSourceType()
     */
    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    /**
     * @param modifiable
     * @return
     * @see
     * uk.org.cse.nhm.simulator.state.IBranch.IModifier#modify(java.lang.Object)
     */
    @Override
    public boolean modify(StructureModel modifiable) {
        switch (device) {
            case Vents:
                modifiable.setPassiveVents(modifiable.getPassiveVents() + adjustment);
                return true;
            case Fans:
                modifiable.setIntermittentFans(modifiable.getPassiveVents() + adjustment);
                return true;
            default:
                return false;
        }
    }
}
