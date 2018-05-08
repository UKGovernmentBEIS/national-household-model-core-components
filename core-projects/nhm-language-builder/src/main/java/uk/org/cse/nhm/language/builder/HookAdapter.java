package uk.org.cse.nhm.language.builder;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.tags.Tag;
import uk.org.cse.nhm.language.definition.two.actions.XAggregateHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XApplyHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XAssertHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XPayHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XRepeatHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XSetHookAction;
import uk.org.cse.nhm.language.definition.two.actions.XTransitionHookAction;
import uk.org.cse.nhm.language.definition.two.dates.XDate;
import uk.org.cse.nhm.language.definition.two.dates.XDateSequence;
import uk.org.cse.nhm.language.definition.two.hooks.XChangeHook;
import uk.org.cse.nhm.language.definition.two.hooks.XConstructHook;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;
import uk.org.cse.nhm.language.definition.two.hooks.XFlagHook;
import uk.org.cse.nhm.language.definition.two.hooks.XHook;
import uk.org.cse.nhm.language.definition.two.selectors.XAffectedHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XAllTheHouses;
import uk.org.cse.nhm.language.definition.two.selectors.XBernoulliSet;
import uk.org.cse.nhm.language.definition.two.selectors.XFilteredSet;
import uk.org.cse.nhm.language.definition.two.selectors.XSampledSet;
import uk.org.cse.nhm.language.definition.two.selectors.XUnionSet;
import uk.org.cse.nhm.language.two.build.IBuilder;
import uk.org.cse.nhm.simulator.factories.IHookFactory;
import uk.org.cse.nhm.simulator.factories.ISamplerFactory;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.hooks.IHookRunnable;
import uk.org.cse.nhm.simulator.hooks.RepeatHookAction;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HookAdapter extends ReflectingAdapter {

    final IHookFactory hookFactory;
    final ISamplerFactory samplerFactory;

    @Inject
    protected HookAdapter(final Set<IConverter> delegates, final Set<IAdapterInterceptor> interceptors, final IHookFactory factory, final ISamplerFactory samplerFactory) {
        super(delegates, interceptors);
        hookFactory = factory;
        this.samplerFactory = samplerFactory;
    }

    @Adapt(XDateHook.class)
    public Initializable adaptDateHook(
            // this is hacky, but hopefully can replace it all with something simpler later.
            @FromScope("CURRENT_BUILDER") final IBuilder builder,
            @Prop(XDateHook.P.dates) final List<XDateSequence> dates,
            @Prop(XHook.P.actions) final List<IHookRunnable> runnables
    ) {

        final ImmutableSet.Builder<DateTime> alldates = ImmutableSet.<DateTime>builder();

        for (final XDateSequence seq : dates) {
            alldates.addAll(seq.asDates(builder));
        }

        return hookFactory.createDatesHook(alldates.build(), runnables);
    }

    @Adapt(XChangeHook.class)
    public Initializable adaptChangeHook(
            @Prop(XChangeHook.P.stock) final boolean includeStock,
            @Prop(XHook.P.actions) final List<IHookRunnable> runnables
    ) {
        return hookFactory.createChangeHook(runnables, includeStock);
    }

    @Adapt(XFlagHook.class)
    public Initializable adaptFlagHook(
            @Prop(XFlagHook.P.flags) final List<Glob> flags,
            @Prop(XFlagHook.P.match) final Optional<Boolean> direction,
            @Prop(XHook.P.actions) final List<IHookRunnable> runnables
    ) {
        return hookFactory.createFlagsHook(
                runnables,
                flags,
                direction.or(true),
                !(direction.or(false)));
    }

    @Adapt(XConstructHook.class)
    public Initializable adaptConstructHook(
            @FromScope("CURRENT_BUILDER") final IBuilder builder,
            @Prop(XConstructHook.P.from) final XDate from,
            @Prop(XConstructHook.P.until) final XDate until,
            @Prop(XHook.P.actions) final List<IHookRunnable> runnables) {
        return hookFactory.createConstructHook(
                from.asDate(builder),
                until.asDate(builder),
                runnables);
    }

    @Adapt(XSetHookAction.class)
    public IHookRunnable adaptSet(
            @Prop(XSetHookAction.P.variable) final XNumberDeclaration var,
            @Prop(XSetHookAction.P.value) final IComponentsFunction<Number> valueFunction) {
        return hookFactory.createSetAction(var.getName(), valueFunction);
    }

    @Adapt(XApplyHookAction.class)
    public IHookRunnable adaptApply(
            @Prop(XApplyHookAction.P.houses) final IDwellingSet houses,
            @Prop(XApplyHookAction.P.action) final List<IStateAction> actions
    ) {
        return hookFactory.createApplyAction(houses, actions);
    }

    @Adapt(XAssertHookAction.class)
    public IHookRunnable adaptAssert(
            @Prop(XAssertHookAction.P.test) final IComponentsFunction<Boolean> test,
            @Prop(XAssertHookAction.P.over) final Optional<IDwellingSet> houses,
            @Prop(XAssertHookAction.P.fatal) final boolean fatal,
            @Prop(XAssertHookAction.P.debugValues) final List<IComponentsFunction<?>> debug
    ) {
        return hookFactory.createAssertion(houses, test, fatal, debug);
    }

    @Adapt(XAggregateHookAction.class)
    public IHookRunnable adaptAggregateHook(
            @Prop(XAggregateHookAction.P.over) final List<IDwellingSet> contents,
            @Prop(XAggregateHookAction.P.divideBy) final List<IComponentsFunction<?>> cuts,
            @Prop(XAggregateHookAction.P.values) final List<IAggregationFunction> values) {
        return hookFactory.createAggregateAction(contents, cuts, values);
    }

    @Adapt(XAllTheHouses.class)
    public IDwellingSet adaptAllHouses() {
        return hookFactory.createAllDwellings();
    }

    @Adapt(XAffectedHouses.class)
    public IDwellingSet adaptAffectedHouses() {
        return hookFactory.createAffectedDwellings();
    }

    @Adapt(XFilteredSet.class)
    public IDwellingSet adaptFilteredHouses(
            @Prop(XFilteredSet.P.test) final IComponentsFunction<Boolean> test,
            @Prop(XFilteredSet.P.source) final IDwellingSet source) {
        return hookFactory.createFilterDwellings(test, source);
    }

    @Adapt(XBernoulliSet.class)
    public IDwellingSet adaptBernoulliHouses(
            @Prop(XBernoulliSet.P.number) final IComponentsFunction<Number> test,
            @Prop(XBernoulliSet.P.source) final IDwellingSet source) {
        return hookFactory.createBernoulliDwellings(test, source);
    }

    @Adapt(XSampledSet.class)
    public IDwellingSet adaptSampledHouses(
            @Prop(XSampledSet.P.size) final IComponentsFunction<Number> size,
            @Prop(XSampledSet.P.source) final IDwellingSet source) {
        Preconditions.checkNotNull(size);
        return hookFactory.createRuntimeSampleDwellings(size, source);
    }

    @Adapt(XUnionSet.class)
    public IDwellingSet adaptSampledHouses(
            @Prop(XUnionSet.P.contents) final List<IDwellingSet> contents) {
        return hookFactory.createUnionDwellings(contents);
    }

    @Adapt(XTransitionHookAction.class)
    public IHookRunnable adaptTransitionsHook(
            @Prop(XTransitionHookAction.P.source) final IDwellingSet source,
            @Prop(XTransitionHookAction.P.divideBy) final List<IComponentsFunction<?>> divisions) {
        return hookFactory.createTransitionsAction(source, divisions);
    }

    @Adapt(XPayHookAction.class)
    public IHookRunnable adaptPayHook(
            @Prop(XPayHookAction.P.from) final String from,
            @Prop(XPayHookAction.P.to) final String to,
            @Prop(XPayHookAction.P.tags) final List<Tag> tags,
            @Prop(XPayHookAction.P.amount) final IComponentsFunction<? extends Number> amount) {
        return hookFactory.createPayAction(from, to, Tag.asSet(tags), amount);
    }

    @Adapt(XRepeatHookAction.class)
    public IHookRunnable adaptRepeatHook(@Prop(XRepeatHookAction.P.toRepeat) final List<IHookRunnable> commands,
            @Prop(XRepeatHookAction.P.until) final IComponentsFunction<Boolean> until,
            @Prop(XRepeatHookAction.P.preserving) final List<XNumberDeclaration> preserving) {
        final ImmutableList.Builder<RepeatHookAction.KeepValue> keeps = ImmutableList.builder();
        for (final XNumberDeclaration decl : preserving) {
            keeps.add(new RepeatHookAction.KeepValue(decl.getName(), decl.getOn()));
        }
        return hookFactory.createRepeatAction(commands, until, keeps.build());
    }
}
