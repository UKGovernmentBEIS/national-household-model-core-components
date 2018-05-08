package uk.org.cse.nhm.language.definition.sequence;

import uk.org.cse.nhm.language.definition.Doc;

@Doc("Defines one of the places where a numeric value can be stored in the model")
public enum XScope {
    @Doc({
        "Event scoped values are stored against each house, and live for the duration of the current event.",
        "An event means the top-level apply statement or report in which this variable is set.",
        "When an event scoped value is changed, the change is visible everywhere else in the event, except when",
        "(a) the change is in a hypothetical situation, like a choice alternative which has not taken place, or",
        "(b) the change is in a (do) block whose hide: attribute is true."
    })
    Event,
    @Doc({
        "The value will be kept on the current house for the rest of the simulation. It will be available",
        "whenever the same house is present."
    })
    House,
    @Doc({"The value will be shared between all houses for the rest of the simulation.",
        "These values can be used to track things like how many times an action has taken place, or the total amount of some quantity produced by modifying houses one at a time.",
        "Changes to a simulation scoped variables are still un-done when a hypothetical is not adopted as the truth, so if you modify a simulation value inside a (do) statement with",
        "another action, the variable will only be affected if the action is successful."
    })
    Simulation
}
