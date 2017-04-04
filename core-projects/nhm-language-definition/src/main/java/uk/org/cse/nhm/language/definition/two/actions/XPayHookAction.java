package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.tags.Tag;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("pay")
@Doc("Makes a transaction from one global account to another.")
@Category(CategoryType.MONEY)
public class XPayHookAction extends XHookAction {
	public static final class P {
		public static final String from = "from";
		public static final String to = "to";
		public static final String amount = "amount";
		public static final String tags = "tags";
	}
	
	private String from;
	private String to;
	private List<Tag> tags = new ArrayList<>();
	private XNumber amount;
	
	@BindNamedArgument
	@NotNull(message = "pay element expected a 'from' argument.")
	@Doc("The named global account which the payment will be made from.")
	public String getFrom() {
		return from;
	}
	public void setFrom(final String from) {
		this.from = from;
	}
	
	@BindNamedArgument
	@NotNull(message = "pay element expected a 'to' argument.")
	@Doc("The named global account which the payment will be made to.")
	public String getTo() {
		return to;
	}
	
	public void setTo(final String to) {
		this.to = to;
	}
	
	@BindNamedArgument
	@Doc("Tags which will be written on the transaction.")
	public List<Tag> getTags() {
		return tags;
	}
	
	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}
	@BindPositionalArgument(0)
	@NotNull(message = "pay element expected an amount to be paid")
	@Doc("The amount to be transferred between the accounts. May be negative.")
	public XNumber getAmount() {
		return amount;
	}
	
	public void setAmount(final XNumber amount) {
		this.amount = amount;
	}
}
