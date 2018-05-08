package uk.org.cse.nhm.language.definition.batch.inputs.random;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;

@Bind("discrete")
@Doc(value = {"A batch input which draws values randomly from a supplied list.",
    "Distributions are unbounded, and so may yield as many values as required."})
public class XDiscreteInput extends XDistributionInput {

    public static final class P {

        public static final String CHOICES = "choices";
    }

    List<XChoice> choices = new ArrayList<>();

    @BindRemainingArguments
    @Prop(P.CHOICES)
    @Doc("The choices which this distribution will draw from.")
    @Size(min = 1, message = "discrete distribution must contain one or more choices to select from.")
    public List<XChoice> getChoices() {
        return choices;
    }

    public void setChoices(final List<XChoice> choices) {
        this.choices = choices;
    }

    @Bind("choice")
    @Category(CategoryType.BATCH)
    public static class XChoice extends XElement {

        public static final class P {

            public static final String WEIGHT = "weight";
            public static final String VALUE = "value";
        }

        private String value;
        private int weight = 1;

        @Prop(P.VALUE)

        @BindPositionalArgument(0)
        @NotNull(message = "choice element must have a value.")
        @Doc("The value which will be inserted into the scenario if this choice is picked.")
        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }

        @Prop(P.WEIGHT)

        @BindNamedArgument
        @Min(value = 0, message = "choice element must have a weight greater than or equal to 0.")
        @Doc("Modifies the chance that this choice will get picked relative to the other choices.")
        public int getWeight() {
            return weight;
        }

        public void setWeight(final int weight) {
            this.weight = weight;
        }
    }
}
