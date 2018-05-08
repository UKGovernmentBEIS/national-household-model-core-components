package uk.org.cse.stockimport.domain.types;

/**
 * Used to desribe the position of extra modules in relation to a foor plan.
 *
 * @author richardt
 */
public enum AddedFloorModulePosition {
    __MISSING(),
    LeftElevation_Centre,
    Unknown,
    FrontElevation_Centre,
    FrontElevation_Left,
    RightElevation_Centre,
    RightElevation_Front,
    BackElevation_Centre,
    LeftElevation_Back,
    RightElevation_Back,
    BackElevation_Left,
    LeftElevation_Front,
    BackElevation_Right,
    NoAdditionalPart,
    FrontElevation_Right,
}
