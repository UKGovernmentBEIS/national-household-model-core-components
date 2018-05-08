package uk.org.cse.nhm.language.definition.reporting.aggregate;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.reporting.XCustomReportingElement;
import uk.org.cse.nhm.language.definition.reporting.modes.XDateMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XIntegralMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XOnChangeMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XReportMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XSumMode;
import uk.org.cse.nhm.language.definition.two.actions.XApplyHookAction;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;
import uk.org.cse.nhm.language.validate.BatchRestricted;

@Bind("report.aggregate")
@Doc(
        value = {
            "An aggregate report provides a view on the way an aggregate numerical quantity changes over time.",
            "Each aggregate report is composed of two key parts; firstly, a way of subdividing the housing stock into",
            "some groups, and then secondly a way of producing an aggregate value for a group of houses.",
            "The report produced contains a timeseries of the aggregate value, broken down by the different groups."
        }
)
@Obsolete(
        reason = XDateHook.SCHEDULING_OBSOLESCENCE,
        inFavourOf = {XDateHook.class, XApplyHookAction.class}
)
@Category(CategoryType.OBSOLETE)
public class XAggregateReport extends XCustomReportingElement {

    public static final class P {

        public static final String division = "division";
        public static final String aggregations = "aggregations";
        public static final String mode = "mode";
    }

    private XReportMode mode = new XOnChangeMode();
    private XDivision division = XGroupDivision.allHouses();
    private List<XAggregation> aggregations = new ArrayList<XAggregation>();

    @BindNamedArgument("mode")
    @Prop(P.mode)
    @NotNull(message = "report.aggregate must always contain a mode.")
    @Doc("The output mode of the report. This can be used to restrict the volume of the output data to a single row from the start or end of the scenario. Alternatively it can be used to compute an aggregate (over time) of each column.")
    @BatchRestricted(
            attribute = "mode",
            message = "report.aggregate cannot be used in 'on-change' mode in a batch run. Set it to one of the other modes.",
            value = {XDateMode.class, XSumMode.class, XIntegralMode.class})
    public XReportMode getMode() {
        return mode;
    }

    public void setMode(final XReportMode mode) {
        this.mode = mode;
    }

    @BindNamedArgument
    @Doc(
            {
                "The division rule provides a way to split the whole housing stock into some subsets,",
                "for each of which the specified aggregations will be recorded."
            }
    )
    @NotNull(message = "report.aggregate must always contain a division.")
    public XDivision getDivision() {
        return division;
    }

    public void setDivision(final XDivision division) {
        this.division = division;
    }

    @BindRemainingArguments
    @Doc({
        "Each of these aggregations will be computed for each group produced by",
        "the division rule, and emitted when the value changes."
    })
    public List<XAggregation> getAggregations() {
        return aggregations;
    }

    public void setAggregations(final List<XAggregation> aggregations) {
        this.aggregations = aggregations;
    }
}
