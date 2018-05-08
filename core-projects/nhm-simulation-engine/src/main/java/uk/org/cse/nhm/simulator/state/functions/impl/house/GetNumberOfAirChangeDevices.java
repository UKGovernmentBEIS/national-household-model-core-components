package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustNumberOfAirChangeDevices;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * GetNumberOfPassiveVents.
 *
 * @author trickyBytes
 */
public class GetNumberOfAirChangeDevices extends StructureFunction<Integer> {

    private final XAdjustNumberOfAirChangeDevices.XAirChangeDevice device;

    @Inject
    protected GetNumberOfAirChangeDevices(IDimension<StructureModel> structure,
            @Assisted final XAdjustNumberOfAirChangeDevices.XAirChangeDevice device) {
        super(structure);
        this.device = device;
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see
     * uk.org.cse.nhm.simulator.state.functions.IComponentsFunction#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     * uk.org.cse.nhm.simulator.let.ILets)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer compute(IComponentsScope scope, ILets lets) {
        switch (device) {
            case Vents:
                return getStructure(scope).getPassiveVents();
            case Fans:
                return getStructure(scope).getIntermittentFans();
        }
        return 0;
    }
}
