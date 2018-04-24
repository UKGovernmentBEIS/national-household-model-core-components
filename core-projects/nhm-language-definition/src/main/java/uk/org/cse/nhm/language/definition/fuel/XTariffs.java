package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.XExtraContextParameter;
import uk.org.cse.nhm.language.definition.fuel.validation.TariffPerFuel;


@Bind("context.tariffs")
@Doc(value = { "Defines the fuel tariffs to be used in the simulation.", })
@Category(CategoryType.TARIFFS)
public class XTariffs extends XExtraContextParameter {
	public static class P {
		public static final String defaultTariffs = "defaults";
		public static final String otherTariffs = "others";
	}

	private List<XTariffBase> defaults = new ArrayList<>();
	private List<XTariffBase> others = new ArrayList<>();
	
	@Prop(P.defaultTariffs)
	@Doc({ 
		"A list of tariffs which will be used as the defaults for all houses.",
		"If a fuel has no default tariff, houses will get that fuel for free until they are placed on a tariff.",  
	})
	@TariffPerFuel(message = "context.tariffs must not include more than one default tariff per fuel.")
	@BindNamedArgument("defaults")
	public List<XTariffBase> getDefaults() {
		return defaults;
	}

	public void setDefaults(final List<XTariffBase> defaults) {
		this.defaults = defaults;
	}

	@Prop(P.otherTariffs)
	
	@Doc("A list of tariffs which may be referred to from elsewhere.")
	@BindNamedArgument("others")
	public List<XTariffBase> getOthers() {
		return others;
	}

	public void setOthers(final List<XTariffBase> others) {
		this.others = others;
	}
}
