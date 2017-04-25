package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/**
 * EnergyUseTransducerTest.
 *
 * @author trickyBytes
 */
@RunWith(MockitoJUnitRunner.class)
public class EnergyUseTransducerTest {

    @Mock private IEnergyState state;
    private EnergyUseTransducer transducer;
    
    @Before
    public void setUp(){
        transducer = new EnergyUseTransducer(ServiceType.APPLIANCES, 2, 1);
    }
        
    @Test
    public void canCalcProportionOfTotalDemandOfSpecificEnergyType() throws Exception {
        when(state.getTotalDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, ServiceType.APPLIANCES)).thenReturn(20d);
        when(state.getTotalDemand(EnergyType.FuelPEAK_ELECTRICITY, ServiceType.APPLIANCES)).thenReturn(50d);
        when(state.getTotalDemand(EnergyType.FuelGAS, ServiceType.APPLIANCES)).thenReturn(30d);
                
        EnumMap<EnergyType, Double> props = transducer.proportionOfEnergyDemand(ServiceType.APPLIANCES, state);
           
        assertEquals(0.2, props.get(EnergyType.FuelOFFPEAK_ELECTRICITY), 0d);
        assertEquals(0.5, props.get(EnergyType.FuelPEAK_ELECTRICITY), 0d);
        assertEquals(0.3, props.get(EnergyType.FuelGAS), 0d);
        
        props = transducer.calcProportionOfConstantTerm(2, props);
        assertEquals(0.4, props.get(EnergyType.FuelOFFPEAK_ELECTRICITY), 0d);
        assertEquals(1, props.get(EnergyType.FuelPEAK_ELECTRICITY), 0d);
        assertEquals(0.6, props.get(EnergyType.FuelGAS), 0d);
    }
}
