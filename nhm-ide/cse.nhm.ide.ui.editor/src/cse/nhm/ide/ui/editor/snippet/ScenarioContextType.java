package cse.nhm.ide.ui.editor.snippet;

import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateContextType;

public class ScenarioContextType extends TemplateContextType {

	public static final String CTX_SCENARIO = "cse.nhm.ide.ui.editor.scenarioContext";

	public ScenarioContextType() {
		super(CTX_SCENARIO);
		addResolver(new GlobalTemplateVariables.Cursor());
	}
}
