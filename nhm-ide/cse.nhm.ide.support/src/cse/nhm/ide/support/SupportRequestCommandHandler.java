package cse.nhm.ide.support;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import cse.nhm.ide.support.wizards.NewSupportRequestWizard;

public class SupportRequestCommandHandler extends AbstractHandler implements IHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		  Shell activeShell = HandlerUtil.getActiveShell(event);
		  
		  final ISelection sel =  HandlerUtil.getActiveWorkbenchWindow(event).getSelectionService().getSelection();
		  NewSupportRequestWizard wizard = new NewSupportRequestWizard();
		  wizard.init(HandlerUtil.getActiveWorkbenchWindow(event).getWorkbench(),
				 sel instanceof IStructuredSelection ? (IStructuredSelection) sel : null);
		  WizardDialog dialog = new WizardDialog(activeShell, wizard);
		  
		  dialog.open();

		  return null;
	}
}
