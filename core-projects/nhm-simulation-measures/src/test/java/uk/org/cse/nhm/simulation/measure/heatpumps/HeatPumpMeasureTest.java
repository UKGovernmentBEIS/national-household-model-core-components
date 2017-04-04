package uk.org.cse.nhm.simulation.measure.heatpumps;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.heatpumps.AbstractHeatPumpMeasure.Hybrid;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulation.measure.util.Util.MockDimensions;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class HeatPumpMeasureTest {
	protected MockDimensions dims;
	
	@Before
	public void setup() {
		dims = Util.getMockDimensions();
	}
	
	@Test
	public void testApplicationOfMeasureToEmptyHouse() throws NHMException {		
		for (final HeatPumpSourceType heatPumpSourceType : HeatPumpSourceType.values()) {
			final AbstractHeatPumpMeasure m = new AbstractHeatPumpMeasure(
					dims.time,
					Util.mockWetHeatingMeasureFactory(),
					TechnologyType.airSourceHeatPump(),
					Util.mockSizingFunction(Optional.of(1d)),
					Util.mockCapexFunction(1d, 2d),
					Util.mockOpexFunction(1d, 2d, 3d),
					ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
                    ConstantComponentsFunction.<Number>of(Name.of("Test COP"), 3d),
					50,
                    ConstantComponentsFunction.<Number>of(Name.of("Test tank"), 110),
					heatPumpSourceType,
					new TechnologyOperations(),
					dims.technology,
					dims.structure,
					FuelType.ELECTRICITY,
					Optional.<Hybrid>absent()
					) {
				@Override
				protected boolean doDoIsSuitable(final IComponents house) {
					return true;
				}
			};
			
			final IPowerTable ecr = mock(IPowerTable.class);
			
			final StructureModel structure = new StructureModel();
			final ITechnologyModel technologies = new TechnologyModelImpl() {
			};
			
			final ISettableComponentsScope c = Util.mockComponents(dims, structure, technologies, ecr);
			
			final ITechnologyModel transformedTechnologies = Util.applyAndGetTech(dims, m, c);
			
			Assert.assertNotNull(transformedTechnologies.getPrimarySpaceHeater());
			Assert.assertNotNull(transformedTechnologies.getCentralWaterSystem());
			Assert.assertNotNull(transformedTechnologies.getIndividualHeatSource());
			
			final IHeatSource hs = transformedTechnologies.getIndividualHeatSource();
			Assert.assertTrue(hs instanceof IHeatPump);
			final IHeatPump heatPump = (IHeatPump) hs;
			Assert.assertEquals(heatPumpSourceType, heatPump.getSourceType());
			Assert.assertEquals(1983, hs.getInstallationYear());
			
			Assert.assertEquals(3d, hs.getAnnualOperationalCost(), 0d);
			
			assertCentralWaterHeatSourceSameAs(transformedTechnologies.getIndividualHeatSource(), transformedTechnologies);
		}
	}

	private void assertCentralWaterHeatSourceSameAs(final IHeatSource expected, final ITechnologyModel actual) {
		final IWaterHeater whs = actual.getCentralWaterSystem();
		Assert.assertTrue(whs instanceof ICentralWaterSystem);
		final ICentralWaterSystem centralHotWater = (ICentralWaterSystem) whs;
		Assert.assertTrue(centralHotWater.getPrimaryWaterHeater() instanceof IMainWaterHeater);
		final IMainWaterHeater mainWaterHeater = (IMainWaterHeater) centralHotWater.getPrimaryWaterHeater();
		Assert.assertSame(expected, mainWaterHeater.getHeatSource());
	}
	
	@Test
	public void testApplicationOfMeasureToHouseWithCommunityHotWater() throws NHMException {
		final ISimulator sim = mock(ISimulator.class);
		
		when(sim.getCurrentDate()).thenReturn(new DateTime(1983, 01, 01, 01, 01, 01, 01));
		
		for (final HeatPumpSourceType heatPumpSourceType : HeatPumpSourceType.values()) {

			
			final AbstractHeatPumpMeasure m = new AbstractHeatPumpMeasure(
					dims.time,
					Util.mockWetHeatingMeasureFactory(),
					TechnologyType.airSourceHeatPump(),
					
					Util.mockSizingFunction(Optional.of(1d)),
					Util.mockCapexFunction(1d, 2d),
					Util.mockOpexFunction(1d, 2d, 3d),
					ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
                    ConstantComponentsFunction.<Number>of(Name.of("Test COP"), 3d),
					50,
					ConstantComponentsFunction.<Number>of(Name.of("Test tank"), 110),
					heatPumpSourceType,
					new TechnologyOperations(),
					dims.technology,
					dims.structure,
					FuelType.ELECTRICITY,
					Optional.<Hybrid>absent()) {
				@Override
				protected boolean doDoIsSuitable(final IComponents house) {
					return true;
				}
			};
			
			final IPowerTable ecr = mock(IPowerTable.class);
			
			final ICommunityHeatSource community = new CommunityHeatSourceImpl() {
			};
			
			final StructureModel structure = new StructureModel();
			final ITechnologyModel technologies = new TechnologyModelImpl() {{
				setCommunityHeatSource(community);
				
				setPrimarySpaceHeater(new CentralHeatingSystemImpl() {{
					setHeatSource(community);
				}});
				
				setCentralWaterSystem(new CentralWaterSystemImpl() {{
					setPrimaryWaterHeater(new MainWaterHeaterImpl() {{
						setHeatSource(community);
					}});
				}});
			}};
			
			final ISettableComponentsScope c = Util.mockComponents(dims, structure, technologies, ecr);
			
			final ITechnologyModel transformedCase = Util.applyAndGetTech(dims, m, c);

			Assert.assertNotNull(transformedCase.getPrimarySpaceHeater());
			Assert.assertNotNull(transformedCase.getCentralWaterSystem());
			Assert.assertNotNull(transformedCase.getIndividualHeatSource());
			
			final IHeatSource hs = transformedCase.getIndividualHeatSource();
			Assert.assertTrue(hs instanceof IHeatPump);
			Assert.assertEquals(heatPumpSourceType, ((IHeatPump) hs).getSourceType());
			Assert.assertEquals(1983, hs.getInstallationYear());
			Assert.assertEquals(3d, hs.getAnnualOperationalCost(), 0d);
			assertCentralWaterHeatSourceSameAs(transformedCase.getCommunityHeatSource(), transformedCase);
		}
	}
}
