package cse.nhm.ide.stock.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import cse.nhm.ide.ui.wizards.ModelVersionSelectionPage;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IStockImportCallback;

public class ImportRunningWizardPage extends WizardPage {
	private Text text;
	private Button go;
	private ModelVersionSelectionPage mvsp;
	private StockImportWizardPage mainPage;
	private Path completedFilePath;
	boolean running = false;
	
	public ImportRunningWizardPage(StockImportWizardPage mainPage, final ModelVersionSelectionPage mvsp) {
		super("Running import");
		this.mainPage = mainPage;
		this.mvsp = mvsp;
		setTitle("Running stock importer...");
		setDescription("The stock import process is running...");
		setPageComplete(false);
	}
	
	public Path getCompletedFilePath() {
		return completedFilePath;
	}
	
	@Override
	public void createControl(Composite parent) {
		final Composite top = new Composite(parent, SWT.NONE);
		top.setLayout(new GridLayout(1, false));
		text = new Text(top, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setFont(JFaceResources.getTextFont());
		text.setEditable(false);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		go = new Button(top, SWT.NONE);
		go.setText("Re-run import");
		go.setEnabled(false);
		go.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));
		go.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pressedGo();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		setControl(top);
	}
	
	private synchronized void pressedGo() {
		if (running) return;
		running = true;
		
		text.setText("");
		
		final INationalHouseholdModel model = mvsp.getSelectedVersion().getModel();
		final Path pathToSource = mainPage.getPathToSource();
		
		text.append("Using NHM " + model.version() + "\n");
		text.setSelection(text.getText().length());
		
		final StringBuffer buffer = new StringBuffer();
		
		final Job job = new Job("Import stock from " + pathToSource) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Importing stock...", 100);
				
				final IStockImportCallback cb = 
						new IStockImportCallback() {

					@Override
					public boolean shouldCancel() {
						return monitor.isCanceled();
					}

					private void flush() {
						final String s = buffer.toString();
						buffer.setLength(0);
						
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								text.setRedraw(false);
								text.append(s);
								text.setTopIndex(text.getLineCount() - 1);
								text.setRedraw(true);
							}
						});
					}
					
					@Override
					public void log(final String arg0, final String arg1) {
						buffer.append(String.format("[%s] %s\n", arg0, arg1));
						if (buffer.length() > 300) {							
							flush();
						}
					}

					private void compl(final boolean b) {
						flush();
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								setPageComplete(b);
								if (b) log("INFO", "Stock imported successfully; press 'finish' to add it to your project.");
								go.setEnabled(true);
							}
						});
					}
					
					@Override
					public void failed() {
						log("ERROR", "Stock import failed");
						compl(false);
					}

					@Override
					public void completed(Path arg0) {
						if (monitor.isCanceled()) {
							try 	{Files.delete(arg0);} 
							catch   (final IOException ex) {};
						} else {
							mainPage.setPathToResult(arg0);
						}
						compl(true);
					}

					@Override
					public void cancelled() {
						compl(false);
					}
				};
				
				try {
					model.importStock(PathFS.INSTANCE, pathToSource, cb);
				} catch (Throwable th) {
					cb.log("ERROR", th.getMessage());
					cb.failed();
				}
				
				monitor.done();
				
				running = false;
				
				return Status.OK_STATUS;
			}
		};
		
		job.schedule();
	}
	
	@Override
	public void setVisible(boolean visible) {
		// invoked by platform when we go on the page
		super.setVisible(visible);
		if (visible) pressedGo();
	}
}
