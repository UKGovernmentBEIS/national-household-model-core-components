package cse.nhm.ide.ui.editor.outline;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import cse.nhm.ide.ui.editor.ScenarioEditor;

public class QuickOutlineHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		if (activeEditor instanceof ScenarioEditor) {
			final ScenarioOutlineInformationControl widget = 
					new ScenarioOutlineInformationControl(
							(ScenarioEditor) activeEditor,
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							SWT.RESIZE);
			widget.setInput(((ScenarioEditor) activeEditor).getScenarioModel());
			widget.setVisible(true);
			widget.setFocus();
		} else {}
		return null;
	}

}
