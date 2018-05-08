package uk.org.cse.nhm.language.definition.batch.inputs;

import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

public abstract class XSingleInput extends XInputs {

    public static final class P {

        public static final String PLACEHOLDER = "placeholder";
    }

    private String placeholder;

    @BindNamedArgument
    @NotNull(message = "input element must define a placeholder name.")
    @Pattern(message = "placeholder attribute must always start with the '$' character, and should be follow by one or more characters including numbers, letters and hyphens.", regexp = "\\$[a-zA-Z0-9\\-]+")
    @Prop(P.PLACEHOLDER)
    @Doc(value = {"The placeholder is used to tell the batch runner which parts of the scenario this input should modify.",
        "The scenario should have matching placeholders written as the values of attributes.",
        "Placeholders must always start with the '$' character."})
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(final String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    @Prop(XInputs.P.PLACEHOLDERS)
    public List<String> getPlaceholders() {
        return Collections.singletonList(getPlaceholder());
    }
}
