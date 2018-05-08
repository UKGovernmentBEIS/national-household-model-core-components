package uk.org.cse.nhm.energycalculator.api.types.steps;

public class SAPLocation {

    public static final SAPLocation None = new SAPLocation(null, 0, ' ') {
        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public String toString() {
            return "This step is not present in the SAP worksheet.";
        }
    };

    public final SAPWorksheetSection section;
    public final int cell;
    public final Character subcell;

    SAPLocation(SAPWorksheetSection section, int cell, Character subcell) {
        this.section = section;
        this.cell = cell;
        this.subcell = subcell;
    }

    public boolean exists() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("SAP 2012 worksheet cell (%d%s) in section %s.",
                cell,
                subcell == null ? "" : subcell,
                section.toString()
        );
    }
}
