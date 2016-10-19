package cse.nhm.ide.support.editors;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.part.EditorPart;

import cse.nhm.ide.support.GetInputsUtil;
import cse.nhm.ide.support.format.SupportRequestFile;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class SupportRequestEditor extends EditorPart implements IResourceChangeListener {
	private SupportRequestFile content;
	private Text titleField;
	private Text descriptionBox;
	private TableViewer filesList;
	private Composite parent;
	/**
	 * If this is false, change events from editors will not be propagated into the backing store.
	 */
	private boolean hookEvents = true;
	
	public SupportRequestEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// if our input has changed, we should reload it?
		// not really sure.
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (content != null) {
			try {
				content.save(monitor);
				// TODO synchronise resource. writing to the filestore does not seem to work right.
			} catch (IOException | CoreException e) {
				e.printStackTrace();
			}
		}
		updateDirtyFlag();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}
	
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		try {
			if (input instanceof IFileEditorInput) {
				final IFileEditorInput fei = (IFileEditorInput) input;
				final IFile file = fei.getFile();
				this.content = new SupportRequestFile(file);
			} else if (input instanceof IURIEditorInput) {
				final IURIEditorInput u = (IURIEditorInput) input;
				final URI uri = u.getURI();
				IFileStore store = EFS.getStore(uri);
				this.content = new SupportRequestFile(store);
			}
			populate();
		} catch (IOException | CoreException e) {
			e.printStackTrace();
		}
	}

	private void populate() {
		if (content == null)
			return;
		if (parent == null)
			return;

		updatePartName(); 
		setContentDescription("Scenario support request data.");
		
		hookEvents = false;
		
		titleField.setText(content.getTitle());
		descriptionBox.setText(content.getDescription());
		filesList.setInput(content);
		
		hookEvents = true;
		
		updateDirtyFlag();
	}

	private void updatePartName() {
		setPartName(content.getFileName() + ": " + content.getTitle());
	}

	private void updateDirtyFlag() {
		SupportRequestEditor.this.firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	@Override
	public boolean isDirty() {
		return content != null && content.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		this.parent = parent;
		// what do we need to display?
		// box at the top for title
		// box for description
		// author and date of creation (immutable attributes)
		// attached files.
		parent.setLayout(GridLayoutFactory.swtDefaults().create());
		// do we want an outer scrollbar?
		final Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).create());
		top.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).create());

		new Label(top, SWT.NONE).setText("Subject:");
		this.titleField = new Text(top, SWT.BORDER);
		titleField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (hookEvents) {
					if (content != null) {
						content.setTitle(titleField.getText());
						updatePartName();
					}
					updateDirtyFlag();
				}
			}
		});
		titleField.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).create());

		new Label(parent, SWT.NONE).setText("Description:");
		this.descriptionBox = new Text(parent, SWT.MULTI | SWT.BORDER);
		descriptionBox.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		descriptionBox.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (hookEvents) {
					if (content != null) content.setDescription(descriptionBox.getText());
					updateDirtyFlag();
				}
			}
		});
		
		new Label(parent, SWT.NONE).setText("Files:");
		
		{
			final Composite files = new Composite(parent, SWT.NONE);
			files.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
			files.setLayout(GridLayoutFactory.swtDefaults().margins(0, 0).numColumns(2).create());
			this.filesList = new TableViewer(files, SWT.BORDER);
			
			filesList.setContentProvider(new IStructuredContentProvider() {
				// we are not doing a listener pattern for this content provider
				// because our editor is the only thing which should ever create changes
				
				@Override public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
				
				@Override
				public void dispose() {}
				
				@Override
				public Object[] getElements(Object inputElement) {
					if (inputElement instanceof SupportRequestFile) {
						return ((SupportRequestFile) inputElement).getAttachments().toArray();
					}
					return null;
				}
			});
			
			filesList.getControl().setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(1, 3).create());
			final Button attach = new Button(files, SWT.NONE); attach.setText("Attach...");
			final Button remove = new Button(files, SWT.NONE); remove.setText("Remove selected");
			final Button update = new Button(files, SWT.NONE); update.setText("Update selected");
			
			attach.addSelectionListener(new Click() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					// select some resources and then add them
					final ResourceSelectionDialog rsd = new ResourceSelectionDialog(getSite().getShell(), 
							ResourcesPlugin.getWorkspace().getRoot(), "Select files to attach. Scenario includes and stocks should be added automatically.");
					if (rsd.open() == Window.OK) {
						// selection worked, we need to find deps + attach
						final Object[] files = rsd.getResult();
						
						final Set<IFile> filesToAdd = new HashSet<IFile>();
						
						for (final Object o : files) {
							if (o instanceof IFile) {
								// TODO find dependencies here (NHM plugin required)
								final IFile file = (IFile) o;
								filesToAdd.add(file);
							}
						}
						
						for (final IFile file : GetInputsUtil.getInputs(filesToAdd)) {
							content.addAttachment(file);
						}
						// we also need to refresh the files list,
						// so it reflects the new state of affairs.
						
						filesList.refresh();
						updateDirtyFlag();
					}
				}
			});
			
			remove.addSelectionListener(new Click() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final ISelection selection = filesList.getSelection();
					if (selection instanceof IStructuredSelection) {
						final IStructuredSelection ss = (IStructuredSelection) selection;
						for (final Object o : ss.toArray()) {
							if (o instanceof String) {
								content.removeAttachment((String) o);
							}
						}
					}
					filesList.refresh();
					updateDirtyFlag();
				}
			});
			
			update.addSelectionListener(new Click() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					final ISelection selection = filesList.getSelection();
					if (selection instanceof IStructuredSelection) {
						final IStructuredSelection ss = (IStructuredSelection) selection;
						for (final Object o : ss.toArray()) {
							if (o instanceof String) {
								// we need to convert it into an IFile, see whether
								// it exists in the workspace, and if it does re-add it.
								
								final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(Path.fromPortableString((String)o));
								if (file.exists()) {
									content.addAttachment(file);
								}
							}
						}
					}
					
					filesList.refresh();
					updateDirtyFlag();
				}
			});
		}
		
		populate();
	}
	
	private static abstract class Click implements SelectionListener {
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
	}

	@Override
	public void setFocus() {
		titleField.setFocus();
	}

	@Override
	public void doSaveAs() {

	}
}
