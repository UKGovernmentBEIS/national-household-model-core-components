package uk.org.cse.nhm.energycalculator.api.types;

/**
 * The zone 2 control parameter from SAP 2012 Table 9 and Table 4e.
 *
 * Used to determine the zone 2 demand temperature and heating schedule.
 */
public enum Zone2ControlParameter {
    One(0),
    Two(1),
    Three(1);

    private final int controlledProportion;

    private Zone2ControlParameter(final int controlledProportion) {
        this.controlledProportion = controlledProportion;
    }

    public double controlledProportion() {
        return controlledProportion;
    }
}
