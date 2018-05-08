package uk.org.cse.nhm.language.definition.function.bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Joiner;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;
import com.larkery.jasb.io.atom.NumberAtomIO;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.ISelfValidating;

@Doc("Test a categorical function to see whether it has one of a range of values")
@Bind("is")
public class XTestValue extends XBoolean implements ISelfValidating {

    public static final class P {

        public static final String function = "function";
        public static final String values = "values";
    }

    private XFunction function;

    private List<String> values = new ArrayList<>();

    @Prop(P.function)
    @NotNull(message = "is must have at least one argument")
    @BindPositionalArgument(0)
    @Doc("A house will pass this test if the value of this function is one of the values given as further arguments")
    public XFunction getFunction() {
        return function;
    }

    public void setFunction(XFunction function) {
        this.function = function;
    }

    @Prop(P.values)
    @BindRemainingArguments
    @Doc("The house will pass this test if the first function's value is amongst the values written here.")
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public List<IError> validate(final Deque<XElement> context) {
        if (this.function != null) {
            final List<String> badStrings = new ArrayList<>();
            final HashSet<String> legalStrings = new HashSet<>();
            if (this.function.getClass().isAnnotationPresent(ReturnsEnum.class)) {
                final Class<?> c = this.function.getClass().getAnnotation(ReturnsEnum.class).value();

                for (final Object o : c.getEnumConstants()) {
                    legalStrings.add(String.valueOf(o).toLowerCase());
                }

                for (final String s : getValues()) {
                    if (!legalStrings.contains(s.toLowerCase())) {
                        badStrings.add(s);
                    }
                }

            } else if (this.function instanceof XNumber) {
                final NumberAtomIO io = new NumberAtomIO();
                legalStrings.add("a number");
                for (final String s : getValues()) {
                    if (!io.read(s, Double.class).isPresent()) {
                        badStrings.add(s);
                    }
                }
            } else if (this.function instanceof XBoolean) {
                return Collections.singletonList(
                        BasicError.warningAt(
                                getLocation(),
                                String.format("%s is already a test, and so does not need to be within 'is'",
                                        this.function.getIdentifier().getName())));
            }

            if (!badStrings.isEmpty()) {
                return Collections.singletonList(
                        BasicError.warningAt(
                                getLocation(),
                                String.format("%s can never take the value %s; maybe this is a typo? It can be %s",
                                        this.function.getIdentifier().getName(),
                                        Joiner.on(" or ").join(badStrings),
                                        Joiner.on(" or ").join(legalStrings))));
            }
        }
        return Collections.emptyList();
    }
}
