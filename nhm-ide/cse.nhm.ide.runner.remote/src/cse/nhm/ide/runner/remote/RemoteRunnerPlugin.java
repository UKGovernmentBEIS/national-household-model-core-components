package cse.nhm.ide.runner.remote;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.google.common.base.Splitter;
import com.google.common.base.Joiner;

import cse.nhm.ide.runner.api.IScenarioRunner;

/**
 * The activator class controls the plug-in life cycle
 */
public class RemoteRunnerPlugin extends AbstractUIPlugin implements IPropertyChangeListener {
    private static final String DELIM = ":::";
    public static final Splitter splitter = Splitter.on(DELIM).trimResults().omitEmptyStrings();
    public static final Joiner joiner = Joiner.on(DELIM).skipNulls();

	// The plug-in ID
	public static final String PLUGIN_ID = "cse.nhm.ide.runner.remote"; //$NON-NLS-1$

	// The shared instance
	private static RemoteRunnerPlugin plugin;

	private final Map<String, ServiceRegistration<IScenarioRunner>> runners = new HashMap<>();
	private final Map<String, RemoteRunner> impls = new HashMap<>();

	private Job job;

	/**
	 * The constructor
	 */
	public RemoteRunnerPlugin() {
	}

	// @Inject
	// public void preferenceChanged(
	// @Preference(nodePath = PLUGIN_ID, value="hosts")
	// final String hostsStr) {
	// final String user = "bob";
	// final String[] hosts = hostsStr.split(":::");
	// final Set<String> seen = new HashSet<>();
	// for (final String host : hosts) {
	// try {
	// new URL(host);
	// seen.add(host);
	// if (runners.containsKey(host)) continue;
	// final RemoteRunner runner = new RemoteRunner(host, user);
	// provide(host, runner);
	// } catch (MalformedURLException e) {}
	// }
	// for (final String missing :
	// Sets.difference(ImmutableSet.copyOf(runners.keySet()), seen)) {
	// remove(missing);
	// }
	// }
	// the above is the e4 DI version for managing preferences, which is much
	// nicer
	// however, we cannot use it because of some versioning issues at the moment
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=437470

	private void remove(final String missing) {
		final ServiceRegistration<IScenarioRunner> reg = runners.get(missing);
		reg.unregister();
		runners.remove(missing);
		impls.remove(missing);
	}

	private void provide(final String host, final RemoteRunner runner) {
		final ServiceRegistration<IScenarioRunner> reg = this.getBundle().getBundleContext()
				.registerService(IScenarioRunner.class, runner, null);
		runners.put(host, reg);
		impls.put(host, runner);
	}

	private long updateAll() {
		boolean anyWorking = false;
		for (final RemoteRunner runner : ImmutableList.copyOf(impls.values())) {
			try {
				anyWorking = runner.update() || anyWorking;
            } catch (final Throwable e) {
                // the given runner is down; it should probably go away
				e.printStackTrace();
			}
		}
		if (anyWorking) {
			return 2000;
		} else {
			return 10000;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// we need to generate a set of services, and we need to get the
		// current user name.
		updateRegistrations();
		getPreferenceStore().addPropertyChangeListener(this);

		job = new Job("Querying NHM servers for status of remote jobs") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				if (!monitor.isCanceled()) schedule(updateAll());
				return Status.OK_STATUS;
			}

		};

		job.schedule(2000);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		for (final ServiceRegistration<IScenarioRunner> reg : runners.values()) {
			reg.unregister();
		}
		runners.clear();
		getPreferenceStore().removePropertyChangeListener(this);
		if (job != null) {
			job.cancel();
		}
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RemoteRunnerPlugin getDefault() {
		return plugin;
	}

	public void error(final String string, final Throwable th) {
		getLog().log(new Status(Status.ERROR, PLUGIN_ID, string + " " + th.getMessage(), th));
	}

	public void warn(final String string, final Throwable th) {
		getLog().log(new Status(Status.WARNING, PLUGIN_ID, string + " " + th.getMessage(), th));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getProperty().equals("hosts")) {
			updateRegistrations();
		}
	}

	private void updateRegistrations() {
		final String hostsStr = getPreferenceStore().getString("hosts");

		final String user = System.getProperty("user.name");
        final Iterable<String> hosts = splitter.split(hostsStr);

		final Set<String> seen = new HashSet<>();
		for (final String host : hosts) {
			try {
				new URL(host);
				seen.add(host);
				if (runners.containsKey(host))
					continue;
				final RemoteRunner runner = new RemoteRunner(host, user);
				provide(host, runner);
			} catch (final MalformedURLException e) {
			}
		}
		for (final String missing : Sets.difference(ImmutableSet.copyOf(runners.keySet()), seen)) {
			remove(missing);
		}
	}
}
