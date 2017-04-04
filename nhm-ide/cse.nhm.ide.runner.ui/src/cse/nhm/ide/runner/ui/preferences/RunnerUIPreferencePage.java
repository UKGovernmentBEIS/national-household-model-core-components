package cse.nhm.ide.runner.ui.preferences;

import cse.nhm.ide.runner.ui.RunnerUIPlugin;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.jface.preference.BooleanFieldEditor;

public class RunnerUIPreferencePage
    extends FieldEditorPreferencePage
    implements IWorkbenchPreferencePage {

    public RunnerUIPreferencePage() {
        super(GRID);
        setPreferenceStore(RunnerUIPlugin.getDefault().getPreferenceStore());
    }

    public void init(IWorkbench workbench) {

    }

    @Override
    protected void createFieldEditors() {
        addField(new BooleanFieldEditor(RunnerUIPreferences.K_HIDE_LOCAL,
                                        "Disable local running of scenarios when there are other options.",
                                        getFieldEditorParent()));
    }
}
