package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetHouseholdIncome extends FinancialAttributesFunction<Double> implements
		IComponentsFunction<Double> {
	
	private final IDimension<BasicCaseAttributes> basicAttributes;
	private final ILogEntryHandler handler;

	@Inject
	public GetHouseholdIncome(
			final IDimension<FinancialAttributes> bad, 
			final IDimension<BasicCaseAttributes> basicAttributes,
			final ILogEntryHandler handler) {
		super(bad);
		this.basicAttributes = basicAttributes;
		this.handler = handler;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		Double income = getAttributes(scope).getHouseHoldIncomeBeforeTax();
		
		if (income == null){
			handler.acceptLogEntry(new WarningLogEntry(
					"Survey has no income data for case (it is probably an empty house); an income of zero has been used, which will skew the distribution.",
                    ImmutableMap.of("aacode", scope.get(basicAttributes).getAacode())));
            return 0d;
		}
		
		return income;
	}
}
