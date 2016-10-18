package cse.nhm.ide.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import uk.org.cse.nhm.bundle.api.IFS;

/**
 * An IFS which lets the NHM talk to the eclipse workspace.
 * The paths used are always relative to workspace root
 */
public class WorkspaceFS implements IFS<IPath> {
	final IContainer workspaceRoot = 
			ResourcesPlugin.getWorkspace().getRoot();
	
	public static final IFS<IPath> INSTANCE = 
			new WorkspaceFS();
	
	private WorkspaceFS() {}
	
	@Override
	public IPath deserialize(final String path_) {
		return org.eclipse.core.runtime.Path.fromPortableString(path_);
	}

	@Override
	public Path filesystemPath(final IPath res) {
		final IPath path = workspaceRoot.getFile(res).getLocation();
		return path == null ? null :
			path.toFile().toPath();
	}

	@Override
	public Reader open(final IPath eclipsePath) throws IOException {
		final IFile file = workspaceRoot.getFile(eclipsePath);
		if (file.exists()) {
			try {
				final StringWriter sw = new StringWriter();
				try (final InputStream is = file.getContents(false);
						final BufferedReader br = new BufferedReader(
							new InputStreamReader(is, file.getCharset()))) {
					String s;
					while ((s = br.readLine()) != null) {
						sw.append(s);
						sw.append('\n');
					}
				}
				return new StringReader(sw.toString());
			} catch (final CoreException e) {
				throw new IOException(e.getMessage(), e);
			}
		} else {
			throw new IOException("No such file: " + eclipsePath);
		}
	}

	@Override
	public IPath resolve(final String from, final String include) {
		if (include == null) {
			throw new IllegalArgumentException(String.format(
					"The model tried to resolve null against %s; this error should probably never be visible to a user, but it is something to do with either an include statement or stock ID.",
					include
					));
		}
		final IPath includePath = deserialize(include);
		if (includePath.isAbsolute()) {
			return includePath;
		} else {
			return deserialize(from)
				.removeLastSegments(1)
				.append(includePath);
		}
	}
}
