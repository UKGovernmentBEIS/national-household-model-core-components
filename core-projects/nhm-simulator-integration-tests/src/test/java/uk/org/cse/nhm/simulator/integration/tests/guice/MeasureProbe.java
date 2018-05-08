package uk.org.cse.nhm.simulator.integration.tests.guice;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput.MeasureInvocation;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class MeasureProbe extends AbstractNamed implements IStateAction {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
            .getLogger(MeasureProbe.class);
    private final ISimulator sim;
    private final Collection<MeasureInvocation> invocations;
    private final String name;

    @Inject
    public MeasureProbe(final ISimulator sim, final IntegrationTestOutput output, @Assisted final String name) {
        this.sim = sim;
        this.name = name;
        this.invocations = output.getInvocationsForId(name);
    }

    @Override
    public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets)
            throws NHMException {
        log.debug("{} invoked on {} at {}", new Object[]{name, dwellings.size(), sim.getCurrentDate()});
        invocations.add(new MeasureInvocation(sim.getCurrentDate(), dwellings.size()));
        return Collections.<IDwelling>emptySet();
    }

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return dwellings;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.INTERNAL;
    }
}
