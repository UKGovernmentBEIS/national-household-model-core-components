package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class BreakBoilerMeasureTest {

	private static final Optional<IBoiler> NO_BOILER = Optional.absent();
	private IDimension<ITechnologyModel> techDimension;
	private ITechnologyOperations operations;
	
	private ISettableComponentsScope scope;
	private ITechnologyModel tech;
	private Optional<IBoiler> boiler;
	private BreakBoilerMeasure measure;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		techDimension = mock(IDimension.class);
		operations = mock(ITechnologyOperations.class);
		
		tech = mock(ITechnologyModel.class);
		scope = new TestTechScope(tech);		
		boiler = Optional.of(mock(IBoiler.class));
		when(operations.getBoiler(tech)).thenReturn(boiler);
		
		measure = new BreakBoilerMeasure(techDimension, operations);
	}
	
	@Test
	public void testSuitability() {
		when(operations.getBoiler(tech)).thenReturn(NO_BOILER);
		Assert.assertFalse("Should be unsuitable if no boiler was found.", measure.isSuitable(scope, ILets.EMPTY));
		
		when(operations.getBoiler(tech)).thenReturn(boiler);
		Assert.assertTrue("Should be suitable if a boiler was found.", measure.isSuitable(scope, ILets.EMPTY));
	}
	
	@Test
	public void shouldRemoveBoilerAndAttemptSecondaryMeasures() {
		scope.apply(measure, ILets.EMPTY);
		verify(operations, times(1)).removeHeatSource(boiler.get());
	}
}
