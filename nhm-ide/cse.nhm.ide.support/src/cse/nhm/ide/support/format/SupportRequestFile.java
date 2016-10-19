package cse.nhm.ide.support.format;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.zip.Adler32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.io.ByteStreams;

import cse.nhm.ide.ui.builder.NHMNature;

/**
 * Interfaces with a zip file containing a support request Since modifying a zip
 * file on disk is highly annoying, the save process involves rebuilding the
 * original zip. We only store the metadata in memory as it ought to be quite
 * small
 * 
 * File contents are not displayed and so do not get loaded.
 * 
 * @author hinton
 *
 */
public class SupportRequestFile {
	private static final String K_METADATA = ".metadata/";

	private static final String K_TITLE = "title";
	private static final String K_DESCRIPTION = "description";

	private static final String K_CREATED = "created";

	private static final String K_USER = "user";

	private static final String K_IDE_VERSION = "ide-version";

	private static final String K_UUID = "uuid";

	private static final String K_MODIFIED = "modified";

	private static final String K_PLATFORM = ".platform/";

	private final Map<String, String> metadata = new HashMap<>();

	private final Set<String> attachmentNames = new TreeSet<>();
	private final Set<IFile> resourcesToAdd = new HashSet<>();
	
	private boolean dirty = true;

	/**
	 * Since the eclipse editor may open an {@link IFile}, from within the workspace,
	 * or an {@link IFileStore} from an {@link URI}, and these have different interfaces,
	 * a stupid shim interface is required to make this work.
	 */
	private interface IContents {
		public boolean exists();
		public void write(final InputStream newContents, final IProgressMonitor monitor) throws CoreException, IOException;
		public InputStream read() throws CoreException;
		public String name();
	}
	
	public static class FileContents implements IContents {
		final IFile file;

		public FileContents(IFile file) {
			super();
			this.file = file;
		}

		@Override
		public boolean exists() { return file.exists();	}

		@Override
		public void write(InputStream newContents, IProgressMonitor monitor) throws CoreException {
			file.setContents(newContents, true, true, monitor);
		}

		@Override
		public InputStream read() throws CoreException {
			return file.getContents();
		}

		@Override
		public String name() {
			return file.getName();
		}
	}
	
	public static class StoreContents implements IContents {
		final IFileStore store;

		public StoreContents(IFileStore store) {
			super();
			this.store = store;
		}

		@Override
		public boolean exists() { return store.fetchInfo().exists(); }

		@Override
		public void write(InputStream newContents, IProgressMonitor monitor) throws CoreException, IOException {
			try (final OutputStream os = ((IFileStore) store).openOutputStream(EFS.NONE, null)){				
				ByteStreams.copy(newContents, os);
			}
		}

		@Override
		public InputStream read() throws CoreException {
			return store.openInputStream(EFS.NONE, null);
		}

		@Override
		public String name() {
			return store.getName();
		}
	}
	
	private IContents contents;

	public SupportRequestFile(final Object container, final boolean init) throws IOException, CoreException {
		assert container instanceof IFile || container instanceof IFileStore;
		if (container instanceof IFile) {
			contents = new FileContents((IFile) container);
		} else if (container instanceof IFileStore) {
			contents = new StoreContents((IFileStore) container);
		} else {
			throw new RuntimeException("Support request file must come from an IFile or an IFileStore.");
		}
		
		if (contents.exists()) {
			revert();
		}
		
		if (init) {
			// insert new request metadata
			setMeta(K_UUID, UUID.randomUUID().toString());
			setMeta(K_CREATED, new Date().toString());
			setMeta(K_USER, System.getProperty("user.name"));
		}
	}
	
	public SupportRequestFile(IFileStore store) throws IOException, CoreException {
		this(store, false);
	}

	public SupportRequestFile(IFile file) throws IOException, CoreException {
		this(file, false);
	}

	private static String getProductVersion() {
		String version = "unknown";
		try {
			final IProduct product = Platform.getProduct();
			version = 
					product.getName() + " " +
					String.valueOf(product.getDefiningBundle().getVersion());
		} catch (final Exception e) {
			version = "error getting version: " + e.getMessage();
		}
		return version;
	}

	private void setMeta(final String k, final String v) {
		if (Objects.equals(metadata.get(k), v)) return;
		metadata.put(k, v);
		dirty = true;
	}

	private String getMeta(final String k, final String d) {
		final String v = metadata.get(k);
		if (v == null) return d;
		else return v;
	}

	public Set<String> getAttachments() {
		return ImmutableSortedSet.copyOf(attachmentNames);
	}

	public void addAttachment(final IFile resource) {
		resourcesToAdd.add(resource);
		attachmentNames.add(resource.getFullPath().toPortableString());
		dirty = true;
	}

	public boolean isDirty() {
		return dirty;
	}

	private static void copy(final String path, final Supplier<InputStream> input, final ZipOutputStream out) throws IOException {
		final Adler32 crc = new Adler32();
		try (final InputStream in = input.get()) {
			int k;
			while ((k = in.read()) != -1) {
				crc.update(k);
			}
		}
		final ZipEntry ze = new ZipEntry(path);
		ze.setCrc(crc.getValue());
		ze.setMethod(ZipOutputStream.DEFLATED);
		out.putNextEntry(ze);
		try (final InputStream in = input.get()) {
			ByteStreams.copy(in, out);
		}
	}
	
