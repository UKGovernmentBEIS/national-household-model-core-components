package uk.org.cse.nhm.simulator.state.functions.impl.num;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class NetPresentValueAnnotation implements INetPresentValueAnnotation {

    private final double npv;
    private final double undiscountedFuture;
    private final double discountedFuture;
    private final double capex;

    public NetPresentValueAnnotation(double capex, double undiscountedFuture,
            double discountedFuture) {
        this.capex = capex;
        this.undiscountedFuture = undiscountedFuture;
        this.discountedFuture = discountedFuture;
        this.npv = discountedFuture + capex;
    }

    public static final INetPresentValueAnnotation of(final double capex, final double undiscountedFuture, final double discountedFuture) {
        return new NetPresentValueAnnotation(capex, undiscountedFuture, discountedFuture);
    }

    public double getNpv() {
        return npv;
    }

    public double getUndiscountedFuture() {
        return undiscountedFuture;
    }

    public double getDiscountedFuture() {
        return discountedFuture;
    }

    public double getCapex() {
        return capex;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
