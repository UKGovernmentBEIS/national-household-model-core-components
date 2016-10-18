package uk.org.cse.nhm.language.definition.reporting.aggregate;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.group.XAllHousesGroup;
import uk.org.cse.nhm.language.definition.group.XGroup;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;


@Bind("division.by-group")
@Doc(
		value = {
				"This division rule produces subsets of the housing stock using the language elements used",
				"for defining groups. Each group contained in the division will have an associated timeseries in the aggregate."
		}
	)
public class XGroupDivision extends XDivision {
	public static final class P {
		public static final String groups = "groups";
	}
	
	private List<XGroup> groups = new ArrayList<XGroup>();

	@BindRemainingArguments
	
	@Doc({"Each of these groups will produce a timeseries in the aggregate report.",
		"Note that these groups may legally intersect without causing any probems."
	})
	public List<XGroup> getGroups() {
		return groups;
	}

	public void setGroups(final List<XGroup> groups) {
		this.groups = groups;
	}
	
	public static XGroupDivision allHouses() {
		final XGroupDivision result = new XGroupDivision();
		result.setName("division.all-houses");
		result.getGroups().add(XAllHousesGroup.create());
		return result;
	}
}
