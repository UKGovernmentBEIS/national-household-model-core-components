package uk.org.cse.stockimport.hom.impl.steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.directory.BasicAttributes;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * StoreyBuilderStep.
 *
 * @author richardt
 * @version $Id: StoreyBuilderStep.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StoreyBuildStep implements ISurveyCaseBuildStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreyBuildStep.class);

    /** @since 1.0*/
    public static final String IDENTIFIER = StoreyBuildStep.class.getCanonicalName();
    private static final double TEN = 10.00;
    
    private final int floorPolygonScalingFactor = 100;

    private static final WallConstructionType DEFAULT_PARTYWALL = WallConstructionType.Party_Cavity;

    /**
     * @assumption All cavity wall insulation is 50mm thick
     */
	private static final double DEFAULT_CAVITY_INSULATION_THICKNESS = 50;

	 /**
     * @assumption All external wall insulation is 50mm thick.
     */
	private static final double DEFAULT_EXTERNAL_INSULATION_THICKNESS = 50;

	 /**
     * @assumption All internal wall insulation is 50mm thick.
     */
	private static final double DEFAULT_INTERNAL_INSULATION_THICKNESS = 50;

    /**
     * @return
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getDependencies()
     */
    @Override
    public Set<String> getDependencies() {
        return ImmutableSet.of(
                BasicAttributesBuildStep.IDENTIFIER,
                StructureInitializingBuildStep.IDENTIFIER);
    }

    /**
     * 1. Assumes that {@link SurveyCase} is not null object, and has {@link BasicAttributes} set with an AACode
     * 
     * @param model
     * @param dtoProvider
     * @see uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     *      uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final List<IStoreyDTO> storeyDTOs = new ArrayList<>(dtoProvider.getAll(IStoreyDTO.class));
        final List<IElevationDTO> elevationDTOs = dtoProvider.getAll(IElevationDTO.class);

        final StructureModel structure = model.getStructure();

        Collections.sort(storeyDTOs, StoreyDTO.StoreyLocationTypeComparator);
        Storey storey;
        for (final IStoreyDTO dto : storeyDTOs) {
            storey = new Storey();
            storey.setFloorLocationType(dto.getLocationType());
            storey.setHeight(dto.getStoreyHeight());
            storey.setPerimeter(dto.getPolygon().toSillyPolygon(), floorPolygonScalingFactor);

            buildExternalWallConstructionType(storey.getWalls(), elevationDTOs);
            final double areaBefore = storey.getArea();
            buildAttachedWalls(storey.getWalls(), elevationDTOs);

            
            if (Precision.equals(areaBefore, storey.getArea(), 0.01) == false) {
                throw new RuntimeException("area altered {aacode:" + dto.getAacode() + ",storey:"
                        + dto.getLocationType() + ",areaBefore:" + areaBefore + ",areaAfter:" + storey.getArea()
                        + "}");
            }

            if (storey.getArea() <= 0) {
                throw new RuntimeException(String.format("Area was zero for aacode:%s,storey", dto.getAacode(), dto.getLocationType()));
            }
            
            structure.addStorey(storey);
        }
    }

    /**
     * <p>Takes the elevation for each wall, finds the current construction type set for that elevation and sets the
     * walls construction type to this.</p>
     * 
     * @param walls
     * @param elevationDTOs
     * @since 0.0.1-SNAPSHOT
     */
    protected void buildExternalWallConstructionType(final Iterable<IMutableWall> walls, final List<IElevationDTO> elevationDTOs) {
        final Map<ElevationType, IElevationDTO> elevationDTOMap = new HashMap<ElevationType, IElevationDTO>();
        
        for (final IElevationDTO elevation : elevationDTOs) {
            elevationDTOMap.put(elevation.getElevationType(), elevation);
        }

        for (final IMutableWall wall : walls) {
        	final IElevationDTO elevationDTO = elevationDTOMap.get(wall.getElevationType());
        	if (elevationDTO == null) {
        		throw new IllegalArgumentException("No " + wall.getElevationType() + "(" + elevationDTOs + ")");
        	}
			wall.setWallConstructionType(elevationDTO.getExternalWallConstructionType().or(DEFAULT_PARTYWALL));
			if (elevationDTO.getCavityInsulation().or(false)) {
				tryInsulatingWall(wall, WallInsulationType.FilledCavity, DEFAULT_CAVITY_INSULATION_THICKNESS);
			}
			if (elevationDTO.getExternalInsulation().or(false)) {
				tryInsulatingWall(wall, WallInsulationType.External, DEFAULT_EXTERNAL_INSULATION_THICKNESS);
			}
			if (elevationDTO.getInternalInsulation().or(false)) {
				tryInsulatingWall(wall, WallInsulationType.Internal, DEFAULT_INTERNAL_INSULATION_THICKNESS);
			}
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("buildExternalWallConstructionType() completed");
        }
    }

	protected void tryInsulatingWall(final IMutableWall wall, final WallInsulationType type, final double insulationThickness) {
		if (wall.getWallConstructionType().isAllowedInsulationType(type)) {
			wall.setWallInsulationThicknessAndAddOrRemoveInsulation(type, insulationThickness);
		} else {
			LOGGER.warn("a {} wall has suggested insulation {}, which cannot go on that kind of wall", 
						wall.getWallConstructionType(), type);
		}
	}
	
	/**
     * <p>Adjust attached wall lengths, currently sets Party wall/attached construction type to
     * {@link StoreyBuildStep#DEFAULT_PARTYWALL}.</p>
     * 
     * @param walls
     * @param elevationDTOs
     * @since 0.0.1-SNAPSHOT
     */
    protected void buildAttachedWalls(final Iterable<IMutableWall> walls, final List<IElevationDTO> elevationDTOs) {
        final Map<ElevationType, Double> elevationMap = new HashMap<ElevationType, Double>();
        for (final IElevationDTO elevation : elevationDTOs) {
            elevationMap.put(elevation.getElevationType(), elevation.getTenthsAttached());
        }

        final Iterator<IMutableWall> wallsItr = walls.iterator();
        IMutableWall wall;
        while (wallsItr.hasNext()) {
            wall = wallsItr.next();
            
			final double partyWallProportion = elevationMap.get(wall.getElevationType());
			if (partyWallProportion > 0) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("buildAttachedWalls(): creating party wall for elevation:{}", wall.getElevationType());
				}
				if (partyWallProportion < TEN) {
					wall.split(partyWallProportion / TEN);
					wall.setWallConstructionType(
							wall.getWallConstructionType().getPartyWallEquivalent());

					for (final WallInsulationType wit : WallInsulationType.values()) {
						wall.setWallInsulationThicknessAndAddOrRemoveInsulation(wit, 0);
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("buildAttachedWalls(): wall length:{},type:" + wall.getWallConstructionType(), wall.getLength());
					}
					wall = wallsItr.next(); // skip the next wall, because it's
											// the unattached half of the one we
											// just did.
				} else {
					wall.setWallConstructionType(wall.getWallConstructionType().getPartyWallEquivalent());
					for (final WallInsulationType wit : WallInsulationType.values()) {
						if (wall.getWallInsulationThickness(wit) > 0) {
							LOGGER.warn("a party wall was specified with insulation type {}; this insulation will be ignored", 
										wit);
						}
						wall.setWallInsulationThicknessAndAddOrRemoveInsulation(wit, 0);
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("buildAttachedWalls(): wall length:{},type:" + wall.getWallConstructionType().getPartyWallEquivalent(), wall.getLength());
					}
				}
			}
		}

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("buildAttachedWalls() completed");
        }
    }
}
