package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.FunctionTariff.FuelRule;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.FunctionTariff.FuelRule.Transactor;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class FunctionTariffTest {

    @SuppressWarnings("unchecked")
    @Test
    public void transactorInRuleInTariffMakesTransactions() {
        final IComponentsFunction<Number> fn = mock(IComponentsFunction.class);

        final Transactor t = new FunctionTariff.FuelRule.Transactor(fn, "steve", ImmutableSet.of("hello"));

        final FuelRule fr = new FuelRule(FuelType.MAINS_GAS, ImmutableList.of(t));

        final FunctionTariff ft = new FunctionTariff(XMethodOfPayment.DirectDebit, ImmutableList.of(fr));

        ft.setIdentifier(Name.of("test"));

        final ISettableComponentsScope scope = mock(ISettableComponentsScope.class);

        when(fn.compute(same(scope), any(ILets.class))).thenReturn(123d);

        ft.apply(FuelType.MAINS_GAS, scope);

        final ArgumentCaptor<IPayment> p = ArgumentCaptor.forClass(IPayment.class);

        verify(scope).addTransaction(p.capture());

        final IPayment payment = p.getValue();

        Assert.assertEquals(123d, payment.getAmount(), 0.01);
        Assert.assertEquals("steve", payment.getPayee());
        Assert.assertEquals(ImmutableSet.of("hello", ITransaction.Tags.FUEL, ITransaction.Tags.FUEL.substring(1)), payment.getTags());

        Assert.assertEquals(ImmutableSet.of(FuelType.MAINS_GAS), ft.getFuelTypes());
    }
}
