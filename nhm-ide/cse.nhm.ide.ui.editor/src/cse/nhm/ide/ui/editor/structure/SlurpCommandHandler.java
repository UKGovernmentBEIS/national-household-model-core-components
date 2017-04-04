package cse.nhm.ide.ui.editor.structure;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;

import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public class SlurpCommandHandler extends AbstractStructuralEdit {
	@Override
	void doStructuralEdit(final ITextEditor editor, final IDocument doc, final TextSelection sel, final Expr root) {
		final Expr containingExpr = root.findExpr(sel.getOffset());
		if (containingExpr != null && containingExpr.next != null) {
			try {
				final char c = doc.getChar((int) (containingExpr.eoffset));
				final String insert = "" + Form.opposite(containingExpr.delimiter);
				if (Character.isWhitespace(c) || containingExpr.offset == containingExpr.eoffset - 2) {
					doc.replace((int) (containingExpr.eoffset-1), 1, "");
					doc.replace((int) containingExpr.next.eoffset - 1, 0, insert);
				} else {
					doc.replace((int) (containingExpr.eoffset - 1), 1, " ");
					doc.replace((int) (containingExpr.next.eoffset), 0, insert);
				}
				editor.selectAndReveal((int) containingExpr.offset+1, 0);
			} catch (final BadLocationException e) {}
		}
	}
}
