package uk.org.cse.nhm.language.definition.money;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Doc(value = {
    "Finances the performance of another action using a fixed-rate fixed-term loan; ",
    "this works by performing the action to be financed first, and then evaluating the principal",
    "paying the dwelling the principal immediately, ",
    "and then entering the dwelling into an obligation to repay the principal with interest over the term.",
    "By default, the loan principal is charged to the enclosing policy's account.",
    "The loan value is calculated including the progressive repayment of the principal; the repayment of principal",
    "and accrual of interest both occur annually."
})
@Bind("finance.with-loan")
@ProducesTags({
    @Tag(value = TransactionTags.Internal.repayment, detail = "Each loan repayment will have this tag")
    ,
	@Tag(value = TransactionTags.Internal.loan, detail = "All transactions will have this tag")
    ,
	@Tag(value = TransactionTags.Internal.principal, detail = "The loan principal transaction will have this tag")
})
public class XLoanAction extends XFinanceAction {

    public static final class P {

        public static final String term = "term";
        public static final String rate = "rate";
        public static final String tilt = "tilt";
        public static final String principal = "principal";
    }

    private XNumber term;
    private XNumber rate;
    private XNumber tilt = XNumberConstant.create(0d);
    private XNumber principal;

    @Doc("The term of the loan in years")
    @BindNamedArgument("term")
    @Prop(P.term)
    @NotNull(message = "finance.with-loan must have a term: argument; if this is not a whole number, it will be rounded.")
    public XNumber getTerm() {
        return term;
    }

    public void setTerm(final XNumber term) {
        this.term = term;
    }

    @Doc("The rate of the loan, expressed as a proportion; for example, five percent interest would be 0.05.")
    @Prop(P.rate)
    @NotNull(message = "finance.with-loan must have a rate: argument")
    @BindNamedArgument("rate")
    public XNumber getRate() {
        return rate;
    }

    public void setRate(final XNumber rate) {
        this.rate = rate;
    }

    @Doc({
        "The tilt of the loan's repayment schedule, expressed as a proportion.",
        "By default this is zero, which results in a loan with equal repayments;",
        "if this is nonzero, the repayments grow geometrically by this amount, so",
        "the repayment each year is the previous year's multiplied by (1+tilt)."
    })
    @Prop(P.tilt)
    @BindNamedArgument("tilt")
    public XNumber getTilt() {
        return tilt;
    }

    public void setTilt(final XNumber tilt) {
        this.tilt = tilt;
    }

    @Doc("A function which will be used after the installation of the action to finance to compute the principal of the loan.")
    @Prop(P.principal)
    @BindNamedArgument
    @NotNull(message = "finance.with-loan must specify a principal")
    public XNumber getPrincipal() {
        return principal;
    }

    public void setPrincipal(final XNumber principal) {
        this.principal = principal;
    }
}
