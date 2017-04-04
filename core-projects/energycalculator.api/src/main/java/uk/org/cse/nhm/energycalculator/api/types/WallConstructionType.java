package uk.org.cse.nhm.energycalculator.api.types;

import static uk.org.cse.nhm.energycalculator.api.types.WallType.Internal;
import static uk.org.cse.nhm.energycalculator.api.types.WallType.Party;

import java.util.EnumSet;
import java.util.Set;

import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;

/**
 * WallConstructionType.
 *
 * @author richardt
 * @version $Id: WallConstructionType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum WallConstructionType {
	
    Internal_Any(Internal),
   
    Party_Solid(Party),
    Party_Cob(Party),
    Party_Cavity(Party),
    Party_TimberFrame(Party),
    Party_SystemBuild(Party),
    Party_MetalFrame(Party),
    
    GraniteOrWhinstone(internalAndExternal(), Party_Solid),
    Sandstone(internalAndExternal(), Party_Solid),
    SolidBrick(internalAndExternal(), Party_Solid),
    Cob(internalAndExternal(), Party_Cob),
    Cavity(EnumSet.allOf(WallInsulationType.class), Party_Cavity),
    TimberFrame(EnumSet.allOf(WallInsulationType.class), Party_TimberFrame),
    SystemBuild(EnumSet.allOf(WallInsulationType.class), Party_SystemBuild),
    MetalFrame(EnumSet.allOf(WallInsulationType.class), Party_MetalFrame);
    
    private static Set<WallInsulationType> internalAndExternal() { 
    	return EnumSet.of(WallInsulationType.Internal, WallInsulationType.External);
    }
    
    private final WallType wallType;
    private final Set<WallInsulationType> allowedInsulationTypes;
	private final WallConstructionType partyWallEquivalent;
    
    public boolean isAllowedInsulationType(final WallInsulationType wit) {
    	return allowedInsulationTypes.contains(wit);
    }

    private WallConstructionType(final Set<WallInsulationType> insulationTypes, final WallConstructionType partyWallEquivalent) {
    	this(WallType.External, insulationTypes, partyWallEquivalent);
    }
    
    private WallConstructionType(final WallType type, final Set<WallInsulationType> insulationTypes, final WallConstructionType partyWallEquivalent) {
    	this.wallType = type;
    	this.allowedInsulationTypes = EnumSet.copyOf(insulationTypes);
    	this.partyWallEquivalent = partyWallEquivalent;
    }
    
    private WallConstructionType(final WallType type) {
        this(type, EnumSet.noneOf(WallInsulationType.class), null);
    }

   public WallType getWallType() {
       return wallType;
   }
   
   public WallConstructionType getPartyWallEquivalent() {
	   if (getWallType() == WallType.Party) {
		   return this;
	   } else if (partyWallEquivalent == null) {
		   throw new UnsupportedOperationException("Cannot make a party wall out of " + this);
	   } else {
		   return partyWallEquivalent;
	   }
   }

   /**
    * Get all the construction types suitable for external walls.
    * @since 1.1.0
    * @return
    */
	public static Set<WallConstructionType> getExternalWallTypes() {
		return EnumSet.of(GraniteOrWhinstone,
			    Sandstone,
			    SolidBrick,
			    Cob,
			    Cavity,
			    TimberFrame,
			    SystemBuild,
			    MetalFrame);
	}
}
