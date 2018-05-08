package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

/**
 * WideInputCombinator is a type of batch input which combines multiple delegate
 * batch inputs into a wide table with columns from all of them.
 */
abstract class WideInputCombinator implements IBatchInputs {

    protected final List<IBatchInputs> delegates;

    protected WideInputCombinator(List<IBatchInputs> delegates) {
        this.delegates = delegates;
    }

    @Override
    public final List<String> getPlaceholders() {
        List<String> placeholders = new ArrayList<>();
        for (IBatchInputs d : delegates) {
            placeholders.addAll(d.getPlaceholders());
        }
        return placeholders;
    }
}
