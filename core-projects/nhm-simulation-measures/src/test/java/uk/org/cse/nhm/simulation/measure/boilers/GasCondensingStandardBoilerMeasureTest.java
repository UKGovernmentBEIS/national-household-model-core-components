package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class GasCondensingStandardBoilerMeasureTest extends AbstractGasBoilerMeasureTest {
	private StandardBoilerMeasure constructMeasure() {
		return new StandardBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				operations,
				
				FuelType.MAINS_GAS, 
				Util.mockSizingFunction(Optional.of(1000d)),
				Util.mockCapexFunction(1000d, 2000d),
				Util.mockOpexFunction(1000d, 2000d, 3000d),
				
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
                ConstantComponentsFunction.<Number>of(Name.of("Test winter eff"), 0.5d),
                ConstantComponentsFunction.<Number>of(Name.of("Test summer eff"), 0.5d),
				ConstantComponentsFunction.<Number>of(Name.of("Test tank vol"), 100d),
				50d, 
				0d,
				0d);
	}
	
	@Test
	public void testInstallIntoEmptyHouse() throws NHMException {
		final StandardBoilerMeasure m = constructMeasure();
		
		final StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(true);
			}
		};
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};

		final IPowerTable ecr = mock(IPowerTable.class);
		
		final ISettableComponentsScope components = Util.mockComponents(dims, structure, technologies, ecr);
			
		assertBoilerBasics(Util.applyAndGetTech(dims, m, components), 0.5d, FuelType.MAINS_GAS, EmitterType.RADIATORS, true, 1983, 50, 100);
	}
	
	/**
	 * These tests for the base class ought still to work, because the suitability constraints are unchanged.
	 */
	@Test
	public void testBasicSuitability() {
		AbstractGasBoilerMeasureTest.testSuitabilityFalseGivenExistingCommunitySpaceHeating(dims, constructMeasure());
		AbstractGasBoilerMeasureTest.testSuitabilityFalseGivenExistingCommunityWaterHeating(dims, constructMeasure());
		AbstractGasBoilerMeasureTest.testSuitabilityOnGas(dims, constructMeasure());
		AbstractGasBoilerMeasureTest.testSuitabilityWithStandardHeatingSystemInstalledPreviously(dims, constructMeasure());
	}
}
