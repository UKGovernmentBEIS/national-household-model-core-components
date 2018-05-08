package uk.org.cse.nhm.language.definition.group;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("group.difference")
@Doc({
    "At any time, this group will contain the difference between its first source, and the union of the remaining sources."
})
public class XDifferenceGroup extends XGroupWithSources {

}
