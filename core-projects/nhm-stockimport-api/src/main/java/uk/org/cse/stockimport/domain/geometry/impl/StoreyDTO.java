package uk.org.cse.stockimport.domain.geometry.impl;

import java.util.Comparator;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;

/**
 * StoreyDTO.
 *
 * @author richardt
 * @version $Id: StoreyDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@AutoProperty
public class StoreyDTO extends AbsDTO implements IStoreyDTO {

    /**
     * @since 1.0
     */
    public static final Comparator<IStoreyDTO> StoreyLocationTypeComparator = new StoreyLocationTypeComparator();

    private static class StoreyLocationTypeComparator implements Comparator<IStoreyDTO> {

        /**
         * @param o1
         * @param o2
         * @return
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(final IStoreyDTO o1, final IStoreyDTO o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else if (o2 == null) {
                return 0;
            } else {
                final FloorLocationType o1Location = o1.getLocationType();
                final FloorLocationType o2Location = o2.getLocationType();

                if ((o1Location == null) && (o2Location == null)) {
                    return 0;
                } else if (o2Location == null) {
                    return 0;
                } else {
                    return o1Location.getLevel() < o2Location.getLevel() ? -1 : 1;
                }
            }
        }

    }

    private FloorLocationType locationType;

    private double ceilingHeight = 0.0d;
    private double storeyHeight = 0.0d;

    private SimplePolygon polygon;

    public StoreyDTO() {
    }

    @Override
    public FloorLocationType getLocationType() {
        return locationType;
    }

    @Override
    public void setLocationType(final FloorLocationType locationType) {
        this.locationType = locationType;
    }

    @Override
    public double getCeilingHeight() {
        return ceilingHeight;
    }

    @Override
    public void setCeilingHeight(final double ceilingHeight) {
        this.ceilingHeight = ceilingHeight;
    }

    @Override
    public double getStoreyHeight() {
        return storeyHeight;
    }

    @Override
    public void setStoreyHeight(final double storeyHeight) {
        this.storeyHeight = storeyHeight;
    }

    @Override
    public SimplePolygon getPolygon() {
        return polygon;
    }

    @Override
    public void setPolygon(final SimplePolygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return Pojomatic.equals(this, other);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
