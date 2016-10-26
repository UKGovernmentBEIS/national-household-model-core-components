package cse.nhm.ide.runner.ui.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.base.Optional;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRun.State;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;

public class GetResultsCommandHandler extends AbstractHandler {
	static final SimpleDateFormat DTFM = new SimpleDateFormat("YYYY-MM-dd HHmm");
	
	static abstract class AbsUnzipResultTask implements IRunnableWithProgress {
		protected IScenarioRun run;
		
		static class UnclosingStream extends InputStream {
			final InputStream input;
			
			public UnclosingStream(final InputStream input) {
				super();
				this.input = input;
			}

			@Override
			public int read() throws IOException {
				return input.read();
			}
		}
		
		public AbsUnzipResultTask(final IScenarioRun run2) {
			run = run2;
		}

		protected abstract void store(String name, InputStream stream, final IProgressMonitor progress) throws Exception;

        static class TableOutput {
            final LinkedHashMap<String, Integer> columns = new LinkedHashMap<>();
            final List<List<String>> rows = new ArrayList<>();
            List<String> thisRow;

            void addEntireRow(String... keyValue) {
                addRow();

                for (int i = 0; i<keyValue.length; i+=2) {
                    addFieldToRow(keyValue[i], keyValue[i+1]);
                }
            }

            void addRow() {
                thisRow = new ArrayList<>();
                rows.add(thisRow);
            }

            void addFieldToRow(final String column, final String value) {
                Integer columnNumber = columns.get(column);

                if (columnNumber == null) {
                    columnNumber = columns.size();
                    columns.put(column, columnNumber);
                }

                while (thisRow.size() < columnNumber + 1) {
                    thisRow.add("");
                }

                thisRow.set(columnNumber, value);
            }

            public String getString() {
                final StringBuffer sb = new StringBuffer();
                boolean first = true;
                for (final String s : columns.keySet()) {
                    if (first) first = false;
                    else sb.append("\t");
                    sb.append(s);
                }

                sb.append("\n");

                for (final List<String> row : rows) {
                    first = true;
                    for (final String s : row) {
                        if (first) first = false;
                        else sb.append("\t");
                        sb.append(s);
                    }
                    for (int i = row.size(); i<columns.size(); i++) {
                        sb.append("\t");
                    }
                    sb.append("\n");
                }

                return sb.toString();
            }
        }

        static class BatchResult {
            final TableOutput results = new TableOutput();
            final TableOutput errors = new TableOutput();

            void addObjectToRow(final TableOutput table, final String prefix, final JsonObject object) {
                if (object == null) return;
				for (final String s : object.names()) {
					final JsonValue jv = object.get(s);
					if (jv.isString()) {
                        table.addFieldToRow(prefix + s, jv.asString());
					} else if (jv.isNumber()) {
                        table.addFieldToRow(prefix + s, String.valueOf(jv.asDouble()));
					} else {
                        table.addFieldToRow(prefix + s, String.valueOf(jv));
					}
				}
			}

            public void addBatchOutputRow(final JsonObject in, final JsonObject out) {
                if (in != null || out != null) {
                    results.addRow();
                    addObjectToRow(results, "input.", in);
                    addObjectToRow(results, "output.", out);
                }
            }

			public void addBatchOutput(final JsonValue j) {
				if (j != null && j.isObject()) {

                    // inputs: {key : value}
                    // outputs : {key : value} or [{key : value}]
					// errors: [strings]
					final JsonObject jo = j.asObject();
					if (jo != null) {
						final JsonValue in = jo.get("inputs");
						final JsonValue out = jo.get("outputs");
                        final JsonValue errors = jo.get("errors");

                        final JsonObject ino = (in == null || !in.isObject()) ? null : in.asObject();

                        if (out != null) {
                            if (out.isObject()) {
                                addBatchOutputRow(ino, out.asObject());
                            } else if (out.isArray()) {
                                final JsonArray a = out.asArray();
                                for (int i = 0; i<a.size(); i++) {
                                    final JsonValue v = a.get(i);
                                    if (v != null && v.isObject()) {
                                        addBatchOutputRow(ino, v.asObject());
                                    }
                                }
                            }
                        }

                        if (errors != null && errors.isArray()) {
							final JsonArray a = errors.asArray();
                            for (int i = 0; i< a.size(); i++) {
                                this.errors.addRow();

                                addObjectToRow(this.errors, "input.", ino);

								final JsonValue v = a.get(i);
								if (v.isString()) {
                                    this.errors.addFieldToRow("error", v.asString());
								}
							}
						}
					}
				}
			}
        }
		
