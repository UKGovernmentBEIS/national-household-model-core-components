package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Doc;

public enum XChangeDirection {
    @Doc("The value will be set exactly to the specified number.")
    Set,
    @Doc("If the existing value is currently below the specified number, it will be set to the specified number. Otherwise, continue to use the existing value.")
    Increase,
    @Doc("If the existing value is currently above the specified number, it will be set to the specified number. Otherwise, continue to use the existing value.")
    Decrease
}
