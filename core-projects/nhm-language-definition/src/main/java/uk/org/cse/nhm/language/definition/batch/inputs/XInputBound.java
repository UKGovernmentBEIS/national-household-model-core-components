package uk.org.cse.nhm.language.definition.batch.inputs;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

@Doc(value = {
    "Wrap this element around unbounded inputs to restrict the maximum number of variations those inputs can produce."})
@Bind("bounded")
public class XInputBound extends XInputs {

    public static final class P {

        public static final String DELEGATE = "delegate";
        public static final String BOUND = "bound";
    }

    private Integer bound = 1;
    private XInputs delegate;

    @Min(value = 1, message = "bound attribute bounded element must be at least 1.")
    @Doc(value = "The number of rows which should be produced.")
    @BindNamedArgument
    @Prop(P.BOUND)
    public Integer getBound() {
        return bound;
    }

    public void setBound(final Integer bound) {
        this.bound = bound;
    }

    @Override
    public boolean hasBound() {
        return true;
    }

    @NotNull(message = "bounded element must contain an inputs element")
    @Doc("The input which will be bounded.")
    @BindNamedArgument("inputs")

    public XInputs getDelegate() {
        return delegate;
    }

    public void setDelegate(final XInputs delegate) {
        this.delegate = delegate;
    }

    @Override
    @Prop(XInputs.P.PLACEHOLDERS)
    public List<String> getPlaceholders() {
        return delegate.getPlaceholders();
    }
}
