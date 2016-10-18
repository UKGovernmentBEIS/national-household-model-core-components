package cse.nhm.ide.runner.ui.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;

public class DynamicRunCommandContributor extends CompoundContributionItem {
	public DynamicRunCommandContributor() {
		
	}

	public DynamicRunCommandContributor(final String id) {
		super(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IContributionItem[] getContributionItems() {
		final List<IContributionItem> items = new ArrayList<>();
		for (final IScenarioRunner runner : RunnerUIPlugin.getDefault().tracker.getRunners()) {
			final CommandContributionItemParameter p = new CommandContributionItemParameter(PlatformUI.getWorkbench(), null, RunUtil.COMMAND_ID, CommandContributionItem.STYLE_PUSH);
			p.parameters = new HashMap<String, String>();
			p.parameters.put(RunUtil.SERVICE_NAME_PARAMETER, runner.getName());
			p.label = "Run on " + runner.getDescription();
			items.add(new CommandContributionItem(p));
		}
		return items.toArray(new IContributionItem[items.size()]);
	}

}
