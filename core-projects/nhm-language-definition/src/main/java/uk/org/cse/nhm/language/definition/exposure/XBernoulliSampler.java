package uk.org.cse.nhm.language.definition.exposure;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc(
        {
            "A Bernoulli sampler which can be taken to flip a biased coin for each house under consideration each time it is sampled.",
            "This should contain a single function which can compute a probability for the house's membership.",
            "The function's value is clamped to the range 0..1, but may legally yield values outside this range."
        }
)

@Bind("sample.bernoulli")
public class XBernoulliSampler extends XSampler {

    private XNumber parameter;

    @Doc("This function will be used to determine, on a house-by-house basis for each sampling, the probability that the house is included in the sample.")

    @NotNull(message = "sample.bernoulli must contain a function to determine the probability of each dwelling being sample.")
    @BindPositionalArgument(0)
    public XNumber getParameter() {
        return parameter;
    }

    public void setParameter(final XNumber parameter) {
        this.parameter = parameter;
    }
}
