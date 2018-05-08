package uk.org.cse.nhm.hom;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;

/**
 * POJO containing the basic attributes of a case, which can't really be changed
 * by a measure, and are not derived properties of any of the rest of the case's
 * condition. These are things like AACode, built form, region and so on.
 *
 * @author hinton
 *
 */
@AutoProperty
public class BasicCaseAttributes implements ICopyable<BasicCaseAttributes> {

    private String aacode;
    private double dwellingCaseWeight;
    private double householdCaseWeight;
    private RegionType regionType;
    private int buildYear;
    private TenureType tenureType;
    private MorphologyType morphologyType;
    private SiteExposureType siteExposure = SiteExposureType.Average;

    /**
     * Default Constructor.
     */
    public BasicCaseAttributes() {
    }

    public BasicCaseAttributes(
            final String aacode,
            final double dwellingCaseWeight,
            final double householdCaseWeight,
            final RegionType regionType,
            final MorphologyType morphologyType,
            final TenureType tenureType,
            final int buildYear,
            final SiteExposureType siteExposure) {

        this.aacode = aacode;
        this.dwellingCaseWeight = dwellingCaseWeight;
        this.householdCaseWeight = householdCaseWeight;
        this.regionType = regionType;
        this.tenureType = tenureType;
        this.morphologyType = morphologyType;
        this.buildYear = buildYear;
        this.siteExposure = siteExposure;
    }

    public String getAacode() {
        return aacode;
    }

    public double getDwellingCaseWeight() {
        return dwellingCaseWeight;
    }

    public double getHouseholdCaseWeight() {
        return householdCaseWeight;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public TenureType getTenureType() {
        return tenureType;
    }

    public MorphologyType getMorphologyType() {
        return morphologyType;
    }

    public void setMorphologyType(final MorphologyType morphologyType) {
        this.morphologyType = morphologyType;
    }

    public SiteExposureType getSiteExposure() {
        return siteExposure;
    }

    public void setSiteExposure(final SiteExposureType siteExposure) {
        this.siteExposure = siteExposure;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    /**
     * hashcode generated using Eclipse source menu, includes all fields.
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aacode == null) ? 0 : aacode.hashCode());
        result = prime * result + buildYear;
        long temp;
        temp = Double.doubleToLongBits(dwellingCaseWeight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(householdCaseWeight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((morphologyType == null) ? 0 : morphologyType.hashCode());
        result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
        result = prime * result + ((siteExposure == null) ? 0 : siteExposure.hashCode());
        result = prime * result + ((tenureType == null) ? 0 : tenureType.hashCode());
        return result;
    }

    @Override
    /**
     * equals generated using Eclipse source menu, includes all fields.
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicCaseAttributes other = (BasicCaseAttributes) obj;
        if (aacode == null) {
            if (other.aacode != null) {
                return false;
            }
        } else if (!aacode.equals(other.aacode)) {
            return false;
        }
        if (buildYear != other.buildYear) {
            return false;
        }
        if (Double.doubleToLongBits(dwellingCaseWeight) != Double.doubleToLongBits(other.dwellingCaseWeight)) {
            return false;
        }
        if (Double.doubleToLongBits(householdCaseWeight) != Double.doubleToLongBits(other.householdCaseWeight)) {
            return false;
        }
        if (morphologyType != other.morphologyType) {
            return false;
        }
        if (regionType != other.regionType) {
            return false;
        }
        if (siteExposure != other.siteExposure) {
            return false;
        }
        if (tenureType != other.tenureType) {
            return false;
        }
        return true;
    }

    @Override
    public BasicCaseAttributes copy() {
        return new BasicCaseAttributes(aacode, dwellingCaseWeight, householdCaseWeight, regionType, morphologyType, tenureType, buildYear, siteExposure);
    }
}
