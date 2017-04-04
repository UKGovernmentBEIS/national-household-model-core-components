package uk.org.cse.nhm.language.builder.action;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.Period;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.Glob;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.FromScope;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.TopLevelAdapter;
import uk.org.cse.nhm.language.definition.money.XAdditionalCost;
import uk.org.cse.nhm.language.definition.money.XFinanceAction;
import uk.org.cse.nhm.language.definition.money.XFullSubsidy;
import uk.org.cse.nhm.language.definition.money.XLoanAction;
import uk.org.cse.nhm.language.definition.money.XSubsidy;
import uk.org.cse.nhm.language.definition.money.obligations.XObligationAction;
import uk.org.cse.nhm.language.definition.money.obligations.XPeriodicPayment;
import uk.org.cse.nhm.language.definition.tags.Tag;
import uk.org.cse.nhm.simulator.action.finance.obligations.IPaymentSchedule;
import uk.org.cse.nhm.simulator.action.finance.obligations.PeriodicPaymentFactory;
import uk.org.cse.nhm.simulator.factories.IDefaultFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IFinanceFactory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.CostResultFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class FinanceAdapter extends ReflectingAdapter {
	final IFinanceFactory factory;
    private final IDefaultFunctionFactory functions;
    
	@Inject
	public FinanceAdapter(final Set<IConverter> delegates,
                          final IFinanceFactory factory,
                          final IDefaultFunctionFactory functions,
                          final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
        this.functions = functions;
	}

	@Adapt(XLoanAction.class)
	public IComponentsAction buildFixedRateLoan(final Name name,
			@FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
			@Prop(XLoanAction.P.principal) final IComponentsFunction<Number> principal, 
			@Prop(XFinanceAction.P.ACTION) final IComponentsAction delegate,
			@Prop(XLoanAction.P.term) final IComponentsFunction<Number> term, 
			@Prop(XLoanAction.P.rate) final IComponentsFunction<Number> rate, 
			@Prop(XLoanAction.P.tilt) final IComponentsFunction<Number> tilt, 
			@Prop(XFinanceAction.P.COUNTERPARTY) final Optional<String> payee, 
			@Prop(XFinanceAction.P.TAGS) final List<Tag> tags) {
		return factory.createFixedInterestLoanAction(
                functions.createWarningFunction(name, "loan principal", principal, 0d, 1000000d, true, false),
				delegate,
				functions.createWarningFunction(name, "loan term", term, 1d, 25d, true, false), 
				functions.createWarningFunction(name, "loan rate", rate, 0.005d, 100d, true, false), 
				functions.createWarningFunction(name, "loan tilt", tilt, 0d, 10d, true, false), 
				payee.or(policyName.or("unknown")), 
				Tag.asSet(tags));
	}
	
	 @Adapt(XAdditionalCost.class) 
	 public IComponentsAction buildMeasureAdditionalCost(
		 @FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
	     @Prop(XFinanceAction.P.TAGS) final List<Tag> tags, 
	     @Prop(XFinanceAction.P.ACTION) final IComponentsAction action,
	     @Prop(XFinanceAction.P.COUNTERPARTY) final Optional<String> counterparty,
	     @Prop(XAdditionalCost.P.COST) final IComponentsFunction<Number> cost
	     ) { 
	   return factory.createCost(
			   Tag.asSet(tags), 
			   action, 
			   counterparty.or(policyName.or("unknown")), 
			   cost); 
	 }
	 
	 @Adapt(XSubsidy.class)
	 public IComponentsAction buildSubsidy(
		 @FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
		 @Prop(XFinanceAction.P.TAGS) final List<Tag> tags, 
	     @Prop(XFinanceAction.P.ACTION) final IComponentsAction action,
	     @Prop(XFinanceAction.P.COUNTERPARTY) final Optional<String> counterparty,
	     @Prop(XSubsidy.P.SUBSIDY) final IComponentsFunction<Number> subsidy
	     ) {
		 return factory.createSubsidy(
				 Tag.asSet(tags), 
				 action, 
				 counterparty.or(policyName.or("unknown")), 
				 subsidy);
	 }
	 
	 @Adapt(XFullSubsidy.class)
	 public IComponentsAction buildFullSubsidy(
		 @FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
		 @Prop(XFinanceAction.P.TAGS) final List<Tag> tags, 
	     @Prop(XFinanceAction.P.ACTION) final IComponentsAction action,
	     @Prop(XFinanceAction.P.COUNTERPARTY) final Optional<String> counterparty
	     ) {
		 return factory.createSubsidy(
				 Tag.asSet(tags), 
				 action, 
				 counterparty.or(policyName.or("unknown")), 
				 new CostResultFunction(
						 Glob.requireAndForbid(
								 ImmutableList.of(
										 Glob.of(ITransaction.Tags.CAPEX)
										 )
								 )));
	 }
	 
	 @Adapt(XObligationAction.class)
	 public IComponentsAction buildWithObligation(
		 @FromScope(TopLevelAdapter.POLICY_NAME) final Optional<String> policyName,
		 @Prop(XFinanceAction.P.TAGS) final List<Tag> tags, 
	     @Prop(XFinanceAction.P.ACTION) final IComponentsAction action,
	     @Prop(XFinanceAction.P.COUNTERPARTY) final Optional<String> counterparty,
		 @Prop(XObligationAction.P.amount) final IComponentsFunction<Number> amount,
		 @Prop(XObligationAction.P.schedule) final IPaymentSchedule.IFactory scheduleFactory) {
		 return factory.createObligation(
		 	Tag.asSet(tags),
		 	action,
		 	counterparty.or(policyName.or("unknown")),
		 	amount,
		 	scheduleFactory);
	 }
	 
	 @Adapt(XPeriodicPayment.class)
	 public IPaymentSchedule.IFactory buildPeriodicPayment(
		 @Prop(XPeriodicPayment.P.interval) final Period interval,
		 @Prop(XPeriodicPayment.P.lifetime) final Optional<Period> lifetime,
		 @Prop(XPeriodicPayment.P.whileCondition) final Optional<IComponentsFunction<Boolean>> whileCondition
			 ) {
		 return new PeriodicPaymentFactory(interval, lifetime, whileCondition);
	 }
}
