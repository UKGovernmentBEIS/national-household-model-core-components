package cse.nhm.ide.runner.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

import cse.nhm.ide.runner.ui.views.ScenarioRunnerTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class RunnerUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cse.nhm.ide.runner.ui"; //$NON-NLS-1$

	// The shared instance
	private static RunnerUIPlugin plugin;
	public ScenarioRunnerTracker tracker;

	
	/**
	 * The constructor
	 */
	public RunnerUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		tracker = new ScenarioRunnerTracker(context);
		tracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		tracker.close();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RunnerUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static void error(final String string, final Throwable th) {
		final IStatus s = new Status(Status.ERROR, PLUGIN_ID, string, th);
		StatusManager.getManager().handle(s, StatusManager.LOG | StatusManager.SHOW);
	}
}
