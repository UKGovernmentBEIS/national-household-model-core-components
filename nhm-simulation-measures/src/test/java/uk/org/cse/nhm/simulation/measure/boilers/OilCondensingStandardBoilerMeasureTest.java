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

public class OilCondensingStandardBoilerMeasureTest extends AbstractGasBoilerMeasureTest {
	private StandardBoilerMeasure constructMeasure() {
		return new StandardBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology, 
				dims.structure, 
				operations,
				
				FuelType.OIL, 
				Util.mockSizingFunction(Optional.of(1000d)),
				Util.mockCapexFunction(1000d, 2000d),
				Util.mockOpexFunction(1000d, 2000d, 3000d),
				
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				ConstantComponentsFunction.<Number>of(Name.of("Winter Eff"), 0.5d),
				ConstantComponentsFunction.<Number>of(Name.of("Summer Eff"), 0.5d),

                ConstantComponentsFunction.<Number>of(Name.of("Test vol"), 100d),
				50d, 
				0d,
				0d);
	}
	
	@Test
	public void testInstallIntoEmptyHouse() throws NHMException {
		final StandardBoilerMeasure m = constructMeasure();
		
		final StructureModel structure = new StructureModel() {
			{
				setOnGasGrid(false);
			}
		};
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};

		final IPowerTable ecr = mock(IPowerTable.class);
		
		final ISettableComponentsScope components = Util.mockComponents(dims, structure, technologies, ecr);
		
		final ITechnologyModel resultTechnologies = Util.applyAndGetTech(dims, m, components);
		
		assertBoilerBasics(resultTechnologies, 0.5f, FuelType.OIL, EmitterType.RADIATORS, true, 1983, 50, 100);
	}
}
