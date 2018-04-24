package uk.org.cse.nhm.language.definition.reporting.modes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;


@Bind("mode.dates")
@Doc("Report mode which records the current values at given dates or date intervals.")
@Category(CategoryType.OBSOLETE)
public class XDateMode extends XReportMode {
	public static class P {
		public static final String REPORTING_DATES = "reportingDates";
	}
	
	private List<XReportingDates> reportingDates = new ArrayList<>();
	
	@BindRemainingArguments
	
	@Prop(P.REPORTING_DATES)
	@Size(min = 1, message = "mode.dates must include at least one date when the report should record date.")
	@Doc({"The dates and date ranges on which the report will record data.", 
		"These dates may overlap or include each other and the report will attempt to meet them all.",
		"Dates before the start of the scenario or after it has ended will be disregarded."})
	public List<XReportingDates> getReportingDates() {
		return reportingDates;
	}

	public void setReportingDates(final List<XReportingDates> reportingDates) {
		this.reportingDates = reportingDates;
	}
	@Category(CategoryType.OBSOLETE)
	public static abstract class XReportingDates extends XElement {
	}

	
	@Bind("on")
	@Doc("Instructs the report to record data at a specific date and time.")
	public static class XOn extends XReportingDates {
		public static class P {
			public static final String ON = "on";
		}
		
		private DateTime on;

		
		@BindPositionalArgument(0)
		@NotNull(message = "once element must always include a date.")
		@Prop(P.ON)
		@Doc("A date on which the report will record data.")
		public DateTime getOn() {
			return on;
		}

		public void setOn(final DateTime on) {
			this.on = on;
		}
	}
	
	
	@Bind("scenario-start")
	@Doc("Instructs the report to record data at the start of the scenario.")
	public static class XScenarioStart extends XReportingDates {
	}
	
	
	@Bind("scenario-end")
	@Doc("Instructs the report to record data at the end of the scenario.")
	public static class XScenarioEnd extends XReportingDates {
	}
	
	
	@Bind("between")
	@Doc("Instructs the report to record data at intervals between two dates.")
	public static class XInterval extends XReportingDates {
		public static class P {
			public static final String START = "start";
			public static final String END = "end";
			public static final String INTERVAL = "interval";
		}
		
		private DateTime start;
		private DateTime end;
		private Period interval = PeriodFormat.getDefault().parsePeriod("1 year");
		
		
@BindNamedArgument
		@Prop(P.START)
		@NotNull(message = "between element always requires a start.")
		@Doc("The first date on which the report will be instructed to record data.")
		public DateTime getStart() {
			return start;
		}
		
		public void setStart(final DateTime start) {
			this.start = start;
		}
		
		
@BindNamedArgument
		@Prop(P.END)
		@NotNull(message = "between element always requires an end.")
		@Doc("This element will not instruct the report to generate data on or after this date.")
		public DateTime getEnd() {
			return end;
		}
		
		public void setEnd(final DateTime end) {
			this.end = end;
		}
		
		
@BindNamedArgument
		@Prop(P.INTERVAL)
		@NotNull(message = "between element always requires an interval.")
		@Doc("The duration of time the report will wait before recording again.")
		public Period getInterval() {
			return interval;
		}
		
		public void setInterval(final Period interval) {
			this.interval = interval;
		}
	}
}
