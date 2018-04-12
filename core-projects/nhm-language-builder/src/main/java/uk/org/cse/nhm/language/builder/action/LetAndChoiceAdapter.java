package uk.org.cse.nhm.language.builder.action;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.action.XActionWithDelegates;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;
import uk.org.cse.nhm.language.definition.action.choices.XChoiceAction;
import uk.org.cse.nhm.language.definition.action.choices.XChoiceSelector;
import uk.org.cse.nhm.language.definition.action.choices.XCombinationsChoiceAction;
import uk.org.cse.nhm.language.definition.action.choices.XFallbackSelector;
import uk.org.cse.nhm.language.definition.action.choices.XFilterSelector;
import uk.org.cse.nhm.language.definition.action.choices.XMaximumSelector;
import uk.org.cse.nhm.language.definition.action.choices.XMinimumSelector;
import uk.org.cse.nhm.language.definition.action.choices.XWeightedSelector;
import uk.org.cse.nhm.language.definition.sequence.*;
import uk.org.cse.nhm.simulator.action.choices.ChoiceAction;
import uk.org.cse.nhm.simulator.action.choices.CombinationChoiceAction;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.sequence.ChangeValue;
import uk.org.cse.nhm.simulator.sequence.ConsumeAction;
import uk.org.cse.nhm.simulator.sequence.FailUnless;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.sequence.SequenceAction;
import uk.org.cse.nhm.simulator.sequence.SnapshotAction;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class LetAndChoiceAdapter extends ReflectingAdapter {
	final IActionFactory measureFactory;
	final IObjectFunctionFactory functions;

	@Inject
	public LetAndChoiceAdapter(final Set<IConverter> delegates,
			final IActionFactory measureFactory,
			final IObjectFunctionFactory functions,
			final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.measureFactory = measureFactory;
		this.functions = functions;
	}
	
	@Adapt(XCombinationsChoiceAction.class)
	public IComponentsAction buildCombinationsChoice(
	        final XCombinationsChoiceAction combinations,
	        final IAdaptingScope scope,
	        @Prop(XCombinationsChoiceAction.P.SELECTOR) final IPicker selector
	        ){
	    
	    final ImmutableList.Builder<Set<IComponentsAction>> groupsBuilder = ImmutableList.builder();
	    //combinations.getAllChildren();
	    	    
	    for (final List<XDwellingAction> group : combinations.getDelegates()) {
            final ImmutableSet.Builder<IComponentsAction> groupBuilder = ImmutableSet.builder();
            for (final XDwellingAction action : group) {
                // this is the line which doesn't work unless we have done the getAllChildren method above.
                groupBuilder.add(action.adapt(IComponentsAction.class, scope));
            }
            groupsBuilder.add(groupBuilder.build());
        }
	    
	    return new CombinationChoiceAction(selector, groupsBuilder.build());
	}

	@Adapt(XChoiceAction.class)
	public IComponentsAction buildChoice(
			final Name name,
			@Prop(XChoiceAction.P.bindings) final List<IComponentsAction> bindings,			
			@Prop(XChoiceAction.P.selector) final IPicker selector,
			@Prop(XActionWithDelegates.P.DELEGATES) final List<IComponentsAction> alternatives) {
		final ChoiceAction choice = measureFactory.createChoice(selector,
				alternatives);

		if (bindings.isEmpty()) {
			return choice;
		} else {
			// glom actions together here
			final SequenceAction withLets = measureFactory.createSequenceAction(
					ImmutableList.<IComponentsAction>builder().addAll(bindings).add(choice).build(), 
					true,
					ImmutableSet.<String>of()
					);

			choice.setIdentifier(name);

			return withLets;
		}
	}

	@Adapt(XFilterSelector.class)
	public IPicker buildChoiceFilter(
			@Prop(XChoiceSelector.P.bindings) final List<ISequenceSpecialAction> bindings,
			@Prop(XFilterSelector.P.test) final IComponentsFunction<Boolean> test,
			@Prop(XFilterSelector.P.selector) final IPicker delegate) {

		return measureFactory.createSelectByFilter(bindings, test, delegate);
	}

	@Adapt(XMaximumSelector.class)
	public IPicker buildChoiceMaximum(
			@Prop(XChoiceSelector.P.bindings) final List<ISequenceSpecialAction> bindings,
			@Prop(XMaximumSelector.P.objective) final IComponentsFunction<Number> objective) {
		return measureFactory.createSelectMaximum(bindings, objective);
	}

	@Adapt(XMinimumSelector.class)
	public IPicker buildChoiceMinimum(
			@Prop(XChoiceSelector.P.bindings) final List<ISequenceSpecialAction> bindings,

			@Prop(XMinimumSelector.P.objective) final IComponentsFunction<Number> objective) {
		return measureFactory.createSelectMaximum(bindings, negative(objective));
	}

	@Adapt(XWeightedSelector.class)
	public IPicker buildChoiceWeighted(
			@Prop(XChoiceSelector.P.bindings) final List<ISequenceSpecialAction> bindings,

			@Prop(XWeightedSelector.P.weight) final IComponentsFunction<Number> weight) {
		return measureFactory.createSelectRandomWeighted(bindings, weight);
	} 
	
	@Adapt(XFallbackSelector.class)
	public IPicker buildChoiceFallback(
			@Prop(XChoiceSelector.P.bindings) final List<ISequenceSpecialAction> bindings,

			@Prop(XFallbackSelector.P.delegates) final List<IPicker> delegates
			) {
		return measureFactory.createSelectFallback(bindings, delegates);
	}

	private static IComponentsFunction<Number> negative(
			final IComponentsFunction<Number> base) {
		return new IComponentsFunction<Number>() {

			@Override
			public Name getIdentifier() {
				return base.getIdentifier();

			}

			@Override
			public Double compute(final IComponentsScope scope, final ILets lets) {
				return -base.compute(scope, lets).doubleValue();
			}

			@Override
			public Set<IDimension<?>> getDependencies() {
				return base.getDependencies();
			}

			@Override
			public Set<DateTime> getChangeDates() {
				return base.getChangeDates();
			}
		};
	}

	
	@Adapt(XSequenceAction.class) 
	public IComponentsAction buildSequenceAction(
			@Prop(XSequenceAction.P.actions) final List<IComponentsAction> delegates,
			@Prop(XSequenceAction.P.all) final boolean all,
			@Prop(XSequenceAction.P.hide) final List<XNumberDeclaration> decls
			) {
		final ImmutableSet.Builder<String> l = ImmutableSet.builder();
		
		for (final XNumberDeclaration decl : decls) {
			l.add(decl.getName());
		}
		
		return measureFactory.createSequenceAction(delegates, all, l.build());
	}

    @Adapt(XNumberDeclaration.class)
    public ChangeValue.Variable createVariableDescriptor(final XNumberDeclaration decl) {
        return new ChangeValue.Variable(decl.getName(), decl.getOn(), Optional.fromNullable(decl.getDefaultValue()));
    }

    @Adapt(XFailUnless.class)
    public FailUnless buildFailUnless(
        @Prop(XFailUnless.P.condition) final IComponentsFunction<Boolean> condition
        ) {
        return measureFactory.createFailUnless(condition);
    }

    @Adapt(XConsumeAction.class)
    public ConsumeAction buildConsume(
    		@Prop(XConsumeAction.P.variable) final ChangeValue.Variable variable,
    		@Prop(XConsumeAction.P.amount) final IComponentsFunction<Number> amount
    		) {
    	return measureFactory.createConsumeAction(variable, amount);
    }
    
	@Adapt(XSetAction.class)
	public ChangeValue buildSetAction(
			@Prop(XVarSetAction.P.variable) final List<ChangeValue.Variable> variables, 
			@Prop(XVarSetAction.P.value) final List<IComponentsFunction<Number>> functions,
            @Prop(XVarSetAction.P.under) final List<IComponentsAction> hypotheses
			) {
        return measureFactory.createValueSetter(variables, functions, hypotheses);
	}
	
	@Adapt(XIncreaseAction.class)
	public ChangeValue.Increaser buildIncreaseAction(
			@Prop(XVarSetAction.P.variable) final List<ChangeValue.Variable> variables, 
			@Prop(XVarSetAction.P.value) final List<IComponentsFunction<Number>> functions,
            @Prop(XVarSetAction.P.under) final List<IComponentsAction> hypotheses
			) {
        return measureFactory.createValueIncreaser(variables, functions, hypotheses);
	}
	
	@Adapt(XDecreaseAction.class)
	public ChangeValue.Decreaser buildDecreaseAction(@Prop(XVarSetAction.P.variable) final List<ChangeValue.Variable> variables, 
                                                   @Prop(XVarSetAction.P.value) final List<IComponentsFunction<Number>> functions,
                                                   @Prop(XVarSetAction.P.under) final List<IComponentsAction> hypotheses
                                                   ) {
        return measureFactory.createValueDecreaser(variables, functions, hypotheses);
	}
	
	@Adapt(XSnapshotAction.class)
	public SnapshotAction buildSnapshotAction(
			@Prop(XSnapshotAction.P.name) final XSnapshotDeclaration snapshot,
			@Prop(XSnapshotAction.P.modifications) final List<IComponentsAction> changes
			) {
		return measureFactory.createSnapshotAction(snapshot.getName(), changes);
	}
	
	@Adapt(XSequenceFunction.class)
	public IComponentsFunction<Number> buildSequenceFunction(
			@Prop(XSequenceFunction.P.delegate) final IComponentsFunction<Number> delegate,
			@Prop(XSequenceFunction.P.sequence) final List<ISequenceSpecialAction> actions
			) {
		return functions.createSequenceFunction(delegate, actions);
	}
	
	@Adapt(XNumberDeclaration.class)
	public Initializable ignoreDeclaration() {
		return Initializable.NOP;
	}
	
	@Adapt(XSnapshotDeclaration.class)
	public Initializable ignoreSnapshotDeclaration() {
		return Initializable.NOP;
	}
}
