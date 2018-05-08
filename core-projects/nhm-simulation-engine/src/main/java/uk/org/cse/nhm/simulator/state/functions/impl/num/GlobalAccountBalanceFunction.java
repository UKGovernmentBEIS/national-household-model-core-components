package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GlobalAccountBalanceFunction extends AbstractNamed implements IComponentsFunction<Number> {

    private final String accountName;

    @AssistedInject
    GlobalAccountBalanceFunction(@Assisted final String accountName) {
        super();
        this.accountName = accountName;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        return scope.getGlobalAccount(accountName).getBalance();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
