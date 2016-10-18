package cse.nhm.ide.runner.ui.views;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.ui.PlatformUI;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.ui.commands.GetResultsCommandHandler.UnzipResultTempTask;

public class ResultDragSourceListener implements DragSourceListener {
	private final TreeViewer viewer;
	private UnzipResultTempTask unz;

	public ResultDragSourceListener(final TreeViewer viewer) {
		this.viewer = viewer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dragStart(final DragSourceEvent event) {
		
	}
	
	@Override
	public void dragSetData(final DragSourceEvent event) {
		final IScenarioRun run = 
				(IScenarioRun) viewer.getStructuredSelection().getFirstElement();
		
		if (unz != null) unz.cleanup();
		
		unz = new UnzipResultTempTask(run);
		
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(unz);
		} catch (InvocationTargetException | InterruptedException e) {
			event.doit = false;
		}
		
		if (event.doit) {
			event.data = new String[] {
			    unz.getOutputFolder().toAbsolutePath().toString()
			};
		}
	}
	
	@Override
	public void dragFinished(final DragSourceEvent event) {
		// delete the temporary file which we created in dragSetData.
//		if (unz != null) {
//			unz.cleanup();
//			unz = null;
//		}
		// annoyingly we cannot safely do this, because dragFinished is called at the wrong
		// point by the platform, and eclipse is dumb about this.
		
		// we might be able to do better using an IResourceTransfer?
	}
}
