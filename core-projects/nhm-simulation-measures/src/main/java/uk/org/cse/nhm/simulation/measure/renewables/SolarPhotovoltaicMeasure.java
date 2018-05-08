package uk.org.cse.nhm.simulation.measure.renewables;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class SolarPhotovoltaicMeasure extends AbstractMeasure {

    private final ISizingFunction sizing;
    private final IComponentsFunction<Number> capex;
    private final IDimension<ITechnologyModel> techDimension;
    private final IDimension<StructureModel> structureDimension;
    private final ILogEntryHandler log;
    private final ITechnologiesFactory techFactory;
    private final IComponentsFunction<Number> ownUse;

    @AssistedInject
    public SolarPhotovoltaicMeasure(@Assisted("sizing") final ISizingFunction sizing,
            @Assisted("capex") final IComponentsFunction<Number> capex,
            @Assisted("ownUse") final IComponentsFunction<Number> ownUse,
            final IDimension<ITechnologyModel> techDimension, final IDimension<StructureModel> structureDimension,
            final ILogEntryHandler log) {
        this.sizing = sizing;
        this.capex = capex;
        this.ownUse = ownUse;
        this.techDimension = techDimension;
        this.structureDimension = structureDimension;
        this.log = log;

        techFactory = ITechnologiesFactory.eINSTANCE;
    }

    @Override
    public boolean doApply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        final ISizingResult peakPower = sizing.computeSize(scope, lets, Units.KILOWATTS);

        final double ownUseProportion = clampProportion(ownUse.compute(scope, lets).doubleValue(),
                scope.getDwellingID());

        scope.modify(techDimension, new Modifier(peakPower, ownUseProportion, techFactory));

        scope.addNote(peakPower);

        final double cost = capex.compute(scope, lets).doubleValue();
        scope.addTransaction(Payment.capexToMarket(cost));

        scope.addNote(
                new TechnologyInstallationDetails(
                        SolarPhotovoltaicMeasure.this,
                        TechnologyType.solaPhotovoltaic(),
                        peakPower.getSize(),
                        peakPower.getUnits(),
                        cost,
                        0
                )
        );

        return true;
    }

    private double clampProportion(final double ownUseProportion, final int dwellingID) {
        if (ownUseProportion < 0) {
            log.acceptLogEntry(new WarningLogEntry(
                    String.format(
                            "Attempted to install photovoltaic panels with an own use proportion %f, which was less than 0. Using fallback value 0 instead.",
                            ownUseProportion),
                    ImmutableMap.<String, String>builder()
                            .put("measure", SolarPhotovoltaicMeasure.this.getIdentifier().getName())
                            .put("dwellingID", Integer.toString(dwellingID)).put("invalid", Double.toString(ownUseProportion))
                            .put("fallback", Double.toString(0)).build()));
            return 0;

        } else if (ownUseProportion > 1) {
            log.acceptLogEntry(new WarningLogEntry(
                    String.format(
                            "Attempted to install photovoltaic panels with an own use proportion %f, which was greater than 1. Using fallback value 1 instead.",
                            ownUseProportion),
                    ImmutableMap.<String, String>builder()
                            .put("measure", SolarPhotovoltaicMeasure.this.getIdentifier().getName())
                            .put("dwellingID", Integer.toString(dwellingID)).put("invalid", Double.toString(ownUseProportion))
                            .put("fallback", Double.toString(1)).build()));

            return 1;

        } else {
            return ownUseProportion;
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        final StructureModel structureModel = scope.get(structureDimension);
        final ITechnologyModel tech = scope.get(techDimension);

        if (structureModel.hasExternalRoof()) {
            return tech.getSolarPhotovoltaic() == null && structureModel.getRoofConstructionType() != RoofConstructionType.Thatched;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    static class Modifier implements IModifier<ITechnologyModel> {

        private final ISizingResult peakPower;
        private final double ownUseProportion;
        private final ITechnologiesFactory techFactory;

        public Modifier(
                final ISizingResult peakPower,
                final double ownUseProportion,
                final ITechnologiesFactory techFactory
        ) {

            this.peakPower = peakPower;
            this.ownUseProportion = ownUseProportion;
            this.techFactory = techFactory;
        }

        @Override
        public boolean modify(final ITechnologyModel tech) {
            final ISolarPhotovoltaic pv = techFactory.createSolarPhotovoltaic();
            // Convert from kW to W
            pv.setPeakPower(peakPower.getSize() * 1000);
            pv.setOwnUseProportion(ownUseProportion);
            tech.setSolarPhotovoltaic(pv);

            return true;
        }
    }
}
