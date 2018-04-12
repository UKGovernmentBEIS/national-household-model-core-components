package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("The different kinds of energy calculation available in the model.")
@Category(CategoryType.CATEGORIES)
public enum XEnergyCalculatorType {
	@Doc({"Based on http://www.bre.co.uk/filelibrary/SAP/2012/SAP-2012_9-92.pdf.",
		"",
		"In SAP2012 mode, all of the parameters affecting the energy calculation are constrained to SAP values.",
		"These include:",
		"",
		"- Thermal properties like u-values.",
		"",
		"- The weather and latitude.",
		"",
		"- The heating temperature and heating schedule.",
		"",
		"SAP2012 mode does not impose SAP tariffs or SAP carbon factors, so you still need to write",
		"these into your scenario if you want to compute a SAP score or an environmental impact rating."
	})
	SAP2012,
	
	@Doc("Based on http://www.bre.co.uk/filelibrary/bredem/BREDEM-2012-specification.pdf")
	BREDEM2012,
	
	@Doc({"Like SAP2012, except that the following parameters are free: ",
	"",
		"- Energy calculator adjustment terms are allowed.",
		"",
		"- Real occupancy is used.",
		"",
		"- Energy calibration is allowed.",
		"",
		"- Heating temperatures and schedule can be changed.",
		"",
		"- Scenario weather is used."
	})
	SAP2012_PHYSICAL,
	
	@Doc({"Like BREDEM2012, except that the lighting energy use coefficients differ.",
		"",
		"The changed coefficients were introduced for consistency with products policy modelling.",
		"They allow for additional types of lighting in houses, and also double the efficiency",
		"of compact flourescent lighting relative to SAP or BREDEM."
	})
	BEIS
	;
	
}
