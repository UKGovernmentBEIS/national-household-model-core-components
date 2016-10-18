package cse.nhm.ide.runner.local;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

public class LocalRunnerPlugin extends Plugin {
	private static final String PLUGIN_ID = "cse.nhm.ide.runner.local";
	private static LocalRunnerPlugin plugin;
	
	private BundleContext context;
	
	static LocalRunnerPlugin getPlugin() {
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		LocalRunnerPlugin.plugin = this;
		context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		context = null;
		LocalRunnerPlugin.plugin = null;
	}


	public ServiceReference<INationalHouseholdModel> findModel(final String version) {
		// get hold of a model with this version
		try {
			final Collection<ServiceReference<INationalHouseholdModel>> serviceReferences = context
					.getServiceReferences(INationalHouseholdModel.class, null);
			
			for (final ServiceReference<INationalHouseholdModel> sr : serviceReferences) {
				//final String srVersion = String.valueOf(sr.getBundle().getVersion());
				if (version.equals(sr.getBundle().getVersion().toString())) { //srVersion.equals(version)) {
					return sr;
				}
			}
		} catch (final InvalidSyntaxException e) {
		}
		return null;
	}

	public static void logException(final String string, final Throwable e) {
		final IStatus s = new Status(Status.ERROR, PLUGIN_ID, string, e);
		StatusManager.getManager().handle(s, StatusManager.LOG | StatusManager.SHOW);
	}
	
	public static void logInformation(final String string, final Object... os) {
		final IStatus s = new Status(Status.INFO, PLUGIN_ID, String.format(string, os));
		StatusManager.getManager().handle(s, StatusManager.LOG);
	}
}
