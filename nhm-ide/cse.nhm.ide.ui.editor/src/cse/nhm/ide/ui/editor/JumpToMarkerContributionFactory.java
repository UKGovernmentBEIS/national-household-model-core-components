package cse.nhm.ide.ui.editor;

import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.common.base.Joiner;

import cse.nhm.ide.ui.builder.NHMBuilder;

public class JumpToMarkerContributionFactory extends
		ExtensionContributionFactory {

	public JumpToMarkerContributionFactory() {
	}

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
	    final ITextEditor editor = (ITextEditor) 
	            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
	    if (editor instanceof ScenarioEditor) {
	    	additions.addContributionItem(new Contribution(editor), null);
	    }
	}

	
	public static class Contribution extends ContributionItem {
		private ITextEditor editor;

		public Contribution(final ITextEditor editor) {
			this.editor = editor;
		}
		
		@Override
		public void fill(Menu menu, int index) {
			try {
				final IEditorInput input = editor.getEditorInput();
				if (input instanceof IPathEditorInput) {
					final IPath path = ((IPathEditorInput) input).getPath();
					final IWorkspace workspace = ResourcesPlugin.getWorkspace();
					final IFile[] files = workspace.getRoot().findFilesForLocationURI(path.toFile().toURI());
					if (files.length > 0) {
						final IFile file = files[0];
						final IMarker[] markers = file.findMarkers(NHMBuilder.MARKER_TYPE, true, IResource.DEPTH_ZERO);
						final int clickedLine = ((IVerticalRulerInfo) editor.getAdapter(IVerticalRulerInfo.class))
								.getLineOfLastMouseButtonActivity() + 1;
						
						int markerIndex = 0;
						for (final IMarker m : markers) {
							if (m.getAttribute(IMarker.LINE_NUMBER, 0) == clickedLine) {
								Menu markerMenu = null;
								int ix = 0;
								for (final Map.Entry<String, Object> e : m.getAttributes().entrySet()) {
									if (e.getKey().startsWith(NHMBuilder.MARKER_ADDITIONAL_LINE_PREFIX)) {
										if (markerMenu == null) {
											final MenuItem markerMenuItem = new MenuItem(menu, SWT.CASCADE, index+markerIndex++);
											markerMenu = new Menu(menu);
											markerMenuItem.setMenu(markerMenu);
											markerMenuItem.setText(shorten(m.getAttribute(IMarker.MESSAGE)));
										}
										
										final String pathAndLine = String.valueOf(e.getValue());
										// line number is the final part.
										
										final MenuItem item = new MenuItem(markerMenu, SWT.CHECK, ix++);
										
										final String [] fragments = pathAndLine.split("/");
										
										final String pathString = Joiner.on('/').join(Arrays.copyOf(fragments, fragments.length - 2));
										final IPath pathPath = Path.fromPortableString(pathString);
										final int line = Integer.valueOf(fragments[fragments.length - 1]);
										final String type = fragments[fragments.length - 2];
										
										item.setText(type + " " + pathPath.lastSegment() + ":" + line);
										
										item.addSelectionListener(new SelectionListener() {
											@Override
											public void widgetSelected(SelectionEvent e) {
												widgetDefaultSelected(e);											
											}
											
											@Override
											public void widgetDefaultSelected(SelectionEvent e) {
												try {
													final IFile file = NHMBuilder.fileForPath(pathPath).get();
													final IMarker jump = file.createMarker(IMarker.TEXT);
													jump.setAttribute(IMarker.LINE_NUMBER, line);
													IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
															jump);
													jump.delete();
												} catch (Exception ex) {}
											}
										});
									}
								}
							}
						}
					}
					}
			} catch (final Exception e) {}
		}
		

		private String shorten(Object attribute) {
			final String val = String.valueOf(attribute);
			if (val.length() > 30) {
				return val.substring(0, 30) + "...";
			}
			return val;
		}
	}

}
