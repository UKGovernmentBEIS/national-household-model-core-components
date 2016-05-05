package uk.org.cse.hom.money;

import org.pojomatic.annotations.AutoProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.ICopyable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FinancialAttributes.
 * 
 * @author richardt
 * @version 1
 * @since 1.1.0
 */
@AutoProperty
public class FinancialAttributes implements ICopyable<FinancialAttributes> {
    @SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(FinancialAttributes.class);
    private final Double houseHoldIncomeBeforeTax;
    private final Integer ageOfChiefIncomeEarner;

    /**
     * Default Constructor.
     */
	@JsonCreator
    public FinancialAttributes(
    		@JsonProperty("ageOfChiefIncomeEarner") final Integer ageOfChiefIncomeEarner, 
    		@JsonProperty("houseHoldIncomeBeforeTax") final Double houseHoldIncomeBeforeTax) {
        this.houseHoldIncomeBeforeTax = houseHoldIncomeBeforeTax;
        this.ageOfChiefIncomeEarner = ageOfChiefIncomeEarner;
    }

    /**
     * Returns the age of the chief income earner in the house.
     * @return
     * @since 1.1.0
     */
    public Integer getAgeOfChiefIncomeEarner() {
        // FIX: Could be linked to the People in a dwelling, could also be a date of birth on that person
        return ageOfChiefIncomeEarner;
    }

    /**
     * Returns the hose hold income before tax.
     * 
     * @return house income before tax, can be zero
     * @since 1.1.0
     */
    public Double getHouseHoldIncomeBeforeTax() {
        return houseHoldIncomeBeforeTax;
    }
    /**
     * @since 1.1.1
     */
	@Override
	public FinancialAttributes copy() {
		return new FinancialAttributes(getAgeOfChiefIncomeEarner(), getHouseHoldIncomeBeforeTax());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ageOfChiefIncomeEarner == null) ? 0
						: ageOfChiefIncomeEarner.hashCode());
		result = prime
				* result
				+ ((houseHoldIncomeBeforeTax == null) ? 0
						: houseHoldIncomeBeforeTax.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FinancialAttributes other = (FinancialAttributes) obj;
		if (ageOfChiefIncomeEarner == null) {
			if (other.ageOfChiefIncomeEarner != null)
				return false;
		} else if (!ageOfChiefIncomeEarner.equals(other.ageOfChiefIncomeEarner))
			return false;
		if (houseHoldIncomeBeforeTax == null) {
			if (other.houseHoldIncomeBeforeTax != null)
				return false;
		} else if (!houseHoldIncomeBeforeTax
				.equals(other.houseHoldIncomeBeforeTax))
			return false;
		return true;
	}
}
