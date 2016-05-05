package uk.org.cse.nhm.simulation.measure.boilers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class ElectricBoilerMeasureTest extends AbstractGasBoilerMeasureTest {
	private StandardBoilerMeasure measure;
	
	private final IComponentsFunction<Number> efficiency =
        ConstantComponentsFunction.<Number>of(Name.of("eff"),0.86f)
        ;
    private final IComponentsFunction<Number> cylinderVolume = ConstantComponentsFunction.<Number>of(Name.of("Test vol"), 110f);
	private final float cylinderInsulation = 50f;
	
	@Before
	public void createMeasure() {
		measure = new StandardBoilerMeasure(
				dims.time, 
				Util.mockWetHeatingMeasureFactory(),
				dims.technology,
				dims.structure, 
				operations, 
				FuelType.ELECTRICITY, 
				Util.mockSizingFunction(Optional.of(1000d)),
				Util.mockCapexFunction(1000d, 2000d),
				Util.mockOpexFunction(1000d, 2000d, 3000d), 
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),				
				efficiency,
				efficiency,
				cylinderVolume,
				cylinderInsulation, 
				0d,
				0d);
	}
	
	@After
	public void clear() {
		measure = null;
	}

	@Test
	public void testBasicInstallation() throws NHMException {
		assertBoilerBasics(applyAndGetResult(measure), 1d, FuelType.ELECTRICITY, EmitterType.RADIATORS, false, 1983, cylinderInsulation,
                           cylinderVolume.compute(null, null).doubleValue());
		
		// ToDo: off peak 7 tariff type
		// ToDo: high rate fraction
	}
}
