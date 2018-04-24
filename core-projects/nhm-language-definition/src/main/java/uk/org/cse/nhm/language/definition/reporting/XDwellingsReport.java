package uk.org.cse.nhm.language.definition.reporting;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.group.XAllHousesGroup;
import uk.org.cse.nhm.language.definition.group.XGroup;
import uk.org.cse.nhm.language.definition.reporting.modes.XOnChangeMode;
import uk.org.cse.nhm.language.definition.reporting.modes.XReportMode;
import uk.org.cse.nhm.language.definition.two.actions.XApplyHookAction;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;
import uk.org.cse.nhm.language.validate.BatchForbidden;


@Doc({
	"Defines a report on the dwellings in a particular group."
})
@BatchForbidden
@Bind("report.dwellings")
@Category(CategoryType.OBSOLETE)
@Obsolete(
	reason = XDateHook.SCHEDULING_OBSOLESCENCE,
	inFavourOf = {XDateHook.class, XApplyHookAction.class, XDwellingActionProbe.class}
		)
public class XDwellingsReport extends XCustomReportingElement implements IHouseContext {
	public static class P {
		public static final String GROUP = "group";
		public static final String FIELDS = "fields";
		public static final String MODE = "mode";
	}
	
	private XGroup group = XAllHousesGroup.create();
	private List<XFunction> fields = new ArrayList<>();
	private XReportMode mode = new XOnChangeMode();
	
	@Prop(P.GROUP)
	
	@NotNull(message = "report.dwellings must always contain a group to be reported on.")
	@Doc("The group whose contents the report will track.")
	@BindNamedArgument("group")
	public XGroup getGroup() {
		return group;
	}
	
	public void setGroup(final XGroup group) {
		this.group = group;
	}
	
	
	@Prop(P.FIELDS)
	@Doc("Additional fields this report will output data on.")
	@BindRemainingArguments
	public List<XFunction> getFields() {
		return fields;
	}
	
	public void setFields(final List<XFunction> fields) {
		this.fields = fields;
	}

	@Prop(P.MODE)
	
	@NotNull(message = "report.dwellings must always contain a mode to determine when it should report.")
	@Doc("The output mode of the report. This can be used to restrict the volume of the output data to fewer rows. Alternatively it can be used to compute an aggregate (over time) of each column.")
	@BindNamedArgument("mode")
	public XReportMode getMode() {
		return mode;
	}

	public void setMode(final XReportMode mode) {
		this.mode = mode;
	}
}
