package uk.org.cse.nhm.hom.structure;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Elevation.IDoorVisitor;

public class ElevationTest {
	@Test
	public void testGlazingHeatLoss() {
		final Elevation e = new Elevation();
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

		e.setOpeningProportion(0.5);
		final Glazing glazing = new Glazing(1, GlazingType.Single, FrameType.uPVC);
		glazing.setUValue(2);

		e.addGlazing(glazing);

		e.visitGlazing(visitor, 100, 0);

		verify(visitor).visitWindow(eq(50d), eq(2d), eq(FrameType.uPVC), eq(GlazingType.Single), eq(WindowInsulationType.Air),eq(WindowGlazingAirGap.gapOf6mm));
	}

	@Test
	public void testGlazingLightAndGains() {
		final Elevation e = new Elevation();
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

		e.setAngleFromNorth(Math.PI/3);
		e.setOpeningProportion(0.5);
		final Glazing glazing = new Glazing(1, GlazingType.Single, FrameType.uPVC);

		glazing.setLightTransmissionFactor(2);
		glazing.setGainsTransmissionFactor(3);
		glazing.setFrameFactor(0.5);

		e.addGlazing(glazing);

		e.visitGlazing(visitor, 100, 0);

		verify(visitor).visitTransparentElement(
				GlazingType.Single,
				WindowInsulationType.Air,
				2,
				3,
				50,
				FrameType.uPVC,
				0.5,
				Math.PI/2, Math.PI/3, OvershadingType.AVERAGE);
	}

	@Test
	public void testDoors() {
		final Elevation e = new Elevation();
		final Door d = new Door();
		d.setArea(10);
		d.setDoorType(DoorType.Glazed);
		d.setuValue(5);
		d.setFrameFactor(0.9);
		d.setFrameType(FrameType.uPVC);
		d.setGainsTransmissionFactor(1);
		d.setLightTransmissionFactor(0.5);
		d.setGlazingType(GlazingType.Double);
		e.addDoor(d);

		e.setOpeningProportion(0.5);

		final IDoorVisitor doorVisitor = e.getDoorVisitor();

		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

		final double visitDoors = doorVisitor.offerPotentialDoorArea(100);
		Assert.assertEquals(10d, visitDoors, 0d);
		Assert.assertEquals(0d, doorVisitor.offerPotentialDoorArea(100), 0d);

		doorVisitor.visitDoors(visitor);
		verify(visitor).visitDoor(10d, 5d);
		verify(visitor).visitTransparentElement(
				eq(GlazingType.Double),
				eq(WindowInsulationType.Air),
				eq(0.5d),
				eq(1d),
				eq(10d),
				eq(FrameType.uPVC),
				eq(0.9d),
				eq(Math.PI / 2),
				eq(0d),
				eq(OvershadingType.AVERAGE));
	}
}
