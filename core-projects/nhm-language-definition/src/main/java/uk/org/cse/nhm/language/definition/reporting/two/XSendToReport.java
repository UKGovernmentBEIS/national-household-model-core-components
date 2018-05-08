package uk.org.cse.nhm.language.definition.reporting.two;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XDwellingAction;

@Doc("Sends the current house to one or more reports")
@Bind("send-to-report")
public class XSendToReport extends XDwellingAction {

    public static class P {

        public static final String reports = "reports";
    }
    private List<XReportDefinition> reports = new ArrayList<>();

    @Doc({"In the report, there will always be a column called sent-from; it takes the value from here. This lets you see where in your scenario a house is when it has arrived in the report.",
        "For example, you could send the house to a report before and after applying a measure, with from: being before-my-measure beforehand, and after-my-measure afterwards."})
    @Override
    @BindNamedArgument("from")
    public String getName() {
        return super.getName();
    }

    @Prop(P.reports)
    @Doc("The reports to send the house to.")
    @BindRemainingArguments
    public List<XReportDefinition> getReports() {
        return reports;
    }

    public void setReports(List<XReportDefinition> reports) {
        this.reports = reports;
    }
}
