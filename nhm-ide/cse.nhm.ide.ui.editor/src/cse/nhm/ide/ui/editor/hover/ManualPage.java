package cse.nhm.ide.ui.editor.hover;

import uk.org.cse.nhm.bundle.api.ILanguage.ICursor;

class ManualPage extends HelpThing {
	public final String content;
	public ManualPage(ICursor cursor, String content) {
		super(cursor);
		this.content = content.replace("  ", "\n\n");
	}
	
	@Override
	public String description() {
		return content;
	}
	
	@Override
	public String type() {
		return "Manual page";
	}
}
