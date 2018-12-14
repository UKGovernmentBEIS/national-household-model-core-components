package cse.nhm.ide.ui.builder;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import cse.nhm.ide.ui.NHMUIPlugin;
import cse.nhm.ide.ui.WorkspaceFS;
import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.ILocation.LocationType;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IValidationResult;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem.ProblemLevel;

public class NHMBuilder extends IncrementalProjectBuilder {
	public static final String BUILDER_ID = "cse.nhm.ide.ui.nhmBuilder";
	public static final String MARKER_TYPE = "cse.nhm.ide.ui.scenarioProblem";

	public static final String MARKER_ADDITIONAL_LINE_PREFIX = MARKER_TYPE + ".otherLine";
	public static final String MARKER_PREVIOUS_MARKER = MARKER_TYPE + ".previous";
	public static final String MARKER_NEXT_MARKER = MARKER_TYPE + ".next";
	protected static final int K_MAX_PROBLEMS = 2000;

	private final Multimap<IPath, IPath> dependencies = HashMultimap.create();
	private final Multimap<IPath, IPath> dependents = HashMultimap.create();

	private IMarker addMarker(final IFile file, final String message, int lineNumber, final int offset, final int severity) {
		try {

			final IMarker marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.CHAR_START, offset);
			marker.setAttribute(IMarker.CHAR_END, offset+1);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			return marker;
		} catch (final CoreException e) {

		}
		return null;
	}

	private static boolean isScenarioResource(final IResource resource) {
		return resource.getName().endsWith(".nhm") && resource instanceof IFile;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected IProject[] build(final int kind, final Map args, final IProgressMonitor monitor) throws CoreException {
		final Set<IResource> resourcesToProcess = new LinkedHashSet<IResource>();
		final IResourceDelta delta = getDelta(getProject());
		final NHMNature nature = ((NHMNature) getProject().getNature(NHMNature.NATURE_ID));
		final INationalHouseholdModel nhmVersion = nature.getModel();

		this.dependents.clear();
		Multimaps.invertFrom(this.dependencies, this.dependents);

		if (kind == FULL_BUILD || delta == null) {
			NHMUIPlugin.logInformation("FULL_BUILD for %s", getProject().getName());
			getProject().accept(new IResourceVisitor() {
				@Override
				public boolean visit(final IResource resource) {
					if (isScenarioResource(resource)) resourcesToProcess.add(resource);
					return true;
				}
			});
		} else {
			NHMUIPlugin.logInformation("DELTA_BUILD for %s", getProject().getName());

			delta.accept(new IResourceDeltaVisitor() {
				@Override
				public boolean visit(final IResourceDelta delta)
						throws CoreException {
					// we need to enqueue all the resources that need processing
					if (isScenarioResource(delta.getResource())) {
						for (final IPath p : dependencyClosure(delta.getResource())) {
							final Optional<IFile> wsFile = fileForPath(p);
							resourcesToProcess.addAll(wsFile.asSet());
						}
						switch (delta.getKind()) {
						case IResourceDelta.CHANGED:
						case IResourceDelta.ADDED:
							NHMUIPlugin.logInformation("Resource changed in %s : %s",
									getProject().getName(),
									delta.getResource().getName());
							resourcesToProcess.add(delta.getResource());
							break;
						case IResourceDelta.REMOVED:
							resourceRemoved(delta.getResource());
							NHMUIPlugin.logInformation("Resource removed from %s : %s",
									getProject().getName(),
									delta.getResource().getName());
							break;
						}
					}
					return true;
				}
			});

		}

		NHMUIPlugin.logInformation("Resources to process for %s: %s",
				getProject().getName(),
				resourcesToProcess);

		// we have a set of all the resources that may have been affected or
		// need processing. this needs updating to compute the closure of
		// resources that include any of these resources up and have a scenario
		// in them.

		monitor.beginTask("Validating " + getProject(), resourcesToProcess.size());

		final Multimap<IResource, IValidationProblem<IPath>> problemsForFiles = LinkedListMultimap.create();

		final ExecutorService executor = Executors.newSingleThreadExecutor();

		for (final IResource resource : resourcesToProcess) {
			monitor.subTask("Validate " + resource.getName());
			final Future<Collection<IValidationProblem<IPath>>> future = executor.submit(new Callable<Collection<IValidationProblem<IPath>>>() {
				@Override
				public Collection<IValidationProblem<IPath>> call() throws Exception {
					return validate(nature, nhmVersion, resource);
				}
			});
			boolean done = false;
			int counter = 0;
			while (!done) {
				try {
					final Collection<IValidationProblem<IPath>> problems = future.get(5, TimeUnit.SECONDS);
					counter+=5;
					problemsForFiles.putAll(resource, problems);
					done = true;
				} catch (final InterruptedException ie) {
					NHMUIPlugin.logInformation("Validation for %s (interrupted) %s", getProject().getName(),
							ie.getMessage());
					done = true;
				} catch (final ExecutionException ee) {
					NHMUIPlugin.logException(
							String.format("During validation of %s in %s, an exception occurred. This probably should not happen.\n" +
					"The scenario is likely to be invalid, but unfortunately there is no more detail available here, because the code which determines validity has itself broken in some way.",
									getProject().getName(),
									resource.getName()),
							ee);
					done = true;
				} catch (final TimeoutException e) {
					monitor.subTask("Validate " + resource.getName() + " (" + counter +"s)...");
					NHMUIPlugin.logInformation("Validation of %s in %s has taken %d seconds so far...",
							getProject().getName(),
							resource.getName(),
							counter);
				}

				if (isInterrupted()) {
					break;
				}
			}
		}

		executor.shutdownNow();
		if (isInterrupted()) {
			NHMUIPlugin.logInformation(
					"Stopped validating %s early due to user cancel - the problem markers will be out of date.",
					getProject().getName());
			return null;
		}

		this.dependents.clear();
		Multimaps.invertFrom(this.dependencies, this.dependents);

		nature.setDependencies(this.dependencies);

		final IWorkspaceRunnable r = new IWorkspaceRunnable() {
			@Override
			public void run(final IProgressMonitor monitor) throws CoreException {
				// update all markers in one go. this should lock?
				monitor.beginTask("Updating markers", resourcesToProcess.size());

				final int nProblems = problemsForFiles.values().size();
				final int problemsPerFile;

				if (nProblems > K_MAX_PROBLEMS) {
					NHMUIPlugin.logInformation("There are %d problems, which exceeds the max. marker count of %d. Some problems will be omitted.",
							nProblems, K_MAX_PROBLEMS);
					problemsPerFile = 1 + K_MAX_PROBLEMS / problemsForFiles.keySet().size();
				} else {
					problemsPerFile = Integer.MAX_VALUE;
				}

				outer:
				for (final IResource resource : resourcesToProcess) {
					if (resource instanceof IFile) {
						final IFile file = (IFile) resource;
						Collection<IValidationProblem<IPath>> problemsForThisFile = problemsForFiles.get(file);
						try {
							file.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
							int problemCounter = 0;

							if (problemsForThisFile.size() > problemsPerFile) {
								addMarker(file, "There are too many validation errors in the workspace to display all of them. Some errors have been omitted.",
										1, 1, IMarker.SEVERITY_WARNING);
								// if we are limiting the display of errors, we need to do the fatal errors
								// first so that we don't display a million warnings and nothing else.
								for (final IValidationProblem<IPath> p : problemsForThisFile) {
									if (p.level().isFatal()) {
										addMarkersForProblem(file, p);
										problemCounter++;
									}
									if (problemCounter >= problemsPerFile) continue outer;
								}
								for (final IValidationProblem<IPath> p : problemsForThisFile) {
									if (!p.level().isFatal()) {
										addMarkersForProblem(file, p);
										problemCounter++;
									}
									if (problemCounter >= problemsPerFile) continue outer;
								}
							} else {
								// we can display all the problems.
								for (final IValidationProblem<IPath> p : problemsForThisFile) {
									addMarkersForProblem(file, p);
								}
							}
						} catch (final Exception ex) {
							NHMUIPlugin.logException(
									String.format("Whilst adding problem markers to %s in %s, an exception occurred. This probably should not happen.\n"+
											"The problems with that file are: " +
											Joiner.on("\n - ").join(problemsForThisFile),
									file.getName(),
									getProject().getName())
									, ex);
						}
					}
					monitor.worked(1);
				}
			}
		};

		ResourcesPlugin.getWorkspace().run(r, getProject(), IWorkspace.AVOID_UPDATE, monitor);

		return null;
	}

	/**
	 * Create the problem markers related to a problem. A problem can put markers in several files, if it is induced from a template.
	 *
	 * @param file
	 * @param p
	 * @param
	 * @throws CoreException
	 */
	private void addMarkersForProblem(final IFile file, final IValidationProblem<IPath> p)
			throws CoreException {
		IMarker sourceMarker = null;
		final Map<String, Object> additionalLocations = new HashMap<String, Object>();
		int i = 0;

		IMarker previousMarker = null;
		String previousHere = null;

		for (final ILocation<IPath> loc : p.locations()) {
			final Optional<IFile> psFile = fileForPath(loc.path());
			final String here = String.valueOf(loc.path() + "/" + loc.type() + "/" + loc.line());
			IMarker thisMarker = null;
			if (loc.type() == LocationType.Source) {
				thisMarker = sourceMarker = addMarker(psFile.or(file), p.message(), loc.line(), loc.offset(), severityOf(p));
			} else if (psFile.isPresent() && p.level().isFatal()) {
				// secondary error through template or include - we should probably only display a few of these
				// in the event that there are a vast number of errors.
				thisMarker = addMarker(psFile.get(), "From " + file.getName() + ": " + p.message(), loc.line(), loc.offset(), IMarker.SEVERITY_INFO);
				additionalLocations.put(MARKER_ADDITIONAL_LINE_PREFIX + "." + i, here);
				i++;
			} else {

			}

			if (thisMarker != null) {
				if (previousMarker != null) {
					thisMarker.setAttribute(
							MARKER_PREVIOUS_MARKER,
							previousHere);
					previousMarker.setAttribute(
							MARKER_NEXT_MARKER,
							here);
				}
				previousMarker = thisMarker;
				previousHere = here;
			}
		}
		if (sourceMarker != null) {
			additionalLocations.putAll(sourceMarker.getAttributes());
			sourceMarker.setAttributes(additionalLocations);
		}
	}

	public static Optional<IFile> fileForPath(final IPath p) {
		try {
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IResource res = workspace.getRoot().findMember(p);
			if (res instanceof IFile) {
				return Optional.of((IFile) res);
			}
			return Optional.absent();

		} catch (final Exception ex) {
		}
		return Optional.absent();
	}

	protected void resourceRemoved(final IResource resource) {
		final Path path = resource.getLocation().toFile().toPath();
		this.dependencies.removeAll(path);
	}

	private Collection<IValidationProblem<IPath>> validate(final NHMNature nature, final INationalHouseholdModel nhm, final IResource resource) {
		if (resource instanceof IFile) {
			final IFile file = (IFile) resource;
			final IPath path = file.getFullPath();

			this.dependencies.removeAll(path);
			try {
				final IValidationResult<IPath> validate = nhm.validate(WorkspaceFS.INSTANCE, path);

				boolean showErrors = validate.isScenario();

				if (!showErrors) {
					for (final IValidationProblem<IPath> p : validate.problems()) {
						if (p.level() == ProblemLevel.SyntacticError) {
							showErrors = true;
							break;
						}
					}
				}

				final Set<IPath> inputs = validate.includes().getInputs(path, true);
				this.dependencies.putAll(path, inputs);

				if (showErrors) {
					nature.updateDefinitions(resource, validate.definitions());
					for (final IPath p : inputs) {
						final Optional<IFile> f = fileForPath(p);
						if (f.isPresent()) {
							nature.updateDefinitions(f.get(), validate.definitions());
						}
					}
					return validate.problems();
				}
			} catch (final RuntimeException re) {
				NHMUIPlugin.logExceptionQuietly("Within-validation error " + path, re);
                final IValidationProblem<IPath> problem =
                    new IValidationProblem<IPath>(){
                        @Override
                        public String message() {
                            return "An un-recoverable error occurred whilst validating this file: " +
                            re.getMessage() +" - debugging information should be in the error log view.";
                        }

                        @Override
                        public ProblemLevel level() {
                            return ProblemLevel.SyntacticError;
                        }

                        @Override
                        public List<ILocation<IPath>> locations() {
                            return Collections.singletonList(sourceLocation());
                        }

                        @Override
                        public ILocation<IPath> sourceLocation() {
                            return new ILocation<IPath>() {
                                public IPath path() {return path;}
                                public int line() {return 1;}
                                public int column() {return 0;}
                                public int offset() {return 0;}
                                public int length() {return 1;}
                                public LocationType type() {
                                    return LocationType.Source;
                                }
                            };
                        }
                    };
				return Collections.singletonList(problem);
			}
		}
		return Collections.emptySet();
	}

	private static int severityOf(final IValidationProblem<IPath> p) {
		switch (p.level()) {
		case SemanticWarning:
			return IMarker.SEVERITY_WARNING;
		default:
			return IMarker.SEVERITY_ERROR;
		}
	}

	protected Collection<IPath> dependencyClosure(final IResource resource) {
		return this.dependents.get(resource.getFullPath());
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		NHMUIPlugin.logInformation("Removing all markers in %s", getProject().getName());
		getProject().deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_INFINITE);
	}
}
