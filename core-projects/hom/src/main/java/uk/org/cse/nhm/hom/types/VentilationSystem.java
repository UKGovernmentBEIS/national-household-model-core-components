package uk.org.cse.nhm.hom.types;

public enum VentilationSystem {
    Natural("Natural ventilation"),
    MechanicalPositiveInputFromOutside("Mechanical  - positive input ventilation from outside"),
    MechanicalPositiveInputFromLoft("Mechanical  - positive input ventilation from loft"),
    MechanicalWholeHouseExtract("Mechanical  - whole house extract ventilation"),
    MechanicalBalancedWholeHouseWithoutHeatRecovery("Mechanical - balanced whole house ventilation without heat recovery"),
    MechanicalBalancedWholeHouseWithHeatRecovery("Mechanical - balanced whole house ventilation with heat recovery");

    private String friendlyName;

    private VentilationSystem(final String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
