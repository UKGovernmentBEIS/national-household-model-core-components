package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.doubleThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.DummyHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

public class BoilerTest {
	private BoilerImpl boiler;

	@Before
	public void create() {
		boiler = (BoilerImpl) IBoilersFactory.eINSTANCE.createBoiler();
		boiler.setFuel(FuelType.MAINS_GAS);

		final ICentralWaterSystem system = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();
		final IMainWaterHeater heater = ITechnologiesFactory.eINSTANCE.createMainWaterHeater();

		heater.setHeatSource(boiler);
		system.setPrimaryWaterHeater(heater);

		final ICentralHeatingSystem hs = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
		boiler.setSpaceHeater(hs);
	}

	@After
	public void clear() {
		boiler = null;
	}

	private double around(final double value, final double margin) {
		return doubleThat(new BaseMatcher<Double>() {
			@Override
			public boolean matches(final Object arg0) {
				if (arg0 instanceof Double) {
					return Math.abs(((Double) arg0).doubleValue() - value) < margin;
				}
				return false;
			}

			@Override
			public void describeTo(final Description arg0) {
				arg0.appendText(String.format("%f +/- %f", value, margin));
			}
		});
	}

	private double around(final double value) {
		return around(value, 0.00001);
	}

	@Test
	public void testGenerateHotWater() {
		boiler.setSummerEfficiency(Efficiency.fromDouble(1.0));
		boiler.setWinterEfficiency(Efficiency.fromDouble(1.0));

		boiler.getWaterHeater().getSystem().setPrimaryPipeworkInsulated(true);

		final IEnergyState state = mock(IEnergyState.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);

		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

		final ArgumentCaptor<EnergyType> fuelTypeCaptor = ArgumentCaptor.forClass(EnergyType.class);

		when(state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100.0);
		when(state.getTotalDemand(EnergyType.DemandsHOT_WATER)).thenReturn(100.0);

		final IWaterTank tank = mock(IWaterTank.class);
		when(tank.isThermostatFitted()).thenReturn(true);

		final double generated = boiler.generateHotWaterAndPrimaryGains(parameters, state,
				tank, false, 1, 1, 0.5);

		// 50 W of demand met, plus 1 W of primary pipework losses
		verify(state).increaseDemand(fuelTypeCaptor.capture(), around(50 + 1));

		// 50 W of demand met
		verify(state).increaseSupply(eq(EnergyType.DemandsHOT_WATER), around(50));

		// 1 W of primary pipework losses
		verify(state).increaseSupply(eq(EnergyType.GainsHOT_WATER_SYSTEM_GAINS), around(1));

		assertEquals(50d, generated, 0d);
	}

	@Test
	public void testGenerateSystemLosses() {
		boiler.setSummerEfficiency(Efficiency.fromDouble(1.0));
		boiler.setWinterEfficiency(Efficiency.fromDouble(1.0));

		final IWaterTank tank = mock(IWaterTank.class);
		when(tank.isThermostatFitted()).thenReturn(true);

		final IEnergyState state = mock(IEnergyState.class);
		final IInternalParameters parameters = mock(IInternalParameters.class);
		boiler.generateHotWaterSystemGains(parameters, state, tank, false, 50);

		final ArgumentCaptor<EnergyType> fuelTypeCaptor = ArgumentCaptor.forClass(EnergyType.class);

		verify(state).increaseDemand(fuelTypeCaptor.capture(), around(50));
		verify(state).increaseSupply(eq(EnergyType.GainsHOT_WATER_SYSTEM_GAINS), around(50));
	}

	/**
	 * Check that the whole thing works: the
	 */
	@Test
	public void testGenerateSpaceAndWaterHeat() {
		final IInternalParameters parameters = mock(IInternalParameters.class);
		final IEnergyCalculatorVisitor visitor = mock(IEnergyCalculatorVisitor.class);

		when(parameters.getConstants()).thenReturn(DefaultConstants.INSTANCE);

		when(parameters.getInternalEnergyType(boiler)).thenReturn(EnergyType.FuelINTERNAL1);
		final double summerEfficiency = 0.8, winterEfficiency = 0.7;
		boiler.setSummerEfficiency(Efficiency.fromDouble(summerEfficiency));
		boiler.setWinterEfficiency(Efficiency.fromDouble(winterEfficiency));
		final double adjustedWinterEfficiency = winterEfficiency - 0.05;
		final double adjustedSummerEfficiency = summerEfficiency - 0.05;
		boiler.acceptFromHeating(DefaultConstants.INSTANCE, parameters, visitor, 1.0, 0);

		boiler.accept(DefaultConstants.INSTANCE, parameters, visitor, new AtomicInteger(), DummyHeatProportions.instance);

		final ArgumentCaptor<IEnergyTransducer> transducerCaptor = ArgumentCaptor.forClass(IEnergyTransducer.class);

		verify(visitor, Mockito.atLeastOnce()).visitEnergyTransducer(transducerCaptor.capture());

		final List<IEnergyTransducer> transducers = transducerCaptor.getAllValues();

		// The test is overly restrictive here; going to assume that the transducers are already in the right order
		// which they don't have to be really.

		final IEnergyCalculatorHouseCase house = mock(IEnergyCalculatorHouseCase.class);
		final ISpecificHeatLosses losses = mock(ISpecificHeatLosses.class);
		final ClassEnergyState state = new ClassEnergyState();

		state.setCurrentServiceType(ServiceType.SINKS, "Test Case");

		final double qHeat = 100, qWater = 100;
		final double qSystem = 30;

		state.increaseDemand(EnergyType.DemandsHEAT, qHeat);
		state.increaseDemand(EnergyType.DemandsHOT_WATER, qWater);

		state.setCurrentServiceType(ServiceType.WATER_HEATING, "Test Case");

		final IWaterTank store = mock(IWaterTank.class);
		when(store.isThermostatFitted()).thenReturn(true);


		boiler.generateHotWaterAndPrimaryGains(parameters, state, store, false, 0, 1, 1);
		boiler.generateHotWaterSystemGains(parameters, state, store, false, qSystem);

		final ArgumentCaptor<IEnergyTransducer> captor2 = ArgumentCaptor.forClass(IEnergyTransducer.class);

		verify(visitor, Mockito.atLeastOnce()).visitEnergyTransducer(captor2.capture());

		for (final IEnergyTransducer transducer : transducers) {
			state.setCurrentServiceType(transducer.getServiceType(), transducer.toString());

			transducer.generate(house, parameters, losses, state);
		}

		// system efficiency should be (qheat + qwater) / (qwinter / qheat + qsummer / qheat)

		final double systemEfficiency = (qHeat + qWater + qSystem) / ( (qHeat/adjustedWinterEfficiency) + ((qWater + qSystem) / adjustedSummerEfficiency) );

		final double gasRequiredForWater = (qWater + qSystem) / systemEfficiency;
		final double gasRequiredForHeat = qHeat / adjustedWinterEfficiency;

		Assert.assertEquals(gasRequiredForHeat + gasRequiredForWater, state.getTotalDemand(EnergyType.FuelGAS), 0.1);
		Assert.assertEquals(gasRequiredForHeat, state.getTotalDemand(EnergyType.FuelGAS, ServiceType.PRIMARY_SPACE_HEATING), 0.1);
		Assert.assertEquals(gasRequiredForWater, state.getTotalDemand(EnergyType.FuelGAS, ServiceType.WATER_HEATING), 0.1);
		Assert.assertEquals(qSystem, state.getTotalSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS), 0.1);
	}
}
