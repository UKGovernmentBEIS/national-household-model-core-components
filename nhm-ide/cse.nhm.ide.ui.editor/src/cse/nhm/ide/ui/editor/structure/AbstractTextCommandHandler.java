package cse.nhm.ide.ui.editor.structure;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

abstract class AbstractTextCommandHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor instanceof ITextEditor) {
			final ITextEditor textEditor = (ITextEditor) editor;
			final IDocumentProvider prov = textEditor.getDocumentProvider();
			final IDocument doc = prov.getDocument(textEditor.getEditorInput());
			final ISelection sel = textEditor.getSelectionProvider().getSelection();
			if (sel instanceof TextSelection) {
				final TextSelection textSel = (TextSelection) sel;
				doExecute(event, textEditor, doc, textSel);
				return null;
			}
		}
		
		doExecuteOtherwise(event);
		
		return null;
	}

	protected void doExecuteOtherwise(final ExecutionEvent event) {
		
	}

	abstract void doExecute(ExecutionEvent event, ITextEditor textEditor, IDocument doc, TextSelection sel);
}
