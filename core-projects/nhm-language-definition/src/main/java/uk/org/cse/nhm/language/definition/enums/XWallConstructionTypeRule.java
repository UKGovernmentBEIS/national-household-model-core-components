package uk.org.cse.nhm.language.definition.enums;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc("This is used to match the construction types of walls.")
@Category(CategoryType.LOGICHOUSE)
public enum XWallConstructionTypeRule {
	@Doc({
		"Any, any solid and any cavity are not actual construction types a wall can have.",
		"Instead, they are shorthands for subsets of the actual list of construction types.",
		"Any is self-explanatory."
	})
	Any,
	@Doc({"This corresponds to any construction type which is not matched by AnyCavity."})
	AnySolid,
	@Doc({"This is any of cavity, timber frame, system build or metal frame."})
	AnyCavity,
	
    GraniteOrWhinstone,
    Sandstone,
    SolidBrick,
    Cob,
    @Doc("This is specifically a masonry cavity; timber frame, system build and metal frame may also have a cavity.")
    Cavity,
    TimberFrame,
    SystemBuild,
    MetalFrame;
}
