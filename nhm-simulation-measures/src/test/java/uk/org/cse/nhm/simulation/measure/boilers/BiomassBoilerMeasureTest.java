package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.simulation.measure.util.Util;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
	 * Make a house case with or without the various conditions which needs to be good for the measures
	 * @param enoughExternalSpace if true has enough external space
	 * @param isNotFlat if true it's not a flat
	 * @param enoughFloorArea if true, there is enough floor area
	 * @return
	 * @throws NHMException 
	 */
public class BiomassBoilerMeasureTest extends AbstractGasBoilerMeasureTest {
	private StandardBoilerMeasure measure;
	private final float minimumExternalSpace = 1f;
	private final float minimumFloorArea = 1f;
	private final IComponentsFunction<Number> efficiency =
        ConstantComponentsFunction.<Number>of(Name.of("Test eff"), 0.8d)
        ;
	private final IComponentsFunction<Number> cylinderVolume = ConstantComponentsFunction.<Number>of(Name.of("Test vol"), 110f);
	private final float cylinderInsulationThickness = 50f;
	
	@Before
	public void initalize() {
		measure = new StandardBoilerMeasure(
				dims.time,
				Util.mockWetHeatingMeasureFactory(),
				dims.technology,
				dims.structure,
				operations,
				FuelType.BIOMASS_PELLETS,
				Util.mockSizingFunction(Optional.of(1000d)),
				Util.mockCapexFunction(1000d, 2000d),
				Util.mockOpexFunction(1000d, 2000d, 3000d),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				efficiency,
				efficiency,
				cylinderVolume,
				cylinderInsulationThickness,
				minimumFloorArea,
				minimumExternalSpace);
	}
	
	@Test
	public void testSuitability() throws NHMException {
		final boolean[] tf = new boolean[] {false, true};

		for (final boolean espace : tf) {
			for (final boolean notFlat : tf) {
				for (final boolean ispace : tf) {
					final IComponentsScope hc = makeHouseCase(espace, notFlat, ispace);
					Assert.assertEquals(
							String.format("external space: %s, not a flat : %s, internal space: %s",
									espace, notFlat, ispace),
							espace && notFlat && ispace,
							measure.isSuitable(hc, ILets.EMPTY));
				}
			}
		}
	}
	
	/**
	 * Make a house case with or without the various conditions which needs to be good for the measures
	 * @param enoughExternalSpace if true has enough external space
	 * @param isNotFlat if true it's not a flat
	 * @param enoughFloorArea if true, there is enough floor area
	 * @return
	 * @throws NHMException 
	 */
	private ISettableComponentsScope makeHouseCase(final boolean enoughExternalSpace, final boolean isNotFlat, final boolean enoughFloorArea) throws NHMException {
		final StructureModel structure = new StructureModel(isNotFlat ? BuiltFormType.Detached : BuiltFormType.PurposeBuiltHighRiseFlat) {
			{
				setBackPlotDepth(1.0);
				setBackPlotWidth(enoughExternalSpace ? minimumExternalSpace : minimumExternalSpace - 1);
				setFrontPlotDepth(1.0);
				setFrontPlotWidth(enoughExternalSpace ? minimumExternalSpace : minimumExternalSpace - 1);
			}
			
			@Override
			public double getFloorArea() {
				return enoughFloorArea ? minimumFloorArea : minimumFloorArea-1;
			}
		};
		
		final ITechnologyModel technologies = new TechnologyModelImpl() {
		};
		
		return Util.mockComponents(dims, structure, technologies, mock(IPowerTable.class));
	}
	
	@Test
	public void testBasicInstallation() throws NHMException {
		final ISettableComponentsScope input = makeHouseCase(true, true, true);
		
		final ITechnologyModel output = Util.applyAndGetTech(dims, measure, input);
		
		assertBoilerBasics(output, 0.8, FuelType.BIOMASS_PELLETS, EmitterType.RADIATORS, false, 1983, cylinderInsulationThickness, cylinderVolume.compute(null, null).doubleValue());
		
		final IBoiler boiler = (IBoiler)output.getIndividualHeatSource();
		Assert.assertEquals(FlueType.FAN_ASSISTED_BALANCED_FLUE, boiler.getFlueType());
		Assert.assertEquals(3000d, boiler.getAnnualOperationalCost(), 0d);
	}
}
