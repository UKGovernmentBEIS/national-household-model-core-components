package uk.org.cse.nhm.hom.components.fabric.types;

import static uk.org.cse.nhm.hom.components.fabric.types.WallType.Internal;
import static uk.org.cse.nhm.hom.components.fabric.types.WallType.Party;

import java.util.EnumSet;
import java.util.Set;

/**
 * WallConstructionType.
 *
 * @author richardt
 * @version $Id: WallConstructionType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum WallConstructionType {
	
    Internal_PlasterboardOnTimberFrame(Internal),
    Internal_DenseBlockDensePlaster(Internal),
    Internal_DenseBlockPlasterboardOnDabs(Internal),
   
    Party_DensePlasterBothSidesDenseBlocksCavity(Party),
    Party_SinglePlasterboardBothSidesDenseBlocksCavity(Party),
    Party_SinglePlasterboardBothSidesDenseCellularBlocksCavity(Party),
    Party_SinglePlasterboardBothSidesDenseAACBlocksCavity(Party),
    Party_DoublePlasterBothSidesTwinTimberFrame(Party),
    Party_MetalFrame(Party),
   
    GraniteOrWhinstone(internalAndExternal()),
    Sandstone(internalAndExternal()),
    SolidBrick(internalAndExternal()),
    Cob(internalAndExternal()),
    Cavity(EnumSet.allOf(WallInsulationType.class)),
    TimberFrame(EnumSet.allOf(WallInsulationType.class)),
    SystemBuild(EnumSet.allOf(WallInsulationType.class)),
    MetalFrame(EnumSet.allOf(WallInsulationType.class));
    
    private static Set<WallInsulationType> internalAndExternal() { 
    	return EnumSet.of(WallInsulationType.Internal, WallInsulationType.External);
    }
    
    private WallType wallType;
    private Set<WallInsulationType> allowedInsulationTypes;
    
    public boolean isAllowedInsulationType(final WallInsulationType wit) {
    	return allowedInsulationTypes.contains(wit);
    }

    private WallConstructionType(final Set<WallInsulationType> insulationTypes) {
    	this(WallType.External, insulationTypes);
    }
    
    private WallConstructionType(final WallType type, final Set<WallInsulationType> insulationTypes) {
    	this.wallType = type;
    	this.allowedInsulationTypes = EnumSet.copyOf(insulationTypes);
    }
    
    private WallConstructionType(final WallType type) {
        this(type, EnumSet.noneOf(WallInsulationType.class));
    }

   public WallType getWallType() {
       return wallType;
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
