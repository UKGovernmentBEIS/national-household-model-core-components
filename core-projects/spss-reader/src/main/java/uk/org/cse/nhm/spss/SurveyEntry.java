package uk.org.cse.nhm.spss;

import uk.org.cse.nhm.spss.wrap.SavVariableMapping;

public interface SurveyEntry {

    public String getId();

    @SavVariableMapping("AACODE")
    public String getAacode();
}
