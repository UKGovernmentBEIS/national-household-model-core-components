package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

import com.larkery.jasb.bind.Bind;


@Bind("house.all-walls")
@Doc(
		{
			"A test which will match houses for which every wall meets all of the given requirements.",
			"The requirements are defined by the arguments below."
		}
	)
@SeeAlso(XAnyWall.class)
public class XEveryWall extends XWallsTest {
}
