package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("group.all")
@Doc("At any time, this group contains all of the houses in the simulation.")
public class XAllHousesGroup extends XGroup {
	public static XAllHousesGroup create() {
		XAllHousesGroup group = new XAllHousesGroup();
		group.setName("all-houses");
		return group;
	}
}
