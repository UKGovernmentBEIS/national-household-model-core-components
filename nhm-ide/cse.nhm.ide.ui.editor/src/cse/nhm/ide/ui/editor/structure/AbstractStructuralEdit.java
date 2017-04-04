package cse.nhm.ide.ui.editor.structure;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;

import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

abstract class AbstractStructuralEdit extends AbstractTextCommandHandler implements IHandler {
	
	@Override
	void doExecute(ExecutionEvent event, final ITextEditor editor, final IDocument doc, final TextSelection sel) {
		final IRewriteTarget rwt = (IRewriteTarget) editor.getAdapter(IRewriteTarget.class);
		final int[] minmax = new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
		final IDocumentListener listener = new IDocumentListener() {
			@Override
			public void documentChanged(final DocumentEvent event) {
				try {
					minmax[0] = Math.min(minmax[0], event.getDocument().getLineOfOffset(event.getOffset()));
					minmax[1] = Math.max(minmax[1], event.getDocument().getLineOfOffset(event.getOffset()));
				} catch (final BadLocationException e) {}
			}
			
			@Override
			public void documentAboutToBeChanged(final DocumentEvent event) {}
		};
		try {
			doc.addDocumentListener(listener);
			if (rwt != null) rwt.beginCompoundChange();
			final Expr root = Form.readAllAsOne(new StringReader(doc.get()), true);
			
			// so, we want to extend this thing to contain its next sibling
			
			doStructuralEdit(editor, doc, sel, root);
			
			minmax[1] = Math.min(minmax[1], doc.getNumberOfLines()-1);
			Indenter.indent(doc, minmax[0], minmax[1]);
		} catch (final IOException e) {
		} catch (final BadLocationException e) {
		} finally {
			doc.removeDocumentListener(listener);
			if (rwt != null) rwt.endCompoundChange();
		}
	}

	abstract void doStructuralEdit(ITextEditor editor, IDocument doc, TextSelection sel, Expr root);
}
