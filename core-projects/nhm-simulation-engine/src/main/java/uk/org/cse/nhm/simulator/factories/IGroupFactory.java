package uk.org.cse.nhm.simulator.factories;

import java.util.List;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.AllHousesGroupWrapper;
import uk.org.cse.nhm.simulator.groups.impl.ChoiceDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.groups.impl.FunctionDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.RandomDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.SetOperationDwellingGroups;
import uk.org.cse.nhm.simulator.groups.impl.SetOperationDwellingGroups.Operation;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IGroupFactory {

    public Condition createCondition(final IDwellingGroup source, final List<IComponentsFunction<Boolean>> tests);

    public AllHousesGroupWrapper getAllHouses();

    public RandomDwellingGroup createRandomSamplingGroup(
            final IDwellingGroup source,
            final IComponentsFunction<Number> function);

    public ChoiceDwellingGroup createRandomSubsetGroup(
            final IDwellingGroup source,
            final double size);

    public FunctionDwellingGroup createFilteredGroup(
            final IDwellingGroup source,
            final IComponentsFunction<Boolean> function);

    public SetOperationDwellingGroups createOperationGroup(final List<IDwellingGroup> sources, final Operation operation);
}
