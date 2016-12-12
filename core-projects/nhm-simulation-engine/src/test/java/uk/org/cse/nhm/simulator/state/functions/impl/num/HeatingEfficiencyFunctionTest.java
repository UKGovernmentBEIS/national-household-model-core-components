package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;
import uk.org.cse.nhm.language.definition.function.num.XEfficiencyMeasurement;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.CentralWaterHeater;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.WaterHeater;

public class HeatingEfficiencyFunctionTest {

	private static final double NO_ERROR = 0;
	private IComponentsScope scope;
	private IDimension<ITechnologyModel> dimension;
	private TechnologyTestBuilder builder;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(IComponentsScope.class);
		dimension = mock(IDimension.class);
		builder = new TechnologyTestBuilder();

		when(scope.get(dimension)).thenReturn(builder.build());
	}

	@Test
	public void noSystems() {
		testAll(0.0);
	}

	private void testAll(final double efficiency) {
		testPrimarySpaceHeating(efficiency);
		testSecondarySpaceHeating(efficiency);
		testCentralHotWater(efficiency);
		testAuxiliaryHotWater(efficiency);
	}

	@Test
	public void unconnectedSystems() {
		builder.addSpaceHeating(SpaceHeater.CentralHeating);
		builder.addWaterHeating(WaterHeater.CentralHotWater);

		testAll(0.0);
	}

	@Test
	public void settableHeatSources() {
		final double reasonableEfficiency = 0.8;
		for (final HeatSource source : ImmutableSet.of(
				HeatSource.Boiler,
				HeatSource.BackBoiler,
				HeatSource.Community,
				HeatSource.CommunityCHP,
				HeatSource.CPSU,
				HeatSource.InstantaneousCombiBoiler,
				HeatSource.StorageCombiBoiler
				)) {

				builder.addHeatSource(source, reasonableEfficiency);
				only(reasonableEfficiency, XHeatingSystem.CentralHotWater, XHeatingSystem.PrimarySpaceHeating);
		}
	}

	@Test
	public void heatPump() {
		builder.addHeatSource(HeatSource.HeatPump, 3.0);
		only(3.0, XHeatingSystem.CentralHotWater, XHeatingSystem.PrimarySpaceHeating);
	}


	@Test
	public void immersionHeater() {
		builder.addCentralWaterHeating(CentralWaterHeater.Immersion);
		only(1.0, XHeatingSystem.CentralHotWater);
	}

	@Test
	public void solarHotWater() {
		builder.addCentralWaterHeating(CentralWaterHeater.Solar);
		testAll(0.0); // Solar hot water has an efficiency of 0 for now. Someone would need to do some thinking to work out how its values mapped to efficiency.
	}

	@Test
	public void warmAirCiculator() {
		builder.addCentralWaterHeating(CentralWaterHeater.WarmAir, 0.8); // Warm air system's efficiency needs setting (usually comes from stock import).
		only(0.8, XHeatingSystem.CentralHotWater);
	}

	@Test
	public void pointOfUseDHW() {
		builder.addWaterHeating(WaterHeater.PointOfUse, 1.0); // Point of use DHW is usually electric, but gas and solar models are also available, so we have settable efficiency.
		only(1.0, XHeatingSystem.AuxiliaryHotWater);
	}

	private void only(final double expected, final XHeatingSystem...types) {
		final Set<XHeatingSystem> typeSet = ImmutableSet.copyOf(types);

		for (final XHeatingSystem system : XHeatingSystem.values()) {
			if (typeSet.contains(system)) {
				test(system, expected);
			} else {
				test(system, 0.0);
			}
		}
	}

	private void testPrimarySpaceHeating(final double expected) {
		test(XHeatingSystem.PrimarySpaceHeating, expected);
	}

	private void testSecondarySpaceHeating(final double expected) {
		test(XHeatingSystem.SecondarySpaceHeating, expected);
	}

	private void testCentralHotWater(final double expected) {
		test(XHeatingSystem.CentralHotWater, expected);
	}

	private void testAuxiliaryHotWater(final double expected) {
		test(XHeatingSystem.AuxiliaryHotWater, expected);
	}

	private void test(final XHeatingSystem type, final double expected) {
		final HeatingEfficiencyFunction fun = new HeatingEfficiencyFunction(type, XEfficiencyMeasurement.Winter, null, null, dimension, null);
		when(scope.get(dimension)).thenReturn(builder.build());
		Assert.assertEquals("efficiency for " + type + " should be " + expected, expected, fun.compute(scope, ILets.EMPTY), NO_ERROR);
	}
}
