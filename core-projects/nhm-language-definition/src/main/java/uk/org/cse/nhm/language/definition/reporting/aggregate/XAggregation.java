package uk.org.cse.nhm.language.definition.reporting.aggregate;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

@Category(CategoryType.AGGREGATE_VALUES)
public abstract class XAggregation extends XElement implements IHouseContext {
    @BindNamedArgument
	@Doc("The name of this aggregation, which will be used as a column heading in the report.")
	public String getName() {
		return super.getName();
	}

	public void setName(String name) {
		super.setName(name);
	}
}
