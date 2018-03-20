package uk.org.cse.nhm.language.definition.function.num.basic;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;


@Bind("-")
@Doc({"If there is more than one argument, computes the difference between the first argument and the sum of all the remaining arguments.",
	"If there is only one argument, computes its negation."})
public class XDifference extends XBasicNumberFunction {

}
