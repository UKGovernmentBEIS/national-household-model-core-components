package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * MatchHasInsulatedHotWaterCylinderTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchHasInsulatedHotWaterCylinderTest {

    private MatchHasInsulateHotWaterCylinder function;

    @Mock IDimension<ITechnologyModel> techDimension;
    @Mock ITechnologyModel technologies;
    @Mock ICentralWaterSystem centralWaterHeatingSystem;
    @Mock IWaterTank store;

    @Before
    public void initialiseTests() {
        function = new MatchHasInsulateHotWaterCylinder(techDimension);
    }

    @Test
    public void ShouldReturnFalseIfNoCentralWaterSystemHasNoCylinder() throws Exception {
        when(technologies.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
        assertFalse("When no cylinder present", function.isCylinderInsulated(technologies));
    }

    @Test
    public void ShouldReturnFalseWhenCylinderPresentButHasNoInsulation() throws Exception {
        when(technologies.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        assertFalse(function.isCylinderInsulated(technologies));
    }

    @Test
    public void ShouldReturnTrueIfCylinderIsPresentAndIsInsulated() throws Exception {
        when(technologies.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.getInsulation()).thenReturn(2d);

        assertTrue(function.isCylinderInsulated(technologies));
    }
}
