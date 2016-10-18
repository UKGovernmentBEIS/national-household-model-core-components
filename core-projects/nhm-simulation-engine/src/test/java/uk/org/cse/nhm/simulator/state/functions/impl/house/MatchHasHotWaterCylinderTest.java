package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
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
 * MatchHasHotWaterCylinderTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class MatchHasHotWaterCylinderTest {

    private MatchHasHotWaterCylinder function;

    @Mock IDimension<ITechnologyModel> techDimension;
    @Mock ITechnologyModel technologies;
    @Mock ICentralWaterSystem centralWaterHeatingSystem;

    @Before
    public void initialiseTests() {
        function = new MatchHasHotWaterCylinder(techDimension);
    }

    @Test
    public void ShouldReturnFalseIfHouseHasNoHotwaterSystem() throws Exception {
        when(technologies.getCentralWaterSystem()).thenReturn(null);

        assertFalse(function.hasHotWaterCylinder(technologies));
    }

    @Test
    public void ShouldReturnFalseIfHasHOtWaterSystemAndNoCylinderEgCombiBoiler() {
        when(technologies.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
        when(centralWaterHeatingSystem.getStore()).thenReturn(null);

        assertFalse(function.hasHotWaterCylinder(technologies));
    }

    @Test
    public void ShouldReturnTrueIfHasHotWaterSystemUsingCylinder() throws Exception {
        when(technologies.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
        when(centralWaterHeatingSystem.getStore()).thenReturn(mock(IWaterTank.class));

        assertTrue(function.hasHotWaterCylinder(technologies));
    }
}
