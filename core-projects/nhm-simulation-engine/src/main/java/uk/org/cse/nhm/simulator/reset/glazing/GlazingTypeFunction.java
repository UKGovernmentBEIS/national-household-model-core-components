package uk.org.cse.nhm.simulator.reset.glazing;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GlazingTypeFunction extends AbstractScopedThingFunction<GlazingType, Glazing> {
	@AssistedInject
	GlazingTypeFunction(final ILogEntryHandler log, final IDimension<StructureModel> structureDimension) {
		super(ResetGlazingsAction.GLAZING_LET_KEY, Glazing.class, log, structureDimension);
	}

	@Override
	protected GlazingType doFail() {
		return null;
	}

	@Override
	protected GlazingType doCompute(final Glazing wall) {
		return wall.getGlazingType();
	}
}
