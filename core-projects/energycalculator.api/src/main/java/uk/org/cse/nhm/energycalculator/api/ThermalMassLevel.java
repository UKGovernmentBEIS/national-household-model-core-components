package uk.org.cse.nhm.energycalculator.api;

public enum ThermalMassLevel {
	/*
	BEISDOC
	NAME: Thermal Mass Level
	DESCRIPTION: The simplified thermal mass lookup.
	TYPE: lookup
	UNIT: kJ/m^2℃
	SAP: Table 1f
        SAP_COMPLIANT: Yes
	BREDEM: 4A
        BREDEM_COMPLIANT: Yes
	ID: thermal-mass-level
	CODSIEB
	*/
	LOW(100),
	MEDIUM(250),
	HIGH(450);

	private final double thermalMassParameter;

	private ThermalMassLevel(final double thermalMassParameter) {
		this.thermalMassParameter = thermalMassParameter;
	}

	public double getThermalMassParameter() {
		return thermalMassParameter;
	}
}
