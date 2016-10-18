package uk.org.cse.stockimport.hom.impl.steps.services;

import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * @since 1.0
 */
public class CookerBuildStep implements ISurveyCaseBuildStep {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(CookerBuildStep.class);
	/** @since 1.0 */
	public static final String IDENTIFIER = CookerBuildStep.class.getCanonicalName();
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return ImmutableSet.<String>of(StructureInitializingBuildStep.IDENTIFIER);
	}

	@Override
	public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
		final Optional<ISpaceHeatingDTO> space = dtoProvider.getOne(ISpaceHeatingDTO.class);
		final Optional<IWaterHeatingDTO> water = dtoProvider.getOne(IWaterHeatingDTO.class);
		
		final boolean hasGas = 
				(space.isPresent() && space.get().getMainHeatingFuel() == FuelType.MAINS_GAS) ||
				(water.isPresent() && water.get().getMainHeatingFuel().or(FuelType.BIOMASS_PELLETS) == FuelType.MAINS_GAS);
		
		final ICooker electricOven = ITechnologiesFactory.eINSTANCE.createCooker();
		electricOven.setFuelType(FuelType.ELECTRICITY);
		electricOven.setBaseLoad(ICooker.ELECTRIC_BASE_LOAD);
		electricOven.setOccupancyFactor(ICooker.ELECTRIC_OCCUPANCY_FACTOR);
		electricOven.setOven(true);
		
		if (hasGas) {
			log.debug("{} installing gas hob and electric oven", dtoProvider.getAacode());
			final ICooker hob = ITechnologiesFactory.eINSTANCE.createCooker();
			hob.setHob(true);
			hob.setFuelType(FuelType.MAINS_GAS);
			
			hob.setBaseLoad(ICooker.GAS_BASE_LOAD);
			hob.setOccupancyFactor(ICooker.GAS_OCCUPANCY_FACTOR);
			hob.setGainsFactor(ICooker.GAS_GAINS_FACTOR);
			model.getTechnologies().getCookers().add(hob);
		} else {
			log.debug("{} installing electric hob and electric oven", dtoProvider.getAacode());
			electricOven.setHob(true);
		}
		
		model.getTechnologies().getCookers().add(electricOven);
	}

}
