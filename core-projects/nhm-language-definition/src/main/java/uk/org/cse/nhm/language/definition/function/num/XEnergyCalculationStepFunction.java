package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XEnergyCalculationStep;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Bind("house.energy-calculation-step")
@Doc({
        "Returns an internal value from the energy calculator, corresponding to a cell in the SAP 2012 worksheet."
})
public class XEnergyCalculationStepFunction extends XHouseNumber implements IEnergyFunction {
    public static final class P {
        public static final String month = "month";
        public static final String step = "step";
    }

    private XEnergyCalculationStep step;
    private Integer month = null;

    @BindPositionalArgument(0)
    @NotNull(message = "house.energy-calculation-step must specify a step")
    @Prop(P.step)
    @Doc("The cell in the SAP 2012 worksheet which we will lookup the value for.")
    public XEnergyCalculationStep getStep() {
        return step;
    }

    public void setStep(final XEnergyCalculationStep step) {
        this.step = step;
    }

    @BindNamedArgument("month")
    @Prop(P.month)
    @Max(12)
    @Min(1)
    @Doc({
            "The number of the month to get the value for.",
            "If this is omitted, the value will be reported in annual units instead, using the aggregation specified in the step's documentation.",
            "The documentation for individual steps determines whether or not you can look at them on a monthly basis."
    })
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
