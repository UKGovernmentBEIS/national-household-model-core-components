package uk.org.cse.stockimport.hom.impl.steps.imputation;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.BasicAttributesBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.ElevationBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.RoofBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StoreyBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.imputation.ISchemaForImputation;
import uk.org.cse.stockimport.imputation.apertures.doors.DoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.doors.IDoorPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.windows.IWindowPropertyImputer;
import uk.org.cse.stockimport.imputation.apertures.windows.WindowPropertyImputer;
import uk.org.cse.stockimport.imputation.ceilings.CeilingPropertyImputer;
import uk.org.cse.stockimport.imputation.ceilings.ICeilingPropertyImputer;
import uk.org.cse.stockimport.imputation.ceilings.RdSAPCeilingUValues;
import uk.org.cse.stockimport.imputation.floors.FloorPropertyImputer;
import uk.org.cse.stockimport.imputation.floors.IFloorPropertyImputer;
import uk.org.cse.stockimport.imputation.floors.RdSAPFloorPropertyTables;
import uk.org.cse.stockimport.imputation.walls.IWallPropertyImputer;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * @since 1.0
 */
public class MainImputationStep implements ISurveyCaseBuildStep {
    /** @since 1.0 */
    public static final String IDENTIFIER = MainImputationStep.class.getCanonicalName();

