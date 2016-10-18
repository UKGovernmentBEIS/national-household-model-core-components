package cse.nhm.ide.ui.wizards;

import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

import cse.nhm.ide.ui.PluginPreferences.VersionPreference;

public class ModelVersionSelectionPage extends WizardPage implements IWizardPage {
	private TableViewer viewer;

	public ModelVersionSelectionPage() {
		super("Choose NHM Version");
		setTitle("Select a model version");
		setDescription("Scenarios in this project will be validated and run using the selected version. This can be changed later.");
	}

	@Override
	public void createControl(Composite parent) {
		viewer = new TableViewer(parent);
		viewer.setLabelProvider(new LabelProvider());
		ArrayContentProvider provider = new ArrayContentProvider();
		viewer.setContentProvider(provider);

		final Set<VersionPreference> options = VersionPreference.values();

		final VersionPreference[] versions = options.toArray(new VersionPreference[0]);

		viewer.setInput(versions);
		viewer.setSelection(new StructuredSelection(versions[0]));
		setControl(viewer.getControl());
	}

	public VersionPreference getSelectedVersion() {
		final ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection) selection;
			if (sel.size() > 0) {
				return (VersionPreference) sel.getFirstElement();
			}
		}
		return VersionPreference.defaultSetting();
	}
}