		@Override
		public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			monitor.beginTask("Getting results", 100);
            final String outputFolderName = run.getName() + " (" + DTFM.format(run.getQueuedDate()) + ")";

			try {
				switch (run.getType()) {
				case Batch:
					// process the parts into a whole thing somehow
					
					prepare(outputFolderName);
					final BatchResult br = new BatchResult();
					
					for (int i = 0; i<run.getNumberOfParts(); i++) {
						final Optional<InputStream> is_ = run.getPartStream(i);
						if (is_.isPresent()) {
							try (final InputStream is = is_.get();
								final Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {								
								final JsonValue j = Json.parse(r);
								br.addBatchOutput(j);
							}
						}
					}

					monitor.worked(50);
					
					// combine the parts
                    store("results.tab", new ByteArrayInputStream(br.results.getString()
                                                                  .getBytes(StandardCharsets.UTF_8)),
                            new SubProgressMonitor(monitor, 25));

                    store("errors.tab", new ByteArrayInputStream(br.errors.getString()
                                                                  .getBytes(StandardCharsets.UTF_8)),
                            new SubProgressMonitor(monitor, 25));

					break;
					
				default:
				case Scenario:
					// it's a zip stream and it should have but one part.
					if (run.getNumberOfParts() != 1) 
						throw new UnsupportedOperationException(
								"The normal scenario " + run.getName() + " has " + run.getNumberOfParts() + " parts, not one part"
								);
						final Optional<InputStream> is_ = run.getPartStream(0);
						try (final InputStream is = is_.get(); 
							 final ZipInputStream zs = new ZipInputStream(is)) {
	
							prepare(outputFolderName);
	
							ZipEntry ze = null;
							while ((ze = zs.getNextEntry()) != null) {
								// create a suitable folder in the output, and then
								// unzip the file into it.
								store(ze.getName(), zs, new SubProgressMonitor(monitor,
										Math.max(0, (int) (ze.getCompressedSize() / 1024))));
	
							}
	
					}
					break;
                }

                {
                    // EXS-195 RUN INFORMATION
                    final TableOutput metadata = new TableOutput();
                    final String K_KEY = "key";
                    final String K_VALUE = "value";
                    metadata.addEntireRow(K_KEY, "id",         K_VALUE, run.getID());
                    metadata.addEntireRow(K_KEY, "name",       K_VALUE, run.getName());
                    metadata.addEntireRow(K_KEY, "user",       K_VALUE, run.getUser());
                    metadata.addEntireRow(K_KEY, "queued",     K_VALUE, String.valueOf(run.getQueuedDate()));
                    metadata.addEntireRow(K_KEY, "downloaded", K_VALUE, String.valueOf(new java.util.Date()));
                    metadata.addEntireRow(K_KEY, "version",    K_VALUE, run.getVersion());
                    metadata.addEntireRow(K_KEY, "type",       K_VALUE, String.valueOf(run.getType()));
                    metadata.addEntireRow(K_KEY, "server",     K_VALUE, run.getRunner().getName());
                    final InputStream stream =
                        new ByteArrayInputStream(metadata.getString().getBytes(StandardCharsets.UTF_8));
                    store("run-information.tab", stream, null);
                }
            } catch (final Exception ex) {
				RunnerUIPlugin .error("Error getting results for " + run.getName(), ex);
			}
			monitor.done();
		}

		abstract void prepare(String outputFolderName) throws IOException;
	}

	public static class UnzipResultTempTask extends AbsUnzipResultTask {
		private java.nio.file.Path tempDirectory;
		private java.nio.file.Path contentDirectory;

		public UnzipResultTempTask(final IScenarioRun run2) {
			super(run2);
		}

		@Override
		protected void store(final String name, final InputStream stream, final IProgressMonitor progress) throws Exception {
			final java.nio.file.Path outPath = contentDirectory.resolve(name);
			Files.createDirectories(outPath.getParent());
			progress.beginTask(name, 100);
			Files.copy(stream, outPath);
			progress.worked(100);
		}

