package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * GetNumberOfPassiveVents.
 *
 * @author trickyBytes
 */
public class GetNumberOfPassiveVents extends StructureFunction<Integer> {

    @Inject
    protected GetNumberOfPassiveVents(IDimension<StructureModel> structure) {
        super(structure);
    }

    /**
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.state.functions.IComponentsFunction#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope, uk.org.cse.nhm.simulator.let.ILets)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Integer compute(IComponentsScope scope, ILets lets) {
        return getStructure(scope).getPassiveVents();
    }

}
