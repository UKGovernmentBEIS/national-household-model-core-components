package uk.org.cse.nhm.simulator.action.fuels.extracharges;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class ExtraChargeTest {

	private ISettableComponentsScope scope;
	private IComponentsFunction<Number> amount;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(ISettableComponentsScope.class);
		amount = mock(IComponentsFunction.class);
	}

	@Test
	public void dependencies() {
		final ExtraCharge first = new ExtraCharge(Optional.absent(), "payee", ImmutableSet.of("tag"), amount, ImmutableSet.of());
		final ExtraCharge second = new ExtraCharge(Optional.absent(), "payee", ImmutableSet.of("tag"), amount, ImmutableSet.of(first));

		Assert.assertEquals("Only one dependency", 1, second.getDependencies().size());
		Assert.assertEquals("Should be first dependencies", first, second.getDependencies().toArray()[0]);
	}

	@Test(expected = IExtraCharge.DependencyWrongFuelTypeException.class)
	public void dependenciesShoudNotHaveDifferentTypes() {
		final ExtraCharge first = new ExtraCharge(Optional.of(FuelType.MAINS_GAS), "payee", ImmutableSet.of("tag"), amount, ImmutableSet.of());
		final ExtraCharge second = new ExtraCharge(Optional.of(FuelType.BIOMASS_PELLETS), "payee", ImmutableSet.of("tag"), amount, ImmutableSet.of(first));
	}

	@Test
	public void shouldIncludeDefaultTags() {
		final String payee = "payee";
		final ExtraCharge charge = new ExtraCharge(Optional.of(FuelType.MAINS_GAS), payee, ImmutableSet.of("tag"), amount, ImmutableSet.of());
		when(amount.compute(scope, ILets.EMPTY)).thenReturn(10.0);
		
		final ArgumentCaptor<IPayment> paymentCaptor = ArgumentCaptor.forClass(IPayment.class);
		charge.apply(scope, ILets.EMPTY);
		verify(scope, times(1)).addTransaction(paymentCaptor.capture());
		
		final IPayment payment = paymentCaptor.getValue();
		
		Assert.assertEquals("Amount should be computed by passed in function.", 10.0, payment.getAmount(), 0.0);
		Assert.assertEquals("Payee should come from construct arg.", payee, payment.getPayee());
		Assert.assertEquals("Tags should include those passed in, plus the defaults.", 
				ImmutableSet.of("tag", ITransaction.Tags.FUEL, 
						ITransaction.Tags.FUEL.substring(1),
						FuelType.MAINS_GAS.name()), payment.getTags());
	}
}
