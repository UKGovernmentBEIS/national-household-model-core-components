package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("This is used to match the insulation which is or is not present on a wall.")
@Category(CategoryType.LOGICHOUSE)
public enum XWallInsulationRule {
    Any,
    NoInsulation,
    NoCavity,
    NoExternal,
    NoInternal,
    SomeInsulation,
    OnlyFilledCavity,
    OnlyInternal,
    OnlyExternal
}
