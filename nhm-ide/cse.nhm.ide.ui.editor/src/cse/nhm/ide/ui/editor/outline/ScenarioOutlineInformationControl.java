package cse.nhm.ide.ui.editor.outline;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlExtension;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cse.nhm.ide.ui.editor.ScenarioEditor;
import cse.nhm.ide.ui.editor.ScenarioModel;
import cse.nhm.ide.ui.editor.ScenarioModel.Node;

public class ScenarioOutlineInformationControl extends PopupDialog implements IInformationControl, IInformationControlExtension, IInformationControlExtension2{
	private Text filterText;
	private TreeViewer content;
	private Object input;
	final ScenarioOutlineFilter filter = new ScenarioOutlineFilter();
	private final ScenarioEditor editor;
	
	public ScenarioOutlineInformationControl(
			final ScenarioEditor editor,
			final Shell parent, 
			final int shellStyle) {
		super(parent, shellStyle, 
				true, 
				true, 
				true, 
				true, 
				true, 
				null, 
				null);
		this.editor = editor;
	}

	@Override
	protected Control createTitleControl(final Composite parent) {
		this.filterText = createFilterText(parent);
		return this.filterText;
	}
	
	protected Text createFilterText(final Composite parent) {
		this.filterText = new Text(parent, SWT.NONE);
		Dialog.applyDialogFont(this.filterText);
		final GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.CENTER;
		this.filterText.setLayoutData(data);
		this.filterText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.keyCode == 0x0D || e.keyCode == SWT.KEYPAD_CR) // Enter													// key
					gotoSelectedElement();
				if (e.keyCode == SWT.ARROW_DOWN)
					ScenarioOutlineInformationControl.this.content.getTree().setFocus();
				if (e.keyCode == SWT.ARROW_UP)
					ScenarioOutlineInformationControl.this.content.getTree().setFocus();
				if (e.character == 0x1B) // ESC
					dispose();
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				// do nothing
			}
		});
		
		this.filterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				ScenarioOutlineInformationControl.this.filter.setFilterText(((Text) e.widget).getText());
				if (ScenarioOutlineInformationControl.this.content != null) {
					ScenarioOutlineInformationControl.this.content.getControl().setRedraw(false);
					ScenarioOutlineInformationControl.this.content.refresh();
					if (ScenarioOutlineInformationControl.this.filter.isEmpty()) {
						ScenarioOutlineInformationControl.this.content.expandToLevel(2);
					} else {						
						ScenarioOutlineInformationControl.this.content.expandAll();
						selectFirstMatch();
					}
					ScenarioOutlineInformationControl.this.content.getControl().setRedraw(true);
				}
			}
		});
		return this.filterText;
	}
	
	protected void selectFirstMatch() {
		final ISelection existing = this.content.getSelection();
		if (existing instanceof ITreeSelection) {
			if (this.input instanceof ScenarioModel) {
				final Node firstMatch = this.filter.firstMatch(
						((ScenarioModel) this.input).getNodes()
						);
				if (firstMatch == null) {
					this.content.setSelection(StructuredSelection.EMPTY);
				} else {
					this.content.setSelection(new StructuredSelection(firstMatch), true);
				}
			}
		}
	}

	protected void gotoSelectedElement() {
		final ISelection selection = this.content.getSelection();
		if (selection instanceof ITreeSelection) {
			final ITreeSelection ts = (ITreeSelection) selection;
			final Object firstElement = ts.getFirstElement();
			if (firstElement != null) {
				dispose();
				this.editor.display(firstElement);
			}
		}
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		this.content = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		
		this.content.setContentProvider(new ScenarioContentOutlinePage.ScenarioContentProvider());
		this.content.setLabelProvider(new ScenarioContentOutlinePage.ScenarioLabelProvider());
		
		this.content.addFilter(this.filter);
		
		this.content.setAutoExpandLevel(2);
		this.content.setInput(this.input);
		
		this.content.getTree().addSelectionListener(
				new SelectionListener() {
					@Override
					public void widgetSelected(final SelectionEvent e) {}
					
					@Override
					public void widgetDefaultSelected(final SelectionEvent e) {
						gotoSelectedElement();
					}
				}
				);
		
		this.content.getTree().addKeyListener(
				new KeyListener() {
					
					@Override
					public void keyReleased(final KeyEvent e) {}
					
					@Override
					public void keyPressed(final KeyEvent e) {
						if (e.character == 0x1B) // ESC
							dispose();
					}
				}
				);
		
		return this.content.getTree();
	}
	
	@Override
	public void setInput(final Object input) {
		if (input != this.input) {
			this.input = input;
			if (this.content != null) {
				this.content.setInput(this.input);
			}
		}
	}

	@Override
	public void setVisible(final boolean visible) {
		if (visible) {
			open();
		} else {
			saveDialogBounds(getShell());
			getShell().setVisible(false);
		}
	}
	
	@Override
	public void setFocus() {
		getShell().forceFocus();
		if (this.filterText != null) this.filterText.setFocus();
	}
	
	@Override
	protected Point getInitialSize() {
		final Point p = super.getInitialSize();
		return new Point(Math.max(p.x, 300), Math.max(p.y, 500));
	}
	
	@Override
	public boolean hasContents() {
		return true;
	}

	@Override public void setInformation(final String information) {}
	@Override public void setSizeConstraints(final int maxWidth, final int maxHeight) {}

	@Override
	public Point computeSizeHint() {
		return getShell().getSize();
	}
	
	@Override
	public void setSize(final int width, final int height) {
		getShell().setSize(width, height);
	}

	@Override
	public void setLocation(final Point location) {
		if (!getPersistLocation() || getDialogSettings() == null) getShell().setLocation(location);
	}

	@Override
	public void dispose() {
		close();
	}

	@Override
	public void addDisposeListener(final DisposeListener listener) {
		getShell().addDisposeListener(listener);
	}

	@Override
	public void removeDisposeListener(final DisposeListener listener) {
		getShell().removeDisposeListener(listener);
	}

	@Override
	public void setForegroundColor(final Color foreground) {
		applyForegroundColor(foreground, getContents());
	}

	@Override
	public void setBackgroundColor(final Color background) {
		applyBackgroundColor(background, getContents());
	}

	@Override
	public boolean isFocusControl() {
		return getShell().getDisplay().getActiveShell() == getShell();
	}

	@Override
	public void addFocusListener(final FocusListener listener) {
		getShell().addFocusListener(listener);
	}

	@Override
	public void removeFocusListener(final FocusListener listener) {
		getShell().removeFocusListener(listener);
	}
}