	public void save(IProgressMonitor monitor) throws IOException, CoreException {
		if (!dirty)	return;
		if (monitor == null) monitor = new NullProgressMonitor();
		
		setMeta(K_IDE_VERSION, getProductVersion());
		setMeta(K_MODIFIED, new Date().toString());

		// refresh the error log
		monitor.beginTask("Saving "+contents.name(), 100);
		// 1. create temporary zipfile
		final Path tempFile = Files.createTempFile(getTitle(), ".ssr");
		try {
			try (final ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempFile),
					StandardCharsets.UTF_8)) {
				zos.setMethod(ZipOutputStream.DEFLATED);
				// 2. write metadata into zipfile
				SubProgressMonitor spm = new SubProgressMonitor(monitor, 20);
				spm.beginTask("Saving metadata", metadata.size());
				for (final Map.Entry<String, String> e : metadata.entrySet()) {
					final byte[] bytes = e.getValue().getBytes(StandardCharsets.UTF_8);

					copy(K_METADATA + e.getKey(), new Supplier<InputStream>() {
						@Override
						public InputStream get() {
							return new ByteArrayInputStream(bytes);
						}
					}, zos);
					spm.worked(1);
				}

				spm = new SubProgressMonitor(monitor, 40);
				spm.beginTask("Copying attachments", attachmentNames.size());
				
				final Set<String> remainingResources = new HashSet<String>(attachmentNames);
				// 3. copy resources to add into zipfile
				for (final IFile resource : resourcesToAdd) {
					String resourcePath = resource.getFullPath().toPortableString();
					spm.subTask(resourcePath);
					// this check is related to removeAttachment
					// when we remove an attachment we remove its name
					// from the names set. we don't want to add it back
					// if it's still in resourcesToAdd!
					if (!attachmentNames.contains(resourcePath)) continue;
					
					remainingResources.remove(resourcePath);
					// store resource into file

					copy(resourcePath, new Supplier<InputStream>() {
						@Override
						public InputStream get() {
							try {
								return resource.getContents();
							} catch (CoreException e) {
								throw new RuntimeException(e.getMessage(), e);
							}
						}
					}, zos);
					spm.worked(1);
				}
				// we have added all the resources; this prevents us from saving them again next time.
				resourcesToAdd.clear();
				// 4. copy any old resources from existing zipfile
				try (final InputStream in = contents.read();
						final ZipInputStream zis = new ZipInputStream(in, StandardCharsets.UTF_8)) {
					ZipEntry ze;
					while ((ze = zis.getNextEntry()) != null) {
						if (remainingResources.isEmpty())
							break;
						if (remainingResources.remove(ze.getName())) {
							spm.subTask(ze.getName());
							// copy the entry. maybe safe just to move it?
							final ZipEntry zo = new ZipEntry(ze);
							zos.putNextEntry(zo);
							ByteStreams.copy(zis, zos);
							spm.worked(1);
						}
					}
				}
				
				// 5. insert the special extra resources for the error markers, and version data
				// platform error log:
				final Path logFileLocation = Platform.getLogFileLocation().toFile().toPath();

				copy(K_PLATFORM+"application.log", new Supplier<InputStream>() {
					@Override
					public InputStream get() {
						try {
							return Files.newInputStream(logFileLocation);
						} catch (IOException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					}
				}, zos);

				// model versions:
				
				final byte[] sbContents = getModelVersions();
				copy(K_PLATFORM+"model-versions.tab", new Supplier<InputStream>() {
					@Override
					public InputStream get() {
						return new ByteArrayInputStream(sbContents);
					}
				}, zos);
			}
			// 5. zip file safely closed, swap files over
			monitor.subTask("Writing file...");
			
			try (final InputStream is = Files.newInputStream(tempFile)) {
				SubProgressMonitor spm = new SubProgressMonitor(monitor, 20);
				contents.write(is, spm);
			}
			monitor.worked(20);
		} finally {
			// 6. cleanup temp file
			Files.deleteIfExists(tempFile);
		}
		
		dirty = false;
	}



	private byte[] getModelVersions() {
		final StringBuffer sb = new StringBuffer();
		for (final IProject proj : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			final Optional<NHMNature> nature = NHMNature.of(proj);
			if (nature.isPresent()) {
				final NHMNature nhmNature = nature.get();
				final String versionPreference = nhmNature.getModelVersion().getVersionString();
				final String version = nhmNature.getModel().version();
				sb.append(String.format("%s\t%s\t%s\n", proj.getName(), versionPreference, version));
			}
		}
		
		final byte[] sbContents = sb.toString().getBytes(StandardCharsets.UTF_8);
		return sbContents;
	}

	public void revert() throws IOException, CoreException {
		if (!dirty)
			return;
		dirty = false;
		metadata.clear();
		resourcesToAdd.clear();
		attachmentNames.clear();
		try (final InputStream is = contents.read();
				final ZipInputStream zis = new ZipInputStream(is, StandardCharsets.UTF_8)) {
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				final String name = ze.getName();
				if (name.startsWith(K_METADATA)) {
					final ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ByteStreams.copy(zis, baos);
					final String readResult = new String(baos.toByteArray(), StandardCharsets.UTF_8);
					metadata.put(name.substring(K_METADATA.length()), readResult);
				} else if (name.startsWith(K_PLATFORM)) {
					// ignore
				} else {
					attachmentNames.add(name);
				}
			}
		}
	}

	public String getTitle() {
		return getMeta(K_TITLE, "");
	}

	public String getDescription() {
		return getMeta(K_DESCRIPTION, "");
	}

	public void setTitle(String text) {
		setMeta(K_TITLE, text);
	}

	public void setDescription(String text) {
		setMeta(K_DESCRIPTION, text);
	}

	public void removeAttachment(String o) {
		attachmentNames.remove(o);
		dirty = true;
	}

	public String getFileName() {
		return contents.name();
	}
}
