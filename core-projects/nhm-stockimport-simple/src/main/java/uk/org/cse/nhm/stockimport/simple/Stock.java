package uk.org.cse.nhm.stockimport.simple;

import java.util.List;

import uk.org.cse.nhm.hom.SurveyCase;

public class Stock {

    public final Metadata metadata;
    public final List<SurveyCase> cases;
    public final int missingCases;

    public Stock(final Metadata metadata, final List<SurveyCase> cases, final int missingCases) {
        super();
        this.metadata = metadata;
        this.cases = cases;
        this.missingCases = missingCases;
    }
}
