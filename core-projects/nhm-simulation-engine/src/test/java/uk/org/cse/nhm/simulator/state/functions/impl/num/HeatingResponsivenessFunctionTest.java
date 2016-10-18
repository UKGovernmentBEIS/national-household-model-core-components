package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.BackBoiler;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.Boiler;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.CPSU;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.Community;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.CommunityCHP;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.HeatPump;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.InstantaneousCombiBoiler;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource.StorageCombiBoiler;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater.CentralHeating;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater.RoomHeater;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater.StorageHeater;
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater.WarmAir;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource;

public class HeatingResponsivenessFunctionTest {

	private static final double NO_ERROR = 0.0;
	private IComponentsScope scope;
	private IDimension<ITechnologyModel> dimension;
	private TechnologyTestBuilder builder;
	private IConstants constants;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(IComponentsScope.class);
		dimension = mock(IDimension.class);
		builder = new TechnologyTestBuilder();
		constants = DefaultConstants.INSTANCE;
		
		when(scope.get(dimension)).thenReturn(builder.build());
	}
	
	@Test
	public void noHeating() {
		testPrimary(0.0);
		testSecondary(0.0);
	}
	
	@Test
	public void unconnectedCentralHeating() {
		builder.addSpaceHeating(CentralHeating);
		testPrimary(0.0);
		testSecondary(0.0);
	}
	
	@Test
	public void backBoiler() {
		builder.addHeatSource(BackBoiler);
		testPrimary(1.0);
		testSecondary(0.0);
	}
	
	private final Map<EmitterType, Double> sapTable4d = ImmutableMap.of(
			EmitterType.RADIATORS, 1.0,
			EmitterType.UNDERFLOOR_TIMBER, 1.0,
			EmitterType.UNDERFLOOR_SCREED, 0.75,
			EmitterType.UNDERFLOOR_CONCRETE, 0.25,
			EmitterType.WARM_AIR_FAN_COIL, 1.0);
	
	private void testSAPTable4d(final HeatSource source) {
		for (final Entry<EmitterType, Double> e : sapTable4d.entrySet()) {
			builder.addHeatSource(source, FuelType.MAINS_GAS, e.getKey());
			testPrimary(e.getValue());
			testSecondary(0.0);
		}
	}
	
	@Test
	public void boiler() {
		builder.addHeatSource(Boiler, FuelType.HOUSE_COAL);
		testPrimary(1.0);
		testSecondary(0.0);
		
		testSAPTable4d(Boiler);
	}

	@Test
	public void instantaneousCombiBoiler() {
		builder.addHeatSource(InstantaneousCombiBoiler, FuelType.HOUSE_COAL);
		testPrimary(1.0);
		testSecondary(0.0);
		
		testSAPTable4d(InstantaneousCombiBoiler);
	}
	
	@Test
	public void storageCombiBoiler() {
		builder.addHeatSource(StorageCombiBoiler, FuelType.ELECTRICITY);
		testPrimary(1.0);
		testSecondary(0.0);
		
		testSAPTable4d(StorageCombiBoiler);
	}
	
	@Test
	public void cpsu() {
		builder.addHeatSource(CPSU, FuelType.ELECTRICITY);
		testPrimary(1.0);
		testSecondary(0.0);
		
		testSAPTable4d(CPSU);
	}
	
	@Test
	public void heatPump() {
		for (final Entry<EmitterType, Double> e : sapTable4d.entrySet()) {
			builder.addHeatSource(HeatPump, e.getKey());
			testPrimary(e.getValue());
		}
	}
	
	@Test
	public void communityHeatSource() {
		builder.addHeatSource(Community);
		testPrimary(1.0);
		testSecondary(0.0);
	}
	
	@Test
	public void communityCHP() {
		builder.addHeatSource(CommunityCHP);
		testPrimary(1.0);
		testSecondary(0.0);
	}
	
	@Test
	public void warmAir() {
		builder.addSpaceHeating(WarmAir, FuelType.ELECTRICITY);
		testPrimary(0.75);
		testSecondary(0.0);
		
		builder.addSpaceHeating(WarmAir);
		testPrimary(1.0);
		testSecondary(0.0);
	}
	
	@Test
	public void storage() {
		// Default responsiveness for a storage heater is 0.0, so we'll set it. It normally comes out of the stock import.
		builder.addSpaceHeating(StorageHeater, 1.0);
		testPrimary(1.0); 
		testSecondary(0.0);
	}

	@Test
	public void roomHeater() {
		// Default responsiveness for a room heater is 0.0, so we'll set it here. It normally comes out of the stock import.
		builder.addSpaceHeating(RoomHeater, 1.0);
		testPrimary(0.0);
		testSecondary(1.0);
	}
	
	private void testPrimary(final double expected) {
		test(XSpaceHeatingSystem.PrimarySpaceHeating, expected);
	}

	private void testSecondary(final double expected) {
		test(XSpaceHeatingSystem.SecondarySpaceHeating, expected);
	}

	private void test(final XSpaceHeatingSystem type, final double expected) {
		final HeatingResponsivenessFunction fun = new HeatingResponsivenessFunction(type, constants, dimension);
		when(scope.get(dimension)).thenReturn(builder.build());
		Assert.assertEquals("responsiveness is " + expected, expected, fun.compute(scope, ILets.EMPTY), NO_ERROR);
	}
}
