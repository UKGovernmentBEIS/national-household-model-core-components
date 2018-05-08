package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("interpolate")
@Doc("A simple interpolation function, which maps a value from one range to another range by reading off a piecewise linear curve. If you would prefer to write the coordinates as a long list, see the ~transpose macro.")
@Category(CategoryType.ARITHMETIC)
public class XInterpolate extends XNumber {

    public static final class P {

        public static final String x = "x";
        public static final String xCoordinates = "xCoordinates";
        public static final String yCoordinates = "yCoordinates";
        public static final String extrapolate = "extrapolate";
    }

    private XNumber x;

    @Prop(P.x)
    @BindPositionalArgument(0)
    @Doc("The value to interpolate")
    @NotNull(message = "no value to interpolate was supplied")
    public XNumber getX() {
        return x;
    }

    public void setX(final XNumber x) {
        this.x = x;
    }

    private List<Double> xCoordinates = new ArrayList<>();

    @Doc("The x-coordinates of the interpolation points; these should be in the same order as the y-coordinates.")
    @Size(min = 2, message = "the x-coordinates of at least two points are required for interpolation")
    @BindPositionalArgument(1)
    @Prop(P.xCoordinates)
    public List<Double> getXCoordinates() {
        return xCoordinates;
    }

    public void setXCoordinates(List<Double> xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    private List<Double> yCoordinates = new ArrayList<>();

    @Doc("The y-coordinates of the interpolation points; these should be in the same order as the x-coordinates.")
    @BindPositionalArgument(2)
    @Size(min = 2, message = "the y-coordinates of at least two points are required for interpolation")
    @Prop(P.yCoordinates)
    public List<Double> getYCoordinates() {
        return yCoordinates;
    }

    public void setYCoordinates(List<Double> yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    private boolean extrapolate = false;

    @Prop(P.extrapolate)
    @BindNamedArgument
    @Doc("If true, and the input x-value is out of range, the resulting y-value will be computed by linear extrapolation using the first or last segment of the piecewise curve defined by the x- and y- coordinates. If false, the result will be clamped to the y-value of the nearest x-value.")
    public boolean isExtrapolate() {
        return extrapolate;
    }

    public void setExtrapolate(boolean extrapolate) {
        this.extrapolate = extrapolate;
    }

}
