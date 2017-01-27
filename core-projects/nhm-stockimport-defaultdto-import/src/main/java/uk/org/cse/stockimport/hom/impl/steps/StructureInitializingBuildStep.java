package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.imputation.house.IHousePropertyImputer;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Initializes the {@link StructureModel} and the {@link ITechnologyModel} in a {@link SurveyCase}.
 *
 * Doesn't do any inference or anything like that.
 *
 * @author hinton
 * @since 1.0
 */
public class StructureInitializingBuildStep implements ISurveyCaseBuildStep {
	/** @since 1.0 */
    public static final String IDENTIFIER = StructureInitializingBuildStep.class.getCanonicalName();

	private static final Logger log = LoggerFactory.getLogger(StructureInitializingBuildStep.class);

	private static final double DEFAULT_DRAUGHT_STRIPPED_PROPORTION = 0;

	/**
	 * The latest SAP age band in which you are assumed to have an unsealed floor, if you have a suspended timber floor.
	 */
	private final SAPAgeBandValue.Band lastAgeBandForUnsealed = SAPAgeBandValue.Band.E;

    private IHousePropertyImputer housePropertyImputer;

    /**
     * @since 1.0
     */
    public StructureInitializingBuildStep() {
        super();
    }

    /**
     * @since 1.0
     */
    public StructureInitializingBuildStep(final IHousePropertyImputer housePropertyImputer) {
        super();
        this.housePropertyImputer = housePropertyImputer;
    }

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final IHouseCaseDTO dto = dtoProvider.requireOne(HouseCaseDTO.class);

        final StructureModel structure = new StructureModel(dto.getBuiltFormType());
        structure.setGroundFloorConstructionType(
        		convertGroundFloorType(
        			dto.getFloorConstructionType(),
        			dto.getBuildYear(),
        			dto.getRegionType()
        		)
    		);
        structure.setLivingAreaProportionOfFloorArea(getLivingAreaFaction(dto));
        structure.setHasDraughtLobby(dto.isHasDraftLoby());

        structure.setFrontPlotDepth(dto.getFrontPlotDepth());
        structure.setFrontPlotWidth(dto.getFrontPlotWidth());
        structure.setBackPlotDepth(dto.getBackPlotDepth());
        structure.setBackPlotWidth(dto.getBackPlotWidth());

        structure.setOnGasGrid(dto.isOnGasGrid());
        structure.setNumberOfBedrooms(dto.getNumberOfBedrooms());
        structure.setHasAccessToOutsideSpace(dto.isHasAccessToOutsideSpace());
        structure.setOwnsPartOfRoof(dto.isOwnsPartOfRoof());

        structure.setMainFloorLevel(dto.getMainFloorLevel());
        structure.setZoneTwoHeatedProportion(1);
        structure.setThermalBridigingCoefficient(0.15);
        structure.setReducedInternalGains(false);

        final Optional<IVentilationDTO> ventilation = dtoProvider.getOne(IVentilationDTO.class);
        if (ventilation.isPresent()) {
        	structure.setDraughtStrippedProportion(ventilation.get().getWindowsAndDoorsDraughtStrippedProportion());
        } else {
        	log.warn("{} Could not find ventilation DTO", dtoProvider.getAacode());
        	structure.setDraughtStrippedProportion(DEFAULT_DRAUGHT_STRIPPED_PROPORTION);
        }

		model.setStructure(structure);

		final ITechnologyModel tech = ITechnologiesFactory.eINSTANCE.createTechnologyModel();
		model.setTechnologies(tech);
	}

    /**
     * 1. If living area faction returned from DTO is not null, returns this value otherwise, uses number of rooms and a
     * lookup to get living area faction.
     *
     * @param dto
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    protected double getLivingAreaFaction(final IHouseCaseDTO dto) {
        if (dto.getLivingAreaFaction() > 0) {
            return dto.getLivingAreaFaction();
        }

        return this.housePropertyImputer.getLivingAreaFraction(dto.getNumOfHabitalRooms());
    }

    /**
     * Categorizes SuspendedTimber floors as sealed or unsealed based on the SAP age band of the dwelling.
     *
     * @param dtoFloorType
     * @param regionType
     */
    protected FloorConstructionType convertGroundFloorType(
    		final DTOFloorConstructionType dtoFloorType,
    		final int buildYear,
    		final RegionType region
		) {
    	switch (dtoFloorType) {
    	case Solid:
    		return FloorConstructionType.Solid;
    	case SuspendedTimber:
    		final SAPAgeBandValue band = SAPAgeBandValue.fromYear(buildYear, region);
        	return band.getName().after(lastAgeBandForUnsealed) ? FloorConstructionType.SuspendedTimberSealed : FloorConstructionType.SuspendedTimberUnsealed;
    	default:
    		throw new IllegalArgumentException("Unknown floor construction type " + dtoFloorType);
    	}
    }
}
