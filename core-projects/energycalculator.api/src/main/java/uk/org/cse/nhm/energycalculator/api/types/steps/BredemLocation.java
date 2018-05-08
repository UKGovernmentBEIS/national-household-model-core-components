package uk.org.cse.nhm.energycalculator.api.types.steps;

class BREDEMLocation {

    static final BREDEMLocation NotDetermined = new BREDEMLocation(null, null, null) {
        @Override
        boolean exists() {
            return false;
        }

        @Override
        public String toString() {
            return "We have not yet added BREDEM steps.";
        }
    };
    public static BREDEMLocation None = new BREDEMLocation(null, null, null) {
        @Override
        boolean exists() {
            return false;
        }

        @Override
        public String toString() {
            return "This step does not exist in BREDEM.";
        }
    };

    private final BREDEMSection section;
    private final Character step;
    private final String variable;

    boolean exists() {
        return true;
    }

    BREDEMLocation(BREDEMSection section, Character step, String variable) {
        this.section = section;
        this.step = step;
        this.variable = variable;
    }
}
