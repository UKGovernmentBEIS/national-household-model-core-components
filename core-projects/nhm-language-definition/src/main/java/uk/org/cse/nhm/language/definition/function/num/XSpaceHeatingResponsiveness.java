package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("house.heating-responsiveness")
@Doc({
	"Returns the responsiveness of a dwelling's primary space-heating.",
	"If the system is absent, the dwelling will be heated with assumed portable electric heating, which has a responsiveness of 1.",
	"Responsiveness is a quantity defined by SAP which determines how quickly a heating system adapts to changing temperatures. It ranges between 0 and 1.",
})
public class XSpaceHeatingResponsiveness extends XHouseNumber {
}
