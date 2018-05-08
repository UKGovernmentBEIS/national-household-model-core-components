package uk.org.cse.nhm.logging.logentry;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.hom.SurveyCase;

@AutoProperty
public class SurveyCaseLogEntry extends AbstractLogEntry {

    private SurveyCase surveyCase;

    @JsonCreator
    public SurveyCaseLogEntry(@JsonProperty("surveyCase") final SurveyCase sc) {
        this.surveyCase = sc;
    }

    public SurveyCase getSurveyCase() {
        return surveyCase;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
