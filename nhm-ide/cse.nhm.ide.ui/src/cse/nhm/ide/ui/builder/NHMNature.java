package cse.nhm.ide.ui.builder;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import cse.nhm.ide.ui.NHMUIPlugin;
import cse.nhm.ide.ui.PluginPreferences.VersionPreference;
import cse.nhm.ide.ui.models.ServiceTrackingModel;
import uk.org.cse.nhm.bundle.api.IArgument;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.ILocation;

public class NHMNature implements IProjectNature {
	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = "cse.nhm.ide.ui.nhmNature";

	private IProject project;

	final Multimap<IResource, Defn> defns = HashMultimap.create();
	
	static class Defn implements IDefinition<IPath> {
		final IDefinition<IPath> delegate;

		protected Defn(final IDefinition<IPath> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		public List<ILocation<IPath>> locations() {
			return this.delegate.locations();
		}

		@Override
		public ILocation<IPath> sourceLocation() {
			return this.delegate.sourceLocation();
		}

		@Override
		public Set<IArgument> arguments() {
			return this.delegate.arguments();
		}

		@Override
		public String name() {
			return this.delegate.name();
		}

		@Override
		public DefinitionType type() {
			return this.delegate.type();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if (obj instanceof IDefinition) {
				final IDefinition<?> oth = (IDefinition<?>) obj;
				return oth.type() == type() && oth.name().equals(name());
			}
			return false;
		}
		
		@Override
		public String toString() {
			return name();
		}
		
		@Override
		public int hashCode() {
			return name().hashCode();
		}
	}
		
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	@Override
	public void configure() throws CoreException {
		final IProjectDescription desc = project.getDescription();
		configureProject(desc);
		this.project.setDescription(desc, null);
	}

	public static void configureProject(final IProjectDescription desc) throws CoreException {
		final ICommand[] commands = desc.getBuildSpec();

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(NHMBuilder.BUILDER_ID)) {
				return;
			}
		}

		final ICommand[] newCommands = new ICommand[commands.length + 1];
		System.arraycopy(commands, 0, newCommands, 0, commands.length);
		final ICommand command = desc.newCommand();
		command.setBuilderName(NHMBuilder.BUILDER_ID);
		newCommands[newCommands.length - 1] = command;
		desc.setBuildSpec(newCommands);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	@Override
	public void deconfigure() throws CoreException {
		final IProjectDescription description = getProject().getDescription();
		final ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(NHMBuilder.BUILDER_ID)) {
				final ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
						commands.length - i - 1);
				description.setBuildSpec(newCommands);
				this.project.setDescription(description, null);			
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	@Override
	public IProject getProject() {
		return this.project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	@Override
	public void setProject(final IProject project) {
		this.project = project;
	}
	
	private void revalidate() {
		new RevalidateJob(project).schedule();
	}
	
	public VersionPreference getModelVersion() {
		return VersionPreference.forProject(this.project);
	}
	
	private ServiceTrackingModel model;

	private HashMultimap<IPath, IPath> dependencies = HashMultimap.create();
	
	public ServiceTrackingModel getModel() {
		if (model == null) {
			model = new ServiceTrackingModel(
					NHMUIPlugin.getDefault().getBundle().getBundleContext(), 
					getModelVersion());
			
			model.addListener(new ServiceTrackingModel.IListener() {
				@Override
				public void modelChanged() {
					revalidate();
				}
			});
			
			model.startTracking();
		}
		
		model.setVersion(getModelVersion());
		
		return model;
	}
	
	public void updateDefinitions(final IResource resource, final Set<IDefinition<IPath>> definitions) {
		this.defns.removeAll(resource);
		
		for (final IDefinition<IPath> d : definitions) {
			this.defns.put(resource, new Defn(d));
		}
	}

	public Set<? extends IDefinition<IPath>> getDefinitions(final IResource resource, final IProgressMonitor monitor) {
		return ImmutableSet.copyOf(this.defns.get(resource));
	}
	
	public static void addNatureTo(final IProject project) throws CoreException {
		final IProjectDescription description = project.getDescription();
		final String[] natures = description.getNatureIds();

		for (int i = 0; i < natures.length; ++i) {
			if (NHMNature.NATURE_ID.equals(natures[i])) {
				return;
			}
		}

		// Add the nature
		final String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = NHMNature.NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);
	}
	
	public static Optional<NHMNature> of(final IProject project) {
		// add nature when asking for it
		
		try {
			addNatureTo(project);
			return Optional.fromNullable((NHMNature) project.getNature(NATURE_ID));
		} catch (final CoreException e) {
			return Optional.absent();
		}
	}
	
	public Set<? extends IDefinition<IPath>> getAllDefinitions(final IProgressMonitor monitor) {
		return ImmutableSet.copyOf(this.defns.values());
	}

	public void setDependencies(Multimap<IPath, IPath> dependencies) {
		this.dependencies.clear();
		this.dependencies.putAll(dependencies);
	}
	
	public Set<IPath> getDependencies(final IPath path) {
		return this.dependencies.get(path);
	}
}
