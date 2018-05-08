package uk.org.cse.nhm.language.definition.exposure;

import javax.validation.constraints.Min;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

@Doc(
        {
            "The count sampler will pick count houses from the source group when used.",
            "Houses are sampled uniformly at random with replacement.",
            "If the number of houses in the group is less than count, all the houses will be sampled."
        }
)
@Bind("sample.count")
public class XCountSampler extends XSampler {

    public static class P {

        public static final String COUNT = "count";
    }
    private int count;

    @Prop(P.COUNT)

    @BindPositionalArgument(0)
    @Doc("The number of houses to sample (this is automatically scaled by the quantum, so it represents a number of real households)")
    @Min(value = 0, message = "sample.count 'count' attribute must be a positive whole number.")
    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }
}
