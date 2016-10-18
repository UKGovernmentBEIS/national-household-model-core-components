package cse.nhm.ide.ui.editor.hover;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.google.common.base.Optional;

import cse.nhm.ide.ui.editor.structure.Indenter;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.ILanguage.ICursor;

class UserTemplate extends HelpThing {
	public final ICursor cursor;
	public final IDefinition<IPath> definition;
	private String text = null;
	
	public UserTemplate(ICursor cursor, IDefinition<IPath> definition) {
		super(cursor);
		this.cursor = cursor;
		this.definition = definition;
		// TODO add a button to jump to thing
		// TODO make this work for userdefined variables that we are referring to?
		// 		we could show a few lines around the code which created them?
	}

	@Override
	public String description() {
		if (text == null) {
			final ILocation<IPath> defLoc = definition.locations().iterator().next();
			
			final IPath path = defLoc.path();
			final int offset = defLoc.offset();
			final int length = defLoc.length();// unhack
			
			final IResource origin = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
			
			text = "unable to read definition from " + defLoc;
			// read substr from path from offset to length
			// and then indent it.
			
			if (origin instanceof IFile) {
				final IFile originFile = (IFile) origin;
				try (final Reader r = new InputStreamReader(originFile.getContents(), StandardCharsets.UTF_8)) {
					r.skip(offset);
					final char[] buf = new char[length];
					r.read(buf, 0, buf.length);
					text = new String(buf);
					text = Indenter.indent(text);
				} catch (IOException | CoreException e) {
				}
			}
		}
		return text;
	}
	
	@Override
	public Optional<ILocation<IPath>> location() {
		return Optional.of(definition.locations().get(0));
	}

	@Override
	public String type() {
		return "Template";
	}
}
