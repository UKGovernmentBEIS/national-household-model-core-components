package cse.nhm.ide.ui;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

/**
 * The activator class controls the plug-in life cycle
 */
public class NHMUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cse.nhm.ide.ui"; //$NON-NLS-1$

	// The shared instance
	private static NHMUIPlugin plugin;

	private BundleContext context;

	/**
	 * The constructor
	 */
	public NHMUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		this.context = context;
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static NHMUIPlugin getDefault() {
		return plugin;
	}

	public Collection<ServiceReference<INationalHouseholdModel>> getModels() {
		try {
			final Collection<ServiceReference<INationalHouseholdModel>> result = context.getServiceReferences(INationalHouseholdModel.class, null);
			return result;
		} catch (final InvalidSyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void logException(final String string, final Exception e) {
		final IStatus s = new Status(Status.ERROR, PLUGIN_ID, string, e);
		StatusManager.getManager().handle(s, StatusManager.LOG | StatusManager.SHOW);
	}

    public static void logExceptionQuietly(final String string, final Exception e) {
		final IStatus s = new Status(Status.ERROR, PLUGIN_ID, string, e);
		StatusManager.getManager().handle(s, StatusManager.LOG);
	}


	public static void logInformation(final String string, final Object... os) {
		final IStatus s = new Status(Status.INFO, PLUGIN_ID, String.format(string, os));
		StatusManager.getManager().handle(s, StatusManager.LOG);
	}

}
