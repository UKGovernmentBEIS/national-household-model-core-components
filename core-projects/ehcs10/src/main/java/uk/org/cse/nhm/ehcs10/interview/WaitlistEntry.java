package uk.org.cse.nhm.ehcs10.interview;

import uk.org.cse.nhm.ehcs10.derived.types.Enum24;
import uk.org.cse.nhm.ehcs10.derived.types.Enum30;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1035;
import uk.org.cse.nhm.ehcs10.interview.types.Enum1040;
import uk.org.cse.nhm.ehcs10.interview.types.Enum229;
import uk.org.cse.nhm.ehcs10.interview.types.Enum230;
import uk.org.cse.nhm.ehcs10.interview.types.Enum823;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.spss.SurveyEntry;

public interface WaitlistEntry extends SurveyEntry {

    @SavVariableMapping("FUNO2")
    public Integer getFUOfPersonOnW_List();

    @SavVariableMapping("FUNO1")
    public Integer getFUOfPersonOnW_List_FUNO1();

    @SavVariableMapping("WILANUM")
    public Integer getHowManyLAWaitingListsPersonIsOn();

    @SavVariableMapping("NAMEL1")
    public Integer getPersonNoOnW_List1();

    @SavVariableMapping("WILANUM2")
    public Integer getHowManyLAWaitingListsPersonIsOn_WILANUM2();

    @SavVariableMapping("WIHANUM2")
    public Integer getHowManyHAWaitingListsPersonIsOn();

    @SavVariableMapping("NAMEL2")
    public Integer getPersonNoOnW_List2();

    @SavVariableMapping("NAMEL3")
    public Integer getPersonNoOnW_List3();

    @SavVariableMapping("NOLIST")
    public Integer getNoInHhldOnW_List();

    @SavVariableMapping("WIHANUM")
    public Integer getHowManyHAWaitingListsPersonIsOn_WIHANUM();

    @SavVariableMapping("FUNO3")
    public Integer getFUOfPersonOnW_List_FUNO3();

    @SavVariableMapping("WILANUM3")
    public Integer getHowManyLAWaitingListsPersonIsOn_WILANUM3();

    @SavVariableMapping("TIMEW01")
    public Enum823 getTimeOnW_List();

    @SavVariableMapping("WLISTCH5")
    public Enum69 getApplBy_WithANon_HhldMember();

    @SavVariableMapping("WLISTB4")
    public Enum69 getAnotherHhldMemberOnW_List();

    @SavVariableMapping("WLISTB1")
    public Enum69 getAnotherHhldMemberOnW_List_WLISTB1();

    @SavVariableMapping("WLISTCH4")
    public Enum69 getApplBy_WithANon_HhldMember_WLISTCH4();

    @SavVariableMapping("FUNO5")
    public Enum24 getFUOfPersonOnW_List_FUNO5();

    @SavVariableMapping("CASECAT")
    public Enum229 getStatusOfCase();

    @SavVariableMapping("TYPEW3")
    public Enum1035 getLAOrHA();

    @SavVariableMapping("WLISTB3")
    public Enum69 getAnotherHhldMemberOnW_List_WLISTB3();

    @SavVariableMapping("TIMEW04")
    public Enum823 getTimeOnW_List_TIMEW04();

    @SavVariableMapping("WIHANUM4")
    public Enum24 getHowManyHAWaitingListsPersonIsOn_WIHANUM4();

    @SavVariableMapping("TYPEW4")
    public Enum1035 getLAOrHA_TYPEW4();

    @SavVariableMapping("WLISTCH1")
    public Enum1040 getApplBy_WithANon_HhldMember_WLISTCH1();

    @SavVariableMapping("TIMEW03")
    public Enum823 getTimeOnW_List_TIMEW03();

    @SavVariableMapping("GOREHS")
    public Enum30 getRegion_EHSOrder();

    @SavVariableMapping("TIMEW02")
    public Enum823 getTimeOnW_List_TIMEW02();

    @SavVariableMapping("FUNO4")
    public Enum24 getFUOfPersonOnW_List_FUNO4();

    @SavVariableMapping("WIHANUM3")
    public Enum24 getHowManyHAWaitingListsPersonIsOn_WIHANUM3();

    @SavVariableMapping("TYPEW2")
    public Enum1035 getLAOrHA_TYPEW2();

    @SavVariableMapping("WILANUM5")
    public Enum24 getHowManyLAWaitingListsPersonIsOn_WILANUM5();

    @SavVariableMapping("QUARTER")
    public Enum230 getFieldworkQuarter();

    @SavVariableMapping("TYPEW1")
    public Enum1035 getLAOrHA_TYPEW1();

    @SavVariableMapping("WLISTB2")
    public Enum69 getAnotherHhldMemberOnW_List_WLISTB2();

    @SavVariableMapping("WLISTCH2")
    public Enum69 getApplBy_WithANon_HhldMember_WLISTCH2();

    @SavVariableMapping("WLISTCH3")
    public Enum69 getApplBy_WithANon_HhldMember_WLISTCH3();

    @SavVariableMapping("TYPEW5")
    public Enum1035 getLAOrHA_TYPEW5();

    @SavVariableMapping("WLISTB5")
    public Enum69 getAnotherHhldMemberOnW_List_WLISTB5();

    @SavVariableMapping("TIMEW05")
    public Enum823 getTimeOnW_List_TIMEW05();

    @SavVariableMapping("NAMEL5")
    public Enum24 getPersonNoOnW_List5();

    @SavVariableMapping("WILANUM4")
    public Enum24 getHowManyLAWaitingListsPersonIsOn_WILANUM4();

    @SavVariableMapping("WIHANUM5")
    public Enum24 getHowManyHAWaitingListsPersonIsOn_WIHANUM5();

    @SavVariableMapping("NAMEL4")
    public Enum24 getPersonNoOnW_List4();

}
