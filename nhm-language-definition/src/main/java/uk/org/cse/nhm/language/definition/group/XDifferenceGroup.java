package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("group.difference")
@Doc({
	"At any time, this group will contain the difference between its first source, and the union of the remaining sources."
})
public class XDifferenceGroup extends XGroupWithSources {

}
