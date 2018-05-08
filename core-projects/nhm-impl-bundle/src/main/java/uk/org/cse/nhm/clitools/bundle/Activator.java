package uk.org.cse.nhm.clitools.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.reflections.vfs.Vfs;

public class Activator implements BundleActivator {

    public void start(final BundleContext context) {
        Vfs.addDefaultURLTypes(new BundleUrlType(context.getBundle()));
    }

    public void stop(final BundleContext context) {

    }
}
