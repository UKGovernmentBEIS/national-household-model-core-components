package uk.org.cse.nhm.language.definition;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.group.XGroup;

@Bind("case")
@Doc("Defines a set of mutually exclusive groups, with exposures and actions.")
public class XCase extends XPolicyAction implements IHouseContext {

    public static final class P {

        public static final String WHENS = "whens";
        public static final String OTHERWISE = "otherwise";
        public static final String source = "source";
    }

    private XGroup source;
    private List<XCaseWhen> whens = new ArrayList<XCaseWhen>();
    private XCaseOtherwise otherwise = null;

    @BindNamedArgument("cases")
    @Prop(P.WHENS)
    @Doc("Each when defines a group; for any given house, it will end up in the first group for which it passes the test.")
    public List<XCaseWhen> getWhens() {
        return whens;
    }

    public void setWhens(final List<XCaseWhen> whens) {
        this.whens = whens;
    }

    @BindNamedArgument("default")
    @Prop(P.OTHERWISE)
    @Doc("If a house does not pass the test for any of the when clauses, it will end up in the otherwise group.")
    public XCaseOtherwise getOtherwise() {
        return otherwise;
    }

    public void setOtherwise(final XCaseOtherwise otherwise) {
        this.otherwise = otherwise;
    }

    @Prop(P.source)
    @BindNamedArgument
    @Doc("The when and otherwise branches will only 'see' houses from this group; that is to say, the case statement acts on the houses in this group.")
    @NotNull(message = "case must contain a group")
    public XGroup getSource() {
        return source;
    }

    public void setSource(final XGroup source) {
        this.source = source;
    }
}
