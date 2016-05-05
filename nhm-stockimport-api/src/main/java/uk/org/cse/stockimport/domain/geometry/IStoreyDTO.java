package uk.org.cse.stockimport.domain.geometry;

import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * IStoreyDTO.
 *
 * @author richardt
 * @version $Id: IStoreyDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@DTO(value = "storeys", required=true,
		description = {
				"Used to construct a storey for a Survey Case, a house can have one or more stories, no validation is currently implemented but it's assumed that there would be only one BASEMENT, GROUND, FIRST,  SECOND, TOP and ROOM IN ROOF, there may be more than one HIGHER_FLOOR.",
				"The shape of a floor is determined by the floor polygon x, y and number of points, these are used to construct a polygon for the floor. Area is then determined using the polygon.",
				"It's important to note that as of version 2.0, length, width and area are not longer utilised within the stock import."
		}
	)
public interface IStoreyDTO extends IBasicDTO {
	public static final String CEILINGHEIGHT_FIELD = "ceilingHeight";
	public static final String STOREYHEIGHT_FIELD = "storyHeight";
	public static final String TYPE_FIELD = "type";
	public static final String X_POLYPOINTS_FIELD = "polygonXPoints";
	public static final String Y_POLYPOINTS_FIELD = "polygonYPoints";
	public static final String NUM_POLYPOINTS_FIELD = "polyPoints";
	
	@DTOField(TYPE_FIELD)
	public FloorLocationType getLocationType() ;
	public void setLocationType(final FloorLocationType locationType) ;
	
	@DTOField(CEILINGHEIGHT_FIELD)
	public double getCeilingHeight() ;
	public void setCeilingHeight(final double ceilingHeight) ;
	
	@DTOField(STOREYHEIGHT_FIELD)
	public double getStoreyHeight() ;
	public void setStoreyHeight(final double storeyHeight) ;
	
	@DTOField({X_POLYPOINTS_FIELD, Y_POLYPOINTS_FIELD, NUM_POLYPOINTS_FIELD})
	public SimplePolygon getPolygon() ;
	public void setPolygon(final SimplePolygon polygon) ;
}
