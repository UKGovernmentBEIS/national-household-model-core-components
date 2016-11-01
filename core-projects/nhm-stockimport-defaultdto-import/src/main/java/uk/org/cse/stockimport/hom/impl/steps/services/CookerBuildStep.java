package uk.org.cse.stockimport.hom.impl.steps.services;

import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl;
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
		
		final ICooker cooker; 

		if (hasGas) {
			log.debug("{} installing gas hob and electric oven", dtoProvider.getAacode());
			cooker = CookerImpl.createMixed();
			
		} else {
			log.debug("{} installing electric hob and electric oven", dtoProvider.getAacode());
			cooker = CookerImpl.createElectric();
		}
		
		model.getTechnologies().getCookers().add(cooker);
	}

}
