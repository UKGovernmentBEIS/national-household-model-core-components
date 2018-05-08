package uk.org.cse.stockimport.imputation.house;

/**
 * IHousePropertyImputer.
 *
 * @author richardt
 * @version $Id: IHousePropertyImputer.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public interface IHousePropertyImputer {

    /**
     * Returns a living area faction based on the number of rooms in a property,
     * based on RdSAP S16:Living Area fraction.
     *
     * @param numberOfRooms
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    double getLivingAreaFraction(int numberOfRooms);
}