	private ICeilingPropertyImputer ceilings = new CeilingPropertyImputer(new RdSAPCeilingUValues());
	private IFloorPropertyImputer floors = new FloorPropertyImputer(new RdSAPFloorPropertyTables());
	private IWallPropertyImputer walls = new WallPropertyImputer();
	private IDoorPropertyImputer doors = new DoorPropertyImputer();
	private IWindowPropertyImputer windows = new WindowPropertyImputer();
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return ImmutableSet.of(
                BasicAttributesBuildStep.IDENTIFIER,
                ElevationBuildStep.IDENTIFIER,
                RoofBuildStep.IDENTIFIER,
				StructureInitializingBuildStep.IDENTIFIER, 
				StoreyBuildStep.IDENTIFIER);
	}
	
	public MainImputationStep(){
		
	}

	public MainImputationStep(final ISchemaForImputation schemaForImputation){
		this.ceilings = new CeilingPropertyImputer(schemaForImputation.getCeilingUValueTables());
		this.floors = new FloorPropertyImputer(schemaForImputation.getFloorPropertyTables());
		this.walls = new WallPropertyImputer(schemaForImputation.getWallPropertyTables());
		this.doors = schemaForImputation.getDoorPropertyImputer();
		this.windows = new WindowPropertyImputer(schemaForImputation.getWindowPropertyTables());
	}

	@Override
    /**
     * @assumption The thickness used for floor u value calculation should be that of the thickest wall.
     * @assumption If the ground floor is insulated, exposed floors will be too. otherwise, they will not be.
     * @assumption Floor air change rate is zero for internal floors.
     */
	public void build(final SurveyCase sc, final IHouseCaseSources<IBasicDTO> dtos) {
		final StructureModel sm = sc.getStructure();
		final HouseCaseDTO caseDTO = dtos.requireOne(HouseCaseDTO.class);
		
		SAPAgeBandValue ageBand = SAPAgeBandValue.fromYear(caseDTO.getBuildYear(),
                                                           caseDTO.getRegionType(),
                                                           SAPAgeBandValue.Band.K);

		final FloorConstructionType groundFloorConstructionType = sm.getGroundFloorConstructionType();
		final RoofConstructionType roofConstructionType = sm.getRoofConstructionType();
		final double roofInsulationThickness = sm.getRoofInsulationThickness();
		final double floorInsulationThickness = sm.getFloorInsulationThickness();
		
		sm.setInternalWallKValue(walls.getInternalWallKValue());
		
		double areaBelow = 0;
		for (final Storey storey : sm.getStoreys()) {
			// impute wall properties
			/**
			 * This holds the thickness of the thickest wall in all the walls.
			 */
			double maxWallThickness = 0;
			for (final IMutableWall wall : storey.getWalls()) {
				final WallConstructionType wallConstructionType = wall.getWallConstructionType();
				
				final double uValue = walls.getUValue(ageBand.getName(), caseDTO.getRegionType(), 
						wallConstructionType, wall.getWallInsulationTypes());
				final double kValue = walls.getKValue(wallConstructionType, 
						wall.getWallInsulationTypes());
			
				wall.setUValue(uValue);
				wall.setKValue(kValue);
				wall.setAirChangeRate(walls.getAirChangeRate(wallConstructionType));
				if (wallConstructionType.getWallType() == WallType.External) {
					maxWallThickness = Math.max(maxWallThickness,
							walls.getWallThickness(ageBand.getName(), caseDTO.getRegionType(), 
									wallConstructionType, wall.getWallInsulationTypes()));
				}
			}
			
			final FloorLocationType location = storey.getFloorLocationType();
			final boolean isInContactWithGround = 
					location == FloorLocationType.BASEMENT || location == FloorLocationType.GROUND;
			
			final double lowerUValue;
			final double lowerKValue;

			if (isInContactWithGround) {
				// we work out the area of this floor that is actually in contact with the ground
				// by subtracting the area below
				final double contactArea = Math.max(0, storey.getArea() - areaBelow);
				lowerUValue = floors.getGroundFloorUValue(
						groundFloorConstructionType, 
						maxWallThickness, 
						floorInsulationThickness, 
						storey.getExposedPerimeter(), 
						contactArea);
				
				lowerKValue = floors.getGroundFloorKValue(groundFloorConstructionType);
				storey.setFloorAirChangeRate(floors.getFloorInfiltration(ageBand.getName(), groundFloorConstructionType));
			} else {
				lowerUValue = floors.getExposedFloorUValue(ageBand.getName(), floorInsulationThickness > 0);
				lowerKValue = floors.getExposedFloorKValue(floorInsulationThickness > 0);
				storey.setFloorAirChangeRate(0);
			}
			
			storey.setFloorKValue(lowerKValue);
			storey.setFloorUValue(lowerUValue);
			storey.setPartyFloorKValue(floors.getPartyFloorKValue());
			
			// impute ceiling properties using the global information about the top of the house.
			//TODO should we infer something about flat roofs on additional modules differently from the top floor?
			storey.setPartyCeilingKValue(ceilings.getPartyCeilingKValue());
			storey.setCeilingKValue(ceilings.getRoofKValue(roofConstructionType));
			if (roofConstructionType == RoofConstructionType.Flat) {
				storey.setCeilingUValue(ceilings.getRoofUValue(ageBand.getName(), roofConstructionType, location == FloorLocationType.ROOM_IN_ROOF));
			} else {
				storey.setCeilingUValue(ceilings.getRoofUValue(roofConstructionType, 
						roofInsulationThickness, 
						location == FloorLocationType.ROOM_IN_ROOF));
			}
			
			areaBelow = storey.getArea();
		}
		
		// impute window and door properties.
		
		for (final Elevation e : sm.getElevations().values()) {
			for (final Glazing g : e.getGlazings()) {
				final FrameType ft = g.getFrameType();
				final GlazingType gt = g.getGlazingType();
				final WindowInsulationType it = g.getInsulationType();
				g.setFrameFactor(windows.getFrameFactor(ageBand, ft, gt, it));
				g.setGainsTransmissionFactor(windows.getGainsTransmittance(ageBand, ft, gt, it));
				g.setLightTransmissionFactor(windows.getLightTransmittance(ageBand, ft, gt, it));
				g.setUValue(windows.getUValue(ageBand, ft, gt, it));
			}
			
			for (final Door d : e.getDoors()) {
				final DoorType doorType = d.getDoorType();
				final FrameType ft = d.getFrameType();
				final GlazingType gt = d.getGlazingType();
				d.setArea(doors.getArea(doorType));
				d.setuValue(doors.getUValue(ageBand.getName(), doorType));
				d.setFrameFactor(windows.getFrameFactor(ageBand, ft, gt, WindowInsulationType.Air));
				d.setGainsTransmissionFactor(windows.getGainsTransmittance(ageBand, ft, gt, WindowInsulationType.Air));
				d.setLightTransmissionFactor(windows.getLightTransmittance(ageBand, ft, gt, WindowInsulationType.Air));
			}
		}
	}
}
