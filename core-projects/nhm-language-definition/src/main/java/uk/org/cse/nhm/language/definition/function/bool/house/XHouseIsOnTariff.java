package uk.org.cse.nhm.language.definition.function.bool.house;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.fuel.XTariffBase;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;
import uk.org.cse.nhm.language.definition.function.house.XHouseTariff;


@Bind("house.is-on-tariff")
@Doc("A test that matches houses which have the specified tariff.")
@SeeAlso(XHouseTariff.class)
@Category(CategoryType.TARIFFS)
public class XHouseIsOnTariff extends XHouseBoolean {
	public static class P {
		public static final String tariff = "tariff";
	}

	private XTariffBase tariff;

	@Prop(P.tariff)
	@BindPositionalArgument(0)
	
	
	@NotNull(message = "house.is-on-tariff must specify a tariff")
	@Doc("The tariff which a house must be on to pass.")
	public XTariffBase getTariff() {
		return tariff;
	}

	public void setTariff(final XTariffBase tariff) {
		this.tariff = tariff;
	}
}
