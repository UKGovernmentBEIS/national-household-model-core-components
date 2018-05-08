package uk.org.cse.stockimport.imputation.house;

/**
 * HousePropertyImputer.
 *
 * @author richardt
 * @version $Id: HousePropertyImputer.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class HousePropertyImputer implements IHousePropertyImputer {

    private IHousePropertyTables housePropertyTables;

    public HousePropertyImputer(IHousePropertyTables housePropertyTables) {
        this.housePropertyTables = housePropertyTables;
    }

    /**
     * Returns factor storied in livingAreaFactions map, other wise returns
     * default value
     *
     * @param numberOfRooms
     * @return
     * @see
     * uk.org.cse.stockimport.imputation.house.IHousePropertyImputer#getLivingAreaFraction(int)
     */
    @Override
    public double getLivingAreaFraction(int numberOfRooms) {
        if (housePropertyTables.getlivingAreaFactions().containsKey(numberOfRooms)) {
            return housePropertyTables.getlivingAreaFactions().get(numberOfRooms);
        } else {
            return housePropertyTables.getDefaultFraction();
        }
    }
}
