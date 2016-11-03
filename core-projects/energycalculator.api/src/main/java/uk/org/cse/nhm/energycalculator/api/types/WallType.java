package uk.org.cse.nhm.energycalculator.api.types;


/**
 * WallType.
 *
 * @author richardt
 * @version $Id: WallType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum WallType {
    External(AreaType.ExternalWall),
    Internal(AreaType.InternalWall),
    Party(AreaType.PartyWall);
	
	private final AreaType areaType;

	private WallType(AreaType areaType) {
		this.areaType = areaType;
	}
    
    public AreaType getAreaType() {
    	return areaType;
    }
    
    public boolean isExternal() {
    	return getAreaType().isExternal();
    }
}
