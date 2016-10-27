package uk.org.cse.nhm.hom.structure;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
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
		
		verify(visitor).visitFabricElement(any(AreaType.class), eq(50d), eq(2d), eq(Optional.<ThermalMassLevel>absent()));
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
				50, 50 * 3 / 2.0, 
				Math.PI/2, Math.PI/3, OvershadingType.AVERAGE);
	}
	
	@Test
	public void testDoors() {
		final Elevation e = new Elevation();
		final Door d = new Door();
		d.setArea(10);
		d.setDoorType(DoorType.Glazed);
		d.setuValue(5);
		e.addDoor(d);
		
		e.setOpeningProportion(0.5);
		
		IDoorVisitor doorVisitor = e.getDoorVisitor();
		
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		
		double visitDoors = doorVisitor.visitDoors(visitor, 100);
		Assert.assertEquals(10d, visitDoors, 0d);
		verify(visitor).visitFabricElement(any(AreaType.class), eq(10d), eq(5d), eq(Optional.<ThermalMassLevel>absent()));
		
		Assert.assertEquals(0d, doorVisitor.visitDoors(visitor, 100), 0d);
	}
}
