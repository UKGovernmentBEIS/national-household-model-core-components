package uk.org.cse.nhm.simulator.reset.glazing;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GlazingFrameTypeFunction extends AbstractScopedThingFunction<FrameType, Glazing> {

	@AssistedInject
	GlazingFrameTypeFunction(final ILogEntryHandler log, final IDimension<StructureModel> structureDimension) {
		super(ResetGlazingsAction.GLAZING_LET_KEY, Glazing.class, log, structureDimension);
	}

	@Override
	protected FrameType doCompute(final Glazing wall) {
		return wall.getFrameType();
	}
	
	@Override
	protected FrameType doFail() {
		return null;
	}
}
