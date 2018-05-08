package uk.org.cse.nhm.simulator.hooks;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class BernoulliDwellings extends AbstractNamed implements IDwellingSet {

    private final IDwellingSet source;
    private final IComponentsFunction<Number> coin;

    @AssistedInject
    public BernoulliDwellings(
            @Assisted final IDwellingSet source, @Assisted final IComponentsFunction<Number> coin) {
        super();
        this.source = source;
        this.coin = coin;
    }

    @Override
    public Set<IDwelling> get(final IState state, final ILets lets) {
        final Set<IDwelling> source_ = source.get(state, lets);
        final java.util.Random random = state.getRandom();

        if (coin instanceof ConstantComponentsFunction) {
            final double d = ((ConstantComponentsFunction<Number>) coin).getValue().doubleValue();
            final double d_ = Math.max(0, Math.min(1, d));

            if (d_ == 0) {
                return Collections.emptySet();
            } else if (d_ == 1) {
                return source_;
            } else {
                final LinkedHashSet<IDwelling> out = new LinkedHashSet<>();
                for (final IDwelling dwelling : source_) {
                    if (random.nextDouble() < d_) {
                        out.add(dwelling);
                    }
                }
                return out;
            }
        } else {
            final LinkedHashSet<IDwelling> out = new LinkedHashSet<>();
            for (final IDwelling dwelling : source_) {
                final double d_
                        = Math.max(0, Math.min(1,
                                coin.compute(state.detachedScope(dwelling), lets).doubleValue()
                        ));
                if (random.nextDouble() < d_) {
                    out.add(dwelling);
                }
            }
            return out;
        }
    }

}
