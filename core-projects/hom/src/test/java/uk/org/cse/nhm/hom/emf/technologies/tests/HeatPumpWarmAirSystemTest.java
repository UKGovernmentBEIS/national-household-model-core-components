/**
 */
package uk.org.cse.nhm.hom.emf.technologies.tests;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import junit.textui.TestRunner;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.WarmAirFans;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Heat Pump Warm Air System</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class HeatPumpWarmAirSystemTest extends WarmAirSystemTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(final String[] args) {
		TestRunner.run(HeatPumpWarmAirSystemTest.class);
	}

	/**
	 * Constructs a new Heat Pump Warm Air System test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HeatPumpWarmAirSystemTest(final String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Heat Pump Warm Air System test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IHeatPumpWarmAirSystem getFixture() {
		return (IHeatPumpWarmAirSystem)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ITechnologiesFactory.eINSTANCE.createHeatPumpWarmAirSystem());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	@Test
	public void testHighRateFraction() {
		final IHeatPumpWarmAirSystem was = getFixture();
		final IInternalParameters parameters = mock(IInternalParameters.class);
		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);
		when(parameters.getTarrifType()).thenReturn(ElectricityTariffType.ECONOMY_7);

		final IEnergyCalculatorHouseCase house = mock(IEnergyCalculatorHouseCase.class);

		was.setFuelType(FuelType.ELECTRICITY);
		was.setSourceType(HeatPumpSourceType.AIR);

		checkHighRateFraction(was, house, parameters, 0.9);

		was.setAuxiliaryPresent(true);

		checkHighRateFraction(was, house, parameters, 0.9);

		was.setSourceType(HeatPumpSourceType.GROUND);

		checkHighRateFraction(was, house, parameters, 0.7);
	}

	private void checkHighRateFraction(final IHeatPumpWarmAirSystem target, final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final double fraction) {
		final ArgumentCaptor<IEnergyTransducer> tc = ArgumentCaptor.forClass(IEnergyTransducer.class);
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);
		final IEnergyState state = mock(IEnergyState.class);

		target.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), DummyHeatProportions.instance);

		verify(visitor, atLeastOnce()).visitEnergyTransducer(tc.capture());

		for (final IEnergyTransducer value : tc.getAllValues()) {
			if (value instanceof WarmAirFans) continue;
			value.generate(house, parameters, null, state);
		}

		verify(state).increaseElectricityDemand(eq(fraction), anyDouble());
	}

} //HeatPumpWarmAirSystemTest
