package uk.org.cse.stockimport.domain.geometry;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.components.fabric.types.CoveringType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofStructureType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.Constraint;
import uk.org.cse.stockimport.domain.schema.Constraint.Type;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * IRoof.
 *
 * @author richardt
 * @version $Id: IRoof.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */

@DTO("roofs")
public interface IRoofDTO extends IBasicDTO {
	public static final String INSULATION_THICKNESS_FIELD = "insulationThickness";
	public static final String CONSTRUCTION_TYPE_FIELD = "contructionType";
	public static final String COVERING_TYPE_FIELD = "coveringType";
	public static final String STRUCTURE_TYPE = "structureType";
	
	@DTOField(CONSTRUCTION_TYPE_FIELD)
	public RoofConstructionType getRoofType();
	public void setRoofType(RoofConstructionType roofType);
	@DTOField(STRUCTURE_TYPE)
	public RoofStructureType getStructureType();
	public void setStructureType(RoofStructureType structureType);
	@DTOField(COVERING_TYPE_FIELD)
	public CoveringType getCoveringType();
	public void setCoveringType(CoveringType coveringType);
	@DTOField(value=INSULATION_THICKNESS_FIELD, constraint=@Constraint(value=Type.OPTIONAL, missing="null"))
	public Optional<Double> getInsulationThickness();
	public void setInsulationThickness(Optional<Double> insulationThickness);
}
