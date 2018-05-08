package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class ModifyInsulationIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void modifyInsulation() throws NHMException, InterruptedException, IOException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/modifyinsulation.s"), true, Collections.<Class<?>>emptySet());

        for (final IDwelling d : output.state.getDwellings()) {
            final StructureModel structureModel = output.state.get(output.structure, d);
            if (!structureModel.getHasLoft()) {
                continue;
            }
            Assert.assertEquals("Roof insulation should match loft insulation measure param", 123d, structureModel.getRoofInsulationThickness(), 0.1);
            Assert.assertEquals("Floor insulation should match floor insulation measure param", 123d, structureModel.getFloorInsulationThickness(), 0.1);
            for (final Storey s : structureModel.getStoreys()) {
                for (final IWall w : s.getImmutableWalls()) {
                    for (final WallInsulationType type : w.getWallInsulationTypes()) {
                        Assert.assertEquals("Wall insulation should match wall insulation measure param", 123d, w.getWallInsulationThickness(type), 0.1);
                    }
                }
                Assert.assertEquals("Ceiling u-value should match loft insulation measure u-value param", 2.22, s.getCeilingUValue(), 0.1);
                Assert.assertEquals("Floor u-value should match floor insulation measure u-value param", 2.22, s.getFloorUValue(), 0.1);
            }
        }
    }

}
