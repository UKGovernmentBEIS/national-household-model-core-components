package uk.org.cse.nhm.spss;

import uk.org.cse.nhm.spss.wrap.SavVariableMapping;

/**
 * SavAnnotatedSurveyEntry.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since $
 */
public class SavAnnotatedSurveyEntry extends SurveyEntryImpl {

    /**
     * @return
     * @see uk.org.cse.stockimport.spss.SurveyEntryImpl#getAacode()
     */
    @SavVariableMapping("AACODE")
    @Override
    public String getAacode() {
        return super.getAacode();
    }
}
