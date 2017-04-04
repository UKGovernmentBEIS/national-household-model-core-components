package uk.org.cse.nhm.language.definition.two.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.language.validate.contents.ISpecialContentsForValidation;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Doc(
	 {
		 "A command which can be used inside a hook (like on.dates) to check that a particular condition is true",
		 "at a certain point in a scenario. This is intended for scenario quality assurance purposes; if the test fails",
		 "the scenario will stop abruptly and produce a diagnostic message in the error report."
	 }
)
@Bind("assert")
public class XAssertHookAction extends XHookAction 
    implements ISpecialContentsForValidation
{
	public static final class P {
		public static final String test = "test";
        public static final String over = "over";
        public static final String fatal = "fatal";
        public static final String debugValues = "debugValues";
	}

	private XBoolean test = null;
	private boolean fatal = true;
    private ISetOfHouses over = null;
    private List<XFunction> debugValues = new ArrayList<>();

    @Prop(P.debugValues)
    @Doc("A list of values to output for inspection if the assertion fails.")
    @BindNamedArgument("capture")
    public List<XFunction> getDebugValues() {
        return debugValues;
    }

    public void setDebugValues(final List<XFunction> debugValues) {
        this.debugValues = debugValues;
    }
    
    @Doc("A set of houses to run the test for - if specified, the assertion is checked on a house-by-house basis for the given houses.")
    @BindNamedArgument
    @Prop(P.over)
    public ISetOfHouses getOver() {
        return over;
    }
    
    public void setOver(final ISetOfHouses over) {
        this.over = over;
    }
    
    @Doc({"The test to use - this will be checked when the assertion is triggered.",
            "If the over argument has been specified, the test will be computed once for",
            "every house in that set, and must be true for all houses for the assertion to pass.",
            "If the over argument has been omitted, the test is computed once, with no houses.",
            "However, you can use a function like summarize to make an assertion about an aggregate value."      
            })
    @BindPositionalArgument(0)
    @Prop(P.test)
    @NotNull(message="every assertion must contain a test as its first argument")
    public XBoolean getTest() {
		return test;
	}
	
	public void setTest(final XBoolean test) {
		this.test = test;
	}
    
    @Doc({"Unless false, if the assertion is not true the simulation will stop immediately.", 
            "Otherwise, a warning will be generated in the warnings report."})
            @BindNamedArgument
            @Prop(P.fatal)
            public boolean getFatal() {
        return fatal;
    }
    
    public void setFatal(final boolean fatal) {
        this.fatal = fatal;
    }

    @Override
    public Set<Class<?>> getAdditionalRequirements() {
        return Collections.emptySet();
    }

    @Override
    public Set<Class<?>> getAdditionalProvisions() {
        if (over != null) {
            return Collections.<Class<?>>singleton(IHouseContext.class);
        } else {
            return Collections.emptySet();
        }
    }
}
