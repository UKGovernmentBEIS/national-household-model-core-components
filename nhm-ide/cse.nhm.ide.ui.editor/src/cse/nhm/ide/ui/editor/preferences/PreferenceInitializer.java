package cse.nhm.ide.ui.editor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import cse.nhm.ide.ui.editor.NHMEditorPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = NHMEditorPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.DELETE_BOTH, true);
		store.setDefault(PreferenceConstants.INSERT_CLOSING, true);
		store.setDefault(PreferenceConstants.SKIP_CLOSING, true);
		store.setDefault(PreferenceConstants.SKIP_DELETE, false);
		store.setDefault(PreferenceConstants.WRAP_SELECTION, true);
		store.setDefault(PreferenceConstants.AUTO_INDENT, true);
	}

}
