package cse.nhm.ide.ui.models;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import cse.nhm.ide.ui.PluginPreferences.VersionPreference;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;

/**
 * A thing which finds an NHM model by listening for osgi services.
 */
public class ServiceTrackingModel extends DelegatingModel {
    private VersionPreference targetVersion = VersionPreference.defaultSetting();
	
	private ServiceReference<INationalHouseholdModel> reference;
	private INationalHouseholdModel model;
	private final BundleContext context;

	private final Set<IListener> listeners = new LinkedHashSet<>();
	
	public interface IListener {
		public void modelChanged();
	}
	
	public ServiceTrackingModel(final BundleContext context, final VersionPreference version) {
		this.targetVersion = version;
		this.context = context;
	}
	
	public boolean isAvailable() {
		return model != null;
	}
	
	@Override
	public INationalHouseholdModel getDelegate() {
		if (model == null) {
			useReference(findReference());
		}
		return model;
	}
	
	public VersionPreference getTargetVersion() {
		return targetVersion;
	}
	
	public void setVersion(final VersionPreference version) {
		if (Objects.equals(targetVersion, version)) return;
		
		this.targetVersion = version;
		
		clearReference();
	}
	
	public void addListener(final IListener modelListener) {
		this.listeners.add(modelListener);
	}
	
	static class Listener implements ServiceListener {
		private final WeakReference<ServiceTrackingModel> ref;
		private final BundleContext context;
		
		public Listener(final BundleContext context, final ServiceTrackingModel model) {
			this.context = context;
			ref = new WeakReference<ServiceTrackingModel>(model);
			try {
				context.addServiceListener(this, String.format("(%s=%s)", Constants.OBJECTCLASS, INationalHouseholdModel.class.getName()));
			} catch (final InvalidSyntaxException e) {}
		}
		
		@Override
		public void serviceChanged(final ServiceEvent event) {
			final ServiceTrackingModel m = ref.get();
			if (m == null) {
				context.removeServiceListener(this);
				return;
			}
			switch (event.getType()) {
			case ServiceEvent.REGISTERED:
				m.considerGettingService(event.getServiceReference());
				break;
			case ServiceEvent.UNREGISTERING:
				m.findNewService(event.getServiceReference());
				break;
			}
		}
	}

	private void considerGettingService(final ServiceReference<?> serviceReference) {
		if (isSuitableVersion(serviceReference)) useReference(serviceReference);
	}

	private boolean isSuitableVersion(final ServiceReference<?> serviceReference) {
		return targetVersion.isPreferredVersion(serviceReference.getBundle().getVersion());
	}

	@SuppressWarnings("unchecked")
	private synchronized void useReference(final ServiceReference<?> serviceReference) {
		if (this.reference == serviceReference) return;
		clearReference();
		this.reference = (ServiceReference<INationalHouseholdModel>) serviceReference;
		if (this.reference != null) this.model = context.getService(this.reference);
		for (final IListener l : listeners) l.modelChanged();
	}

	private synchronized void clearReference() {
		if (this.reference != null) {
			context.ungetService(this.reference);
			this.reference = null;
			this.model = null;
		}
	}

	public void findNewService(final ServiceReference<?> serviceReference) {
		if (this.reference == serviceReference) {
			clearReference();
			useReference(findReference());
		}
	}

	private ServiceReference<?> findReference() {        
        Collection<ServiceReference<INationalHouseholdModel>> refs;
		try {
			refs = context.getServiceReferences(INationalHouseholdModel.class, null);
		} catch (final InvalidSyntaxException e) {
			return null;
		}
        
		ServiceReference<INationalHouseholdModel> best = null;
		for (final ServiceReference<INationalHouseholdModel> nhm : refs) {
			if (best != null && targetVersion.isPreferredVersion(best.getBundle().getVersion(), nhm.getBundle().getVersion())) best = nhm;
			else if (best == null && targetVersion.isPreferredVersion(nhm.getBundle().getVersion())) best = nhm;
		}
		return best;
	}

	public void startTracking() {
		useReference(findReference());
		new Listener(context, this);
	}
	
	@Override
	protected String errorMessage() {
		return targetVersion.error();
	}
}
