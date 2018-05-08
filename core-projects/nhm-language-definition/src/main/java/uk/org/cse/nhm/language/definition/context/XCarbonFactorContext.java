package uk.org.cse.nhm.language.definition.context;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XCarbonEmissions;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.contents.ForbidChild;

@Doc(value = {
    "A context parameter which defines the carbon factors for fuels.",
    "This will be multiplied by kWh of energy/year to calculate total emissions for a dwelling, so if you write then in kg CO2e / kWh, house.emissions will be in kg CO2e/year.",
    "If not specified, this will default to SAP 2009 carbon factors, measured in kg of CO2 per kWh.",
    "If you specify carbon factors for some fuels but not others, the unspecified fuels will continue to use the default SAP 2009 values."
})
@Bind("context.carbon-factors")
@SeeAlso(XCarbonEmissions.class)
@ForbidChild({
    ICalibratedEnergyFunction.class,
    XAction.class,
    IEnergyFunction.class
})
@Category(CategoryType.CARBON)
public class XCarbonFactorContext extends XContextParameter implements IHouseContext {

    public static final class P {

        public static final String groups = "groups";
        public static final String group_fuels = "fuels";
        public static final String group_factor = "carbonFactor";
    }

    @Doc("Defines a group of fuels, all of which will have the same carbon factor")
    @Bind("group")
    public static class XCarbonGroup extends XElement {

        private List<XFuelType> fuels = new ArrayList<>();
        private XNumber carbonFactor;

        @Doc("The types of fuel in this group")
        @BindNamedArgument("fuels")
        @Prop(P.group_fuels)
        public List<XFuelType> getFuels() {
            return fuels;
        }

        public void setFuels(final List<XFuelType> fuels) {
            this.fuels = fuels;
        }

        @BindNamedArgument("carbon-factor")
        @Doc("The function to compute the group's carbon factor")
        @Prop(P.group_factor)
        public XNumber getCarbonFactor() {
            return carbonFactor;
        }

        public void setCarbonFactor(final XNumber carbonFactor) {
            this.carbonFactor = carbonFactor;
        }
    }

    private List<XCarbonGroup> groups = new ArrayList<>();

    @BindRemainingArguments

    @Doc("Each element in this list defines a group of fuels which have the same carbon factor")
    @Prop(P.groups)
    public List<XCarbonGroup> getGroups() {
        return groups;
    }

    public void setGroups(final List<XCarbonGroup> groups) {
        this.groups = groups;
    }
}
