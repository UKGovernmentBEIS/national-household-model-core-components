package cse.nhm.ide.ui.editor.structure;

import java.util.Comparator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import uk.org.cse.nhm.bundle.api.IDefinition;

import com.google.common.base.Optional;

import cse.nhm.ide.ui.NHMUIPlugin;
import cse.nhm.ide.ui.builder.NHMNature;

public class DefinitionSelectionDialog extends FilteredItemsSelectionDialog {
	public DefinitionSelectionDialog(final Shell shell) {
		super(shell);
		setDetailsLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IDefinition) {
					final IDefinition<?> definition = (IDefinition<?>) element;
					return String.format("%s %s - %s", definition.type(), definition.name(), definition.locations().get(0));
				} else {
					return String.valueOf(element);
				}
			}
		});
	}

	@Override
	protected Control createExtendedContentArea(final Composite parent) {
		return null;
	}
	
	private static final String DIALOG_SETTINGS = "DefinitionSelectionDialog";
	
	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = NHMUIPlugin.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = NHMUIPlugin.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(final Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ItemsFilter() {
	         @Override
			public boolean matchItem(final Object item) {
	            return matches(item.toString());
	         }
	         @Override
			public boolean isConsistentItem(final Object item) {
	            return true;
	         }
	      };
	}

	@Override
	protected Comparator<?> getItemsComparator() {
		return new Comparator<Object>() {
	         @Override
			public int compare(final Object arg0, final Object arg1) {
	            return arg0.toString().compareTo(arg1.toString());
	         }
	      };
	}

	@Override
	protected void fillContentProvider(
			final AbstractContentProvider contentProvider, 
			final ItemsFilter itemsFilter, 
			final IProgressMonitor progressMonitor) throws CoreException {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		progressMonitor.beginTask("Scanning for definitions", projects.length * 100);
		for (final IProject project : projects) {
			progressMonitor.setTaskName("Scan " + project.getName());
			// TODO either make nature save details, or trigger regeneration
			final Optional<NHMNature> nature = NHMNature.of(project);
			if (nature.isPresent()) {
				final NHMNature n = nature.get();
				for (final IDefinition<?> d : n.getAllDefinitions(new SubProgressMonitor(progressMonitor, 100))) {
					if (d.locations().isEmpty()) continue;
					contentProvider.add(d, itemsFilter);
				}
			} else {
				progressMonitor.worked(100);
			}
		}
		progressMonitor.done();
	}

	@Override
	public String getElementName(final Object item) {
		return String.valueOf(item);
	}
}
