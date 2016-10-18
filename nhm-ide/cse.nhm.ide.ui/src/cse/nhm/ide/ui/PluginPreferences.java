package cse.nhm.ide.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.service.prefs.BackingStoreException;

import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import cse.nhm.ide.ui.builder.NHMNature;
import cse.nhm.ide.ui.models.ServiceTrackingModel;

/**
 * Contains keys for preferences in the preference store, and sets up their default values as well.
 */
public class PluginPreferences extends AbstractPreferenceInitializer {
	public static class VersionPreference implements Comparable<VersionPreference> {
		private static final String K_LATEST = "LATEST";
		private static final String K_RELEASE = "RELEASE";
		
		public static VersionPreference forProject(final IProject project) {
			final IScopeContext context = new ProjectScope(project);
			final IEclipsePreferences prefs = context.getNode(NHMNature.NATURE_ID);
			
			String stored = prefs.get(PROJECT_VERSION_KEY, VersionPreference.K_RELEASE);
			
			if (stored == null) stored = "";
			switch (stored) {
			case "latest":
			case K_LATEST:
				return VersionPreference.LATEST;
			case "":
			case "release":
			case K_RELEASE:
				return VersionPreference.RELEASE;
			default:
				return new VersionPreference(stored);	
			}
		}
		
		public void save(final IProject project) {
			// if the project does not have NHM nature, we should add it
			addNHMNatureIfRequired(project);
			final IScopeContext context = new ProjectScope(project);
			final IEclipsePreferences prefs = context.getNode(NHMNature.NATURE_ID);
			prefs.put(PluginPreferences.PROJECT_VERSION_KEY, versionString);
			try {
				prefs.flush();
			} catch (BackingStoreException e) {}
		}
		
		private void addNHMNatureIfRequired(IProject project) {
			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();
	
				for (int i = 0; i < natures.length; ++i) {
					if (NHMNature.NATURE_ID.equals(natures[i])) {
						// we do not need to add the nature
						return;
					}
				}
	
				// we do need to add the nature
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = NHMNature.NATURE_ID;
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			} catch (final CoreException e) {}
		}

		public static Set<VersionPreference> values() {
			final SortedSet<VersionPreference> options = new TreeSet<>();
			options.add(VersionPreference.RELEASE);
			options.add(VersionPreference.LATEST);
			
			final List<ServiceReference<INationalHouseholdModel>> models = new ArrayList<>(NHMUIPlugin.getDefault().getModels());

			for (final ServiceReference<INationalHouseholdModel> m : models) {
				options.add(new VersionPreference(m.getBundle().getVersion().toString()));
			}
			
			return options;
		}
		
		private static final VersionPreference LATEST = new VersionPreference(K_LATEST)
		{
			public String toString() {
				return "Use latest version, including test versions";
			}
			
			@Override
			public boolean isPreferredVersion(Version suggestion) {
				return true;
			}
			
			@Override
			public boolean isPreferredVersion(Version current, Version suggestion) {
				return (current == null ||
					   	suggestion.compareTo(current) > 0);
			}
			
			@Override
			public String error() {
				return "No NHM versions are installed - contact NHM support";
			}
		};
		
		private static final VersionPreference RELEASE = new VersionPreference(K_RELEASE)
		{
			public String toString() {
				return "Use latest release";
			}
			
			@Override
			public boolean isPreferredVersion(Version suggestion) {
				return suggestion.getQualifier().startsWith("R");
			}
			
			@Override
			public boolean isPreferredVersion(Version current, Version suggestion) {
				return isPreferredVersion(suggestion) &&
					   (current == null ||
					   	suggestion.compareTo(current) > 0);
			}
			
			@Override
			public String error() {
				return "No released NHM versions are installed - contact NHM support, or use a test version";
			}
		};
		
		private final String versionString;
		
		public VersionPreference(String stored) {
			this.versionString = stored;
		}
		
		@Override
		public String toString() {
			if (versionString.contains("R")) {
				return "Use release " + versionString;
			} else {
				return "Use test version " + versionString;
			}
		}
		
		public boolean isPreferredVersion(final Version suggestion) {
			return String.valueOf(suggestion).equals(versionString);
		}
		
		public boolean isPreferredVersion(final Version current, final Version suggestion) {
			return isPreferredVersion(suggestion);
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof VersionPreference && 
					((VersionPreference) obj).versionString.equals(versionString);
		}
		
		@Override
		public int hashCode() {
			return versionString.hashCode();
		}

		@Override
		public int compareTo(VersionPreference o) {
			if (this.equals(o)) return 0;
			
			if (this.versionString.equals(K_RELEASE)) return -1;
			if (o.versionString.equals(K_RELEASE)) return 1;
			
			if (this.versionString.equals(K_LATEST)) return -1;
			if (o.versionString.equals(K_LATEST)) return 1;
			
			final Version v = new Version(versionString);
			final Version ov = new Version(o.versionString);
			
			return v.compareTo(ov);
		}

		public static VersionPreference defaultSetting() {
			return RELEASE;
		}
		
		public String error() {
			return String.format("NHM version %s is not installed - to validate or run this project, right click and select a different model version", versionString);
		}
		
		public INationalHouseholdModel getModel() {
			return new ServiceTrackingModel(NHMUIPlugin.getDefault().getBundle().getBundleContext(), this);
		}
	}
	
	private static String k(final String name, final String val) {
		final String key = NHMUIPlugin.PLUGIN_ID + ".editorprefs." + name;
		defaults.put(key, val);
		return key;
	}
	
	private static String pk(final String name) {//, final String val) {
		final String key = NHMUIPlugin.PLUGIN_ID + ".projectprefs." + name;
//		defaults.put(key, val);
		return key;
	}
	
	public static class Theme {
		private static final String PREFIX = NHMUIPlugin.PLUGIN_ID + ".theme.";
		public static final String COMMENT = PREFIX + "comment";
		public static final String STRING = PREFIX + "string";
		public static final String BUILTIN = PREFIX + "builtin";
		public static final String CUSTOM = PREFIX + "custom";
		public static final String KEY = PREFIX + "key";
		public static final String PAREN = PREFIX+ "paren";
		public static final String PLACEHOLDER = PREFIX + "placeholder";
		public static final String MACRO = PREFIX + "macro";
	}
	
	private static final Map<String, String> defaults = new HashMap<>();

	public static final String EDITOR_MATCHING_BRACKETS = k("MATCHING_BRACKETS", "true");
	public static final String EDITOR_MATCHING_BRACKETS_COLOR = 
			k("MATCHING_BRACKETS_COLOR", StringConverter.asString(new RGB(128, 128, 128)));
	
	public static final String PROJECT_VERSION_KEY = pk("NHM_VERSION");
	
	@Override
	public void initializeDefaultPreferences() {
        final IPreferenceStore store = NHMUIPlugin.getDefault().getPreferenceStore();
        for (final Map.Entry<String, String> e : defaults.entrySet()) {
        	store.setDefault(e.getKey(), e.getValue());
        }
	}
}
