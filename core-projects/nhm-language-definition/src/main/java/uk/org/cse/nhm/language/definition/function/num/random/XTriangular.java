package uk.org.cse.nhm.language.definition.function.num.random;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.random.validation.TriangularParameters;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("random.triangular")
@Doc("Produces random numbers from a triangular distribution.")
@TriangularParameters(message = "random.triangular: peak must be between its start and end values inclusive.")
public class XTriangular extends XNumber implements ITriangular {

    public static class P {

        public static final String start = "start";
        public static final String peak = "peak";
        public static final String end = "end";
    }

    private double start;
    private double peak;
    private double end;

    @Override
    @BindNamedArgument
    @Prop(P.start)
    @NotNull(message = "random.triangular must always have a start.")
    @Doc("The start of the triangular distribution.")
    public double getStart() {
        return start;
    }

    public void setStart(final double start) {
        this.start = start;
    }

    @Override
    @BindNamedArgument
    @Prop(P.peak)
    @NotNull(message = "random.triangular must always have a peak.")
    @Doc("The peak of the triangular distribution. The number which it is most likely to produce.")
    public double getPeak() {
        return peak;
    }

    public void setPeak(final double peak) {
        this.peak = peak;
    }

    @Override
    @BindNamedArgument
    @Prop(P.end)
    @NotNull(message = "random.triangular must always have an end.")
    @Doc("The end of the triangular distribution.")
    public double getEnd() {
        return end;
    }

    public void setEnd(final double end) {
        this.end = end;
    }
}
