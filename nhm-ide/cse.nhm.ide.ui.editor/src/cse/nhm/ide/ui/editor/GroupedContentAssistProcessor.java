package cse.nhm.ide.ui.editor;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * Decorates another content assist processor to group its values using a function of some kind
 * and let you cycle through them
 */
public class GroupedContentAssistProcessor<K extends Comparable<K>> implements IContentAssistProcessor, ICompletionListener {
	private final ContentAssistant assistant;
	private final IContentAssistProcessor delegate;
	private final Function<ICompletionProposal, K> categorise;

	private boolean newSession;
	private int lastOffset;
	private K lastCategory = null;
	
	public GroupedContentAssistProcessor(final ContentAssistant assistant, final IContentAssistProcessor delegate, final Function<ICompletionProposal, K> categorise) {
		super();
		this.assistant = assistant;
		this.delegate = delegate;
		this.categorise = categorise;
		
		assistant.setShowEmptyList(true);
		assistant.setStatusLineVisible(true);
        assistant.setRepeatedInvocationMode(true);
	}

	@Override
	public void assistSessionStarted(final ContentAssistEvent event) {
		this.newSession = true;
		this.lastCategory = null;
	}

	@Override
	public void assistSessionEnded(final ContentAssistEvent event) {
		this.lastCategory = null;
	}

	@Override
	public void selectionChanged(final ICompletionProposal proposal, final boolean smartToggle) {
		
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(final ITextViewer viewer, final int offset) {
		ICompletionProposal[] values = delegate.computeCompletionProposals(viewer, offset);
		final Multimap<K, ICompletionProposal> byGroup = Multimaps.index(Arrays.asList(values), categorise);
		
		if (newSession) {
			newSession = false;
		} else if (offset == this.lastOffset) {
			// re-execution, goto next category
			this.lastCategory = nextCategory(byGroup.keySet(), this.lastCategory);
		}

        if (!byGroup.containsKey(this.lastCategory)) {
            this.lastCategory = null;
        }

        this.lastOffset = offset;

        // if we are on a category, filter down to it.
        if (this.lastCategory != null) {
            values = byGroup.get(this.lastCategory).toArray(new ICompletionProposal[0]);
        }
        
        assistant.setStatusMessage(labelFor(this.lastCategory));
		return values;
	}

	private K nextCategory(final Set<K> keySet, final K lastCategory) {
		final ImmutableSortedSet<K> ss = ImmutableSortedSet.copyOf(keySet);
		if (lastCategory == null) {
			return ss.first();
		}
		return ss.higher(lastCategory);
	}

	private String labelFor(final K lastCategory2) {
		if (lastCategory2 == null) return "All suggestions";
		else return String.valueOf(lastCategory2);
	}

	@Override
	public IContextInformation[] computeContextInformation(final ITextViewer viewer, final int offset) {
		return delegate.computeContextInformation(viewer, offset);
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return delegate.getCompletionProposalAutoActivationCharacters();
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return delegate.getContextInformationAutoActivationCharacters();
	}

	@Override
	public String getErrorMessage() {
		return delegate.getErrorMessage();
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return delegate.getContextInformationValidator();
	}
}
