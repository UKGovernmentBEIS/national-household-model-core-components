package cse.nhm.ide.ui.editor;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.help.IContextProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import cse.nhm.ide.ui.NHMUIPlugin;
import cse.nhm.ide.ui.PluginPreferences;
import cse.nhm.ide.ui.builder.NHMNature;
import cse.nhm.ide.ui.editor.ScenarioModel.Node;
import cse.nhm.ide.ui.editor.outline.ScenarioContentOutlinePage;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

public class ScenarioEditor extends TextEditor implements IAdaptable {
	private ScenarioContentOutlinePage outline = null;
	private ScenarioModel model;
	
	
	public ScenarioEditor() {
		// setup display stuff
		setDocumentProvider(new ScenarioDocumentProvider());
		setSourceViewerConfiguration(new ScenarioSourceViewerConfiguration(this));

		setKeyBindingScopes(new String [] {"cse.nhm.ide.ui.editor.context", "org.eclipse.ui.textEditorScope"});
	}
	
	@Override
	protected void configureSourceViewerDecorationSupport(final SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);
		// enable colouring in of brackets
		final char[] matchChars = {'(', ')', '[', ']', '{', '}'}; //which brackets to match
        final DefaultCharacterPairMatcher matcher =
                        new DefaultCharacterPairMatcher(matchChars,
                                        IDocumentExtension3.DEFAULT_PARTITIONING);
        support.setCharacterPairMatcher(matcher);
        support.setMatchingCharacterPainterPreferenceKeys(
                        PluginPreferences.EDITOR_MATCHING_BRACKETS,
                        PluginPreferences.EDITOR_MATCHING_BRACKETS_COLOR);
        setPreferenceStore(new ChainedPreferenceStore(new IPreferenceStore[] {
                        NHMUIPlugin.getDefault().getPreferenceStore(),
                        getPreferenceStore()
        }));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(final Class adapter) {
		if (adapter.isAssignableFrom(ScenarioContentOutlinePage.class)) {
			if (this.outline == null) {
				this.outline = new ScenarioContentOutlinePage(this);
				if (this.model != null) {
					this.outline.updateModel(this.model);
				}
			}
			return this.outline;
		} else if (adapter == IContextProvider.class) {
			return new ScenarioEditorHelpContextProvider();
		}
		return super.getAdapter(adapter);
	}

	public void updateModel(final ScenarioModel model) {
		if (this.outline != null) {
			this.outline.updateModel(model);
		}
		
		this.model = model;
		
		if (getEditorInput() instanceof IFileEditorInput) {
			final IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			try {
				file.deleteMarkers("cse.nhm.ide.ui.editor.parenmarker", true, IResource.DEPTH_INFINITE);

				for (final long l : model.unclosed) {
					bracketMarker(l, file, "Too many opening parenthesis (a close parenthesis is missing)");
				}
				
				for (final long l : model.unopened) {
					bracketMarker(l, file, "Too many closing parentheses (no matching open parenthesis)");
				}
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private static void bracketMarker(final long l, final IFile file, final String message) throws CoreException {
		final IMarker marker = file.createMarker("cse.nhm.ide.ui.editor.parenmarker");
		marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		marker.setAttribute(IMarker.MESSAGE, message);

		// marker.setAttribute(IMarker.LINE_NUMBER, line);
		marker.setAttribute(IMarker.CHAR_START, (int) l);
		marker.setAttribute(IMarker.CHAR_END, (int) l + 1);
	}
	
	public ScenarioModel getScenarioModel() {
		return this.model;
	}
	

	public Optional<INationalHouseholdModel> getNationalHouseholdModel() {
		final IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput) {
			final IFile file = ((IFileEditorInput) input).getFile();
			try {
				final NHMNature nature = (NHMNature) file.getProject().getNature(NHMNature.NATURE_ID);
				return Optional.<INationalHouseholdModel>of(nature.getModel());
			} catch (final Exception e) {
			}
		}
		return Optional.absent();
	}

	public void display(final Object firstElement) {
		if (firstElement instanceof Node) {
			final Node node = (Node) firstElement;
			this.getSourceViewer().revealRange((int)node.position + 1, (node.type.length()));
			this.getSourceViewer().setSelectedRange((int)node.position + 1, (node.type.length()));
		}
	}

	public Set<? extends IDefinition<IPath>> getDefinitions(final IProgressMonitor monitor) {
		final IEditorInput input = getEditorInput();
		if (input instanceof IFileEditorInput) {
			final IFile file = ((IFileEditorInput) input).getFile();
			try {
				final NHMNature nature = (NHMNature) file.getProject().getNature(NHMNature.NATURE_ID);
				return nature.getDefinitions(file, monitor);
			} catch (final Exception e) {
			}
		}
		return ImmutableSet.of();
	}
}
