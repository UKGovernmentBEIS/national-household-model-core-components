package uk.org.cse.nhm.language.builder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.PutScope;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.exposure.IExposureFactory;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.XCase;
import uk.org.cse.nhm.language.definition.XCaseOtherwise;
import uk.org.cse.nhm.language.definition.XCaseWhen;
import uk.org.cse.nhm.language.definition.XPolicy;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.XTarget;
import uk.org.cse.nhm.language.definition.enums.XEnergyCalculatorType;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.factories.IGroupFactory;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.guice.SimpleScope;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Adapter which handles
 * 
 * <ul>
 * <li> {@link XScenario}</li>
 * <li> {@link XPolicy}</li>
 * <li> {@link XTarget}</li>
 * <li> {@link XCase}</li>
 * <li> {@link XCaseWhen}</li>
 * <li> {@link XCaseOtherwise}</li>
 * </ul>
 * 
 * for the simulator builder. Because most of these are top-level parts of the
 * simulation, they are actually adapted to {@link Initializable#NOP}.
 * 
 * @author hinton
 * 
 */
public class TopLevelAdapter extends ReflectingAdapter {
	private final IGroupFactory groupFactory;
	private final Injector injector;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(TopLevelAdapter.class);

	/**
	 * A key used with {@link PutScope} and {@link FromScope} to allow things to
	 * get hold of the name of the enclosing policy
	 */
	public static final String POLICY_NAME = "policy.name";
	private final SimpleScope simulationScope;

	@Inject
	public TopLevelAdapter(final Set<IConverter> delegates, final Injector injector,
			final IGroupFactory groupFactory, final Set<IAdapterInterceptor> interceptors, final SimpleScope simulationScope) {
		super(delegates, interceptors);
		this.injector = injector;
		this.groupFactory = groupFactory;
		this.simulationScope = simulationScope;
	}

	/**
	 * Builds a simulator - because simulator is a simulation singleton, we just
	 * have an injected provider which we get().
	 * 
	 * However, for convenience we also want to put some things into the
	 * adapting scope, and cause the adapting of the context and policies.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param quantum
	 * @param context
	 * @param policies
	 * @return
	 */
	@Adapt(XScenario.class)
	public ISimulator buildSimulator(
			@PutScope(SimulatorConfigurationConstants.START_DATE) @Prop(XScenario.P.START_DATE) final DateTime startDate,
			@PutScope(SimulatorConfigurationConstants.END_DATE) @Prop(XScenario.P.END_DATE) final DateTime endDate,
			@PutScope(SimulatorConfigurationConstants.GRANULARITY) @Prop(XScenario.P.GRANULARITY) final int quantum,
            @PutScope(SimulatorConfigurationConstants.SURVEY_WEIGHT_FUNCTION) @Prop(XScenario.P.SURVEY_WEIGHTING) final IComponentsFunction<Number> surveyWeightFunction,
            @Prop(XScenario.P.CALCULATOR_TYPE) final XEnergyCalculatorType calculatorType,
			@Prop(XScenario.P.CONTENTS) final List<Initializable> context) {

		simulationScope.seed(
                Key.get(new TypeLiteral<IComponentsFunction<Number>>() {}, SimulatorConfigurationConstants.SurveyWeightFunction),
				surveyWeightFunction);
		
		simulationScope.seed(
				Key.get(EnergyCalculatorType.class, SimulatorConfigurationConstants.EnergyCalculatorType), 
				MapEnum.energyCalc(calculatorType));
		
		if (quantum == 0)
			throw new ArithmeticException(
					"Quantum cannot be zero - that would require dividing by zero");

		return injector.getInstance(ISimulator.class);
	}

	/**
	 * Build a policy. Because policies don't currently do anything, we just get
	 * the name and cause the actions to be adapted.
	 * 
	 * @param name
	 * @param actions
	 * @return
	 */
	@Adapt(XPolicy.class)
	public Initializable buildPolicy(
			@PutScope(POLICY_NAME) @Prop("name") final String name,
			@Prop(XPolicy.P.ACTIONS) final List<Initializable> actions) {
		return Initializable.NOP;
	}

	/**
	 * Adapt a target - this has to do something
	 * 
	 * @param name
	 * @param group
	 * @param exposure
	 * @return
	 */
	@Adapt(XTarget.class)
	public Initializable buildTarget(
			final Name id,
			@Prop(XTarget.P.GROUP) final IDwellingGroup group,
			@Prop(XTarget.P.ACTION) final IStateAction action,
			@Prop(XTarget.P.EXPOSURE) final IExposureFactory exposure) {
		log.debug("create target: {} {} {} {}", new Object[] { id, group,
				action, exposure });
		exposure.create(id, group, action);
		return Initializable.NOP;
	}

	/**
	 * Adapt a case - this just triggers adaptation of the whens and the
	 * otherwise.
	 * 
	 * @param whens
	 * @param otherwise
	 * @return
	 */
	@Adapt(XCase.class)
	public Initializable buildCase(
			@Prop(XCase.P.source) final IDwellingGroup group,
			@Prop(XCase.P.WHENS) final List<CaseBranch> whens,
			@Prop(XCase.P.OTHERWISE) final Optional<CaseBranch> maybeOtherwise) {
		final List<IComponentsFunction<Boolean>> functions = new ArrayList<IComponentsFunction<Boolean>>();

		for (final CaseBranch when : whens) {
			functions.add(when.test);
		}

		final Condition c = groupFactory.createCondition(group, functions);

		final Iterator<CaseBranch> whenIterator = whens.iterator();

		for (final IDwellingGroup dg : c.getGroups()) {
			final CaseBranch cb = whenIterator.next();
			cb.exposure.create(cb.identifier, dg, cb.action);
		}

		if (maybeOtherwise.isPresent()) {
			final CaseBranch otherwise = maybeOtherwise.get();
			otherwise.exposure.create(otherwise.identifier, c.getDefaultGroup(),
					otherwise.action);
		}

		return Initializable.NOP;
	}

	public static class CaseBranch {
		public final Name identifier;
		public final IExposureFactory exposure;
		public final IComponentsFunction<Boolean> test;
		public final IStateAction action;

		public CaseBranch(final Name name, final IExposureFactory exposure,
				final IComponentsFunction<Boolean> test, final IStateAction action) {
			super();
			this.identifier = name;
			this.exposure = exposure;
			this.test = test;
			this.action = action;
		}
	}

	@Adapt(XCaseWhen.class)
	public CaseBranch buildWhen(
			final Name id,
			@Prop(XCaseWhen.P.EXPOSURE) final IExposureFactory exposure,
			@Prop(XCaseWhen.P.ACTION) final IStateAction action,
			@Prop(XCaseWhen.P.TEST) final IComponentsFunction<Boolean> test) {
		return new CaseBranch(id, exposure, test, action);
	}

	@Adapt(XCaseOtherwise.class)
	public CaseBranch buildOtherwise(
			final Name id,
			@Prop(XCaseOtherwise.P.EXPOSURE) final IExposureFactory exposure,
			@Prop(XCaseOtherwise.P.ACTION) final IStateAction action) {
		return new CaseBranch(id, exposure, null, action);
	}
}
