package cse.nhm.ide.ui.editor.structure;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRewriteTarget;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;

public class TabulateScenarioHandler extends AbstractTextCommandHandler implements IHandler {
	@Override
	void doExecute(ExecutionEvent event, final ITextEditor ed, final IDocument doc, final TextSelection textSel) {
		final int startLine;
		final int endLine;
		
		if (textSel.getLength() == 0) {
			return;
		} else {
			startLine = textSel.getStartLine();
			endLine = textSel.getEndLine();
		}
		
		final IRewriteTarget rwt = (IRewriteTarget) ed.getAdapter(IRewriteTarget.class);
		
		try {
			if (rwt != null) rwt.beginCompoundChange();
			Tabulator.tabulate(doc, textSel.getOffset(), textSel.getLength());
			Indenter.indent(doc, startLine, endLine);
		} catch (final BadLocationException e) {
			e.printStackTrace();
		} finally {
			if (rwt != null) rwt.endCompoundChange();
		}
	}
}
