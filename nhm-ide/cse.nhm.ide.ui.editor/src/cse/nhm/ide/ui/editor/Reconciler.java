package cse.nhm.ide.ui.editor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;

import cse.nhm.ide.ui.reader.Form;

public class Reconciler implements 
		IReconcilingStrategy,
		IReconcilingStrategyExtension {
	
	private IDocument document;
	private final ScenarioEditor editor;

	public Reconciler(final ScenarioEditor editor) {
		this.editor = editor;
	}

	@Override
	public void setProgressMonitor(final IProgressMonitor monitor) {
	}

	@Override
	public void initialReconcile() {
		try {
			final List<Long> errors = new ArrayList<>();
			final String s = document.get();
			final List<Form> f = Form.readAll(
					new StringReader(s),
					errors);
			
			final List<Long> unclosed = new ArrayList<>();
			final List<Long> unopened = new ArrayList<>();
			for (final long l : errors) {
				if (s.charAt((int)l) == '(') unclosed.add(l);
				else unopened.add(l);
			}
			
			final ScenarioModel model = new ScenarioModel(f, unclosed, unopened);

			editor.updateModel(model);
		} catch (final Exception e) {
			
		}
	}

	@Override
	public void setDocument(final IDocument document) {
		this.document = document;
	}

	@Override
	public void reconcile(final DirtyRegion dirtyRegion, final IRegion subRegion) {
	}

	@Override
	public void reconcile(final IRegion partition) {
		initialReconcile();
	}
}