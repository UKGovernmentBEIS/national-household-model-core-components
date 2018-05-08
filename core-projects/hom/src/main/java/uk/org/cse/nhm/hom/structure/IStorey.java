package uk.org.cse.nhm.hom.structure;

import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;

public interface IStorey {

    /**
     * TODO should there be an internal height as well
     *
     * @return the exterior height of the storey, which determines the area of
     * the walls in the storey
     */
    public double getHeight();

    /**
     * @param height a new exterior height for all walls in this storey
     */
    public void setHeight(double height);

    /**
     * @return the location type of this storey (is it a room in the roof, or a
     * basement, etc).
     */
    public FloorLocationType getFloorLocationType();

    /**
     * @param floorLocationType set the floor location type for this storey
     */
    public void setFloorLocationType(FloorLocationType floorLocationType);

    /**
     * @return the U-value for the floor of this storey - this is used along
     * with the area of the storey below to determine the specific heat loss
     * into unheated space underneath this storey.
     */
    public double getFloorUValue();

    /**
     * See {@link #getFloorUValue()}
     *
     * @param floorUValue
     */
    public void setFloorUValue(double floorUValue);

    /**
     * Analogous to {@link #getFloorUValue()}, but for the ceiling.
     *
     * @return
     */
    public double getCeilingUValue();

    /**
     * See {@link #getCeilingUValue()}
     *
     * @param ceilingUValue
     */
    public void setCeilingUValue(double ceilingUValue);

    /**
     * @return the product of the area of this storey with its height.
     */
    public double getVolume();

    /**
     * @return the area of this floor's polygon.
     */
    public double getArea();

    /**
     * @return the total length of all the walls in this storey.
     */
    public double getPerimeter();

    /**
     * @param elevationType
     * @return the total length of walls belonging to the given elevation
     */
    public double getLength(ElevationType elevationType);

    /**
     * This method is the gateway to modifying the properties of the individual
     * walls in this storey.
     *
     * See the {@link IMutableWall} interface for for more details.
     *
     * @return an iterable over the walls in this storey, which starts with the
     * segment from the first point in the polygon to the second, and ends with
     * the segment from the last point to the first.
     */
    public Iterable<IMutableWall> getWalls();
}
