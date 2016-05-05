package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;

import uk.org.cse.commons.Glob;

@Bind("house.flags")
@Doc("Produces a comma-separated list of all flags on the house which match a given pattern")
public class XMatchingFlags extends XCategoryFunction {
    private Glob glob = Glob.of("*");

    public static final class P {
        public static final String glob = "glob";
    }
    
    @BindPositionalArgument(0)
    @Doc({
            "A pattern to restrict the flags to include. The default of * includes all flags.",
                "A pattern like my-flag-* includes all flags starting with my-flag-.",
                "A pattern like my-flag-??? includes all flags starting with my-flag and ending with 3 characters.",
                "A pattern like &lt;x,y,z&gt; includes the flags x, y, and z.",
                "A pattern like &lt;x*,y*&gt; includes any flags starting with x or y.",
                "If the first character of the pattern is !, the pattern is negated."
    })
    public Glob getGlob() {
        return glob;
    }
    
    public void setGlob(Glob glob) {
        this.glob = glob;
    }
}
