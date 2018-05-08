package uk.org.cse.stockimport.imputation.house;

import java.util.Map;
import java.util.TreeMap;

public class HousePropertyTables implements IHousePropertyTables {

    private final Map<Integer, Double> livingAreaFactions = new TreeMap<Integer, Double>();
    private double defaultFraction;

    @Override
    public Map<Integer, Double> getlivingAreaFactions() {
        return livingAreaFactions;
    }

    /**
     * @param numberOfRooms
     * @param fraction
     * @since 3.0
     */
    public void addLivingAreaFraction(int numberOfRooms, double fraction) {
        livingAreaFactions.put(numberOfRooms, fraction);
    }

    @Override
    public double getDefaultFraction() {
        return defaultFraction;
    }

    /**
     * @param defaultFraction
     * @since 3.0
     */
    public void setDefaultFraction(double defaultFraction) {
        this.defaultFraction = defaultFraction;
    }

}
