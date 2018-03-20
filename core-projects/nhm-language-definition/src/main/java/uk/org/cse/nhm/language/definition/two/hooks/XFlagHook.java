package uk.org.cse.nhm.language.definition.two.hooks;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.two.selectors.XAffectedHouses;

@Bind("on.flag")
@Doc(
{ "A hook which will trigger when some houses have had a set of flags added or removed.",
        "This hook will be prevented from triggering itself either directly or indirectly."
})
@SeeAlso(XAffectedHouses.class)
public class XFlagHook extends XHook {
    public static class P {
        public static final String match = "match";
        public static final String flags = "flags";
    }

    private Boolean match = null;

    private List<Glob> flags = new ArrayList<>();

    @Prop(P.match)
    @BindNamedArgument
    @Doc({ "If true, the hook will fire when some houses change to match the set of flags specified.",
            "If false, the hook will fire when some houses change to no longer match the set of flags specified.",
            "If not set, the hook will fire in both cases."
    })
    public Boolean getMatch() {
        return match;
    }

    public void setMatch(final Boolean test) {
        match = test;
    }

    @Doc("The flags or set of flags to match when firing this hook.")
    @BindPositionalArgument(0)
    @Prop(P.flags)
    public List<Glob> getFlags() {
        return flags;
    }

    public void setFlags(final List<Glob> flags) {
        this.flags = flags;
    }
}
