package cse.nhm.ide.ui.results.nav;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import cse.nhm.ide.ui.results.efs.ZipDirectory;
import cse.nhm.ide.ui.results.efs.ZipFileFile;
import cse.nhm.ide.ui.results.efs.ZipFileStore;

public class ZipLabelProvider extends LabelProvider implements ILabelProvider {
	@Override
	public String getText(final Object element) {
		if (element instanceof ZipFileStore) {
			return ((ZipFileStore) element).getName();
		}
		return super.getText(element);
	}
	
	@Override
	public Image getImage(final Object element) {
		if (element instanceof ZipFileFile) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		} else if (element instanceof ZipDirectory) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		}
		return super.getImage(element);
	}
}
