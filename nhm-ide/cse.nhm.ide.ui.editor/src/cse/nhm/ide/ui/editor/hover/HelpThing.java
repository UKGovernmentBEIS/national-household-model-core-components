package cse.nhm.ide.ui.editor.hover;

import org.eclipse.core.runtime.IPath;

import com.google.common.base.Optional;

import uk.org.cse.nhm.bundle.api.ILanguage.ICursor;
import uk.org.cse.nhm.bundle.api.ILocation;

public abstract class HelpThing {
	public HelpThing(ICursor cursor) {
		super();
		this.cursor = cursor;
	}
	final ICursor cursor;
	public ICursor cursor() {
		return cursor;
	}
	public abstract String description();
	public abstract String type();
	public Optional<ILocation<IPath>> location() {
		return Optional.absent();
        }
        public boolean help() {
                return false;
        }
}
