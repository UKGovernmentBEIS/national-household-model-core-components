package uk.org.cse.nhm.simulator.action.finance;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class SubsidyAction extends AbsFinanceAction {
	private final IComponentsFunction<? extends Number> subsidy;
	private final ILogEntryHandler loggingService;

	@Inject
	public SubsidyAction(
			@Assisted final Set<String> tags,
			@Assisted final IComponentsAction action,
			@Assisted final String counterparty,
			@Assisted final IComponentsFunction<? extends Number> subsidy,
			final ILogEntryHandler loggingService) {
		super(withSubsidyTag(tags), action, counterparty);
		this.subsidy = subsidy;
		this.loggingService = loggingService;
	}

	@Override
	protected double compute(final ISettableComponentsScope scope, final ILets lets) {
		final double subsidyAmount = subsidy.compute(scope, lets).doubleValue();
		
		if (subsidyAmount < 0) {
			loggingService.acceptLogEntry(new WarningLogEntry("Attempted to apply negative subsidy. Set subsidy to 0 instead.", 
					ImmutableMap.<String, String>of(
							"invalid", Double.toString(subsidyAmount),
							"dwellingID", Integer.toString(scope.getDwellingID()))));
			return 0;
		} else {
			return -subsidyAmount; // Transactions cost the house some money, so a negative value pays it some money.
		}
	}
	
	private static Set<String> withSubsidyTag(final Set<String> tags) {
		return ImmutableSet.<String>builder()
				.addAll(tags)
				.add(ITransaction.Tags.SUBSIDY)
				.build();
	}
}
