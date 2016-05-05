package uk.org.cse.nhm.language.visit;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class SinglyVisitingVisitor<T extends IVisitable<T>> implements IVisitor<T> {
	private final Class<T> clazz;
	
	private final Set<T> instances = Collections.newSetFromMap(new IdentityHashMap<T, Boolean>());
	
	public SinglyVisitingVisitor(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public boolean enter(T v) {
		if (clazz.isInstance(v)) {
			if (instances.contains(v)) return false;
			instances.add(v);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void visit(T v) {
		
	}

	@Override
	public void leave(T v) {
		
	}
	
	public Set<T> getVisitedInstances() {
		return instances;
	}
}
