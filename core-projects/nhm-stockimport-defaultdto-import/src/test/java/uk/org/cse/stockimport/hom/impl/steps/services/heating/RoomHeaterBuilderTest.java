package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.google.common.base.Optional;

import org.junit.Assert;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

public class RoomHeaterBuilderTest {
	@Test
	public void testWithoutBackBoiler() {
		final IRoomHeaterBuilder rhb = new RoomHeaterBuilder();
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.ROOM_HEATER);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.ELECTRICITY);
        when(dto.getCondensing()).thenReturn(Optional.<Boolean> absent());

		when(dto.getBasicEfficiency()).thenReturn(0.75);
		when(dto.getFlueType()).thenReturn(Optional.<FlueType>absent());

		final IRoomHeater roomHeater = rhb.buildRoomHeater(1234, dto);

		Assert.assertEquals(FlueType.NOT_APPLICABLE, roomHeater.getFlueType());
		Assert.assertEquals(Efficiency.fromDouble(0.75), roomHeater.getEfficiency());
	}

	@Test
	public void testWithBackBoiler() {
		final IRoomHeaterBuilder rhb = new RoomHeaterBuilder();
		final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
		when(dto.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.BACK_BOILER);
		when(dto.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
		when(dto.getBasicEfficiency()).thenReturn(0.75);
		when(dto.getFlueType()).thenReturn(Optional.<FlueType>absent());
		when(dto.getInstallationYear()).thenReturn(Optional.<Integer>absent());
        when(dto.getCondensing()).thenReturn(Optional.<Boolean> absent());

		final IRoomHeater roomHeater = rhb.buildRoomHeater(1234, dto);
		Assert.assertTrue(roomHeater instanceof IBackBoiler);

		final IBackBoiler bb = (IBackBoiler) roomHeater;
		Assert.assertEquals(Efficiency.fromDouble(0.75), bb.getSummerEfficiency());
		Assert.assertEquals(Efficiency.fromDouble(0.75), bb.getWinterEfficiency());
		Assert.assertEquals(FlueType.OPEN_FLUE, bb.getFlueType());
		Assert.assertEquals(1234, bb.getInstallationYear());
	}
}
