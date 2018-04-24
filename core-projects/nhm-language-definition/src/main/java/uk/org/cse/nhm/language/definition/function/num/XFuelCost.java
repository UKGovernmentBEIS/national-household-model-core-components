package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;

@Doc({ "The annual fuel cost for a house." ,""
            , "This is the model's prediction of what the house will pay at the end of each year,"
            , "given the house's current condition.", ""
            , "This is calculated by supplying the house's current annual energy use to its"
            , "current set of tariffs."
            })
@Bind("house.fuel-cost")
@Category(CategoryType.MONEY)
public class XFuelCost extends XHouseNumber implements ICalibratedEnergyFunction {
        public static final class P {
                public static final String byFuel = "byFuel";
                public static final String byService = "byService";
                public static final String excludeServices = "excludeServices";
        }

        private XFuelType byFuel = null;
        private List<XServiceType> excludeServices = new ArrayList<>();

        @BindNamedArgument("by-fuel")
        @Doc("If set, yields the annual cost of the given fuel type.")
        public XFuelType getByFuel() {
                return byFuel;
        }
        public void setByFuel(final XFuelType byFuel) {
                this.byFuel = byFuel;
        }

        @BindNamedArgument("exclude-services")
        @Doc({"The fuel bill will be calculated with energy consumption by these services omitted.",
              "However, the related internal gains and other effects will not be omitted.",
              "This is meant for calculating the fuel cost which is used in the definition of a SAP score.","",
              "For that purpose, you should use exclude-services:[Appliances Cooking].", "",
              "WARNING: tariffs can be NON-LINEAR, in which case you cannot safely add up the marginal bills due to each service.", "",
              "IN PARTICULAR, the standard SAP tariffs include standing charges for some fuels (see SAP table 12).",
              "As a result, summing several marginal fuel bills will DOUBLE COUNT the standing charge.", "",
              "Any tariff in which the overall unit price is a function of the number of units used",
              ",of which rising-block tariffs and tariffs with a standing charge are prominent examples,",
              "will have the property that",
              "(house.fuel-cost exclude-services:[a]) + (house.fuel-cost exclude-services:[b c d e])",
              "may not equal (house.fuel-cost), where a,b,c,d,e are all the services, for brevity."
        })
        public List<XServiceType> getExcludeServices() {
                return excludeServices;
        }
        public void setExcludeServices(final List<XServiceType> excludeServices) {
                this.excludeServices = excludeServices;
        }
}
