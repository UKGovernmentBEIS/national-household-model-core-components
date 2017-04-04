package cse.nhm.ide.ui.results.nav;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import cse.nhm.ide.ui.results.NHMResultViewPlugin;

public class ZipActionProvider extends CommonActionProvider {
	// no idea how to make this work properly, unfortunately.

	public ZipActionProvider() {
		super();
	}
	
	private final class OpenExtension extends Action {
		private final StructuredViewer structuredViewer;

		public OpenExtension(final StructuredViewer structuredViewer) {
			super("Open");
			this.structuredViewer = structuredViewer;
		}

		@Override
		public void run() {
			final ISelection selection = this.structuredViewer.getSelection();
			if (selection.isEmpty()) return;
			if (selection instanceof IStructuredSelection) {
				final IStructuredSelection sel = (IStructuredSelection) selection;
				final Object firstElement = sel.getFirstElement();
				if (firstElement instanceof IFileStore) {
					final IFileStore fileStore = (IFileStore) firstElement;
					final IFileInfo info = fileStore.fetchInfo();
					
					if (!info.exists()) return;
					if (info.isDirectory()) return;	
					
					final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				    
				    try {
				        IDE.openEditorOnFileStore(page, fileStore);
				    } catch ( final PartInitException e ) {
				    	NHMResultViewPlugin.getDefault().logException(e);
				    }
				}
			}
		}
	}

	private OpenExtension openAction;

	@Override
	public void init(final ICommonActionExtensionSite aSite) {
		super.init(aSite);
		this.openAction = new OpenExtension(aSite.getStructuredViewer());
	}
	
	@Override
	public void fillContextMenu(final IMenuManager menu) {
		super.fillContextMenu(menu);
		menu.add(new GroupMarker("additions"));
	}

	@Override
	public void fillActionBars(final IActionBars actionBars) {
		super.fillActionBars(actionBars);

		actionBars.setGlobalActionHandler(
				org.eclipse.ui.navigator.ICommonActionConstants.OPEN, 
				this.openAction);
	}
}
