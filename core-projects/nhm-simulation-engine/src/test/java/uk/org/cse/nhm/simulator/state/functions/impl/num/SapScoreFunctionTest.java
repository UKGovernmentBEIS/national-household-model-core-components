package uk.org.cse.nhm.simulator.state.functions.impl.num;

import org.junit.Assert;
import org.junit.Test;

public class SapScoreFunctionTest {

    final double deflator = 0.47;

    private void check(final double area, final double cost, final double ecf, final double score) {
        final double _ecf = SapScoreFunction.ecf(area, cost, deflator);
        Assert.assertEquals("ECF matches", ecf, _ecf, 0.001);
        final double _score = SapScoreFunction.clampedScore(cost, area, deflator);
        Assert.assertEquals("Score matches", score, _score, 0.001);
    }

    @Test
    public void sapScoreWhereEcfBelow3AndOneHalf() {
        check(0, 45 / deflator, 1d, Math.round(100 - 13.95 * 1));
    }

    @Test
    public void sapScoreWhereEcfIsThreeAndOneHalf() {
        check(0, 3.5 * 45 / deflator, 3.5d, Math.round(117 - 121 * Math.log10(3.5)));
    }

    @Test
    public void sapScoreWhereEcfAboveThreeAndOneHalf() {
        check(0, 4 * 45 / deflator, 4d, Math.round(117 - 121 * Math.log10(4)));
    }
}
