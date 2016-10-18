package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("The different kinds of energy calculation available in the model.")
@Category(CategoryType.CATEGORIES)
public enum XEnergyCalculatorType {
	@Doc("Based on http://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf")
	SAP2012,
	
	@Doc("Based on http://www.bre.co.uk/filelibrary/bredem/BREDEM-2012-specification.pdf")
	BREDEM2012
}
