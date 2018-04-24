package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;


@Bind("house.any-wall")
@Doc(
		{
			"A test which will match houses for which there is at least one wall where all of the requirements on that wall are met.",
			"The requirements are defined by the arguments below."
		}
	)
public class XAnyWall extends XWallsTest {
}
