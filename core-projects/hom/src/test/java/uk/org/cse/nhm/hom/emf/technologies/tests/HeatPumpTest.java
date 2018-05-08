/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Heat Pump</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
 * <em>Accept</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HeatPumpTest extends HeatSourceTest {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public static void main(final String[] args) {
        TestRunner.run(HeatPumpTest.class);
    }

    /**
     * Constructs a new Heat Pump test case with the given name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public HeatPumpTest(final String name) {
        super(name);
    }

    /**
     * Returns the fixture for this Heat Pump test case.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected IHeatPump getFixture() {
        return (IHeatPump) fixture;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#setUp()
     *
     * @generated
     */
    @Override
    protected void setUp() throws Exception {
        setFixture(ITechnologiesFactory.eINSTANCE.createHeatPump());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @see junit.framework.TestCase#tearDown()
     *
     * @generated
     */
    @Override
    protected void tearDown() throws Exception {
        setFixture(null);
    }

    /**
     * Tests the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants, IEnergyCalculatorParameters, uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, java.util.concurrent.atomic.AtomicInteger, double, double)
     * <em>Accept</em>}' operation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter#accept(IConstants,
     * IEnergyCalculatorParameters,
     * uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor,
     * java.util.concurrent.atomic.AtomicInteger, double, double)
     * @generated no
     */
    public void testAccept__IInternalParameters_IEnergyCalculatorVisitor_AtomicInteger() {
    }

    @Override
    public void testAcceptFromHeating__IInternalParameters_IEnergyCalculatorVisitor_double_int() {
        final IHeatPump pump = getFixture();

        final ICentralHeatingSystem coilSystem = new CentralHeatingSystemImpl() {
            @Override
            public EmitterType getEmitterType() {
                return EmitterType.WARM_AIR_FAN_COIL;
            }
        };

        final ICentralHeatingSystem radSystem = new CentralHeatingSystemImpl() {
            @Override
            public EmitterType getEmitterType() {
                return EmitterType.RADIATORS;
            }
        };

        pump.setCoefficientOfPerformance(Efficiency.fromDouble(2d));
        pump.setFuel(FuelType.MAINS_GAS);

        final IInternalParameters parameters = mock(IInternalParameters.class);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
        when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.FLAT_RATE);

        final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

        final double proportion = 1d;
        final int priority = 0;

        pump.setSpaceHeater(coilSystem);
        pump.acceptFromHeating(DefaultConstants.INSTANCE, parameters, visitor, proportion, priority);

        pump.setSpaceHeater(radSystem);
        pump.acceptFromHeating(DefaultConstants.INSTANCE, parameters, visitor, proportion, priority);

        final IHybridHeater hybrid = ITechnologiesFactory.eINSTANCE.createHybridHeater();
        hybrid.setEfficiency(Efficiency.fromDouble(0.8d));
        hybrid.setFuel(FuelType.HOUSE_COAL);
        hybrid.getFraction().addAll(Collections.nCopies(12, 0d));
        hybrid.getFraction().set(3, 0.75d);

        pump.setHybrid(hybrid);
        pump.acceptFromHeating(DefaultConstants.INSTANCE, parameters, visitor, proportion, priority);

        final ArgumentCaptor<IEnergyTransducer> captor = ArgumentCaptor.forClass(IEnergyTransducer.class);
        verify(visitor, times(3)).visitEnergyTransducer(captor.capture());

        final List<IEnergyTransducer> transducers = captor.getAllValues();
        testTransducer(transducers.get(0), FuelType.MAINS_GAS.getEnergyType(), EmitterType.WARM_AIR_FAN_COIL, parameters);
        testTransducer(transducers.get(1), FuelType.MAINS_GAS.getEnergyType(), EmitterType.RADIATORS, parameters);
        testHybridTransducer(transducers.get(2), FuelType.MAINS_GAS.getEnergyType(), EmitterType.RADIATORS, FuelType.HOUSE_COAL.getEnergyType(), parameters);
    }

    private void testHybridTransducer(
            final IEnergyTransducer transducer,
            final EnergyType primary,
            final EmitterType warmAirFanCoil,
            final EnergyType secondary,
            final IInternalParameters parameters) {

        final IEnergyState state = mock(IEnergyState.class);
        when(parameters.getClimate()).thenReturn(mock(ISeasonalParameters.class));
        when(parameters.getClimate().getMonthOfYear()).thenReturn(4);
        when(state.getBoundedTotalHeatDemand(1d)).thenReturn(100d);
        when(state.getUnsatisfiedDemand(EnergyType.DemandsHEAT)).thenReturn(100d);

        transducer.generate(null, parameters, null, state);

        verify(state).increaseSupply(EnergyType.DemandsHEAT, 100d);

        double efficiency = 2;

        verify(state).increaseDemand(primary, 75d / efficiency);

        final double secondaryEfficiency = 0.8;
        verify(state).increaseDemand(secondary, 25d / secondaryEfficiency);
    }

    private void testTransducer(final IEnergyTransducer transducer, final EnergyType mainsGas, final EmitterType warmAirFanCoil, final IInternalParameters parameters) {

        final IEnergyState state = mock(IEnergyState.class);
        when(state.getBoundedTotalHeatDemand(1d)).thenReturn(100d);
        when(state.getUnsatisfiedDemand(EnergyType.DemandsHEAT)).thenReturn(100d);
        transducer.generate(null, parameters, null, state);

        verify(state).increaseSupply(EnergyType.DemandsHEAT, 100d);

        double efficiency = 2;

        verify(state).increaseDemand(mainsGas, 100d / efficiency);
    }

    @Override
    public void testGetDemandTemperatureAdjustment__IInternalParameters_EList() {
        final IHeatPump pump = getFixture();
        final IInternalParameters parameters = mock(IInternalParameters.class);
        when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        ICentralHeatingSystem heater = new CentralHeatingSystemImpl() {
            @Override
            public boolean isThermostaticallyControlled() {
                return false;
            }
        };

        pump.setSpaceHeater(heater);

        double demandTemperatureAdjustment = pump.getDemandTemperatureAdjustment(parameters, ECollections.<HeatingSystemControlType>emptyEList());

        // + 0.3 if there no thermostat
        Assert.assertEquals("Should be +0.3 degrees without a demand temp adjustment", 0.3, demandTemperatureAdjustment, 0);

        heater = new CentralHeatingSystemImpl() {
            @Override
            public boolean isThermostaticallyControlled() {
                return true;
            }
        };

        pump.setSpaceHeater(heater);

        demandTemperatureAdjustment = pump.getDemandTemperatureAdjustment(parameters, ECollections.<HeatingSystemControlType>emptyEList());

        // + 0.3 if there no thermostat
        Assert.assertEquals("Should be +0 degrees without a demand temp adjustment", 0.0, demandTemperatureAdjustment, 0);

        pump.setSpaceHeater(null);
    }

} //HeatPumpTest
