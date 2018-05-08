package uk.org.cse.nhm.simulator.factories;

import java.util.Set;

import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.action.finance.AdditionalCostAction;
import uk.org.cse.nhm.simulator.action.finance.FixedInterestLoanAction;
import uk.org.cse.nhm.simulator.action.finance.SubsidyAction;
import uk.org.cse.nhm.simulator.action.finance.obligations.IPaymentSchedule;
import uk.org.cse.nhm.simulator.action.finance.obligations.ObligationAction;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IFinanceFactory {

    public static final String FIXED_REPAYMENT = "fixed-repayment";

    @Named(FIXED_REPAYMENT)
    IObligation createFixedRepaymentLoanObligation(
            DateTime anniversary,
            @Assisted("principal") double principal,
            @Assisted("term") int term,
            @Assisted("index") int index,
            @Assisted("rate") double rate,
            @Assisted("tilt") double tilt,
            String payee,
            Set<String> tags);

    FixedInterestLoanAction createFixedInterestLoanAction(
            @Assisted("principal") IComponentsFunction<? extends Number> principal,
            @Assisted IComponentsAction delegate,
            @Assisted("term") IComponentsFunction<Number> term,
            @Assisted("rate") IComponentsFunction<Number> rate,
            @Assisted("tilt") IComponentsFunction<Number> tilt,
            @Assisted String payee,
            @Assisted Set<String> tags
    );

    AdditionalCostAction createCost(
            @Assisted Set<String> tags,
            @Assisted IComponentsAction action,
            @Assisted String counterparty,
            @Assisted IComponentsFunction<? extends Number> cost);

    SubsidyAction createSubsidy(
            @Assisted Set<String> tags,
            @Assisted IComponentsAction action,
            @Assisted String counterparty,
            @Assisted IComponentsFunction<? extends Number> subsidy);

    ObligationAction createObligation(
            @Assisted Set<String> tags,
            @Assisted IComponentsAction action,
            @Assisted String counterparty,
            @Assisted IComponentsFunction<? extends Number> amount,
            @Assisted IPaymentSchedule.IFactory scheduleFactory);
}
