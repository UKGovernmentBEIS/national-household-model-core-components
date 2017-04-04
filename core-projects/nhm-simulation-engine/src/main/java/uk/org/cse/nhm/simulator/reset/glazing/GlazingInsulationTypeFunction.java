package uk.org.cse.nhm.simulator.reset.glazing;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GlazingInsulationTypeFunction extends AbstractScopedThingFunction<WindowInsulationType, Glazing> {
	@AssistedInject
	GlazingInsulationTypeFunction(final ILogEntryHandler log, final IDimension<StructureModel> structureDimension) {
		super(ResetGlazingsAction.GLAZING_LET_KEY, Glazing.class, log, structureDimension);
	}

	@Override
	protected WindowInsulationType doFail() {
		return null;
	}

	@Override
	protected WindowInsulationType doCompute(final Glazing wall) {
		return wall.getInsulationType();
	}
}
