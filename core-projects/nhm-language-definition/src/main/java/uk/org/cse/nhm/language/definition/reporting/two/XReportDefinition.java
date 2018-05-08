package uk.org.cse.nhm.language.definition.reporting.two;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.*;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.XScenarioElement;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;

@Doc(
		{
			"Define a new report.",
			"This introduces a definition for a report, but does not produce any output on its own.",
			"",
			"To produce output in the report, you need to send houses to it from elsewhere in the scenario using send-to-report or",
			"the report: argument which most measures will have.",
			"",
			"When a house is sent to the report, a row will be appended to the disaggregated output table, and the house will be included in any summary output tables.",
			"The disaggregated table is produced by evaluating each column within the report definition, in the order they are written.",
			"",
			"Three additional columns are always produced: 'outcome', which is used when a measure sends a house to the report before and after its application," ,
			"'sent-from', which indicates which bit of the scenario sent a house to the report, and 'selected' indicates whether the condition the house was in when",
			"it was sent to the report actually came to pass (for example, you might send a house to the report from an alternative within a choice).",
			"",
			"Summary tables are produced from each cut within the report definition, by placing each house sent to the report into",
			"a distinct part of the cut, and then working out the summary: values of each column for each part of the cut."
		}
	)
@Category(CategoryType.REPORTING)
@Bind("def-report")
@SeeAlso(XSendToReport.class)
public class XReportDefinition extends XScenarioElement implements IHouseContext {
	public static class P {
		public static final String contents = "contents";
		public static final String recordChange = "recordChange";
	}
	private List<XReportPart> contents = new ArrayList<>();
	private boolean recordChange = true;

	@Doc({"A name for the report - this will be used to name the files that contain the results of the report, and",
		"as the identity to use when sending houses to the report from elsewhere in the scenario.",
		"",
		"You can use the value here as the first argument of send-to-report, or as the report: argument",
		"on most other actions."
	})
	@BindPositionalArgument(0)
	@Override
	@Identity
	public String getName() {
		return super.getName();
	}

	@Prop(P.recordChange)
	@BindNamedArgument("record-changes")
	@Doc({
			"If true, each column will be split into two (Before) and (After) columns.",
			"Reporting is triggered by an action.",
			"The (Before) column is the initial state of the dwelling before that action was applied.",
			"The (After) column is the state of the dwelling after that action had been applied, and may be N/A if the action failed for that dwelling.",
			"This mode will also cause the special 'outcome' and 'selected' columns to be included.",
			"If record-change is set to false, each column is output once, using only the values from before the action was applied."
	})
	public boolean getRecordChange() {
		return recordChange;
	}

	public void setRecordChange(boolean recordChange) {
		this.recordChange = recordChange;
	}

	@Prop(P.contents)
	@BindRemainingArguments
	@Doc({
		"Columns and cuts which should be present in the report. Each column specifies a value to output, and if/how it should be aggregated.",
		"Each cut specifies a way to subdivide the summary table produced by the report."
	})
	public List<XReportPart> getContents() {
		return contents;
	}

	public void setContents(List<XReportPart> columns) {
		this.contents = columns;
	}
}
