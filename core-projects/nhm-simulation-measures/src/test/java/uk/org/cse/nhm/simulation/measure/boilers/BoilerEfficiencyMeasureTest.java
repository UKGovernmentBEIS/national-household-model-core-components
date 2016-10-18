package uk.org.cse.nhm.simulation.measure.boilers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.definition.enums.XChangeDirection;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;

public class BoilerEfficiencyMeasureTest {

	private static final Optional<Double> UNCHANGED = Optional.<Double>absent();
	private IDimension<ITechnologyModel> techDimension;
	private ITechnologyOperations operations;
	
	private ISettableComponentsScope scope;
	private ITechnologyModel tech;
	private Optional<IBoiler> boiler;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		techDimension = mock(IDimension.class);
		operations = mock(ITechnologyOperations.class);
		
		tech = mock(ITechnologyModel.class);
		scope = new TestTechScope(tech);		
		boiler = Optional.of(mock(IBoiler.class));
		when(operations.getBoiler(tech)).thenReturn(boiler);
	}
	
	@Test
	public void setsEfficiency() {
		testApplication(0.0, Optional.of(0.5), 1.0, Optional.of(0.5), 0.5, XChangeDirection.Set);
	}
	
	@Test
	public void increasesEfficiency() {
		testApplication(0.5, Optional.of(0.9), 0.5, Optional.of(0.9), 0.9, XChangeDirection.Increase);
	}
	
	@Test
	public void doesntIncreaseSummerIfAlreadyHigher() {
		testApplication(1.0, UNCHANGED, 0.5, Optional.of(0.9), 0.9, XChangeDirection.Increase);
	}
	
	@Test
	public void doesntIncreaseWinterIfAlreadyHigher() {
		testApplication(0.5, Optional.of(0.9), 1.0, UNCHANGED, 0.9, XChangeDirection.Increase);
	}
	
	@Test
	public void decreasesEfficiency() {
		testApplication(0.8, Optional.of(0.6), 0.8, Optional.of(0.6), 0.6, XChangeDirection.Decrease);
	}
	
	@Test
	public void doesntDecreaseSummerIfAlreadyLower() {
		testApplication(0.5, UNCHANGED, 0.8, Optional.of(0.6), 0.6, XChangeDirection.Decrease);
	}
	
	@Test
	public void doesntDecreaseWinterIfAlreadyLower() {
		testApplication(0.8, Optional.of(0.6), 0.5, UNCHANGED, 0.6, XChangeDirection.Decrease);
	}
	
	private void testApplication(final double originalSummer, final Optional<Double> newSummer, final double originalWinter, final Optional<Double> newWinter, final double set, final XChangeDirection direction) {
		when(operations.getBoiler(tech).get().getWinterEfficiency()).thenReturn(Efficiency.fromDouble(originalWinter));
		when(operations.getBoiler(tech).get().getSummerEfficiency()).thenReturn(Efficiency.fromDouble(originalSummer));
		
		final BoilerEfficiencyMeasure measure = new BoilerEfficiencyMeasure(
				techDimension, 
				operations,
				Optional.of(ConstantComponentsFunction.<Number>of(Name.of("target winter efficiency"), set)),
				Optional.of(ConstantComponentsFunction.<Number>of(Name.of("target summer efficiency"), set)), 
				direction,
				Optional.<HeatingEfficiencyFunction>absent(),
				Optional.<HeatingEfficiencyFunction>absent());
		
		scope.apply(measure, ILets.EMPTY);
		
		verify(operations).changeHeatingEfficiency(tech, set, set,
				direction == XChangeDirection.Set || direction == XChangeDirection.Increase, 
				direction == XChangeDirection.Set || direction == XChangeDirection.Decrease);
	}
}
