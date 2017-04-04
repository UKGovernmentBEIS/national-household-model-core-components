package cse.nhm.ide.support;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import com.google.common.base.Optional;

import cse.nhm.ide.ui.WorkspaceFS;
import cse.nhm.ide.ui.builder.NHMNature;
import uk.org.cse.nhm.bundle.api.IRunInformation;

public class GetInputsUtil {
	public static Set<IFile> getInputs(final Iterable<IFile> files) {
		// TODO find dependencies here (NHM plugin required)
		Set<IFile> filesToAdd = new HashSet<>();
		for (final IFile file : files) {
			if (!file.exists()) continue;
			filesToAdd.add(file);
			final IProject project = file.getProject();
			if (project != null) {
				final Optional<NHMNature> mnature = NHMNature.of(project);

				if (mnature.isPresent()) {
					final NHMNature nature = mnature.get();
					for (final IPath path : nature.getDependencies(file.getFullPath())) {
						IFile file2 = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
						if (file2.exists()) filesToAdd.add(file2);
					}
					
					try {
						final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
						IRunInformation<IPath> info = nature.getModel().getRunInformation(WorkspaceFS.INSTANCE, file.getFullPath());
						for (final IPath path : info.stocks().values()) {
							IFile stock = root.getFile(path);
							if (stock != null && stock.exists()) {
								filesToAdd.add(stock);
							}
						}
					} catch (final Exception ex) {}
				}
			}
		}
		return filesToAdd;
	}
}
