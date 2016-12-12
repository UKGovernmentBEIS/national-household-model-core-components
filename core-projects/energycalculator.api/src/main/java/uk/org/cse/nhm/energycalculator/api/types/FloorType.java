package uk.org.cse.nhm.energycalculator.api.types;

public enum FloorType {
	External(AreaType.ExternalFloor),
	Party(AreaType.PartyFloor);

	private final AreaType areaType;

	private FloorType(final AreaType areaType) {
		this.areaType = areaType;
	}

	public AreaType getAreaType() {
		return this.areaType;
	}
}
