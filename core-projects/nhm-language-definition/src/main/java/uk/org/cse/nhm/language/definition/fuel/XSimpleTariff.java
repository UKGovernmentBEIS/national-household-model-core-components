package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Doc("A convenience for defining a simple tariff which is just defined by a standing charge and a unit rate for each fuel.")

@Bind("tariff.simple")
public class XSimpleTariff extends XTariffBase {

    public static final class P {

        public static final String fuels = "fuels";
    }

    @Bind("function.simple-pricing")
    @Doc("A simple tariff definition for a fuel")
    @Category(CategoryType.TARIFFS)
    public static class XSimpleTariffFuel extends XHouseNumber {

        public static final class P {

            public static final String fuel = "fuel";
            public static final String standingCharge = "standingCharge";
            public static final String unitRate = "unitRate";
        }
        private XFuelType fuel;
        private double standingCharge = 0;
        private double unitRate = 0;

        @Doc("The fuel to calculate the pricing for")

        @BindNamedArgument("fuel")
        @NotNull(message = "A fuel has to be specified for function.simple-pricing")
        @Prop(P.fuel)
        public XFuelType getFuel() {
            return fuel;
        }

        public void setFuel(final XFuelType fuel) {
            this.fuel = fuel;
        }

        @Doc("The standing charge, in pounds")

        @BindNamedArgument("standing-charge")
        @Prop(P.standingCharge)
        public double getStandingCharge() {
            return standingCharge;
        }

        public void setStandingCharge(final double standingCharge) {
            this.standingCharge = standingCharge;
        }

        @Doc("The price in pounds per kWh")

        @BindNamedArgument("unit-price")
        @Prop(P.unitRate)
        public double getUnitRate() {
            return unitRate;
        }

        public void setUnitRate(final double unitRate) {
            this.unitRate = unitRate;
        }
    }

    private List<XSimpleTariffFuel> fuels = new ArrayList<>();

    @Prop(P.fuels)
    @Doc("Simple pricing definitions for each fuel")
    @BindRemainingArguments
    public List<XSimpleTariffFuel> getFuels() {
        return fuels;
    }

    public void setFuels(final List<XSimpleTariffFuel> fuels) {
        this.fuels = fuels;
    }

    @Override
    public List<XFuelType> getFuelTypes() {
        final ImmutableList.Builder<XFuelType> types = ImmutableList.builder();
        for (final XSimpleTariffFuel fuel : fuels) {
            types.add(fuel.getFuel());
        }
        return types.build();
    }
}
