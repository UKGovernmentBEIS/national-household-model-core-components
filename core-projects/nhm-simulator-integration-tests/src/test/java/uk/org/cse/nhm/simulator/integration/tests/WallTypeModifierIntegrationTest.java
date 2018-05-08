package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class WallTypeModifierIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void modifywalltypes() throws NHMException, InterruptedException, IOException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("action/modifywalltype.s"), true, Collections.<Class<?>>emptySet());
        for (IDwelling d : output.state.getDwellings()) {
            StructureModel structureModel = output.state.get(output.structure, d);
            for (Storey s : structureModel.getStoreys()) {
                for (IWall w : s.getImmutableWalls()) {
                    if (WallConstructionType.getExternalWallTypes().contains(w.getWallConstructionType())) {
                        Assert.assertEquals("All external walls should match the wall construction type given to the measure", w.getWallConstructionType(), WallConstructionType.Cavity);
                    }
                }
            }
        }
    }
}
