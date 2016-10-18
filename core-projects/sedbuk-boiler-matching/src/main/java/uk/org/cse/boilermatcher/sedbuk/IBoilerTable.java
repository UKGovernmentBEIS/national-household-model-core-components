package uk.org.cse.boilermatcher.sedbuk;

import java.util.List;

import org.joda.time.DateTime;

import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

/**
 * @since 1.0
 */
public interface IBoilerTable extends ITable {
    /**
     * @since 1.0
     */
    public static final int ID = 104;
    /**
     * @since 1.0
     */
    public static final int CURRENT_FORMAT = 208;
	
	/**
	 * A non-generated method, which finds the fuel type
	 * @param row
	 * @return
	 * @since 1.0
	 */
	public FuelType getFuelType(int row);
	
	/**
     * @since 1.0
     */
    public FlueType getFlueType(int row);
	
    /**
     * @since 1.0
     */
    public BoilerType getBoilerType(int row);
	
    /**
     * @since 1.0
     */
    @Column(value=5, search=true)
	public List<Integer> findByModelName(final String modelName);
	
    /**
     * @since 1.0
     */
    @Column(value = 0, search = true)
	public List<Integer> findByProductIndexNumber(int productIndexNumber);

    /**
     * @since 1.0
     */
    /**
	 * Looks up the boiler by brand and model.  
	 * 
	 * @param boilerBrand
	 * @param boilerModel
	 * @return An integer row number where the boiler's details may be looked up, or null if it could not be found.
	 */
	public Integer matchByBrandAndModel(final String boilerBrand, final String boilerModel);
	
	/**
     * @since 1.0
     */
    @Column(0)
	public int getProductIndexNumber(int row);
	
    /**
     * @since 1.0
     */
    @Column(1)
	public int getManufacturerReferenceNumber(int row);
	
    /**
     * @since 1.0
     */
    @Column(2)
	public DateTime getEntryUpdatedDate(int row);
	
    /**
     * @since 1.0
     */
    @Column(3)
	public String getManufacturerName(int row);
	
    /**
     * @since 1.0
     */
    @Column(4)
	public String getBrandName(int row);
	
    /**
     * @since 1.0
     */
    @Column(5)
	public String getModelName(int row);
	
    /**
     * @since 1.0
     */
    @Column(6)
	public String getModelQualifier(int row);
	
    /**
     * @since 1.0
     */
    @Column(7)
	public String getBoilerIdentifier(int row);
	
    /**
     * @since 1.0
     */
    @Column(17)
	public Boolean isCondensing(int row);

    /**
     * @since 1.0
     */
    @Column(23)
	public double getAnnualEfficiency(int row);
	
    /**
     * @since 1.0
     */
    @Column(24)
	public double getWinterEfficiency(int row);
	
    /**
     * @since 1.0
     */
    @Column(25)
	public double getSummerEfficiency(int row);

    /**
     * @since 1.0
     */
    @Column(39)
	public double getStoreBoilerVolume(int i);

    /**
     * @since 1.0
     */
    @Column(40)
	public double getStoreSolarVolume(int i);

    /**
     * @since 1.0
     */
    @Column(41)
	public double getStoreInsulationThickness(int i);
}
