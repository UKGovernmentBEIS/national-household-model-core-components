package cse.nhm.ide.ui.editor.snippet;

import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import cse.nhm.ide.ui.editor.NHMEditorPlugin;

public class SnippetPreferences extends TemplatePreferencePage {
	public SnippetPreferences() {
		setPreferenceStore(NHMEditorPlugin.getDefault().getPreferenceStore());
        setTemplateStore(TemplateHelper.getTemplateStore("snippets"));
        setContextTypeRegistry(TemplateHelper.getContextTypeRegistry());
        setDescription("Pre-defined snippets of code for NHM scenarios.");
	}
	
	@Override
	protected boolean isShowFormatterSetting() {
		return false;
	}
}
