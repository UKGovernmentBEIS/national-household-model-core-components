package uk.org.cse.nhm.simulator.state.functions.impl.num.random;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.util.RandomSource;

public class TriangularRandomFunction extends RandomFunction {

    private final double start;
    private final double peak;
    private final double end;
    private final double peakProportion;

    @AssistedInject
    public TriangularRandomFunction(
            @Assisted("start") final double a,
            @Assisted("peak") final double peak,
            @Assisted("end") final double b) {
        this.start = Math.min(a, b);
        this.end = Math.max(a, b);
        this.peak = peak;

        this.peakProportion = (peak - start) / (end - start);
    }

    @Override
    protected double doCompute(final RandomSource random) {
        final double uniform = random.nextDouble();

        return uniform < peakProportion
                ? start + Math.sqrt(uniform * (end - start) * (peak - start))
                : end - Math.sqrt((1 - uniform) * (end - start) * (end - peak));
    }
}
