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
import static uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.SpaceHeater.WarmAir;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.functions.impl.num.TechnologyTestBuilder.HeatSource;

public class HeatingResponsivenessFunctionTest {

	private static final double NO_ERROR = 0.000001;
	private IComponentsScope scope;
	private IDimension<ITechnologyModel> techDimension;
	private IDimension<IHeatingBehaviour> behaviourDimension;
	private TechnologyTestBuilder builder;
	private IConstants constants;
	private IHeatingBehaviour behaviour;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(IComponentsScope.class);
		techDimension = mock(IDimension.class);
		behaviourDimension = mock(IDimension.class);
		behaviour = mock(IHeatingBehaviour.class);
		builder = new TechnologyTestBuilder();
		constants = DefaultConstants.INSTANCE;
		
		when(scope.get(techDimension)).thenReturn(builder.build());
		when(scope.get(behaviourDimension)).thenReturn(behaviour);
		when(behaviour.getEnergyCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
	}
	
	@Test
	public void noHeating() {
		test(1.0);
	}
	
	@Test
	public void unconnectedCentralHeating() {
		builder.addSpaceHeating(CentralHeating);
		test(1.0);
	}
	
	@Test
	public void backBoiler() {
		builder.addHeatSource(BackBoiler);
		test(1.0);
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
			test(e.getValue());
		}
	}
	
	@Test
	public void boiler() {
		builder.addHeatSource(Boiler, FuelType.HOUSE_COAL);
		test(1.0);
		
		testSAPTable4d(Boiler);
	}

	@Test
	public void instantaneousCombiBoiler() {
		builder.addHeatSource(InstantaneousCombiBoiler, FuelType.HOUSE_COAL);
		test(0.75);
	}
	
	@Test
	public void storageCombiBoiler() {
		builder.addHeatSource(StorageCombiBoiler, FuelType.ELECTRICITY);
		test(0.75);
	}
	
	@Test
	public void cpsu() {
		builder.addHeatSource(CPSU, FuelType.ELECTRICITY);
		test(1.0);
	}
	
	@Test
	public void heatPump() {
		for (final Entry<EmitterType, Double> e : sapTable4d.entrySet()) {
			builder.addHeatSource(HeatPump, e.getKey());
			test(e.getValue());
		}
	}
	
	@Test
	public void communityHeatSource() {
		builder.addHeatSource(Community);
		test(1.0);
	}
	
	@Test
	public void communityCHP() {
		builder.addHeatSource(CommunityCHP);
		test(1.0);
	}
	
	@Test
	public void warmAir() {
		builder.addSpaceHeating(WarmAir, FuelType.ELECTRICITY);
		test(0.75);
		
		builder.addSpaceHeating(WarmAir);
		test(1.0);
	}
	
	@Test
	public void storageHeaters() {
		// Overriding the responsiveness works under BREDEM
		when(behaviour.getEnergyCalculatorType()).thenReturn(EnergyCalculatorType.BREDEM2012);
		builder.addStorageHeating(StorageHeaterType.OLD_LARGE_VOLUME, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 1.0);
		test(1.0);
		
		// Always falls back to default responsiveness under SAP
		when(behaviour.getEnergyCalculatorType()).thenReturn(EnergyCalculatorType.SAP2012);
		builder.addStorageHeating(StorageHeaterType.OLD_LARGE_VOLUME, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 1.0);
		test(0.0);
		
		testStorage(StorageHeaterType.OLD_LARGE_VOLUME, 0, 0);
		testStorage(StorageHeaterType.CONVECTOR, 0.2, 0.4);
		testStorage(StorageHeaterType.SLIMLINE, 0.2, 0.4);
		testStorage(StorageHeaterType.FAN, 0.4, 0.6);
		testStorage(StorageHeaterType.INTEGRATED_DIRECT_ACTING, 0.6, 0.6);
	}
	
	private void testStorage(StorageHeaterType type, double expected, double celectExpected) {
		builder.addStorageHeating(type, StorageHeaterControlType.MANUAL_CHARGE_CONTROL);
		test(expected);
		builder.addStorageHeating(type, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL);
		test(expected);
		builder.addStorageHeating(type, StorageHeaterControlType.CELECT_CHARGE_CONTROL);
		test(celectExpected);
	}
	
	@Test
	public void roomHeater() {
		builder.addSpaceHeating(RoomHeater);
		test(1.0);
	}
	
	private void test(final double expected) {
		final HeatingResponsivenessFunction fun = new HeatingResponsivenessFunction(constants, techDimension, behaviourDimension);
		when(scope.get(techDimension)).thenReturn(builder.build());
		Assert.assertEquals("responsiveness is " + expected, expected, fun.compute(scope, ILets.EMPTY), NO_ERROR);
	}
}
