package uk.org.cse.nhm.language.definition.action.measure.insulation;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

@Bind("action.set-wall-construction")
@Doc({"An action which directly changes the construction type of every exterior wall of a house to a specific value.",
    "Note that this change requires the household case study to be re-imputed using the new external wall configurations."})
@Unsuitability("Only households with exterior walls qualify for this measure")
@Category(CategoryType.RESETACTIONS)
public class XModifyWallConstructionTypeAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String wallType = "wallType";
    }
    private WallConstructionType wallType;

    /**
     * Return the wallType.
     *
     * @return the wallType
     */
    @BindNamedArgument("wall-type")
    @Doc("The construction type to set on the wall")
    @Prop(P.wallType)
    @NotNull(message = "action.set-wall-construction must define a walltype")
    public WallConstructionType getWallType() {
        return this.wallType;
    }

    public void setWallType(WallConstructionType wallType) {
        this.wallType = wallType;
    }
}
