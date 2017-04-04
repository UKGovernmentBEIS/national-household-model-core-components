package cse.nhm.ide.ui.results.efs;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public abstract class ZipFileStore extends FileStore {
	protected final URI archiveURI;
	private final ZipDirectory directory;
	private final String name;
	
	public ZipFileStore(final URI archiveURI) {
		this(archiveURI, null, "");
	}
	
	public ZipFileStore(final URI archiveURI, final ZipDirectory directory, final String name) {
		super();
		this.archiveURI = archiveURI;
		this.directory = directory;
		this.name = name;
	}
	
	@Override
	public FileInfo fetchInfo(final int options, final IProgressMonitor monitor) throws CoreException {
		final FileInfo result = new FileInfo(getName());
		result.setExists(true);
		result.setAttribute(EFS.ATTRIBUTE_READ_ONLY, true);
		return result;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IFileStore getParent() {
		return this.directory;
	}

	@Override
	public URI toURI() {
		if (this.directory == null) {
			// special case for root, because path() does not have a leading slash for root.
			return URI.create("zip:/?" + this.archiveURI);
		} else {
			return URI.create("zip:" + path() + "?" + this.archiveURI);
		}
	}

	protected String path() {
		if (this.directory == null) {
			return "";
		} else {
			return this.directory.path() + "/" + getName();
		}
	}
}
