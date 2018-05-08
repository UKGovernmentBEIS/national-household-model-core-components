package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class InterpolateFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IComponentsFunction<? extends Number> x;
    private final double[] xs, ys;
    private final boolean extrapolate;

    @AssistedInject
    InterpolateFunction(
            @Assisted final IComponentsFunction<? extends Number> x,
            @Assisted final boolean extrapolate,
            @Assisted("xs") final List<Double> xs,
            @Assisted("ys") final List<Double> ys) {
        this.x = x;
        this.extrapolate = extrapolate;

        double[][] points = new double[Math.min(xs.size(), ys.size())][2];
        for (int i = 0; i < points.length; i++) {
            points[i][0] = xs.get(i);
            points[i][1] = ys.get(i);
        }

        // sort points using the x-coordinates then the y-coordinates
        Arrays.sort(points, new Comparator<double[]>() {
            @Override
            public int compare(double[] a, double[] b) {
                final int c1 = Double.compare(a[0], b[0]);
                if (c1 == 0) {
                    return Double.compare(a[1], b[1]);
                } else {
                    return c1;
                }
            }
        });

        // unpack them into xs and ys
        this.xs = new double[points.length];
        this.ys = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            this.xs[i] = points[i][0];
            this.ys[i] = points[i][1];
        }
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final double xvalue = x.compute(scope, lets).doubleValue();
        // bounds check first
        final int leftPoint;

        if (xvalue < xs[0]) {
            if (extrapolate) {
                // extrapolate using the bottom segment
                leftPoint = 0;
            } else {
                return ys[0];
            }
        } else if (xvalue > xs[xs.length - 1]) {
            if (extrapolate) {
                // extrapolate using the top segment
                leftPoint = xs.length - 2;
            } else {
                return ys[ys.length - 1];
            }
        } else {
            // in bounds - interpolate! INTERPOLAATE!
            final int xindex = Arrays.binarySearch(xs, xvalue);
            if (xindex < 0) {
                final int ip = -1 - xindex;
                // interpolate from ip - 1 to ip
                // bounds checking not required, as we already did it.
                // This could be made more performant by precomputing the intercept/slope values
                leftPoint = ip - 1;
            } else {
                return ys[xindex];
            }
        }

        final double dx = xs[leftPoint + 1] - xs[leftPoint];
        final double dy = ys[leftPoint + 1] - ys[leftPoint];

        return ys[leftPoint]
                + dy * (xvalue - xs[leftPoint]) / dx;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return x.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return x.getChangeDates();
    }
}
