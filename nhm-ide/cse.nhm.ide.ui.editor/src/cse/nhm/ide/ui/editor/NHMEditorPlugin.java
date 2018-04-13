package cse.nhm.ide.ui.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class NHMEditorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cse.nhm.ide.ui.editor"; //$NON-NLS-1$

	// The shared instance
	private static NHMEditorPlugin plugin;
	
	/**
	 * The constructor
	 */
	public NHMEditorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NHMEditorPlugin getDefault() {
		return plugin;
	}
	

	public static void logException(final String string, final Exception e) {
		final IStatus s = new Status(Status.ERROR, PLUGIN_ID, string, e);
		StatusManager.getManager().handle(s, StatusManager.LOG | StatusManager.SHOW);
	}
}
