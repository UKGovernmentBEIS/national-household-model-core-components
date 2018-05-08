package uk.org.cse.nhm.simulator.action;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class ModifyFlagsAction extends AbstractNamed implements IComponentsAction, IModifier<IFlags> {

    private boolean removeFlag;
    private String flag;
    private final IDimension<IFlags> flagsDimension;

    @Inject
    public ModifyFlagsAction(
            final IDimension<IFlags> flagsDimension,
            @Assisted final boolean setFlag,
            @Assisted final String flag
    ) {
        this.flagsDimension = flagsDimension;
        this.removeFlag = !setFlag;
        this.flag = flag;
    }

    public boolean isRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(final boolean removeFlag) {
        this.removeFlag = removeFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    @Override
    public boolean modify(final IFlags flags) {
        final boolean hasFlag = flags.testFlag(flag);
        if (hasFlag && removeFlag) {
            flags.removeFlag(flag);
            return true;
        } else if (!(hasFlag || removeFlag)) {
            flags.addFlag(flag);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        scope.modify(flagsDimension, this);

        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }
}
