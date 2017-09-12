package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.hom.impl.steps.services.SpaceHeatingBuildStep;

/**
 * Helpers {@link SpaceHeatingBuildStep} construct storage heaters.
 * @author hinton
 * @since 1.0
 */
public class StorageHeaterBuilder implements IStorageHeaterBuilder {
	private static final ITechnologiesFactory T = ITechnologiesFactory.eINSTANCE;
	
	@Override
    /**
     * @assumption If storage heater type is unknown, OLD_LARGE_VOLUME is presumed.
     * @assumption Fan storage heaters have celect charge control, others have manual, unless specified by the dto.
     */
	public IStorageHeater buildStorageHeater(final ISpaceHeatingDTO dto) {
		final IStorageHeater heater = T.createStorageHeater();
		
		heater.setType(dto.getStorageHeaterType().or(StorageHeaterType.OLD_LARGE_VOLUME));
		
		heater.setControlType(dto.getStorageHeaterControlType().or(heater.getType() == StorageHeaterType.FAN ? StorageHeaterControlType.CELECT_CHARGE_CONTROL : StorageHeaterControlType.MANUAL_CHARGE_CONTROL));
		
		
		return heater;
	}
}
