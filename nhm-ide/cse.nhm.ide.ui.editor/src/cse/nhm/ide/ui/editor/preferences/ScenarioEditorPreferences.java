package cse.nhm.ide.ui.editor.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import cse.nhm.ide.ui.editor.NHMEditorPlugin;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ScenarioEditorPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public ScenarioEditorPreferences() {
		super(GRID);
		setPreferenceStore(NHMEditorPlugin.getDefault().getPreferenceStore());
		setDescription("Preferences for NHM scenario editing");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstants.INSERT_CLOSING, "Insert a closing bracket when you type an opening bracket", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.SKIP_CLOSING, "Do not insert a closing bracket when one already exists, but move over it", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.SKIP_DELETE, "When deleting a bracket, pass over it if there is something between the brackets", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.DELETE_BOTH, "When deleting a bracket, delete the corresponding other bracket automatically", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.WRAP_SELECTION, "If there is a selection and you type a bracket, wrap the selection in brackets rather than replacing it", getFieldEditorParent()));
		
		addField(new BooleanFieldEditor(PreferenceConstants.AUTO_INDENT, "When inserting new lines, auto-indent the line to the standard position", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}