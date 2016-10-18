package cse.nhm.ide.ui.editor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TabsToSpacesConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import cse.nhm.ide.ui.PluginPreferences;
import cse.nhm.ide.ui.editor.ScenarioDocumentProvider.PartitionScanner;
import cse.nhm.ide.ui.editor.hover.HelpTextHover;
import cse.nhm.ide.ui.editor.structure.AutoIndentationStragegy;
import cse.nhm.ide.ui.editor.structure.Strategy;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

class ScenarioSourceViewerConfiguration extends SourceViewerConfiguration {
	private static final String[] TYPES = contentTypes();
	
	private static String[] contentTypes() {
		final String[] out = Arrays.copyOf(
				ScenarioDocumentProvider.PartitionScanner.TYPES,
				ScenarioDocumentProvider.PartitionScanner.TYPES.length + 1);
		out[out.length - 1] = IDocument.DEFAULT_CONTENT_TYPE;
		return out;
	}

	static RuleBasedScanner undifferentiated(final String themeColorKey) {
		final Color color = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getColorRegistry().get(themeColorKey);
		final RuleBasedScanner result = new RuleBasedScanner();
		result.setDefaultReturnToken(new Token(new TextAttribute(color)));
		return result;
	}

	static void add(final PresentationReconciler rec, final String contentType, final ITokenScanner scanner) {
		final DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(scanner);
		
		rec.setDamager(ddr, contentType);
		rec.setRepairer(ddr, contentType);
	}

	private final ScenarioEditor editor;
	public ScenarioSourceViewerConfiguration(final ScenarioEditor editor) {
		super();
		this.editor = editor;
	}

	@Override
	public String[] getConfiguredContentTypes(final ISourceViewer sourceViewer) {
		return TYPES;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();

		add(reconciler, PartitionScanner.COMMENT, undifferentiated(PluginPreferences.Theme.COMMENT));
		add(reconciler, PartitionScanner.DOC, undifferentiated(PluginPreferences.Theme.COMMENT));
		add(reconciler, PartitionScanner.STR, undifferentiated(PluginPreferences.Theme.STRING));
		
		// need something better than a default damager here because at the moment it breaks bracket colouring in
		// the syntax scanner uses a slight hack to fix this, but it is not especially nice. it would perhaps be better
		// to do the colouring in for brackets completely differently; for example they could be in their own partition type?
		final Set<String> keys;
		final Optional<INationalHouseholdModel> model = this.editor.getNationalHouseholdModel();
		if (model.isPresent()) {
			keys = model.get().language().elements();
		} else {
			keys = Collections.emptySet();
		}
		
		add(reconciler, IDocument.DEFAULT_CONTENT_TYPE, new ScenarioSyntaxScanner(keys));
		
		return reconciler;
	}
	
	@Override
	public IAnnotationHover getAnnotationHover(final ISourceViewer sourceViewer) {
		return new DefaultAnnotationHover(true);
	}
	
	@Override
	public IReconciler getReconciler(final ISourceViewer sourceViewer) {
		return new MonoReconciler(new Reconciler(this.editor), false);
	}

	@Override
	public IContentAssistant getContentAssistant(final ISourceViewer sourceViewer) {
		final ContentAssistant ca = new ContentAssistant();
		final GroupedContentAssistProcessor<String> processor = new GroupedContentAssistProcessor<>(ca, 
				new ScenarioContentAssistProcessor(editor)
				, new Function<ICompletionProposal, String>() {
					// TODO custom sort order for content assist proposals
					@Override
					public String apply(final ICompletionProposal input) {
						return (input instanceof ScenarioContentAssistProcessor.ScenarioCompletionProposal) ?
								((ScenarioContentAssistProcessor.ScenarioCompletionProposal) input).suggestion.category() : 
									(input instanceof TemplateProposal ? "Snippets" : "Other");
					}
			
				});
		ca.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		ca.addCompletionListener(processor);
		ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		return ca;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			final ISourceViewer sourceViewer, final String contentType) {
		final TabsToSpacesConverter converter = new TabsToSpacesConverter();
		converter.setLineTracker(new DefaultLineTracker());
		converter.setNumberOfSpacesPerTab(getTabWidth(sourceViewer));
		return new IAutoEditStrategy[] {
				new AutoIndentationStragegy(),
				converter,
				new Strategy()
			};
	}
	
	@Override
	public ITextHover getTextHover(
			final ISourceViewer sourceViewer,
			final String contentType) {
		try {Thread.sleep(50); } catch (final InterruptedException e) {}
		final Optional<INationalHouseholdModel> model = this.editor.getNationalHouseholdModel();
		if (model.isPresent()) {
			return new HelpTextHover(editor, model.get());
		}
		return null;
	}
}
