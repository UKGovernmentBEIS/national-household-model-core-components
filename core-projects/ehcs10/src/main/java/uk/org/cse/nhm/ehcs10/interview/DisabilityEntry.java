package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum294;
import uk.org.cse.nhm.ehcs10.interview.types.Enum296;
import uk.org.cse.nhm.ehcs10.interview.types.Enum314;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface DisabilityEntry extends SurveyEntry {

    @SavVariableMapping("HRP")
    public Integer getPersonNumberOfHRP();

    @SavVariableMapping("PERSNO")
    public Integer getPersonIdentifier();

    @SavVariableMapping("QHEALTH1")
    public Enum294 getGeneralHealth();

    @SavVariableMapping("DSDKNW")
    public Enum69 getDon_TKnow();

    @SavVariableMapping("WHFREQ")
    public Enum296 getFrequency_Wheelchair();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("DSBREATH")
    public Enum69 getBreathing();

    @SavVariableMapping("DSMENTAL")
    public Enum69 getMentalHealth();

    @SavVariableMapping("DSVISION")
    public Enum69 getVision();

    @SavVariableMapping("WHCHAIR")
    public Enum69 getUsesWheelchair();

    @SavVariableMapping("DSOTHER")
    public Enum69 getOther();

    @SavVariableMapping("DSLRNDF")
    public Enum69 getLearningDifficulty();

    @SavVariableMapping("CIGNOW")
    public Enum69 getSmokeCigarettesNow();

    @SavVariableMapping("LSILL")
    public Enum69 getLong_StndgIllness_Disab_Infirmity();

    @SavVariableMapping("DSHEART")
    public Enum69 getHeartDisease();

    @SavVariableMapping("DSHEARIN")
    public Enum69 getHearing();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("ILLLIM")
    public Enum69 getWhetherLimitsActivities();

    @SavVariableMapping("DSREG")
    public Enum69 getRegisteredDisabled();

    @SavVariableMapping("DSMOBLTY")
    public Enum69 getMobility();

    @SavVariableMapping("SMOKEVER")
    public Enum69 getEverSmoked();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("WHINSIDE")
    public Enum314 getWheelchairUseInHome();

}
