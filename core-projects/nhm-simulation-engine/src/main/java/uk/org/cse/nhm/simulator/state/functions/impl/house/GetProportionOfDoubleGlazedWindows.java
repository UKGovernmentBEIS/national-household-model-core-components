package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * GetProportionOfDoubleGlazedWindows.
 *
 * @author richardTiffin
 */
public class GetProportionOfDoubleGlazedWindows extends StructureFunction<Double> {

    private final ILogEntryHandler handler;
    private final IDimension<BasicCaseAttributes> basicAttributes;

    /**
     * @param structure
     */
    @Inject
    protected GetProportionOfDoubleGlazedWindows(final IDimension<StructureModel> structure,
            final ILogEntryHandler handler, final IDimension<BasicCaseAttributes> basicAttributes) {
        super(structure);
        this.handler = handler;
        this.basicAttributes = basicAttributes;
    }

    /**
     * Each elevation should have just one window of a given type, meaning the proportion of windows of that type can be
     * taken from the elevation.
     *
     * @param scope
     * @param lets
     * @return
     * @see uk.org.cse.nhm.simulator.state.functions.IComponentsFunction#compute(uk.org.cse.nhm.simulator.scope.IComponentsScope,
     *      uk.org.cse.nhm.simulator.let.ILets)
     */
    @Override
    public Double compute(IComponentsScope scope, ILets lets) {
        return getProportionDoubleGlazed(getStructure(scope).getElevations(), scope.get(basicAttributes).getAacode());
    }

    protected Double getProportionDoubleGlazed(Map<ElevationType, Elevation> elevations, String aacode) {
        Elevation elevation;
        double doubleGlazedProportion = 0d;

        for (ElevationType elevationType : ElevationType.values()) {
            elevation = elevations.get(elevationType);

            if (elevation == null) {
                handler.acceptLogEntry(
                        new WarningLogEntry(
                                "No elevation exists for elevationType",
                                ImmutableMap.of("aacode", "",
                                        "elevation", elevationType.name())));
            } else {
                for (Glazing glazing : elevation.getGlazings()) {
                    if (glazing.getGlazingType().equals(GlazingType.Double)
                            || glazing.getGlazingType().equals(GlazingType.Triple)) {
                        doubleGlazedProportion += glazing.getGlazedProportion();
                    }
                }
            }
        }

        return (doubleGlazedProportion > 0 ?
                doubleGlazedProportion / ElevationType.values().length : doubleGlazedProportion);
    }
}
