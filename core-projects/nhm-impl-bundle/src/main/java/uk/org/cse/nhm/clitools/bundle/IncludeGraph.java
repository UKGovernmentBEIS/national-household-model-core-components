package uk.org.cse.nhm.clitools.bundle;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import uk.org.cse.nhm.bundle.api.IIncludeGraph;

public class IncludeGraph<P> implements IIncludeGraph<P> {
	private SetMultimap<P, P> dependencies;
	private SetMultimap<P, P> dependents;

	public IncludeGraph(SetMultimap<P, P> dependencies) {
		this.dependencies = dependencies;
		this.dependents = HashMultimap.create();
		Multimaps.invertFrom(dependencies, dependents);
	}

	static <Q> Set<Q> get(final SetMultimap<Q, Q> mm, Q root, boolean transitive) {
		if (transitive) {
			final HashSet<Q> out = new HashSet<>();
			final Deque<Q> proc = new LinkedList<>();
			proc.add(root);
			while (!proc.isEmpty()) {
				final Q todo = proc.pop();
				for (final Q p : get(mm, todo, false)) {	
					if (out.contains(p)) continue;
					out.add(p);
					proc.add(p);
				}
			}
			return out;
		} else {
			return mm.get(root);
		}
	}
	
	@Override
	public Set<P> getInputs(P scenario, boolean transitive) {
		return get(dependencies, scenario, transitive);
	}

	@Override
	public Set<P> getOutputs(P scenario, boolean transitive) {
		return get(dependents, scenario, transitive);
	}
}