		public void cleanup() {
			try {
				Files.walkFileTree(tempDirectory, new SimpleFileVisitor<java.nio.file.Path>() {
					@Override
					public FileVisitResult visitFile(final java.nio.file.Path file, final BasicFileAttributes attrs) throws IOException {
						Files.delete(file);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(final java.nio.file.Path dir, final IOException exc) throws IOException {
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (final IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		@Override
		void prepare(final String outputFolderName) throws IOException {
			tempDirectory = Files.createTempDirectory(run.getName());
			contentDirectory = tempDirectory.resolve(outputFolderName);
			Files.createDirectories(contentDirectory);
		}

		public java.nio.file.Path getOutputFolder() {
			return contentDirectory;
		}
		
	}
	
	static class UnzipResultTask extends AbsUnzipResultTask implements IRunnableWithProgress {
		private static void mkdirs(final IContainer folder) throws CoreException {
			final IContainer container = folder.getParent();
			if (container instanceof IFolder && !container.exists()) {
				mkdirs(container);
			}
			if (!folder.exists() && folder instanceof IFolder) ((IFolder)folder).create(true, true, null);
		}
		
		private final IPath output;
		private IFolder outputFolder;

		public UnzipResultTask(final IScenarioRun run, final IPath target) {
			super(run);
			this.output = target;
		}

		@Override
		protected void store(final String name, final InputStream stream, final IProgressMonitor monitor) throws Exception {
			final IFile file = outputFolder.getFile(name);
			mkdirs(file.getParent());
			file.create(new UnclosingStream(stream), true, monitor);	
		}
		
		@Override
		void prepare(final String outputFolderName) {
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IResource parent = root.findMember(this.output);
			
			final IContainer parentC;
			final boolean createSubFolder;
			if (parent == null) {
				createSubFolder = false;
				final IFolder folder = root.getFolder(this.output);
				if (folder != null) {
					try {
						folder.create(true, true, null);
						parentC = folder;
					} catch (final CoreException e) {
						RunnerUIPlugin.error(e.getMessage(), e);
						return;
					}
				} else {
					RunnerUIPlugin.error("Invalid folder for output: " + this.output, null);
					return;
				}
			} else if (parent instanceof IContainer) {
				createSubFolder = true;
				parentC = (IContainer) parent;
			} else {
				RunnerUIPlugin.error("Output folder" + this.output + " does not exist or is not a folder", null);
				return;
			}
			
			if (createSubFolder) {
				int counter = 0;
				do {
					outputFolder = parentC.getFolder(new Path(outputFolderName + (counter > 0 ? " (" + counter + ")" : "")));
					counter++;
				} while (outputFolder.exists());
				
				try {
					mkdirs(outputFolder);
				} catch (final CoreException e) {
					RunnerUIPlugin.error("Creating output folder " + parentC, e);
					return;
				}
			}
		}
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// download the results of a run into the workspace and unzip them.
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			for (final Object o : ((IStructuredSelection) sel).toArray()) {
				if (o instanceof IScenarioRun) {
					final IScenarioRun r = (IScenarioRun)o;
					
				if (r.getState() == State.FINISHED) {

						final ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
								.getSelectionService()
								.getSelection("org.eclipse.ui.navigator.ProjectExplorer");
						
						IContainer c = null;
						if (selection instanceof IStructuredSelection) {								
							final Object element = ((IStructuredSelection) selection).getFirstElement();
							
							if (element instanceof IResource) {
								final IProject proj = ((IResource) element).getProject();
								c = proj;
							}
						}

						if (c == null) {
							c = ResourcesPlugin.getWorkspace().getRoot();
						}
						
						final ContainerSelectionDialog csd = new ContainerSelectionDialog(
								HandlerUtil.getActiveShell(event), c, 
								false,
								"Save outputs from " + r.getName() + " to");

						csd.open();

						final Object[] result = csd.getResult();
						if (result != null && result.length > 0 && result[0] instanceof IPath) {
							try {
								PlatformUI.getWorkbench().getProgressService()
										.busyCursorWhile(new UnzipResultTask(r, (IPath) result[0]));
							} catch (InvocationTargetException | InterruptedException e) {
								RunnerUIPlugin.error("Unzipping " + r.getName(), e);
							}
						}
					}
				}
			}
		}
								
									
									
		return null;
	}

}
