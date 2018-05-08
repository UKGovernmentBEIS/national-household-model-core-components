package uk.org.cse.stockimport.domain.geometry;

import java.awt.Polygon;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class SimplePolygon {

    private final double[][] points;
    private final double area;

    public static class Builder {

        private final ImmutableList.Builder<double[]> builder = ImmutableList.builder();

        double lastX = Double.NaN;
        double lastY = Double.NaN;

        public Builder add(final double x, final double y) {
            if (!(x == lastX && y == lastY)) {
                builder.add(new double[]{x, y});
                lastX = x;
                lastY = y;
            }
            return this;
        }

        public SimplePolygon build() {
            return new SimplePolygon(builder.build().toArray(new double[0][0]));
        }
    }

    SimplePolygon(final double[][] points) {
        super();
        this.points = points;
        this.area = computeArea(points);
    }

    public int size() {
        return points.length;
    }

    public double x(final int point) {
        Preconditions.checkElementIndex(point, points.length);
        return points[point][0];
    }

    public double y(final int point) {
        Preconditions.checkElementIndex(point, points.length);
        return points[point][1];
    }

    public double area() {
        return area;
    }

    public double[] xs() {
        final double[] r = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            r[i] = x(i);
        }
        return r;
    }

    public double[] ys() {
        final double[] r = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            r[i] = y(i);
        }
        return r;
    }

    private static double computeArea(final double[][] points) {
        double accumulator = 0;

        final int count = points.length;

        int j = count - 1;
        for (int i = 0; i < count; i++) {
            final double[] previousPoint = points[j];
            final double[] thisPoint = points[i];

            accumulator += (previousPoint[0] + thisPoint[0])
                    * (previousPoint[1] - thisPoint[1]);

            j = i;
        }

        return Math.abs(accumulator / 2d);
    }

    public SimplePolygon scale(final double scalingFactor) {
        final SimplePolygon.Builder b = builder();
        for (int i = 0; i < size(); i++) {
            b.add(x(i) * scalingFactor, y(i) * scalingFactor);
        }
        return b.build();
    }

    public static SimplePolygon.Builder builder() {
        return new Builder();
    }

    public Polygon toSillyPolygon() {
        //TODO remove this
        final int[] xin = new int[size()];
        final int[] yin = new int[size()];
        for (int i = 0; i < points.length; i++) {
            xin[i] = (int) points[i][0];
            yin[i] = (int) points[i][1];
        }
        return new Polygon(xin, yin, points.length);
    }
}
