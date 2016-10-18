package cse.nhm.ide.ui.editor;

import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContextProvider;

public class ScenarioEditorHelpContextProvider implements IContextProvider {
	@Override
	public int getContextChangeMask() {
		return NONE;
	}

	@Override
	public IContext getContext(Object target) {
		// TODO link to manual
		return HelpSystem.getContext("cse.nhm.ide.ui.editor.scenarioEditorContext");
	}

	@Override
	public String getSearchExpression(Object target) {
		return null;
	}
}
