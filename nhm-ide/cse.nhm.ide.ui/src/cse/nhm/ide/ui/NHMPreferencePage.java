package cse.nhm.ide.ui;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class NHMPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public NHMPreferencePage() {
	}

	public NHMPreferencePage(String title) {
		super(title);
	}

	public NHMPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		final Label l = new Label(parent, SWT.NONE);
		l.setText("This section contains settings particular to the NHM");
		return l;
	}

}
