package cse.nhm.ide.ui;

import java.util.Comparator;

import org.osgi.framework.ServiceReference;

public class VersionComparator implements Comparator<ServiceReference<?>> {
	public static final VersionComparator INSTANCE = new VersionComparator();
	
	private VersionComparator() {}
	
	@Override
	public int compare(final ServiceReference<?> a, final ServiceReference<?> b) {
		return a.getBundle().getVersion().compareTo(b.getBundle().getVersion());
	}
}
