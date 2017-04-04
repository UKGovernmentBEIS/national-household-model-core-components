package cse.nhm.ide.ui.editor.structure;

import java.io.StringReader;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.google.common.base.Preconditions;

import cse.nhm.ide.ui.editor.NHMEditorPlugin;
import cse.nhm.ide.ui.editor.preferences.PreferenceConstants;
import cse.nhm.ide.ui.reader.Atom;
import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public class Strategy implements IAutoEditStrategy, IPropertyChangeListener {
	
//	store.setDefault(PreferenceConstants.DELETE_BOTH, true);
//	store.setDefault(PreferenceConstants.INSERT_CLOSING, true);
//	store.setDefault(PreferenceConstants.SKIP_CLOSING, true);
//	store.setDefault(PreferenceConstants.SKIP_DELETE, true);
//	store.setDefault(PreferenceConstants.WRAP_SELECTION, true);
	
	private boolean deleteBoth;
	private boolean deleteSkip;
	
	private boolean insertClosing;
	private boolean skipJumpClosing;
	
	private boolean insertWrap;
	
	public Strategy() {
		super();
		final IPreferenceStore store = NHMEditorPlugin.getDefault().getPreferenceStore();
		store.addPropertyChangeListener(this);
		readPreferences(store);
	}

	private void readPreferences(final IPreferenceStore store) {
		deleteBoth = store.getBoolean(PreferenceConstants.DELETE_BOTH);
		deleteSkip = store.getBoolean(PreferenceConstants.SKIP_DELETE);
		insertClosing = store.getBoolean(PreferenceConstants.INSERT_CLOSING);
		skipJumpClosing = store.getBoolean(PreferenceConstants.SKIP_CLOSING);
		insertWrap = store.getBoolean(PreferenceConstants.WRAP_SELECTION);
	}

	boolean isEnabled() {
		return deleteBoth || deleteSkip || insertClosing || skipJumpClosing || insertWrap;
	}
	
	@Override
	public void customizeDocumentCommand(final IDocument document, final DocumentCommand command) {
		Preconditions.checkNotNull(document);
		Preconditions.checkNotNull(command);
		
		if (!isStructuralCommand(document, command) || !isEnabled()) {
			return;
		}
		
		// try and parse document as it is now.
		try {
			final Expr forms = Form.readAllAsOne(new StringReader(document.get()), true);
			try {
				if (command.length == 0) {
					customizeInsertCommand(forms, document, command);
				} else if (command.text.isEmpty()) {
					customizeDeleteCommand(forms, document, command);
				} else {
					customizeReplaceCommand(document, command);
				}
				
			} catch (final BadLocationException ble) {
				
			}
		} catch (final Exception e) {
			return;
		}
	}

	/**
	 * We care if the user has (a) typed a structural character or (b) removed a structural character
	 */
	private static boolean isStructuralCommand(final IDocument document, final DocumentCommand command) {
		// TODO check if the typing is in a commented region so we can not bother
		
		// if we are deleting more than one character, give up
		if (command.text.isEmpty() && command.length > 1)  return false;
		else if (command.text.length() > 1) return false;
		
		for (int i = 0; i<command.text.length(); i++) {
			if (Form.isStructural(command.text.charAt(i))) return true;
		}
		
		try {
			for (int i = 0; i<command.length; i++) {
				if (Form.isStructural(document.getChar(command.offset + i))) {
					return true; // because we only handle things of length 1 really.
					// we are replacing some text, and that text is structural
					// if the text we are replacing is a balanced chunk, we can not bother with this
					// but if we are replacing an unbalanced chunk we need to think about it.
					//return !Form.isBalanced(document.get(command.offset, command.length)); 
				}
			}
		} catch (final BadLocationException ble) {}
		
		return false;
	}

	private void customizeInsertCommand(final Expr forms, final IDocument document, final DocumentCommand command) throws BadLocationException {
		if (command.text.length() == 1) {
			final char c = command.text.charAt(0);
			if (insertClosing && Form.isOpening(c)) {
				// add closing to balance it
				command.text += Form.opposite(c);
				command.caretOffset = command.offset + 1;
				command.shiftsCaret = false;
			} else if (skipJumpClosing && Form.isClosing(c)) {
				// if the command is right there, we just do easy option
				final char c2 = document.getChar(command.offset);
				if (c2 == c) {
					command.text = "";
					command.caretOffset = command.offset+1;
					return;
				}
				
				// we pull the bracket to us if that would only delete whitespace
				// otherwise we jump to just after the bracket. 
				// In either case, cursor is after bracket.
				
				Form container = forms.findContainer(command.offset + 1);
				if (container instanceof Atom) {
					container = container.up;
				}
				if (container == forms) {
					// disable the command entirely, and do nothing
					command.text = "";
				} else {
					final int offset = (int) container.eoffset - 1;
					boolean isAllWhitespace = true;
					for (int i = command.offset; i<offset-1; i++) {
						if (!Character.isWhitespace(document.getChar(i))) {
							isAllWhitespace = false;
							break;
						}
					}
					if (isAllWhitespace) {
						command.length = 1 + offset - command.offset;
					} else {
						// just shift the cursor to that location
						command.length = 0;
						command.text = "";
						command.caretOffset = offset;
						command.shiftsCaret = false;
					}
				}
			}
		} else { 
			// unsupported NOP
		}
	}

	private void customizeDeleteCommand(final Expr top, final IDocument document, final DocumentCommand command) throws BadLocationException {
		// things we know:
		// * command.text isempty
		// * document text being killed contains some special characters
		// * the document text being killed is not balanced

		// special case first
		if (command.length == 1) {
			final char deleting = document.getChar(command.offset);
			
			final Form f;
			if (Form.isOpening(deleting)) {
				f = top.findContainer(command.offset + 1);
			} else if (Form.isClosing(deleting)) {
				f = top.findContainer(command.offset + 1);
			} else {
				f = null;
			}
			
			if (deleteBoth && f != null && (f.isEmpty() || ! deleteSkip)) {
				command.offset = (int) f.offset;
				command.length = (int) ((f.eoffset) - f.offset);
				final String content = document.get((int)f.offset+1, (int)(f.eoffset - f.offset) - 2);
				command.text = content;
				if (Form.isOpening(deleting)) {
					command.shiftsCaret = false;
					command.caretOffset = command.offset;
				}
			} else if (deleteSkip && f != null) {
				command.length = 0;
			}
		} else {
			// difficult case: we are deleting some chunk of text, which maybe we need to think about?
			// unsupported NOP
		}
	}

	private void customizeReplaceCommand(final IDocument doc, final DocumentCommand command) throws BadLocationException {
		// I guess we can say :
		// a) there is a selection
		// b) we are writing something on it.
		// if the thing we are writing is ( etc., we can wrap the selection
		// otherwise we do nothing
		if (insertWrap && (command.text.length() == 1 && Form.isOpening(command.text.charAt(0)))) {
			command.text += doc.get(command.offset, command.length) + Form.opposite(command.text.charAt(0));
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		readPreferences(NHMEditorPlugin.getDefault().getPreferenceStore());
	}
}