package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class InflatedFunctionTest {

    private static final double inflationrate = 1.1;
    private InflatedFunction target;
    private ITimeDimension time;
    private IComponentsFunction<Number> delegate;
    private DateTimeFormatter f;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        f = DateTimeFormat.shortDate();
        time = mock(ITimeDimension.class);
        delegate = mock(IComponentsFunction.class);

        this.target = new InflatedFunction(
                f.parseDateTime("01/01/2000"),
                f.parseDateTime("01/01/2004"),
                time,
                Optional.<XForesightLevel>absent(),
                Optional.of(f.parseDateTime("01/05/2001")),
                delegate,
                inflationrate);
    }

    @Test
    public void dimensionsAreDelegatedWithExtraTime() {
        final IDimension<?> A = mock(IDimension.class);
        final IDimension<?> B = mock(IDimension.class);
        when(delegate.getDependencies()).thenReturn(ImmutableSet.of(A, B));

        Assert.assertEquals(
                ImmutableSet.of(A, B, time),
                target.getDependencies());
    }

    @Test
    public void changeDatesAreRight() {
        final DateTime first = f.parseDateTime("01/05/2000");

        Assert.assertEquals(
                ImmutableSet.of(
                        first,
                        first.plus(Years.years(1)),
                        first.plus(Years.years(2)),
                        first.plus(Years.years(3))),
                target.getChangeDates());

    }

    @Test
    public void valueIsCorrectThroughYears() {
        when(delegate.compute(any(IComponentsScope.class), any(ILets.class)))
                .thenReturn(13d);

        final IComponentsScope s = mock(IComponentsScope.class);
        check("At the turn of year 1, no inflation has happened", s, "01/05/2001", 13d);
        check("At the very end of year 1, no inflation has happened", s, "30/04/2002", 13d);
        check("At the turn of year 1, one go of inflation has happened", s, "01/05/2002", inflationrate * 13d);
        check("At the turn of year 2, two goes of inflation has happened", s, "01/05/2003", inflationrate * inflationrate * 13d);
        check("Before year 1, one go of deflation has happened", s, "30/04/2000", 13d / inflationrate);
    }

    private void check(final String message, final IComponentsScope s, final String now, final double expected) {
        when(s.get(time)).thenReturn(TimeUtil.mockTime(f.parseDateTime(now)));

        Assert.assertEquals(message, expected, target.compute(s, ILets.EMPTY), 0.001);
    }
}
