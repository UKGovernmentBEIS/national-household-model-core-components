package uk.org.cse.nhm.language.definition.reporting.aggregate;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;


@Bind("aggregate.count")
@Doc(
		{
			"Count is the simplest aggregation rule - it takes a set of houses to the size of that set"
		}
	)
public class XCountAggregation extends XAggregation {

}
