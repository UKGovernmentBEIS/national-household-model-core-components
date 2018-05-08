package uk.org.cse.nhm.language.definition;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.exposure.XExposure;
import uk.org.cse.nhm.language.definition.exposure.XScheduleInit;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.group.XAllHousesGroup;
import uk.org.cse.nhm.language.definition.group.XGroup;
import uk.org.cse.nhm.language.definition.two.actions.XApplyHookAction;
import uk.org.cse.nhm.language.definition.two.hooks.XDateHook;

@Doc(
        {
            "A target relates together a group, an exposure and an action.",
            "The group restricts the population of houses which the target can ever affect.",
            "The exposure determines when and how houses are sampled from the group.",
            "Finally, when houses are sampled by the exposure, the action determines what is done to them."
        }
)
@Bind("target")
@Obsolete(
        reason = XDateHook.SCHEDULING_OBSOLESCENCE,
        inFavourOf = {XDateHook.class, XApplyHookAction.class}
)
@Category(CategoryType.OBSOLETE)
public class XTarget extends XPolicyAction implements IHouseContext {

    public static final class P {

        public static final String GROUP = "group";
        public static final String EXPOSURE = "exposure";
        public static final String ACTION = "action";
    }

    private XGroup group = new XAllHousesGroup();
    private XExposure exposure = new XScheduleInit();
    private XAction action;

    @Override
    @Doc("A unique name for this target")

    @BindNamedArgument

    @NotNull(message = "target must always have a name.")
    @Identity
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(final String name) {
        super.setName(name);
    }

    @Prop(P.GROUP)
    @Doc("The group of houses to which this target can apply.")
    @NotNull(message = "target must contain a group")
    @BindNamedArgument("group")
    public XGroup getGroup() {
        return group;
    }

    public void setGroup(final XGroup group) {
        this.group = group;
    }

    @Prop(P.EXPOSURE)
    @Doc("An exposure, which determines when and how houses are chosen from the group.")
    @NotNull(message = "target must contain an exposure.")
    @BindNamedArgument("exposure")
    public XExposure getExposure() {
        return exposure;
    }

    public void setExposure(final XExposure exposure) {
        this.exposure = exposure;
    }

    @Prop(P.ACTION)
    @Doc("The action to take against chosen houses.")
    @NotNull(message = "target must contain an action.")
    @BindNamedArgument("action")
    public XAction getAction() {
        return action;
    }

    public void setAction(final XAction action) {
        this.action = action;
    }
}
