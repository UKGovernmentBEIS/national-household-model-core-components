package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IOccupantDetailsDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;

public class OccupantDetailsMapperTest extends AbsMapperTest {
	private final DateTime expectedDateMovedIn = new DateTime(2013, 2, 27, 13, 34);
	private final Integer expectedChiefIncomeEarnersAge = 37;
	private final double expectedHouseHoldIncomeBeforeTax = 37500.23;
	
	private final boolean hasDisabledOrSickOccupant = true;
	private final boolean hasOccupantOnBenefits = false;
	
	private final String expectedHours = "41.3";
	
	@Before
	public void initiateTests(){
		fields()
		//Assertions
		.add(IBasicDTO.AACODE, String.valueOf("WARGLE"))
		.add(IOccupantDetailsDTO.MOVEDINDATE_FIELD, String.valueOf(expectedDateMovedIn.getMillis()))
		
		.add(IOccupantDetailsDTO.INCOMEEARNERAGE_FIELD, String.valueOf(expectedChiefIncomeEarnersAge))
		.add(IOccupantDetailsDTO.HOUSEHOLDINCOME_FIELD, String.valueOf(expectedHouseHoldIncomeBeforeTax))
		
		.add(IOccupantDetailsDTO.ONBENEFITS_FIELD, Boolean.toString(hasOccupantOnBenefits))
		.add(IOccupantDetailsDTO.SICKORDISABLED_FIELD, Boolean.toString(hasDisabledOrSickOccupant))
	
		.add(IOccupantDetailsDTO.WORKINGHOURS_FIELD, expectedHours);
	}
	
	@Test
	public void testMapFieldSet() throws Exception {
		final IOccupantDetailsDTO detailsDTO = new MappableDTOReader<>(OccupantDetailsDTO.class).read(fieldSet);
		
		testWorkingHoursData(detailsDTO, expectedHours);
		testBenefitData(detailsDTO, hasDisabledOrSickOccupant, hasOccupantOnBenefits);
		testPeopleData(detailsDTO, expectedDateMovedIn);
		testIncomeData(detailsDTO, expectedChiefIncomeEarnersAge, expectedHouseHoldIncomeBeforeTax);
	}

	public static final void testWorkingHoursData(final IOccupantDetailsDTO detailsDTO, final String expectedHours){
		assertEquals("working hours", expectedHours, detailsDTO.getWorkingHours());
	}
	
	public static final void testBenefitData(final IOccupantDetailsDTO detailsDTO, final boolean hasDisabledOrSickOccupant, final boolean hasOccupantOnBenefits){
		assertEquals("disabled", hasDisabledOrSickOccupant, detailsDTO.getHasDisabledOrSickOccupant().get());
		assertEquals("on benefits", hasOccupantOnBenefits, detailsDTO.getHasOccupantOnBenefits().get());
	}
	
	public static final void testPeopleData(
			final IOccupantDetailsDTO detailsDTO, final DateTime expectedDateMovedIn){
		assertEquals("Date Moved In", Optional.of(expectedDateMovedIn),detailsDTO.getDateMovedIn());
	}
	
	public static final void testIncomeData(final IOccupantDetailsDTO detailsDTO,
			final Integer expectedChiefIncomeEarnersAge,final double expectedHouseHoldIncomeBeforeTax){
		assertEquals("chiefIncomeEarnerAge", expectedChiefIncomeEarnersAge, detailsDTO.getChiefIncomeEarnersAge().get());
		assertEquals("houseHoldIncome", expectedHouseHoldIncomeBeforeTax, detailsDTO.getHouseHoldIncomeBeforeTax().get(),0d);
	}
}
