package uk.org.cse.stockimport.domain;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * IOccupantDetailsDTO.
 *
 * @author richardt
 * @version $Id: IOccupantDetailsDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0.1
 */
@DTO(value = "occupants", required = false)
public interface IOccupantDetailsDTO extends IBasicDTO {

    public static final String MOVEDINDATE_FIELD = "dateMovedIn";
    public static final String INCOMEEARNERAGE_FIELD = "chiefIncomeEarnersAge";
    public static final String SICKORDISABLED_FIELD = "hasDisabledOrSickOccupant";
    public static final String ONBENEFITS_FIELD = "hasOccupantOnBenefits";
    public static final String HOUSEHOLDINCOME_FIELD = "houseHoldIncomeBeforeTax";
    public static final String WORKINGHOURS_FIELD = "workingHours";

    @DTOField(INCOMEEARNERAGE_FIELD)
    public Optional<Integer> getChiefIncomeEarnersAge();

    public void setChiefIncomeEarnersAge(Optional<Integer> chiefIncomeEarnersAge);

    @DTOField(ONBENEFITS_FIELD)
    public Optional<Boolean> getHasOccupantOnBenefits();

    public void setHasOccupantOnBenefits(Optional<Boolean> hasOccupantOnBenefits);

    @DTOField(SICKORDISABLED_FIELD)
    public Optional<Boolean> getHasDisabledOrSickOccupant();

    public void setHasDisabledOrSickOccupant(Optional<Boolean> value);

    @DTOField(HOUSEHOLDINCOME_FIELD)
    public Optional<Double> getHouseHoldIncomeBeforeTax();

    public void setHouseHoldIncomeBeforeTax(Optional<Double> value);

    @DTOField(MOVEDINDATE_FIELD)
    public Optional<DateTime> getDateMovedIn();

    public void setDateMovedIn(Optional<DateTime> dateMovedIn);

    @DTOField(WORKINGHOURS_FIELD)
    public String getWorkingHours();

    public void setWorkingHours(String workingHours);
}
