package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("account.balance")
@Doc({"Returns the balance for a named global account (these are the parties to all transactions which are not houses).", 
	"For cost recovery purposes, you can also access the value in an account before any extant transactions are processed, ",
	 "for example those caused by paying the current set of fuel bills"})
@Category(CategoryType.MONEY)
public class XGlobalAccountBalance extends XNumber {
	public static class P {
		public static final String account = "account";
	}
	private String account;
	@Doc("The name of the account to take the balance for")
	@BindNamedArgument
	public String getAccount() {
		return account;
	}
	public void setAccount(final String account) {
		this.account = account;
	}
}
