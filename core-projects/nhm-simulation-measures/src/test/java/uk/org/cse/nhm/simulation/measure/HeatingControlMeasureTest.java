package uk.org.cse.nhm.simulation.measure;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.util.BasicEList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class HeatingControlMeasureTest {
	@Mock
	private IDimension<ITechnologyModel> technologies;
	@Mock
	private IComponentsFunction<Number> capex;
	@Mock
	private ISettableComponentsScope scope;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(capex.compute(scope, ILets.EMPTY)).thenReturn(123d);
	}
	
	public void checkSuitable(final ITechnologyModel tech, final HeatingSystemControlType control, final boolean s) {
		final HeatingControlMeasure measure = 
				new HeatingControlMeasure(technologies, control, capex);
		
		when(scope.get(technologies)).thenReturn(tech);
		if (s) {
			Assert.assertTrue(measure.isSuitable(scope, ILets.EMPTY));
		} else {
			Assert.assertFalse(measure.isSuitable(scope, ILets.EMPTY));
		}
	}
	
	@Test
	public void suitableByHeatingSystemTypes() {
		final ITechnologyModel withWetCH = mock(ITechnologyModel.class);
		
		final ICentralHeatingSystem ch = mock(ICentralHeatingSystem.class);
		when(withWetCH.getPrimarySpaceHeater()).thenReturn(ch);
		when(ch.getControls()).thenReturn(new BasicEList<HeatingSystemControlType>());
		
		checkSuitable(withWetCH, HeatingSystemControlType.APPLIANCE_THERMOSTAT, true);
		checkSuitable(withWetCH, HeatingSystemControlType.ROOM_THERMOSTAT, true);
		checkSuitable(withWetCH, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE, true);
		checkSuitable(withWetCH, HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL, true);
		checkSuitable(withWetCH, HeatingSystemControlType.DELAYED_START_THERMOSTAT, true);
		checkSuitable(withWetCH, HeatingSystemControlType.PROGRAMMER, true);
		checkSuitable(withWetCH, HeatingSystemControlType.BYPASS, true);
		
		final ITechnologyModel withWas = mock(ITechnologyModel.class);
		
		final IWarmAirSystem wa = mock(IWarmAirSystem.class);
		when(withWas.getPrimarySpaceHeater()).thenReturn(wa);
		when(wa.getControls()).thenReturn(new BasicEList<HeatingSystemControlType>());
		
		checkSuitable(withWas, HeatingSystemControlType.APPLIANCE_THERMOSTAT, true);
		checkSuitable(withWas, HeatingSystemControlType.ROOM_THERMOSTAT, true);
		checkSuitable(withWas, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE, true);
		checkSuitable(withWas, HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL, true);
		checkSuitable(withWas, HeatingSystemControlType.DELAYED_START_THERMOSTAT, false);
		checkSuitable(withWas, HeatingSystemControlType.PROGRAMMER, false);
		checkSuitable(withWas, HeatingSystemControlType.BYPASS, false);
		
		final ITechnologyModel withRH = mock(ITechnologyModel.class);
		
		final IRoomHeater rh = mock(IRoomHeater.class);
		when(withRH.getSecondarySpaceHeater()).thenReturn(rh);
		checkSuitable(withRH, HeatingSystemControlType.APPLIANCE_THERMOSTAT, true);
		checkSuitable(withRH, HeatingSystemControlType.ROOM_THERMOSTAT, false);
		checkSuitable(withRH, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE, false);
		checkSuitable(withRH, HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL, false);
		checkSuitable(withRH, HeatingSystemControlType.DELAYED_START_THERMOSTAT, false);
		checkSuitable(withRH, HeatingSystemControlType.PROGRAMMER, false);
		checkSuitable(withRH, HeatingSystemControlType.BYPASS, false);
	}
	
	@Test
	public void unsuitableWhenAlreadyPresent() {
		final ITechnologyModel withWetCH = mock(ITechnologyModel.class);
		
		final ICentralHeatingSystem ch = mock(ICentralHeatingSystem.class);
		when(withWetCH.getPrimarySpaceHeater()).thenReturn(ch);
		final BasicEList<HeatingSystemControlType> control = new BasicEList<HeatingSystemControlType>();
		when(ch.getControls()).thenReturn(control);
		
		for (final HeatingSystemControlType ct :HeatingSystemControlType.values()) {
			control.clear();
			control.add(ct);
			checkSuitable(withWetCH, ct, false);			
		}
		
		final ITechnologyModel withWas = mock(ITechnologyModel.class);
		
		final IWarmAirSystem wa = mock(IWarmAirSystem.class);
		when(withWas.getPrimarySpaceHeater()).thenReturn(wa);
		when(wa.getControls()).thenReturn(control);
		

		for (final HeatingSystemControlType ct :HeatingSystemControlType.values()) {
			control.clear();
			control.add(ct);
			checkSuitable(withWas, ct, false);
		}
		
		final ITechnologyModel withRH = mock(ITechnologyModel.class);
		
		final IRoomHeater rh = mock(IRoomHeater.class);
		when(withRH.getSecondarySpaceHeater()).thenReturn(rh);
		when(rh.isThermostatFitted()).thenReturn(true);
		checkSuitable(withRH, HeatingSystemControlType.APPLIANCE_THERMOSTAT, false);
	}
	
	@Test
	public void appliesAsExpectedWithWetCH() {
		final HeatingControlMeasure measure = 
				new HeatingControlMeasure(technologies, HeatingSystemControlType.APPLIANCE_THERMOSTAT, capex);
		
		final ITechnologyModel withWetCH = mock(ITechnologyModel.class);
		
		final ICentralHeatingSystem ch = mock(ICentralHeatingSystem.class);
		when(withWetCH.getPrimarySpaceHeater()).thenReturn(ch);
		final BasicEList<HeatingSystemControlType> control = new BasicEList<HeatingSystemControlType>();
		when(ch.getControls()).thenReturn(control);
		
		when(scope.get(technologies)).thenReturn(withWetCH);
		
		checkAppliesOn(measure, withWetCH, control);
	}
	
	@Test
	public void appliesAsExpectedWithWarmAir() {
		final HeatingControlMeasure measure = 
				new HeatingControlMeasure(technologies, HeatingSystemControlType.APPLIANCE_THERMOSTAT, capex);
		
		final ITechnologyModel withWas = mock(ITechnologyModel.class);
		
		final IWarmAirSystem wa = mock(IWarmAirSystem.class);
		when(withWas.getPrimarySpaceHeater()).thenReturn(wa);
		final BasicEList<HeatingSystemControlType> control = new BasicEList<HeatingSystemControlType>();
		when(wa.getControls()).thenReturn(control);

		when(scope.get(technologies)).thenReturn(withWas);
		checkAppliesOn(measure, withWas, control);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void appliesAsExpectedWithRoomHeater() {
		final HeatingControlMeasure measure = 
				new HeatingControlMeasure(technologies, HeatingSystemControlType.APPLIANCE_THERMOSTAT, capex);
		
		final ITechnologyModel withRH = mock(ITechnologyModel.class);
		
		final IRoomHeater rh = mock(IRoomHeater.class);
		when(withRH.getSecondarySpaceHeater()).thenReturn(rh);
		
		when(scope.get(technologies)).thenReturn(withRH);
		Assert.assertTrue(measure.apply(scope, ILets.EMPTY));
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<IModifier> modifier = ArgumentCaptor.forClass(IModifier.class);
		verify(scope).modify(same(technologies), modifier.capture());
		
		modifier.getValue().modify(withRH);
		verify(rh).setThermostatFitted(true);
		verifyPaidForThing();
	}

	private void verifyPaidForThing() {
		final ArgumentCaptor<IPayment> cap = ArgumentCaptor.forClass(IPayment.class);
		
		verify(scope).addTransaction(cap.capture());
		
		final IPayment p = cap.getValue();
		Assert.assertEquals(123d, p.getAmount(), 0);
	}

	@SuppressWarnings("unchecked")
	void checkAppliesOn(final HeatingControlMeasure measure, final ITechnologyModel model, final BasicEList<HeatingSystemControlType> control) {
		control.clear();

		Assert.assertTrue(measure.apply(scope, ILets.EMPTY));
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<IModifier> modifier = ArgumentCaptor.forClass(IModifier.class);
		verify(scope).modify(same(technologies), modifier.capture());
		
		modifier.getValue().modify(model);
		
		Assert.assertTrue(control.contains(HeatingSystemControlType.APPLIANCE_THERMOSTAT));
		verifyPaidForThing();
	}
}
