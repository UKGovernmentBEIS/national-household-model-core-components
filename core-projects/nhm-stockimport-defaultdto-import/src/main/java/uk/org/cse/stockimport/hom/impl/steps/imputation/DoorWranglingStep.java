package uk.org.cse.stockimport.hom.impl.steps.imputation;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * A step which wrangles doors to match the CHM's expectations for doors by
 * shrinking them.
 *
 * TODO: maybe we should throw away some doors instead of making them all really
 * small? It has a similar impact though.
 *
 * @author hinton
 * @since 1.0
 */
public class DoorWranglingStep implements ISurveyCaseBuildStep {

    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = DoorWranglingStep.class.getCanonicalName();

    private static final Logger log = LoggerFactory.getLogger(DoorWranglingStep.class);

    // At least 50% of the openings must be windows.
    private double maximumOpeningDoorAreaProportion = 0.5;

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<String> getDependencies() {
        return ImmutableSet.of(MainImputationStep.IDENTIFIER);
    }

    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        for (final Map.Entry<ElevationType, Elevation> e : model.getStructure().getElevations().entrySet()) {
            double externalAreaInElevation = 0;
            for (final Storey s : model.getStructure().getStoreys()) {
                for (final IMutableWall wall : s.getWalls()) {
                    if (wall.getElevationType() == e.getKey()
                            && wall.getWallConstructionType().getWallType() == WallType.External) {
                        externalAreaInElevation += wall.getArea();
                    }
                }
            }

            final double openAreaInElevation = externalAreaInElevation * e.getValue().getOpeningProportion();

            double totalDoorArea = 0;
            for (final Door door : e.getValue().getDoors()) {
                totalDoorArea += door.getArea();
            }

            if (totalDoorArea > maximumOpeningDoorAreaProportion * openAreaInElevation) {
                final double requiredDoorArea = maximumOpeningDoorAreaProportion * openAreaInElevation;
                final double scalingFactor = requiredDoorArea / totalDoorArea;

                log.debug("Doors for {} require wranging, with a scaling factor of {}", dtoProvider.getAacode(), scalingFactor);
                for (final Door door : e.getValue().getDoors()) {
                    door.setArea(door.getArea() * scalingFactor);
                }
            }
        }
    }
}
