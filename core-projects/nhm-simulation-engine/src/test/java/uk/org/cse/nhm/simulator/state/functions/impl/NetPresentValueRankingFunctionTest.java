package uk.org.cse.nhm.simulator.state.functions.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.obligations.ObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.num.NetPresentValueFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Payment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class NetPresentValueRankingFunctionTest {

    private IComponentsScope scope;
    private IHypotheticalComponentsScope hypothesis;
    private ITimeDimension time;
    private DateTime now;
    private IDimension<IObligationHistory> obligations;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        scope = mock(IComponentsScope.class);
        hypothesis = mock(IHypotheticalComponentsScope.class);
        obligations = mock(IDimension.class);
        time = mock(ITimeDimension.class);
        now = new DateTime();
        when(scope.get(time)).thenReturn(TimeUtil.mockTime(now));
        when(scope.createHypothesis()).thenReturn(hypothesis);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void obligationsAreIncludedInFutureCosts() {
        when(scope.getAllNotes(ITransaction.class)).thenReturn(ImmutableList.<ITransaction>of());

        final IObligation obligation = mock(IObligation.class);

        final IObligationHistory history = mock(IObligationHistory.class);

        when(history.iterator()).thenReturn(ImmutableList.of(obligation).iterator());

        when(scope.get(obligations)).thenReturn(history);

        final DateTime plus5Months = now.plus(Period.months(5));

        final DateTime plusOne = now.plus(Period.years(1));

        final DateTime plusTwo = now.plus(Period.years(2));

        final DateTime plusTwoAndABit = now.plus(Period.years(2).plus(Period.months(4)));

        final DateTime plusThree = now.plus(Period.years(3));

        final DateTime plusThreeAndABit = now.plus(Period.years(3).plus(Period.seconds(1)));

        when(obligation.getNextTransactionDate(now)).thenReturn(Optional.of(plus5Months));
        when(obligation.getNextTransactionDate(plus5Months)).thenReturn(Optional.of(plusOne));
        when(obligation.getNextTransactionDate(plusOne)).thenReturn(Optional.of(plusTwo));
        when(obligation.getNextTransactionDate(plusTwo)).thenReturn(Optional.of(plusTwoAndABit));
        when(obligation.getNextTransactionDate(plusTwoAndABit)).thenReturn(Optional.of(plusThree));
        when(obligation.getNextTransactionDate(plusThree)).thenReturn(Optional.<DateTime>of(plusThreeAndABit));

        when(obligation.generatePayments(plus5Months, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 100, ImmutableSet.<String>of())));

        when(obligation.generatePayments(plusOne, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 200, ImmutableSet.<String>of())));

        when(obligation.generatePayments(plusTwo, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 300, ImmutableSet.<String>of())));

        when(obligation.generatePayments(plusTwoAndABit, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 400, ImmutableSet.<String>of())));

        when(obligation.generatePayments(plusThree, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 500, ImmutableSet.<String>of())));

        when(obligation.generatePayments(plusThreeAndABit, hypothesis))
                .thenReturn(ImmutableSet.of(Payment.of("test", 600, ImmutableSet.<String>of())));

        final IDimension<IPowerTable> power = mock(IDimension.class);
        final IDimension<IEnergyMeter> meter = mock(IDimension.class);
        // we have a timeline a bit like this:
        /*
		 * NOW: 	Nothing		year	multiplier		value
		 * 
		 * 5mo: 	100			0			1			100
		 * 
		 * NOW+1y:	200			0			1		    200
		 * 
		 * Now+2y:	300			1			0.75		225		
		 * 
		 * Now+2.5: 400			2			0.5625		225		
		 * 
		 * Now+3:	500			2			0.5625		281.25
		 * 
		 * Now+3.1: 600         3           n/a         0
		 *
		 * SUM:											
         */

        final NetPresentValueFunction npv
                = new NetPresentValueFunction(ImmutableSet.<IDimension<?>>of(),
                        meter,
                        power,
                        obligations,
                        time,
                        false,
                        1 / 3d,
                        3,
                        Predicates.<Collection<String>>alwaysTrue());

        final double compute = npv.compute(scope, ILets.EMPTY);

        Assert.assertEquals("value sums to expected amount (see table)", 1031.25, compute, 0.01);

        verify(hypothesis).imagine(same(meter), any(IEnergyMeter.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void capexIsIncludedInNetPresentValue() {
        when(scope.getAllNotes(IObligation.class)).thenReturn(ImmutableList.<IObligation>of());

        final ITransaction t1 = mock(ITransaction.class);
        final ITransaction t2 = mock(ITransaction.class);
        final ITransaction t3 = mock(ITransaction.class);

        when(t1.getAmount()).thenReturn(100d);
        when(t2.getAmount()).thenReturn(-100d);
        when(t3.getAmount()).thenReturn(33d);

        when(scope.getAllNotes(ITransaction.class)).thenReturn(ImmutableList.<ITransaction>of(t1, t2, t3));
        when(scope.get(obligations)).thenReturn(new ObligationHistory());

        final NetPresentValueFunction npv
                = new NetPresentValueFunction(ImmutableSet.<IDimension<?>>of(),
                        mock(IDimension.class),
                        mock(IDimension.class),
                        obligations,
                        time,
                        false, 0.25, 3,
                        Predicates.<Collection<String>>alwaysTrue()
                );

        Assert.assertEquals("NPV is composed of capex sum", 33d, npv.compute(scope, ILets.EMPTY), 0.03);
    }
}
