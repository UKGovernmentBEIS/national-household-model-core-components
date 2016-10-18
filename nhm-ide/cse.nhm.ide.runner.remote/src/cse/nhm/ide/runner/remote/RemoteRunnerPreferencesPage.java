package cse.nhm.ide.runner.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class RemoteRunnerPreferencesPage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	static class NewServerDialog extends TitleAreaDialog implements ModifyListener, Runnable {
		private Label warning;
		String text;
		private final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		private ScheduledFuture<?> schedule;

		public NewServerDialog(Shell parentShell) {
			super(parentShell);
		}
		
		@Override
		public void create() {
			super.create();
			setTitle("Add a server");
			setMessage(
					"Enter an NHM server address here - ask the server administrator if you don't know the address.", 
					IMessageProvider.INFORMATION);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
		
		@Override
		protected void okPressed() {
			ses.shutdownNow();
			super.okPressed();
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			final Composite c = (Composite) super.createDialogArea(parent);
			final Composite content = new Composite(c, SWT.NONE);
			content.setLayout(GridLayoutFactory.fillDefaults()
					.numColumns(2)
					.create());
			content.setLayoutData(new GridData(GridData.FILL_BOTH));
			final Label l = new Label(content, SWT.NONE);
			l.setLayoutData(GridDataFactory.fillDefaults().grab(false, false).align(SWT.RIGHT, SWT.CENTER).create());
			l.setText("Server address: ");
			final Text t = new Text(content, SWT.BORDER);
			
			t.addModifyListener(this);
			
			t.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
			new Label(content, SWT.NONE);
			warning = new Label(content, SWT.NONE);
			warning.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
			
			return c;
		}

		@Override
		public void modifyText(ModifyEvent e) {
			if (schedule != null) schedule.cancel(true);
			text = ((Text) e.getSource()).getText();
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			schedule = ses.schedule(this, 1, TimeUnit.SECONDS);
			warning.setText("Contacting server...");
		}

		@Override
		public void run() {
			boolean valid = false;
			String error = "";
			try {
				// TODO buffer this up and async check it?
				final URL u = new URL(text);
				if (!(u.getProtocol().equals("http") ||
						u.getProtocol().equals("https"))) {
					throw new IOException("The server address should start with http:// or https://");
				}
				final HttpURLConnection con = (HttpURLConnection) u.openConnection();
				final int code = con.getResponseCode();
				if (code != HttpURLConnection.HTTP_OK) {
					throw new IOException(u + ": " + code);
				}
				try (final InputStream is = con.getInputStream();
					 final BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
					error = br.readLine();
				}
				valid = true;
			} catch (UnknownHostException uhe) {
				valid = false;
				error = "Unknown host: " + uhe.getMessage();
			} catch (IOException e1) {
				valid = false;
				error = e1.getMessage();
			}
			
			final boolean valid_ = valid;
			final String error_ = error;
			
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					getButton(IDialogConstants.OK_ID).setEnabled(valid_);
					warning.setText(error_);
				}
				
			});
		}
	}
	
	public RemoteRunnerPreferencesPage() {
		super(GRID);
		setPreferenceStore(RemoteRunnerPlugin.getDefault().getPreferenceStore());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		addField(new ServerListEditor("hosts", "This is a list of remote servers which can run scenarios.", getFieldEditorParent()));	
	}
}