package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;

@Doc({
	"Yields the total of all loan payments made by this house during the simulation.",
	"May be filtered to include only loans with particular tags or owed to particular creditors."
})

@Bind("house.loans.balance-paid")
public class XLoanBalancePaid extends XLoanBalance {

}
