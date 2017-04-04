package uk.org.cse.nhm.simulator.state.dimensions.technology;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.EObjectWrapper;
import uk.org.cse.nhm.simulator.state.dimensions.impl.IDimensionManager;

public class TechnologyManager implements IDimensionManager<ITechnologyModel> {
	private final Interner<EObjectWrapper<ITechnologyModel>> intern = Interners.newWeakInterner();
	
	@Override
	public ITechnologyModel copy(final ITechnologyModel instance) {
		final ITechnologyModel copy = EcoreUtil.copy(instance);
		return copy;
	}

	@Override
	public ITechnologyModel internalise(final ITechnologyModel instance) {
		final EObjectWrapper<ITechnologyModel> wrapper = new EObjectWrapper<ITechnologyModel>(instance);
		final ITechnologyModel result = intern.intern(wrapper).unwrap();
		return result;
	}

	@Override
	public boolean isEqual(final ITechnologyModel first, final ITechnologyModel second) {
		return EcoreUtil.equals(first, second);
	}

	@Override
	public ITechnologyModel getDefaultValue() {
		return null;
	}
}
