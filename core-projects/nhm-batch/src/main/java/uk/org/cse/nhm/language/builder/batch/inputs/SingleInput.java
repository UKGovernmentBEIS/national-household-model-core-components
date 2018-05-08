package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Collections;
import java.util.List;

public abstract class SingleInput implements IBatchInputs {

    private final String placeholder;

    protected SingleInput(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public final List<String> getPlaceholders() {
        return Collections.singletonList(placeholder);
    }
}
