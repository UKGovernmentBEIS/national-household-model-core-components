package uk.ac.ucl.hideem;

/**
 * A health outcome class which cares only about accumulated effects.
 */
public class CumulativeHealthOutcome extends HealthOutcome {
    public CumulativeHealthOutcome(final int horizon) {

    }

    public void addEffects(final Disease.Type disease, final int year, final Person whom,
                           final double mortality, final double morbidity, final double cost) {

    }
}
