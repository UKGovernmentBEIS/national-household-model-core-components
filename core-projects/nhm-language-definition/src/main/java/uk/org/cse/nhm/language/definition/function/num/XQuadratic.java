package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("function.quadratic")
@Doc("A function which defines a simple quadratic equation, taking another function as its argument and computing $ax^2+bx+c$.")
@Category(CategoryType.ARITHMETIC)

public class XQuadratic extends XNumber {

    public static final class P {

        public static final String x = "x";
        public static final String a = "a";
        public static final String b = "b";
        public static final String c = "c";
    }
    private XNumber x;
    private double a, b, c;

    @BindNamedArgument
    @Prop(P.x)
    @Doc("The function which gives the argument to this quadratic - this is 'x'.")
    public XNumber getX() {
        return x;
    }

    public void setX(final XNumber x) {
        this.x = x;
    }

    @BindNamedArgument
    @Prop(P.a)
    @Doc("The coefficient of the quadratic term")
    public double getA() {
        return a;
    }

    public void setA(final double a) {
        this.a = a;
    }

    @BindNamedArgument
    @Prop(P.b)
    @Doc("The coefficient of the linear term")
    public double getB() {
        return b;
    }

    public void setB(final double b) {
        this.b = b;
    }

    @BindNamedArgument
    @Prop(P.c)
    @Doc("The constant term")
    public double getC() {
        return c;
    }

    public void setC(final double c) {
        this.c = c;
    }
}
