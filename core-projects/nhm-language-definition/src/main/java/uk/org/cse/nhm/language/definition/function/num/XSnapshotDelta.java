package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.sequence.XSequenceAction;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotAction;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotDeclaration;

@Bind("snapshot.delta")
@Doc({
    "Returns the difference the result of a function when evaluated under two different named snapshot conditions of a house.",
    "That is: f(after) - f(before).",
    "May only be used inside a place where both snapshot names have been bound using a snapshot element."
})
@SeeAlso({XSequenceAction.class, XSnapshotAction.class})
public class XSnapshotDelta extends XHouseNumber {

    public static class P {

        public static final String BEFORE = "before";
        public static final String AFTER = "after";
        public static final String DELEGATE = "delegate";
    }

    private XNumber delegate;
    private XSnapshotDeclaration before;
    private XSnapshotDeclaration after;

    @Prop(P.DELEGATE)

    @BindPositionalArgument(0)
    @NotNull(message = "snapshot.delta must always contain a function to be compared.")
    @Doc("The function which will be compared under the two snapshot conditions.")
    public XNumber getDelegate() {
        return delegate;
    }

    public void setDelegate(final XNumber delegate) {
        this.delegate = delegate;
    }

    @Prop(P.BEFORE)

    @BindNamedArgument
    @NotNull(message = "snapshot.delta must always specify the name of a before snapshot.")
    @Doc("The name of the snapshot which will be used as the initial state.")
    public XSnapshotDeclaration getBefore() {
        return before;
    }

    public void setBefore(final XSnapshotDeclaration before) {
        this.before = before;
    }

    @Prop(P.AFTER)

    @BindNamedArgument
    @NotNull(message = "snapshot.delta must always specify the name of an after snapshot.")
    @Doc("The name of the snapshot which will be used as the final state.")
    public XSnapshotDeclaration getAfter() {
        return after;
    }

    public void setAfter(final XSnapshotDeclaration after) {
        this.after = after;
    }

}
