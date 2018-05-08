package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

public abstract class XActionWithDelegates extends XFlaggedDwellingAction {

    public static final class P {

        public static final String DELEGATES = "delegates";
    }
    private List<XDwellingAction> delegates = new ArrayList<XDwellingAction>();

    @BindRemainingArguments

    @Prop(P.DELEGATES)
    @Doc("A list of other actions, which this action will choose between, apply, etc.")
    @Size(min = 1, message = "all-of, any-of or choice action was empty: it must contain other actions which it will apply of chose between.")
    public List<XDwellingAction> getDelegates() {
        return delegates;
    }

    public void setDelegates(final List<XDwellingAction> delegates) {
        this.delegates = delegates;
    }
}
