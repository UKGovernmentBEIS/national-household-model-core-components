package uk.org.cse.nhm.language.definition.fuel.validation;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.fuel.XFullTariff;
import uk.org.cse.nhm.language.definition.fuel.XTariffBase;
import uk.org.cse.nhm.language.definition.fuel.XTariffFuel;

public class TariffPerFuelValidatorTest {

	private TariffPerFuelValidator validator;
	private ConstraintValidatorContext context;
	private List<XTariffBase> defaultTariffs;

	@Before
	public void setup() {
		this.validator = new TariffPerFuelValidator();
		this.context = mock(ConstraintValidatorContext.class);
		this.defaultTariffs = new ArrayList<>();
	}
	
	@Test
	public void shouldAllowEmpty() {
		Assert.assertTrue("Empty list of tariffs is valid.", this.validator.isValid(this.defaultTariffs, this.context));
	}
	
	@Test
	public void shouldAllowTariffsCoveringDifferentFuelTypes() {
		addMockTariff(XFuelType.MainsGas, XFuelType.Electricity);
		addMockTariff(XFuelType.Oil);
		Assert.assertTrue("List of multiple tariffs is valid.", this.validator.isValid(this.defaultTariffs, this.context));
	}
	
	@Test
	public void shouldNotAllowSameTariffTwice() {
		final XFullTariff mockTariff = mockTariff(XFuelType.Electricity);
		this.defaultTariffs.add(mockTariff);
		this.defaultTariffs.add(mockTariff);
		
		Assert.assertFalse("Same tariff repeated is invalid.", this.validator.isValid(this.defaultTariffs, this.context));
	}
	
	@Test
	public void shouldNotAllowSameFuelTypeTwice() {
		addMockTariff(XFuelType.Oil, XFuelType.MainsGas);
		addMockTariff(XFuelType.Electricity, XFuelType.Oil);
		Assert.assertFalse("List of multiple tariffs with same fuel type is invalid.", this.validator.isValid(this.defaultTariffs, this.context));
	}
	
	@Test
	public void shouldNotAllowTariffWithSameFuelTwice() {
		addMockTariff(XFuelType.Oil, XFuelType.Oil);
		Assert.assertFalse("Tariff with same fuel type more than once is invalid.", this.validator.isValid(this.defaultTariffs, this.context));
	}
	
	private void addMockTariff(final XFuelType...fuels) {
		this.defaultTariffs.add(mockTariff(fuels));
	}
	
	private XFullTariff mockTariff(final XFuelType...fuels) {
		final XFullTariff result = new XFullTariff();
		
		for(final XFuelType fuel : fuels) {
			XTariffFuel tf = new XTariffFuel();
			tf.setFuel(fuel);
			result.getFuels().add(tf);
		}
		
		return result;
	}
}
