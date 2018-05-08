package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

public class WarmAirSystemBuilderTest {

    @Test
    public void testBuildWarmAirSystem() {
        final IWarmAirSystemBuilder builder = new WarmAirSystemBuilder();
        final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
        when(dto.getBasicEfficiency()).thenReturn(0.33);
        when(dto.getMainHeatingFuel()).thenReturn(FuelType.ELECTRICITY);
        IWarmAirSystem was = builder.buildWarmAirSystem(dto);

        Assert.assertEquals(Efficiency.fromDouble(0.33), was.getEfficiency());
        Assert.assertEquals(FuelType.ELECTRICITY, was.getFuelType());
    }
}
