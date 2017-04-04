package cse.nhm.ide.ui.editor.hover;

import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;

public class ModelInformationControlCreator implements IInformationControlCreator {
	public static final IInformationControlCreator FOCUSED = new ModelInformationControlCreator(true);
	public static final IInformationControlCreator NORMAL = new ModelInformationControlCreator(false);
	private boolean isFocused;

	public ModelInformationControlCreator(boolean isFocused) {
		this.isFocused = isFocused;
	}

	@Override
	public IInformationControl createInformationControl(Shell parent) {
		return new ModelInformationControl(parent, isFocused);
	}
}
