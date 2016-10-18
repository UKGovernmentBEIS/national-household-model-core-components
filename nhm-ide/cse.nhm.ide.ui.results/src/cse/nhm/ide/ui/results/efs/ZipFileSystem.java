package cse.nhm.ide.ui.results.efs;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.NullProgressMonitor;

public class ZipFileSystem extends FileSystem {
	public ZipFileSystem() {
		
	}

	@Override
	public IFileStore getStore(final URI uri) {
		try {
			final URI archiveURI = URI.create(uri.getRawQuery());
			final IFileStore archive = EFS.getStore(archiveURI);
			final File file = archive.toLocalFile(EFS.CACHE, new NullProgressMonitor());
			
			final ZipDirectory root = new ZipDirectory(archiveURI);
			
			try (final ZipFile zf = new ZipFile(file)) {
				final Enumeration<? extends ZipEntry> entries = zf.entries();
				
				while (entries.hasMoreElements()) {
					final ZipEntry ze = entries.nextElement();
					// need to arrange the entries into a tree
					// we don't care about directories, as we are only going to show the implicit ones.
					if (ze.isDirectory()) continue;
					Path path = Paths.get(ze.getName());
					ZipDirectory directory = root;
					while (path.getNameCount() > 1) {
						final Path firstPart = path.getName(0);
						directory = directory.getSubdirectory(firstPart.toString());
						path = firstPart.relativize(path);
					}
					directory.addFile(new ZipFileFile(archiveURI, directory, path.toString(), ze));
				}
			}
			
			if (uri.getPath().equals("/")) return root;
			
			// now we need to resolve the uri within root
			Path path = Paths.get(uri.getPath());
			
			IFileStore result = root;
			
			while (path.getNameCount() > 1) {
				result = result.getChild(path.getName(0).toString());
				path = path.subpath(1, path.getNameCount());
			}
			
			return result.getChild(path.getName(0).toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static URI createZipURI(final URI locationURI) {
		return URI.create("zip:/?" + locationURI);
	}
}
