package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Doc;

@Doc("The different types of lighting understood by the energy calculator.")
public enum XLightType {
	Incandescent("Incandescent"), 
	CFL("CFL"), 
	Halogen("Halogen"), 
	LED("LED"), 
	LVHalogen("LV-Halogen"), 
	APlusPlus("A++");
	
	private XLightType(final String label) {
		this.label = label;
	}
	
	private final String label;
	
	@Override
	public String toString() {
		return label;
	}
}
