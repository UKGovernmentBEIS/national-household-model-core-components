package uk.org.cse.nhm.language.definition.function.lookup;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.IAtomIO;

public class LookupRuleAtomIO implements IAtomIO {

    @Override
    public boolean canReadTo(final Class<?> arg0) {
        return arg0.isAssignableFrom(LookupRule.class);
    }

    @Override
    public String getDisplayName(final Class<?> arg0) {
        return "Lookup Rule";
    }

    @Override
    public Set<String> getLegalValues(final Class<?> arg0) {
        return ImmutableSet.<String>of(
                "*", "0", "1..10", ">10", "<30", "MainsGas"
        );
    }

    @Override
    public <T> Optional<T> read(final String arg0, final Class<T> arg1) {
        final LookupRule rule = LookupRule.of(arg0);
        return Optional.of(arg1.cast(rule));
    }

    @Override
    public boolean canWrite(final Object arg0) {
        return arg0 instanceof LookupRule;
    }

    @Override
    public String write(final Object arg0) {
        return String.valueOf(arg0);
    }

    @Override
    public boolean isBounded() {
        return false;
    }
}
