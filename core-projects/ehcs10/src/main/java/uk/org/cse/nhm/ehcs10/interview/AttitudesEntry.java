package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.*;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface AttitudesEntry extends SurveyEntry {
	@SavVariableMapping("IFCARNEW")
	public Integer getNumberOfCarsOwned_Available();

	@SavVariableMapping("FREWKSAT")
	public Double getHowSatisfiedWereYouWithTheWorkThatWasUndertaken();

	@SavVariableMapping("FREWKCON")
	public Double getWereYouConsultedAboutThisWorkByYourFreeholderBeforeTheWorkWasUndertaken();

	@SavVariableMapping("FRWKSOWN")
	public Double getBecameASharedOwnerAfterFreeholderPurchasedAShareOfProperty();

	@SavVariableMapping("HAS238N")
	public Double getTakingEverythingIntoAccount_HowSatisfiedAreYouWithTheHousingServicesProvidedByYourLandlord_Freeholder();

	@SavVariableMapping("FRWKPAY")
	public Double getHowEasyDidYouFindItToSettleThis_TheseBills();

	@SavVariableMapping("FRWKLOAN")
	public Double getLoan();

	@SavVariableMapping("FREMAJWK")
	public Double getOverTheLast3YearsHasYourFreeholderUndertakenAnyMajorWorkToTheShared_CommonAreasOfThisBlockOfFlats_EgTheRoof_Lifts_CommunalHeatingSystem();

	@SavVariableMapping("FRWKDISC")
	public Double getEarlyPaymentDiscount();

	@SavVariableMapping("FRWKTEN")
	public Double getBecameATenantAfterFreeholderPurchasedProperty();

	@SavVariableMapping("FRWKSPRD")
	public Double getSpreadCostOverASpecifiedPeriod();

	@SavVariableMapping("FRWKBILL")
	public Double getDidYouReceiveABill_S_FromYourFreeholderForTheseWorks();

	@SavVariableMapping("FRWKINFO")
	public Double getInformationOnGovernmentSchemeToWaive_ReduceBill();

	@SavVariableMapping("FRWKCOST")
	public Double getHowMuchInTotalWasYourHouseholdChargedForThisWork();

	@SavVariableMapping("NHALARMS")
	public Enum69 getCar_BurglarAlarms();

	@SavVariableMapping("NHSTREET")
	public Enum69 getOtherNeighboursOrPeopleInStreet();

	@SavVariableMapping("NHSFNTE")
	public Enum233 getFeelSafeAlone_OutsideAtNight();

	@SavVariableMapping("NHROAD")
	public Enum69 getRoadTraffic();

	@SavVariableMapping("HAS44")
	public Enum235 getSatisfiedWithArea();

	@SavVariableMapping("NHANIM")
	public Enum69 getAnimals_E_G_Dogs_();

	@SavVariableMapping("NHNONE")
	public Enum69 getNoise_NoneOfThese();

	@SavVariableMapping("NHSHOPS")
	public Enum69 getFactories_Workshops();

	@SavVariableMapping("NHCOMMON")
	public Enum69 getImmediateNeighbours_CommonAreasOfFlats();

	@SavVariableMapping("QUARTER")
	public Enum230 getFieldworkQuarter();

	@SavVariableMapping("HASC2AL")
	public Enum241 getVandalism_GraffitiOrOtherDeliberateDamageToProperty();

	@SavVariableMapping("NHSITES")
	public Enum69 getBuildingSites();

	@SavVariableMapping("HSATIS")
	public Enum235 getSatisfactionWithAccommodation();

	@SavVariableMapping("HASC2KL")
	public Enum241 getPeopleUsingOrDealingDrugs();

	@SavVariableMapping("HASC2JL")
	public Enum241 getTroublesomeTeenagers();

	@SavVariableMapping("NHRDWKS")
	public Enum69 getRoadworks();

	@SavVariableMapping("HASC2LL")
	public Enum241 getFearOfBeingBurgled();

	@SavVariableMapping("NHENT")
	public Enum69 getPubs_ClubsOrEntertainment();

	@SavVariableMapping("NHHMSF1")
	public Enum249 getFeelSafeAlone_InHome();

	@SavVariableMapping("LLDSAT")
	public Enum235 getSatisfactionWithRepairs_Maintenance();

	@SavVariableMapping("HASINTR")
	public Enum251 getIntroductionToAccommodationSatisfactionQuestions();

	@SavVariableMapping("CASECAT")
	public Enum229 getStatusOfCase();

	@SavVariableMapping("LLDSATRE")
	public Enum253 getReasonForDissatisfaction();

	@SavVariableMapping("NHSFDAY")
	public Enum233 getFeelSafeAlone_OutsideDuringDay();

	@SavVariableMapping("SATTEN")
	public Enum255 getSatisfiedWithCurrentTenure();

	@SavVariableMapping("NHPLANES")
	public Enum69 getAeroplanes();

	@SavVariableMapping("NHBLDD")
	public Enum257 getCauseOfNeighbour_CommonAreasNoise();

	@SavVariableMapping("BETWORS")
	public Enum258 getChangeInArea();

	@SavVariableMapping("HASC2ML")
	public Enum241 getPeopleBeingDrunkOrRowdyInPublic();

	@SavVariableMapping("HASC2FL")
	public Enum241 getNoisyNeighboursOrLoudParties();

	@SavVariableMapping("HASC2CL")
	public Enum241 getGeneralLevelOfCrime();

	@SavVariableMapping("TENBW")
	public Enum262 getChangeInHousingService();

	@SavVariableMapping("NHOTHR")
	public Enum69 getOtherNoiseSources();

	@SavVariableMapping("HASINT")
	public Enum251 getIntroductionToProblemsInTheArea();

	@SavVariableMapping("MANINTR")
	public Enum265 getOpinionAboutHowAccommodationIsManagedByLandlord();

	@SavVariableMapping("HASC2EL")
	public Enum241 getLitterOrRubbishLyingAround();

	@SavVariableMapping("HASC2GL")
	public Enum241 getRacialOrReligiousHarassment();

	@SavVariableMapping("GOREHS")
	public Enum30 getRegion_EHSOrder();

	@SavVariableMapping("NHTRAINS")
	public Enum69 getTrains();

}

