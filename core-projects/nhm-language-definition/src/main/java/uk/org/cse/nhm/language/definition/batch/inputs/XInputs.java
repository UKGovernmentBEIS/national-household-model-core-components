package uk.org.cse.nhm.language.definition.batch.inputs;

import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.TopLevel;
import uk.org.cse.nhm.language.definition.XElement;

@Category(CategoryType.BATCH)
@TopLevel
public abstract class XInputs extends XElement {

    public static class P {

        public static final String PLACEHOLDERS = "placeholders";
    }

    /**
     * This is used for validation purposes only.
     */
    public abstract boolean hasBound();

    @Prop(P.PLACEHOLDERS)
    public abstract List<String> getPlaceholders();
}
