package cse.nhm.ide.ui.editor.structure;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.texteditor.ITextEditor;

import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public abstract class NavigateCommandHandler extends AbstractStructuralEdit {
	public static class Right extends NavigateCommandHandler {
		@Override
		protected Integer moveTo(int offset, Form container) {
			if (offset == container.offset) {
				return (int) container.eoffset;
			} else if (offset == container.eoffset) {
				if (container.next != null) {
					return (int) container.next.offset;
				}
			} else if (container instanceof Expr) {
				// we are in a white space
				for (final Form f : ((Expr)container).children) {
					if (f.offset == offset) {
						return (int) f.eoffset;
					} else if (f.offset > offset) {
						return (int) f.offset;
					}
				}
				return (int) (container.eoffset - 1);
			} else {
				return (int) container.eoffset;
			}
			return null;
		}
		@Override
		protected int skip() {
			return 1;
		}
	}
	
	public static class Left extends NavigateCommandHandler {
		@Override
		protected Integer moveTo(int offset, Form container) {
			if (offset == container.eoffset) {
				return (int) container.offset;
			} else if (offset == container.offset) {
				if (container.prev != null) {
					return (int) container.prev.eoffset;
				}
			} else if (container instanceof Expr) {
				// we are in a white space
				final Expr e = (Expr) container;
				for (int k = e.children.length-1; k >=0 ; k--) {
					final Form f = e.children[k];
					if (f.eoffset == offset) {
						return (int) f.offset;
					} else if (f.eoffset < offset) {
						return (int) f.eoffset;
					}
				}
				return (int) container.offset+1;
			} else {
				return (int) container.offset;
			}
			return null;
		}
		@Override
		protected int skip() {
			return -1;
		}
	}
	
	public static class Up extends NavigateCommandHandler {
		@Override
		protected Integer moveTo(int offset, Form container) {
			if (container instanceof Expr && (offset > (container.offset + 1))) {
				return (int) container.offset + 1;
			}
			return (int) container.up.offset + 1;
		}
		@Override
		protected int skip() {
			return 0;
		}
	}
	
	public static class Down extends NavigateCommandHandler {
		@Override
		protected Integer moveTo(int offset, Form container) {
			
			final Expr e = (container instanceof Expr) ? (Expr) container : container.up;
			for (final Form f : e.children) {
				if (f instanceof Expr && f.offset >= offset) {
					return (int) f.offset+1;
				}
			}
			
			return (int) container.eoffset;
		}
		
		@Override
		protected int skip() {
			return 1;
		}
	}
	
	@Override
	void doStructuralEdit(ITextEditor editor, IDocument doc, TextSelection sel, Expr root) {
		Form container = root.findContainer(sel.getOffset());
		if (container == null) return;
		Integer target = moveTo(sel.getOffset(), container);
		
		if (target == null || target == sel.getOffset()) {
			target = sel.getOffset() + skip();
		}
		editor.selectAndReveal((int) target, 0);
	}
	
	protected abstract int skip();

	protected abstract Integer moveTo(int offset, Form container);

}
