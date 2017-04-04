package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class GetTariffTest {

	private IDimension<ITariffs> tariffsDimension;
	private GetTariff getTariff;
	private IComponentsScope scope;
	private ITariffs tariffs;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		tariffsDimension = mock(IDimension.class);
		getTariff = new GetTariff(FuelType.OIL, tariffsDimension);
		scope = mock(IComponentsScope.class);
		tariffs = mock(ITariffs.class);
		when(scope.get(tariffsDimension)).thenReturn(tariffs);
	}
	
	@Test
	public void checkDependencies() {
		Assert.assertEquals("Depends on tariffs dimension.", Collections.singleton(tariffsDimension), getTariff.getDependencies());
	}
	
	@Test
	public void testGet() {
		final ITariff toFind = mock(ITariff.class);
		when(tariffs.getTariff(FuelType.OIL)).thenReturn(toFind);
		Assert.assertSame("Should find the tariff from the scope by fuel.", toFind, getTariff.compute(scope, ILets.EMPTY));
	}
}
