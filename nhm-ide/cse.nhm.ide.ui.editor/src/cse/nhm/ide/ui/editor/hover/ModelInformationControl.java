package cse.nhm.ide.ui.editor.hover;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.wb.swt.SWTResourceManager;

import uk.org.cse.nhm.bundle.api.ILocation;

import com.google.common.base.Optional;

class ModelInformationControl extends AbstractInformationControl implements IInformationControlExtension2 {
	HelpThing thing;
	private Text text;
	private Link typeLabel;
	private Label cursorLabel;
	private boolean isFocused;
	private Label lblFocusHint;

	public ModelInformationControl(Shell parentShell, boolean isFocused) {
		super(parentShell, true);
		this.isFocused = isFocused;
		create();
	}
	
	@Override
	public boolean hasContents() {
		return thing != null;
	}

	@Override
	public void setInput(Object input) {
		if (input instanceof HelpThing) {
			thing = (HelpThing) input;
			update();
		} else {
			thing = null;
		}
	}

	protected void update() {
		if (thing != null) {
			if (text != null)
				text.setText(thing.description());
			if (typeLabel != null) {
				typeLabel.setText(formatType(thing.type(), thing.location()));
			}
			if (cursorLabel != null)
				cursorLabel.setText(String.valueOf(thing.cursor()));
			
			if (thing instanceof UserTemplate) {
				text.setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
			} else {
				text.setFont(JFaceResources.getFont(JFaceResources.DEFAULT_FONT));
			}
			
		}
	}
	
	static String formatType(final String text, final Optional<ILocation<IPath>> where) {
		if (where.isPresent()) {
			return (String.format("%s defined at <A>%s</A>", text, where.get()));
		} else {
			return (text);
		}
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	protected void createContent(Composite parent) {
		final Composite content = new Composite(parent, SWT.NONE);
		final Display display = parent.getDisplay();
		Color foreground = display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		Color background = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		content.setLayout(new GridLayout(2, false));
		
		color(content, foreground, background);
		
		cursorLabel = new Label(content, SWT.NONE);
		cursorLabel.setFont(SWTResourceManager.getFont("Sans", 10, SWT.NORMAL));
		cursorLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cursorLabel.setText("");
		
		color(cursorLabel, foreground, background);
		
		typeLabel = new Link(content, SWT.NONE);
		typeLabel.setFont(SWTResourceManager.getFont("Sans", 10, SWT.NORMAL));
		typeLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		typeLabel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// jump to a marker thing.
				final ILocation<IPath> loc = thing.location().get();
				final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(loc.path());
				IMarker marker;
				try {
					marker = file.createMarker(IMarker.TEXT);
					marker.setAttribute(IMarker.LINE_NUMBER, new Integer(loc.line()));
					final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IDE.openEditor(activePage, marker);
					marker.delete();
					ModelInformationControl.this.dispose();
				} catch (CoreException e1) {
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		color(typeLabel, foreground, background);
		
		text = new Text(content, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		color(text, foreground, background);
		
		if (!isFocused) {
			lblFocusHint = new Label(content, SWT.NONE);
			lblFocusHint.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
			lblFocusHint.setFont(SWTResourceManager.getFont("Sans", 10, SWT.NORMAL));
			lblFocusHint.setText("Press F2 to focus tooltip for scrolling");
			color(lblFocusHint, foreground, background);
		}
		
		update();
	}

	private static void color(final Control content, Color foreground, Color background) {
		content.setForeground(foreground);
		content.setBackground(background);
	}
	
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return ModelInformationControlCreator.FOCUSED;
	}
}