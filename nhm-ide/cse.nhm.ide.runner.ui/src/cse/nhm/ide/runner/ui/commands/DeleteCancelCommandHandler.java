package cse.nhm.ide.runner.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;

public class DeleteCancelCommandHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) sel;
			for (final Object o : ssel.toArray()) {
				if (o instanceof IScenarioRun) {
					try {
						((IScenarioRun) o).delete();
					} catch (final Throwable th) {
						RunnerUIPlugin.error("Error deleting " + o, th);
					}
				}
			}
		}
		return null;
	}

}
