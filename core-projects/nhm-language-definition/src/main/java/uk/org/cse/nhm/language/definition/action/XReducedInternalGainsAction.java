package uk.org.cse.nhm.language.definition.action;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Bind("action.reduced-internal-gains")
@Doc({
	"Marks the house as having reduced internal gains.",
	"This is based on Table 5 of SAP 2012, although adapted to work with the BREDEM 2012 gains formulae.",
	"Metabolic, lighting and appliance gains are modified as specified in the table.",
	"Cooking gains use the cooking energy use from BREDEM 2012 Table 5, scale it 23/35 for base load (EC1A, EC2A) and by 5/7 for per-occupant load (EC1B, EC2B), and then multiply it by the cooking gains factor from Bredem 2012 table 25.",
	"Reduced internal gains have no effect on the energy consumption of any of these, only on their gains output.",
	"Reduced internal gains have no effect at all when the energy calculator is in SAP 2012 mode."
})
@Category(CategoryType.ACTIONS)
public class XReducedInternalGainsAction extends XFlaggedDwellingAction{
}
