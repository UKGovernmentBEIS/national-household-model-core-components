package cse.nhm.ide.ui;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addView("org.eclipse.ui.navigator.ProjectExplorer", 
				IPageLayout.LEFT, 0.2f, layout.getEditorArea());
		
		layout.addView("org.eclipse.help.ui.HelpView", 
				IPageLayout.RIGHT, 0.8f, 
				layout.getEditorArea());
		
		IFolderLayout folder = layout.createFolder("problems-and-run-stuff",
				IPageLayout.BOTTOM, 0.8f, 
				layout.getEditorArea());
		
		folder.addView("org.eclipse.ui.views.ProblemView");
		folder.addView("org.eclipse.egit.ui.RepositoriesView");
		folder.addView("cse.nhm.ide.runner.ui.views.ScenarioRunnerView");
		folder.addView("cse.nhm.ide.runner.ui.views.LogView");
		
		layout.addShowViewShortcut("org.eclipse.ui.navigator.ProjectExplorer");
		layout.addShowViewShortcut("org.eclipse.ui.views.ProblemView");
		
		layout.addShowViewShortcut("cse.nhm.ide.runner.ui.views.ScenarioRunnerView");
		layout.addShowViewShortcut("cse.nhm.ide.runner.ui.views.LogView");
		
		layout.addShowViewShortcut("org.eclipse.egit.ui.RepositoriesView");
		layout.addShowViewShortcut("org.eclipse.team.ui.GenericHistoryView");

		layout.addShowViewShortcut("org.eclipse.ui.views.ContentOutline");
	}
}
