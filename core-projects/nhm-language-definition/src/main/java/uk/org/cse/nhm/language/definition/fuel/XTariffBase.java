package uk.org.cse.nhm.language.definition.fuel;

import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;

@Category(CategoryType.TARIFFS)
abstract public class XTariffBase extends XElement implements IHouseContext {
	private XMethodOfPayment methodOfPayment;
	
	public static final class P {
		public static final String methodOfPayment = "methodOfPayment";
	}
	
	@Prop(P.methodOfPayment)
	@BindNamedArgument("payment-method")
	@Doc({
		"The payment method used when paying for fuel on this tariff."
	})
	public XMethodOfPayment getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(final XMethodOfPayment methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	@Override
	@BindNamedArgument
	@Identity
	@Doc("The name for this tariff")
	public String getName() {
		return super.getName();
	}
	
	@Override
	public void setName(final String name) {
		super.setName(name);
	}

	public abstract List<XFuelType> getFuelTypes();
}
