package uk.org.cse.nhm.simulation.measure.structure;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public class ModifyWallConstructionTypeMeasureTest {

    private MockDimensions dims;
    private ModifyWallConstructionTypeMeasure measure;

    @Before
    public void setup() {
        this.dims = Util.getMockDimensions();
        this.measure = new ModifyWallConstructionTypeMeasure(
                this.dims.structure, WallConstructionType.Cavity);
    }

    private ISettableComponentsScope buildHouseIncludingWall(IMutableWall wall) {
        final Storey storey = mock(Storey.class);
        when(storey.getWalls()).thenReturn(Collections.singleton(wall));
        when(storey.getImmutableWalls()).thenReturn(
                Collections.<IWall>singleton(wall));
        when(storey.copy()).thenReturn(storey);

        ITechnologyModel technologies = new TechnologyModelImpl() {
        };
        final Elevation dummy = mock(Elevation.class);
        StructureModel structure = new StructureModel() {
            {
                addStorey(storey);
                setElevation(ElevationType.FRONT, dummy);
                setElevation(ElevationType.BACK, dummy);
                setElevation(ElevationType.LEFT, dummy);
                setElevation(ElevationType.RIGHT, dummy);
            }
        };

        return Util.mockComponents(dims, structure, technologies);
    }

    private static class WallCT {

        private WallConstructionType type;

        public WallCT(WallConstructionType type) {
            this.type = type;
        }

        public WallConstructionType getType() {
            return type;
        }

        public void setType(WallConstructionType type) {
            this.type = type;
        }

    }

    @Test
    public void testApplicationOfMeasure() throws NHMException {
        final ModifyWallConstructionTypeMeasure m = this.measure;

        IMutableWall wall = makeAWall(WallConstructionType.Cavity);

        ISettableComponentsScope houseIncludingWall = buildHouseIncludingWall(wall);

        Assert.assertTrue(m.isSuitable(houseIncludingWall, ILets.EMPTY));

        StructureModel modifiedStructure = Util.applyAndGetStructure(dims, m, houseIncludingWall);

        Assert.assertEquals(1, modifiedStructure.getStoreys().size());

        Storey storey = modifiedStructure.getStoreys().get(0);

        IMutableWall modifiedWall = storey.getWalls().iterator().next();

        Assert.assertEquals("Modified wall should have same type as that of measure.", modifiedWall.getWallConstructionType(), m.getWallConstructionType());
    }

    private IMutableWall makeAWall(final WallConstructionType type) {
        IMutableWall wall = mock(IMutableWall.class);
        final WallCT t = new WallCT(type);
        when(wall.getWallConstructionType()).thenAnswer(new Answer<WallConstructionType>() {
            @Override
            public WallConstructionType answer(InvocationOnMock invocation)
                    throws Throwable {
                return t.getType();
            }
        });
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                t.setType((WallConstructionType) arguments[0]);
                return null;
            }
        }).when(wall).setWallConstructionType(any(WallConstructionType.class));
        return wall;
    }

    @Test
    public void testSuitability() throws NHMException {
        final ModifyWallConstructionTypeMeasure m = this.measure;

        IMutableWall wall2 = makeAWall(WallConstructionType.Internal_Any);

        ISettableComponentsScope houseIncludingWall2 = buildHouseIncludingWall(wall2);

        Assert.assertFalse(m.isSuitable(houseIncludingWall2, ILets.EMPTY));

        IMutableWall wall = makeAWall(WallConstructionType.Cavity);

        ISettableComponentsScope houseIncludingWall = buildHouseIncludingWall(wall);

        Assert.assertTrue(m.isSuitable(houseIncludingWall, ILets.EMPTY));
    }
}
