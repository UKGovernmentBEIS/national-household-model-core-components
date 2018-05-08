package uk.org.cse.nhm.language.definition.action.choices;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

@Bind("select.fallback")
@Doc({
    "A selector which delegates to other selectors. It will attempt to run each of these in turn until one of them picks an option."
})
@SeeAlso(XFilterSelector.class)
public class XFallbackSelector extends XChoiceSelector {

    public static class P {

        public static final String delegates = "delegates";
    }

    private List<XChoiceSelector> delegates = new ArrayList<>();

    @Prop(P.delegates)
    @BindRemainingArguments
    @Doc("The selectors which will be delegated to, in the order in which they will be attempted.")
    @Size(min = 1, message = "select.fallback must always contain at least 1 delegate selector.")
    public List<XChoiceSelector> getDelegates() {
        return delegates;
    }

    public void setDelegates(final List<XChoiceSelector> delegates) {
        this.delegates = delegates;
    }
}
