package uk.org.cse.nhm.language.definition.money.obligations;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.money.XFinanceAction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("finance.with-obligation")
@Doc({
	"Adds an obligation to a house. An obligation is a sequence of payments on pre-determined dates.",
	"Keeps copies of any values saved in vars (but not snapshots) by a let or choice. Makes these temporarily available again for calculating the payment amount."
})
@Category(CategoryType.MONEY)
public class XObligationAction extends XFinanceAction {
	public static class P {
		public static final String amount = "amount";
		public static final String schedule = "schedule";
	}
	
	private XNumber amount;
	private XPaymentSchedule schedule;
	
	@Doc({"A function which will determine how much money the house will pay for each payment.",
		"This will be reevaluated for each payment, so the size of payments may vary over time or as the condition of the house changes.",
		"Use negative values to represent payments to the house from the counterparty.",
		"Note that because this is evaluated each time the obligation is due, context-sensitive values like ", 
		"<code>(cost.capex)</code> or yielded values will not work within the amount. To make use of values produced by the delegate",
		"action, use the <code>amount-vars:</code> attribute to capture the desired values at the time of adding the obligation."})
	@Prop(P.amount)
	@NotNull(message = "finance.with-obligation must always specify an amount.")
	@BindNamedArgument
	public XNumber getAmount() {
		return amount;
	}

	public void setAmount(final XNumber amount) {
		this.amount = amount;
	}

	@Doc("A schedule which controls the dates on which payments will be made by the created obligation.")
	@Prop(P.schedule)
	@NotNull(message = "finance.with-obligation must always specify a schedule.")
	@BindNamedArgument
	public XPaymentSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(final XPaymentSchedule schedule) {
		this.schedule = schedule;
	}
}	


