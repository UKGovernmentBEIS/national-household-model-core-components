package cse.nhm.ide.ui.editor.structure;

import java.io.StringReader;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.ILanguage.ICursor;
import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

import com.google.common.base.Optional;

import cse.nhm.ide.ui.builder.NHMBuilder;
import cse.nhm.ide.ui.editor.ScenarioEditor;
import cse.nhm.ide.ui.reader.Cursor;
import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public class JumpToThingHandler extends AbstractTextCommandHandler implements IHandler {
	@SuppressWarnings("unchecked")
	private void selectAndJumpTo(final String input) {
		final DefinitionSelectionDialog dsd = new DefinitionSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		dsd.setInitialPattern(input);
		if (Dialog.OK == dsd.open()) {
			final Object[] result = dsd.getResult();
			if (result != null && result.length > 0) {
				final Object selected = result[0];
				if (selected instanceof IDefinition) {
					// jump to the first location
					if (((IDefinition<IPath>) selected).locations().isEmpty()) return;
					final ILocation<IPath> loc = ((IDefinition<IPath>) selected).locations().get(0);
					jumpTo(loc);
				}
			}
		}
	}
	
	private void jumpTo(final ILocation<IPath> loc) {
		final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final Optional<IFile> fileForPath = NHMBuilder.fileForPath(loc.path());
		if (fileForPath.isPresent()) {
			final IFile file = fileForPath.get();
			try {
				final IMarker marker = file.createMarker(IMarker.TEXT);
				marker.setAttribute(IMarker.LINE_NUMBER, new Integer(loc.line()));
				IDE.openEditor(activePage, marker);
				marker.delete();
			} catch (final CoreException e) {
			}
		}
	}

	@Override
	void doExecute(ExecutionEvent event, final ITextEditor textEditor, final IDocument doc, final TextSelection sel) {
		if (sel.getLength() == 0) {
			try {
				if (textEditor instanceof ScenarioEditor) {
					final Optional<INationalHouseholdModel> model = ((ScenarioEditor) textEditor).getNationalHouseholdModel();
					if (model.isPresent()) {
						final INationalHouseholdModel nhm = model.get();
						ICursor c = Cursor.get(doc.get(), sel.getOffset() + 1);
						while (c != null) {
							if (c.command().isEmpty() ||
								nhm.language().elements().contains(c.command()) ||
								nhm.language().macros().contains(c.command())) {
								c = c.previous().orNull();
							} else {
								break;
							}
						}
						if (c != null) {
							selectAndJumpTo("*" + c.command());
							return;
						}
					}
				}
				final Expr root = Form.readAllAsOne(new StringReader(doc.get()), true);
				final Expr item = root.findExpr(sel.getOffset()+1);
				selectAndJumpTo("*" + item.children[0].toString());
			} catch (final Exception e) {
				doExecuteOtherwise(null);
			}
		} else {
			selectAndJumpTo("*" + sel.getText());
		}
	}
	
	@Override
	protected void doExecuteOtherwise(final ExecutionEvent event) {
		selectAndJumpTo("?");
	}
}
