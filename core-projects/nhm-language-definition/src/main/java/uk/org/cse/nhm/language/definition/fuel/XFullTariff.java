package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.enums.XFuelType;


@Bind("tariff")
@Doc(value = "A tariff produces the fuel bill for a house. To do this, it looks at the base fuel cost and units consumed for a house for one or more fuels.")
@ProducesTags(
		{
			@Tag(value=":fuel", detail="Indicates a transaction that forms part of a fuel bill")
		})
public class XFullTariff extends XTariffBase {
	public static class P {
		public static final String fuels = "fuels";
	}
	
	private List<XTariffFuel> fuels = new ArrayList<XTariffFuel>();

	
	@Prop(P.fuels)
	@Size(min = 1, message = "tariff must contain at least one fuel.")
	@Doc({"The fuels which the tariff provides pricing for."})
	@BindRemainingArguments
	public List<XTariffFuel> getFuels() {
		return fuels;
	}
	
	public void setFuels(final List<XTariffFuel> fuels) {
		this.fuels = fuels;
	}
	
	@Override
	public List<XFuelType> getFuelTypes() {
		final ImmutableList.Builder<XFuelType> types = ImmutableList.builder();
		for (final XTariffFuel fuel : fuels) {
			types.add(fuel.getFuel());
		}
		return types.build();
	}
}
