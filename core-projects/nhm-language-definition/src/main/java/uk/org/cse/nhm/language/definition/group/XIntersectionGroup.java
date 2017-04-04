package uk.org.cse.nhm.language.definition.group;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("group.intersection")
@Doc("At any time, this group will contain only those houses which are present in all of its source groups.")
public class XIntersectionGroup extends XGroupWithSources {

}
