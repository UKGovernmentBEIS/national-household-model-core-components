package uk.org.cse.stockimport.domain.impl;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.domain.IOccupantDetailsDTO;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

/**
 * OccupantDetailsDTO.
 * 
 * @author richardt
 * @version $Id: OccupantDetailsDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0.1
 */
@AutoProperty
public class OccupantDetailsDTO extends AbsDTO implements IOccupantDetailsDTO {
	private Optional<Integer> chiefIncomeEarnersAge;
    private Optional<Boolean> hasOccupantOnBenefits;
    private Optional<Boolean> hasDisabledOrSickOccupant;
    private Optional<Double> houseHoldIncomeBeforeTax;

    private Optional<DateTime> dateMovedIn = Optional.absent();
    private String workingHours = "";

    
    
	@Override
	public Optional<Integer> getChiefIncomeEarnersAge() {
		return chiefIncomeEarnersAge;
	}

	@Override
	public void setChiefIncomeEarnersAge(final Optional<Integer> chiefIncomeEarnersAge) {
		this.chiefIncomeEarnersAge = chiefIncomeEarnersAge;
	}

	@Override
	public Optional<Boolean> getHasOccupantOnBenefits() {
		return hasOccupantOnBenefits;
	}

	@Override
	public void setHasOccupantOnBenefits(final Optional<Boolean> hasOccupantOnBenefits) {
		this.hasOccupantOnBenefits = hasOccupantOnBenefits;
	}

	@Override
	public Optional<Boolean> getHasDisabledOrSickOccupant() {
		return hasDisabledOrSickOccupant;
	}

	@Override
	public void setHasDisabledOrSickOccupant(
			final Optional<Boolean> hasDisabledOrSickOccupant) {
		this.hasDisabledOrSickOccupant = hasDisabledOrSickOccupant;
	}

	@Override
	public Optional<Double> getHouseHoldIncomeBeforeTax() {
		return houseHoldIncomeBeforeTax;
	}

	@Override
	public void setHouseHoldIncomeBeforeTax(
			final Optional<Double> houseHoldIncomeBeforeTax) {
		this.houseHoldIncomeBeforeTax = houseHoldIncomeBeforeTax;
	}

	@Override
	public Optional<DateTime> getDateMovedIn() {
		return dateMovedIn;
	}

	@Override
	public void setDateMovedIn(final Optional<DateTime> dateMovedIn) {
		this.dateMovedIn = dateMovedIn;
	}

	@Override
	public String getWorkingHours() {
		return workingHours;
	}

	@Override
	public void setWorkingHours(final String workingHours) {
		this.workingHours = workingHours;
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
