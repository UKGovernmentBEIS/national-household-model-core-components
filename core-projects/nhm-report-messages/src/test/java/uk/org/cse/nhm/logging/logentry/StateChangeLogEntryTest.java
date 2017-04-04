package uk.org.cse.nhm.logging.logentry;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry.EntryType;
import uk.org.cse.nhm.logging.logentry.components.AbstractFuelServiceLogComponent;
import uk.org.cse.nhm.logging.logentry.components.BasicCaseAttributesLogComponent;
import uk.org.cse.nhm.logging.logentry.components.CostsLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EmissionsLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EnergyLogComponent;

public class StateChangeLogEntryTest {

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		final AbstractFuelServiceLogComponent.MapBuilder builder = 
				AbstractFuelServiceLogComponent.MapBuilder.builder();
		builder.put(FuelType.BIOMASS_WOODCHIP, ServiceType.PRIMARY_SPACE_HEATING, 99d);
		builder.put(FuelType.MAINS_GAS, ServiceType.INTERNALS, 19d);
	
		final Map<FuelType, Map<ServiceType, Double>> values = builder.build();
		final StateChangeLogEntry logEntry = new StateChangeLogEntry(
				99,
				3f,
				EntryType.CREATION,
				new DateTime(12345),
				new BasicCaseAttributesLogComponent(1234),
				new EnergyLogComponent(values),
				new EmissionsLogComponent(values),
				new CostsLogComponent(values)
				);
		LogEntryTestUtility.testLogDeSerialisation(logEntry, StateChangeLogEntry.class);
	}
}
