package uk.org.cse.nhm.language.definition.reporting.two;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;

@Doc("Adds a column to a report definition. This defines the column's name, how its value should be computed, and whether any aggregations should be produced for it.")
@Bind("column")
@Category(CategoryType.REPORTING)
public class XReportColumn extends XReportPart {
	public static final class P {
		public static final String value = "value";
		public static final String aggregations = "aggregations";
	}
	private XFunction value;
	private List<XColumnAggregation> aggregations = new ArrayList<>();
	
	@BindNamedArgument
	@Doc("This will be used as the column title in the report")
	@Override
	public String getName() {
		return super.getName();
	}
	
	@Prop(P.value)
	@BindNamedArgument
	@Doc("This is the function that will be computed to produce the value of this column. The value will be present in the disaggregated and summarised outputs of the report.")
	@NotNull(message = "column element requires a value argument")
	public XFunction getValue() {
		return value;
	}

	public void setValue(XFunction value) {
		this.value = value;
	}

	@Prop(P.aggregations)
	@BindNamedArgument("summary")
	@Doc("These aggregations of the column value will be calculated and emitted into the summary tables of the report, when the value: is numeric.")
	public List<XColumnAggregation> getAggregations() {
		return aggregations;
	}

	public void setAggregations(List<XColumnAggregation> aggregations) {
		this.aggregations = aggregations;
	}
}
