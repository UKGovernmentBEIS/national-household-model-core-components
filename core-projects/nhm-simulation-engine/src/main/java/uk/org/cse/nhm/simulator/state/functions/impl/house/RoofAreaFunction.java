package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class RoofAreaFunction extends StructureFunction<Double> {

    private static final double pitchCorrectionFactor = Math.cos(
            Math.toRadians(35));

    private final boolean pitchCorrection;

    @AssistedInject
    public RoofAreaFunction(
            final IDimension<StructureModel> structure,
            @Assisted final boolean pitchCorrection
    ) {
        super(structure);
        this.pitchCorrection = pitchCorrection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final StructureModel structure = getStructure(scope);

        if (pitchCorrection && structure.getRoofConstructionType().isPitched()) {
            return structure.getExternalRoofArea() / pitchCorrectionFactor;
        } else {
            return structure.getExternalRoofArea();
        }
    }

}
