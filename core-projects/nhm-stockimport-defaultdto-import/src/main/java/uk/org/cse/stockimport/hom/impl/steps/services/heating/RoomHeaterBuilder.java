package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

/**
 * @since 1.0
 */
public class RoomHeaterBuilder implements IRoomHeaterBuilder {
	private static final ITechnologiesFactory T = ITechnologiesFactory.eINSTANCE;
	
	@Override
	public IRoomHeater buildRoomHeater(final int constructionYear, final ISpaceHeatingDTO dto) {
		final IRoomHeater result;
		if (dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.BACK_BOILER || dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING) {
			final IBackBoiler boiler = T.createBackBoiler();
			result = boiler;
			setRoomHeaterProperties(dto, result);
			
			boiler.setInstallationYear(dto.getInstallationYear().or(constructionYear));
			
			if (dto.getCondensing().isPresent()) {
				boiler.setCondensing(dto.getCondensing().get());
			} else {
				boiler.setCondensing(false);
			}
			boiler.setFlueType(result.getFlueType());
			boiler.setSummerEfficiency(result.getEfficiency());
			boiler.setWinterEfficiency(result.getEfficiency());
		} else {
			result = T.createRoomHeater();
			setRoomHeaterProperties(dto, result);
		}

		return result;
	}

	@Override
	public IRoomHeater buildDefaultElectricRoomHeater() {
		final IRoomHeater result = T.createRoomHeater();
		result.setFuel(FuelType.ELECTRICITY);
		result.setEfficiency(Efficiency.ONE);
		result.setThermostatFitted(false);
		result.setFlueType(FlueType.NOT_APPLICABLE);
		return result;
	}
	
    /**
     * @assumption Room heaters default to open flue if no flue type is set in DTO.
     */
	private void setRoomHeaterProperties(final ISpaceHeatingDTO dto, final IRoomHeater result) {
		result.setFuel(dto.getMainHeatingFuel());
		result.setEfficiency(Efficiency.fromDouble(dto.getBasicEfficiency()));
		result.setThermostatFitted(false);
		result.setFlueType(dto.getFlueType().or(result.getFuel() == FuelType.ELECTRICITY ? FlueType.NOT_APPLICABLE : FlueType.OPEN_FLUE));
	}
}
