package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("These are all the construction types that the NHM knows")
@Category(CategoryType.MEASURES)
public enum XWallConstructionType {

    GraniteOrWhinstone, Sandstone, SolidBrick, Cob, @Doc("This is specifically a masonry cavity; timber frame, system build and metal frame may also have a cavity.")
    Cavity, TimberFrame, SystemBuild, MetalFrame;
}
