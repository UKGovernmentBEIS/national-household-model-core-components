package uk.org.cse.nhm.language.builder.function;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.language.definition.function.bool.XAll;
import uk.org.cse.nhm.language.definition.function.bool.XAny;
import uk.org.cse.nhm.language.definition.function.bool.XNone;
import uk.org.cse.nhm.language.definition.function.bool.XTestValue;
import uk.org.cse.nhm.language.definition.function.bool.XYearIs;
import uk.org.cse.nhm.language.definition.function.bool.house.XAgeIs;
import uk.org.cse.nhm.language.definition.sequence.XFunctionDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XTestDeclaration;
import uk.org.cse.nhm.simulator.factories.IBooleanFunctionFactory;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.logic.LogicalCombination.Mode;
import uk.org.cse.nhm.simulator.state.functions.impl.logic.TestValue;

/**
 * Builder adapter for logic, which handles the elements:
 * 
 * <ol>
 * 	<li> {@link XAll} </li>
 *  <li> {@link XAny} </li>
 *  <li> {@link XNone} </li>
 * </ol>
 * @author hinton
 *
 */
public class LogicFunctionAdapter extends ReflectingAdapter {
	private final IBooleanFunctionFactory factory;
	
	@Inject
	public LogicFunctionAdapter(final Set<IConverter> delegates, final IBooleanFunctionFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
	}

	@Adapt(XAll.class)
	public IComponentsFunction<Boolean> buildAll(
			@Prop(XAll.P.INPUTS) final List<IComponentsFunction<Boolean>> inputs
			) {
		return factory.combine(Mode.ALL, inputs);
	}
	
	@Adapt(XAny.class)
	public IComponentsFunction<Boolean> buildAny(
			@Prop(XAll.P.INPUTS) final List<IComponentsFunction<Boolean>> inputs
			) {
		return factory.combine(Mode.ANY, inputs);
	}
	
	@Adapt(XNone.class)
	public IComponentsFunction<Boolean> buildNone(
			@Prop(XAll.P.INPUTS) final List<IComponentsFunction<Boolean>> inputs
			) {
		return factory.combine(Mode.NONE, inputs);
	}
	
	@Adapt(XYearIs.class)
	public IComponentsFunction<Boolean> buildYearFunction(
			@Prop("foresight") final Optional<XForesightLevel> foresight,
			@Prop(XAgeIs.P.ABOVE) final Optional<Integer> above,
			@Prop(XAgeIs.P.EXACTLY) final Optional<Integer> exactly,
			@Prop(XAgeIs.P.BELOW) final Optional<Integer> below
			) {
		return factory.matchSimYear(foresight, new RangeTest(above, exactly, below));
	}
	
	@Adapt(XTestValue.class) 
	public IComponentsFunction<Boolean> buildTestValueFunction(
			@Prop(XTestValue.P.function) final IComponentsFunction<?> function,
			@Prop(XTestValue.P.values) final List<String> values
			) {
		return TestValue.create(function, values);
	}
	
	@Adapt(XTestDeclaration.class)
	public Initializable ignoreTestDeclaration() {
		return Initializable.NOP;
	}

	@Adapt(value = XTestDeclaration.class, cache = true)
	public IComponentsFunction<Boolean> buildTestDeclaration(
			@Prop(XFunctionDeclaration.VALUE) final IComponentsFunction<Boolean> fn
			) {
		return fn;
	}
}
