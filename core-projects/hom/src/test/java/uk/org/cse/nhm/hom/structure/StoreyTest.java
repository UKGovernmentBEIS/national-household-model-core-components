package uk.org.cse.nhm.hom.structure;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Polygon;
import java.util.EnumMap;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.structure.impl.Elevation.IDoorVisitor;
import uk.org.cse.nhm.hom.structure.impl.Storey;

public class StoreyTest {
    @Test
    public void testArea() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey();
        s.setPerimeter(p);

        Assert.assertEquals(100d, s.getArea(), 0d);
    }

    @Test
    public void testVolume() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey();
        s.setPerimeter(p);
        s.setHeight(10);

        Assert.assertEquals(1000d, s.getVolume(), 0d);
    }

    @Test
    public void testWallsInRightPlaces() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey();
        s.setPerimeter(p);
        s.setHeight(10);
        s.setFloorLocationType(FloorLocationType.GROUND);

        int wallCounter = 0;
        for (final IMutableWall wall : s.getWalls()) {
            // TODO check that these assertions are actually as intended.
            switch (wallCounter) {
                case 0:
                    Assert.assertEquals(ElevationType.LEFT, wall.getElevationType());
                    break;
                case 1:
                    Assert.assertEquals(ElevationType.BACK, wall.getElevationType());
                    break;
                case 2:
                    Assert.assertEquals(ElevationType.RIGHT, wall.getElevationType());
                    break;
                case 3:
                    Assert.assertEquals(ElevationType.FRONT, wall.getElevationType());
                    break;
            }
            wallCounter++;
        }

        Assert.assertEquals(4, wallCounter);
    }

    @Test
    public void testVisitorWorks() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey();
        s.setPerimeter(p);
        s.setHeight(10);
        s.setFloorLocationType(FloorLocationType.GROUND);

        int wallCounter = 0;
        for (final IMutableWall wall : s.getWalls()) {
            wallCounter++;
            if (wallCounter == 1) {
                wall.setWallConstructionType(WallConstructionType.Party_MetalFrame);
            } else {
                wall.setWallConstructionType(WallConstructionType.Cavity);
            }
            wall.setUValue(2);
        }

        final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
        final IElevation left = mock(IElevation.class);
        final IElevation right = mock(IElevation.class);
        final IElevation front = mock(IElevation.class);
        final IElevation back = mock(IElevation.class);

        when(left.getDoorVisitor()).thenReturn(mock(IDoorVisitor.class));

        when(right.getDoorVisitor()).thenReturn(mock(IDoorVisitor.class));

        when(back.getDoorVisitor()).thenReturn(mock(IDoorVisitor.class));

        when(front.getDoorVisitor()).thenReturn(mock(IDoorVisitor.class));

        final EnumMap<ElevationType, IElevation> els = new EnumMap<ElevationType, IElevation>(ElevationType.class);

        els.put(ElevationType.LEFT, left);
        els.put(ElevationType.RIGHT, right);
        els.put(ElevationType.FRONT, front);
        els.put(ElevationType.BACK, back);

        s.setCeilingUValue(2);
        s.setFloorUValue(2);

        s.accept(visitor, els, null, 50, 50);

        // should visit the four walls
        verify(visitor, times(4)).visitFabricElement(any(AreaType.class), eq(100d), eq(2d), eq(Optional.<ThermalMassLevel>absent()));
        // then the top and bottom of the room
        verify(visitor, times(2)).visitFabricElement(any(AreaType.class), eq(50d), eq(2d), eq(Optional.<ThermalMassLevel>absent()));
    }

    @Test
    public void testSplit() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey();
        s.setPerimeter(p);
        s.setHeight(10);
        s.setFloorLocationType(FloorLocationType.GROUND);
        boolean onSplitWall = false;

        int counter = 0;
        for (final IMutableWall wall : s.getWalls()) {
            if (onSplitWall) {
                Assert.assertEquals(WallConstructionType.Cavity, wall.getWallConstructionType());
                wall.setWallConstructionType(WallConstructionType.Party_MetalFrame);
                onSplitWall = false;
            } else {
                wall.setWallConstructionType(WallConstructionType.Cavity);
                    wall.split(0.5);
                    onSplitWall = true;
            }
            counter++;
        }

        counter = 0;
        for (final IMutableWall wall : s.getWalls()) {
            System.err.println(String.format("%d %s %s", counter, wall.isPartyWall(), wall));
            // every odd numbered wall should be an original un-party wall
            // every even numbered wall is a party wall.
            Assert.assertEquals(counter % 2 != 0, wall.isPartyWall());
            counter++;
        }

        // started with 4 walls, split them all in half, should have eight
        Assert.assertEquals(8, counter);
    }

    @Test
    public void testPerimeter() {
        final Polygon p = new Polygon(
                new int[] { 0, 10, 10, 0 },
                new int[] { 0, 0, 10, 10 },
                4);

        final Storey s = new Storey(); 
        s.setPerimeter(p);
        Assert.assertEquals(40d, s.getPerimeter(), 0d);
    }

    @Test
    public void testGetSet() {
        final Storey s = new Storey();
        s.setFloorLocationType(FloorLocationType.GROUND);
        Assert.assertEquals(FloorLocationType.GROUND, s.getFloorLocationType());
     
        s.setFloorUValue(99);
        Assert.assertEquals(99d, s.getFloorUValue(), 0d);
    }
}
