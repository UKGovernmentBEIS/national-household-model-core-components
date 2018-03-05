package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.impl.appliances.Appliances09;

/**
 * EnergyUseTransducerTest..
 *
 * @author trickyBytes
 */
@RunWith(MockitoJUnitRunner.class)
public class EnergyUseTransducerTest {


    @Mock private IEnergyCalculatorHouseCase house;
    @Mock private IInternalParameters parameters;
    @Mock private ISpecificHeatLosses losses;
    @Mock private IEnergyState state;
    
    private EnergyUseTransducer transducer;
    
    @Before
    public void setUp(){
        //transducer = new EnergyUseTransducer(ServiceType.APPLIANCES, 1, -10);
    }
    
    @Test
    public void bla() {
        final Appliances09 appliances = new Appliances09(DefaultConstants.INSTANCE);
        final ClassEnergyState ces = new ClassEnergyState();
        final EnergyUseTransducer t = new EnergyUseTransducer(ServiceType.APPLIANCES, 0, 100);
        
        when(house.getFloorArea()).thenReturn(100d);
        when(parameters.getNumberOfOccupants()).thenReturn(1d);
        when(parameters.getCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
        ISeasonalParameters sp = mock(ISeasonalParameters.class);
        when(sp.getMonthOfYear()).thenReturn(1);
        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_7);
        when(parameters.getClimate()).thenReturn(sp);
        ces.setCurrentServiceType(appliances.getServiceType(), "asdf");
        appliances.generate(house, parameters, losses, ces);
        
        System.err.println(
                ces.getTotalDemand(EnergyType.FuelPEAK_ELECTRICITY, ServiceType.APPLIANCES)+" " +
                ces.getTotalDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, ServiceType.APPLIANCES)
                );
        
        t.generate(house, parameters, losses, ces);
        
        System.err.println(
                ces.getTotalDemand(EnergyType.FuelPEAK_ELECTRICITY,ServiceType.APPLIANCES) + ", " +
                ces.getTotalDemand(EnergyType.FuelOFFPEAK_ELECTRICITY,ServiceType.APPLIANCES) + " "
                );
        
        
    }
    
    @Test
    public void testProportionCalculationForEnergyUse() {
        final ClassEnergyState state = new ClassEnergyState();
        
        Map<EnergyType, Double> result = EnergyUseTransducer.proportionalEnergyDemand(ServiceType.APPLIANCES, state);
        for (final EnergyType et : EnergyType.values()) Assert.assertEquals(0, result.get(et), 0d);
        
        state.setCurrentServiceType(ServiceType.APPLIANCES, "Test appliance");
        state.increaseDemand(EnergyType.FuelGAS, 10);
        
        result = EnergyUseTransducer.proportionalEnergyDemand(ServiceType.APPLIANCES, state);
        
        for (final EnergyType et : EnergyType.values()) {
            if (et == EnergyType.FuelGAS) {
                Assert.assertEquals(1, result.get(et), 0d);
            } else {
                Assert.assertEquals(0, result.get(et), 0d);                
            }
        }
        
        state.increaseDemand(EnergyType.FuelSOLID_FUEL, 20);
        
        result = EnergyUseTransducer.proportionalEnergyDemand(ServiceType.APPLIANCES, state);
        
        for (final EnergyType et : EnergyType.values()) {
            if (et == EnergyType.FuelGAS) {
                Assert.assertEquals(0.333333333, result.get(et), 0.01d);
            } else if (et == EnergyType.FuelSOLID_FUEL) {
                Assert.assertEquals(0.666666666, result.get(et), 0.01d);
            } else {
                Assert.assertEquals(0, result.get(et), 0d);                
            }
        }
    }
    
    @Test
    public void testDoesNotIncreaseEnergyDemandIfConstantTermAndLinearFactorAreZero(){
        transducer = new EnergyUseTransducer(ServiceType.APPLIANCES, 0, 0);
        when(state.getTotalDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, ServiceType.APPLIANCES)).thenReturn(100d);
        
        transducer.generate(house, parameters, losses, state);
        verify(state, atMost(1)).setCurrentServiceType(ServiceType.APPLIANCES, ServiceType.APPLIANCES.toString());
        verify(state, atMost(0)).increaseDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, 0);
    }
}
