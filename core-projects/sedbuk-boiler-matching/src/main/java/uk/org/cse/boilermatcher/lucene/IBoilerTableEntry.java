package uk.org.cse.boilermatcher.lucene;

import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

/**
 * IBoilerTableEntry.
 *
 * @since 1.0
 */
public interface IBoilerTableEntry {
    /**
     * @since 1.0
     */
    public String getManufacturer();

    /**
     * @since 1.0
     */
    public String getBrand();

    /**
     * @since 1.0
     */
    public String getModel();

    /**
     * @since 1.0
     */
    public String getQualifier();

    /**
     * @since 1.0
     */
    public BoilerType getBoilerType();

    /**
     * @since 1.0
     */
    /**
     * @since 1.0
     */
    public FuelType getFuelType();

    /**
     * @since 1.0
     */
    public FlueType getFlueType();

    /**
     * @since 1.0
     */
    public Boolean isCondensing();

    /**
     * @since 1.0
     */
    public double getAnnualEfficiency();

    /**
     * @since 1.0
     */
    public double getWinterEfficiency();

    /**
     * @since 1.0
     */
    public double getSummerEfficiency();

    /**
     * @since 1.0
     */
    public double getStoreBoilerVolume();

    /**
     * @since 1.0
     */
    public double getStoreSolarVolume();

    /**
     * @since 1.0
     */
    public double getStoreInsulationThickness();

    /**
     * @since 1.0
     */
    public int getRow();

}
