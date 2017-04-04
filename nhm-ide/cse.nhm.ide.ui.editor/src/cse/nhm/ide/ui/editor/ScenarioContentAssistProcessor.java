package cse.nhm.ide.ui.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import uk.org.cse.nhm.bundle.api.ILanguage.ISuggestion;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;

import cse.nhm.ide.ui.editor.hover.HelpThing;
import cse.nhm.ide.ui.editor.hover.ModelInformationControlCreator;
import cse.nhm.ide.ui.editor.snippet.ScenarioContextType;
import cse.nhm.ide.ui.editor.snippet.TemplateHelper;
import cse.nhm.ide.ui.reader.Cursor;

public class ScenarioContentAssistProcessor implements IContentAssistProcessor {
	private final ScenarioEditor editor;

	static class ResourceCompletionProposal implements ICompletionProposal {
		final IResource resource;
		final String kind;
		final int offset;
		final int prefixOffset;
		
		public ResourceCompletionProposal(IResource resource, String kind, int offset, int prefixOffset) {
			super();
			this.resource = resource;
			this.kind = kind;
			this.offset = offset;
			this.prefixOffset = prefixOffset;
		}

		@Override
		public void apply(final IDocument document) {
			try {
				final String s = getDisplayString();
				document.replace(
						offset,
						0,
						s.substring(prefixOffset));
			} catch (final BadLocationException ble) {}
		}

		@Override
		public Point getSelection(IDocument document) {
			return new Point(offset - prefixOffset + getDisplayString().length(), 0);
		}

		@Override
		public String getAdditionalProposalInfo() {
			return String.format("Use %s from %s", kind, getDisplayString());
		}

		@Override
		public String getDisplayString() {
			final String result = resource.getFullPath().toPortableString();
			if (CharMatcher.WHITESPACE.matchesAnyOf(result)) {
				return "\"" + result.replace("\"", "\\\"") + "\"";
			} else {
				return result;
			}
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public IContextInformation getContextInformation() {
			return new ContextInformation(kind, kind);
		}
		
	}
	
	static class ScenarioCompletionProposal extends HelpThing implements ICompletionProposal, 
		ICompletionProposalExtension3, ICompletionProposalExtension5 {
		final ISuggestion suggestion;
		final int offset;
		
		public ScenarioCompletionProposal(Cursor cursor, ISuggestion suggestion, int offset) {
			super(cursor);
			this.suggestion = suggestion;
			this.offset = offset;
		}

		@Override
		public void apply(IDocument document) {
			try {
				document.replace(
						offset - suggestion.leftOffset(),
						suggestion.leftOffset(),
						suggestion.text());
			} catch (final BadLocationException ble) {}
		}

		@Override
		public Point getSelection(IDocument document) {
			return new Point(
					offset - suggestion.leftOffset() + 
					suggestion.text().length() + suggestion.cursorOffset(), 0
					);
		}

		@Override
		public String getAdditionalProposalInfo() {
			return suggestion.description();
		}

		@Override
		public String getDisplayString() {
			return suggestion.text();
		}
		
		@Override
		public String description() {
			return suggestion.description()
					.replace("  ", "\n\n");
		}

		@Override
		public String type() {
			return suggestion.category();
		}
		
		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public IContextInformation getContextInformation() {
			return new ContextInformation(suggestion.category(), suggestion.category());
		}

		@Override
		public IInformationControlCreator getInformationControlCreator() {
			return ModelInformationControlCreator.NORMAL;
		}

		@Override
		public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
			return null;
		}

		@Override
		public int getPrefixCompletionStart(IDocument document, int completionOffset) {
			return completionOffset;
		}

		@Override
		public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
			return this;
		}
	}
	
	private final TemplateCompletionProcessor tpc = new TemplateCompletionProcessor() {
		@Override
		protected Template[] getTemplates(String contextTypeId) {
			return TemplateHelper.getTemplateStore("snippets").getTemplates();
		}
		
		@Override
		protected Image getImage(Template template) {
			return null;
		}
		
		@Override
		protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
			return new ScenarioContextType(); 
		}
	};
	
	public ScenarioContentAssistProcessor(final ScenarioEditor editor) {
		this.editor = editor;
	}
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(final ITextViewer viewer, final int offset) {
		try {
			final INationalHouseholdModel model = editor.getNationalHouseholdModel().get();
			final Cursor cursor = Cursor.get(viewer.getDocument().get(), offset);
			
			// TODO relativise suggested resources.
			final List<ICompletionProposal> out = new ArrayList<>();
			if (cursor.command().equals("scenario") && cursor.argument().name().equals(Optional.of("stock-id"))) {
				// suggest stocks, from this project and from other projects.
				suggestResources(offset, cursor, out, "stock", ".stock");
			} else if (cursor.command().equals("include") || cursor.command().equals("include-modules")) {
				// suggest includes; part 1 would be relative includes and part 2 project includes
				suggestResources(offset, cursor, out, "include", ".nhm");
			} else {
				final IProgressMonitor monitor = new NullProgressMonitor();
				final List<? extends ISuggestion> suggest = 
						model.language().suggest(cursor, editor.getDefinitions(monitor));
				for (final ISuggestion s : suggest) {
					out.add(new ScenarioCompletionProposal(cursor, s, offset));
				}
			}
			
			out.addAll(Arrays.asList(tpc.computeCompletionProposals(viewer, offset)));
			
			return out.toArray(new ICompletionProposal[out.size()]);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void suggestResources(final int offset, final Cursor cursor, final List<ICompletionProposal> out, final String kind, final String ext) throws CoreException {
		ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceProxyVisitor() {
			@Override
			public boolean visit(IResourceProxy proxy) throws CoreException {
				if (proxy.getType() == IResource.FILE && proxy.getName().endsWith(ext)) {
					final IResource resource = proxy.requestResource();
					if (resource.getFullPath().toPortableString().startsWith(cursor.left())) {
						out.add(new ResourceCompletionProposal(resource, kind, offset, cursor.left().length()));
					}
				}
				return true;
			}
		}, 0);
	}

	@Override
	public IContextInformation[] computeContextInformation(final ITextViewer viewer, final int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] {'(', ' ', ':'};
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}
}
