package uk.org.cse.nhm.energycalculator.api.types;

/**
 * RoofType.
 *
 * @author richardt
 * @version $Id: RoofType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum RoofType {
    ExternalHeatLoss(AreaType.ExternalCeiling),
    Party(AreaType.PartyCeiling);

	private final AreaType areaType;

	private RoofType(AreaType areaType) {
		this.areaType = areaType;
	}

	public AreaType getAreaType() {
		return areaType;
	}
}
