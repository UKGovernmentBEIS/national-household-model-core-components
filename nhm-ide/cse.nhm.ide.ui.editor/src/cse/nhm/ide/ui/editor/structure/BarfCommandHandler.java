package cse.nhm.ide.ui.editor.structure;

import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;

import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public class BarfCommandHandler extends AbstractStructuralEdit implements IHandler {

	@Override
	void doStructuralEdit(final ITextEditor editor, final IDocument doc, final TextSelection sel, final Expr root) {
		final Expr containingExpr = root.findExpr(sel.getOffset());
		if (containingExpr.delimiter == '!') return;
		if (containingExpr != null && containingExpr.children.length > 0) {
			// we need to shift the end of containingExpr back before the last thing in it.
			final String closer = "" + Form.opposite(containingExpr.delimiter);
			final int closePosition;
			
			if (containingExpr.children.length == 1) {
				closePosition = (int) containingExpr.offset+1;
			} else {
				closePosition = (int) containingExpr.children[containingExpr.children.length - 2].eoffset;
			}
			
			try {
				doc.replace((int) (containingExpr.eoffset-1), 1, "");
				doc.replace(closePosition, 0, closer);
				editor.selectAndReveal((int) containingExpr.offset + 1, 0);
			} catch (final BadLocationException e) {}
		}
	}
}
