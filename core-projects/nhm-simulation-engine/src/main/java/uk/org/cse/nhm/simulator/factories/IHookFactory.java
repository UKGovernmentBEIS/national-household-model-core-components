package uk.org.cse.nhm.simulator.factories;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.hooks.AffectedDwellings;
import uk.org.cse.nhm.simulator.hooks.AggregateHookAction;
import uk.org.cse.nhm.simulator.hooks.AllDwellings;
import uk.org.cse.nhm.simulator.hooks.ApplyActionHookAction;
import uk.org.cse.nhm.simulator.hooks.AssertHookAction;
import uk.org.cse.nhm.simulator.hooks.BernoulliDwellings;
import uk.org.cse.nhm.simulator.hooks.ChangeHook;
import uk.org.cse.nhm.simulator.hooks.ConstructHook;
import uk.org.cse.nhm.simulator.hooks.DatesHook;
import uk.org.cse.nhm.simulator.hooks.FilterDwellings;
import uk.org.cse.nhm.simulator.hooks.FlagsHook;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.hooks.IHookRunnable;
import uk.org.cse.nhm.simulator.hooks.PayHookAction;
import uk.org.cse.nhm.simulator.hooks.RepeatHookAction;
import uk.org.cse.nhm.simulator.hooks.RuntimeSampleDwellings;
import uk.org.cse.nhm.simulator.hooks.SetHookAction;
import uk.org.cse.nhm.simulator.hooks.TransitionsHookAction;
import uk.org.cse.nhm.simulator.hooks.UnionDwellings;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IHookFactory {
	public DatesHook createDatesHook(final Collection<DateTime> dates, final List<IHookRunnable> delegates);
	public ChangeHook createChangeHook(final List<IHookRunnable> delegates, final boolean includeStockCreator);
	public FlagsHook createFlagsHook(
			@Assisted final List<IHookRunnable> delegates,
			@Assisted final List<Glob> conditions, 
			@Assisted("in") final boolean affectedIn, 
			@Assisted("out") final boolean affectedOut
			);

    public ConstructHook createConstructHook(@Assisted("from") final DateTime from,
                                             @Assisted("until") final DateTime until,
                                             final List<IHookRunnable> delegates);
    
	public SetHookAction createSetAction(
			@Assisted final String name, 
			@Assisted final IComponentsFunction<Number> valueFunction);
	
	public ApplyActionHookAction createApplyAction(
			@Assisted final IDwellingSet source,
			@Assisted final List<IStateAction> actions
			);

    public AssertHookAction createAssertion(@Assisted final Optional<IDwellingSet> over,
                                            @Assisted final IComponentsFunction<Boolean> test,
                                            @Assisted final boolean isFatal,
                                            @Assisted final List<IComponentsFunction<?>> debug);
	
	public AggregateHookAction createAggregateAction(
			@Assisted final List<IDwellingSet> contents, 
			@Assisted final List<IComponentsFunction<?>> cuts, 
			@Assisted final List<IAggregationFunction> values);
	
	public AffectedDwellings createAffectedDwellings();
	public AllDwellings createAllDwellings();
	public BernoulliDwellings createBernoulliDwellings(final IComponentsFunction<Number> fn, final IDwellingSet source);
	public FilterDwellings createFilterDwellings(final IComponentsFunction<Boolean> test, final IDwellingSet source);
	public RuntimeSampleDwellings createRuntimeSampleDwellings(final IComponentsFunction<Number> size, final IDwellingSet source);
	public UnionDwellings createUnionDwellings(final List<IDwellingSet> contents);

	public TransitionsHookAction createTransitionsAction(final IDwellingSet source, final List<IComponentsFunction<?>> divisions);
	public PayHookAction createPayAction(
		@Assisted("from") final String from, 
		@Assisted("to") final String to, 
		@Assisted final Set<String> tags, 
		@Assisted final IComponentsFunction<? extends Number> amount);

    public RepeatHookAction createRepeatAction(@Assisted final List<IHookRunnable> delegates,
                                               @Assisted final IComponentsFunction<Boolean> termination,
                                               @Assisted final List<RepeatHookAction.KeepValue> valuesToKeep);
}
