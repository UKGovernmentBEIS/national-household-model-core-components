package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;

public enum XEfficiencyMeasurement {
    @Doc({
        "Only boilers have a separate winter efficiency.",
        "For other heat sources, this will return the main efficiency",
        "For resistive electric heaters, this value is always 100%",
        "For heat pumps, returns the coefficient of performance.",
        "Solar hot water installations will return an efficiency of 0 (although in practice there should always be a higher priority system which will be used instead)."
    })
    Winter,
    @Doc({
        "Only boilers have a separate summer efficiency.",
        "For other heat sources, this will return the same value as the winter efficiency."
    })
    Summer,
    @Doc({
        "Computes an effective efficiency for the heating system based on the results of the energy calculation.",
        "Note that the same heat source may be used for multiple heating systems.",
        "When using this mechanism, we only get the heat source's efficiency for the particular system we are looking at."
    })
    InSitu
}
