package uk.org.cse.stockimport.domain.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

/**
 * @since 1.0
 */
@AutoProperty
public class LowEnergyLightingDTO extends AbsDTO implements ILowEnergyLightingDTO {

    private double lowEnergyLightsFraction;

    public LowEnergyLightingDTO() {
        super();
    }

    /**
     * Default Constructor.
     *
     * @since 1.0
     */
    public LowEnergyLightingDTO(String aaCode, double lowEnergyLightsFraction) {
        this.setAacode(aaCode);
        this.lowEnergyLightsFraction = lowEnergyLightsFraction;
    }

    @Override
    public double getLowEnergyLightsFraction() {
        return lowEnergyLightsFraction;
    }

    /**
     * @since 1.0
     */
    public void setLowEnergyLightsFraction(double lowEnergyLightsFraction) {
        this.lowEnergyLightsFraction = lowEnergyLightsFraction;
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return Pojomatic.equals(this, other);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
