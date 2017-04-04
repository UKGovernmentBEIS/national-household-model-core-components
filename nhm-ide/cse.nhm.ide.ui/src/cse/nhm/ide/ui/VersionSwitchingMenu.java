package cse.nhm.ide.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;

import cse.nhm.ide.ui.PluginPreferences.VersionPreference;
import cse.nhm.ide.ui.builder.RevalidateJob;

public class VersionSwitchingMenu extends ContributionItem {
	public VersionSwitchingMenu() {
	}

	public VersionSwitchingMenu(String id) {
		super(id);
	}

	@Override
	public void fill(final Menu menu, final int index) {
		final ISelection selection = 
				PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getSelectionService()
				.getSelection();
		
		final List<IProject> projects = new ArrayList<>();
		
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sel =
					(IStructuredSelection) selection;
			
			for (final Object o : sel.toList()) {
				if (o instanceof IProject) {
					projects.add((IProject) o);
				} else if (o instanceof IAdaptable) {
					final IProject p =
							(IProject)
							((IAdaptable) o).getAdapter(IProject.class);
					if (p != null) projects.add(p);
				}
			}
		}
		
		if (projects.isEmpty()) return;
		
		final VersionPreference selected;
		
		if (projects.size() == 1) {
			selected = VersionPreference.forProject(projects.get(0));
		} else {
			selected = null;
		}
		
		final MenuItem item = new MenuItem(menu, SWT.CASCADE, index);
		item.setText("Model version...");
		item.setMenu(createVersionSelectionMenu(menu, projects, selected));
	}

	private Menu createVersionSelectionMenu(final Menu parent, final List<IProject> projects, VersionPreference selected) {
		final Menu subMenu = new Menu(parent);
		
		final SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Object o = e.getSource();
				if (o instanceof MenuItem) {
					final Object d = ((MenuItem) o).getData("version");
					if (d instanceof VersionPreference) {
						final VersionPreference choice = (VersionPreference) d;
						for (final IProject p : projects) {
							choice.save(p);
							final RevalidateJob revalidate = new RevalidateJob(p);
							revalidate.schedule(100);
						}
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		};
		
		int index = 0;
		for (final VersionPreference pref : VersionPreference.values()) {
			final MenuItem item = new MenuItem(subMenu, SWT.CHECK, index++);
			item.setText(String.valueOf(pref));
			item.addSelectionListener(listener);
			if (pref.equals(selected)) {
				item.setSelection(true);
			}
			item.setData("version", pref);
		}
		
		return subMenu;
	}
}
