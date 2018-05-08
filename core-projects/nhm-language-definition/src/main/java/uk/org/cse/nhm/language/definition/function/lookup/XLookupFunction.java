package uk.org.cse.nhm.language.definition.function.lookup;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.validate.ISelfValidating;

@Bind("lookup")
@Doc({
    "This function looks up a number from an n-dimensional table",
    "where the coordinates are determined by evaluating n other functions."
})
@Category(CategoryType.ARITHMETIC)

public class XLookupFunction extends XNumber implements ISelfValidating {

    public static final class P {

        public static final String keys = "keys";
        public static final String entries = "entries";
        public static final String defaultValue = "defaultFunction";
        public static final String warnOnFallback = "warnOnFallback";
    }

    public XLookupFunction() {
        super();
    }

    @Doc("Defines an entry in a lookup table, by pairing a set of coordinates with a value to use")
    @Bind("entry")
    public static class XLookupEntry extends XElement {

        public static final class P {

            public static final String coordinates = "coordinates";
            public static final String value = "value";
        }

        private List<LookupRule> coordinates = new ArrayList<>();
        private XNumber value;

        public XLookupEntry() {
            super();
        }

        @Doc({
            "A list of values which are the coordinate for this entry; the entry will be used when the",
            "containing lookup function's keys evaluate to these values.",
            "You can also specify ranges and wildcards. See the documentation for LookupRule for more,",
            "but basically * matches any value for the key, &gt;50 matches any number above 50, 1-2",
            "matches any number from 1 to 2 inclusive, &lt;=8 matches any number less than or equal to 8,",
            "and so on."
        })
        @Prop(P.coordinates)
        @BindPositionalArgument(0)
        public List<LookupRule> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(final List<LookupRule> coordinates) {
            this.coordinates = coordinates;
        }

        @Prop(P.value)
        @Doc("The function to use when working out the value of this entry.")
        @BindPositionalArgument(1)
        public XNumber getValue() {
            return value;
        }

        public void setValue(final XNumber value) {
            this.value = value;
        }
    }

    private List<XFunction> keys = new ArrayList<>();
    private List<XLookupEntry> entries = new ArrayList<>();
    private XNumber defaultFunction = new XNumberConstant();
    private boolean warnOnFallback = true;

    @Prop(P.keys)
    @BindNamedArgument("keys")
    @Doc({
        "These functions will be used to generate the coordinates for the value that will be selected.",
        "Each entry has coordinates as its first member; if these functions produce values matching an",
        "entry in the same order as the entry specifies, that entry will be used."})
    public List<XFunction> getKeys() {
        return keys;
    }

    @Prop(P.entries)
    @BindRemainingArguments
    @Doc({"Each entry pairs some coordinates with a value; the first entry whose coordinates match the values produced",
        "by the key functions will be used to determine the value of the lookup for a house."
    })
    public List<XLookupEntry> getEntries() {
        return entries;
    }

    @Prop(P.defaultValue)
    @BindNamedArgument("default")
    @Doc("If no entry matches the coordinates for a house, this function will be used as a fallback.")
    public XNumber getDefaultFunction() {
        return defaultFunction;
    }

    @Prop(P.warnOnFallback)
    @BindNamedArgument("log-warnings")
    @Doc("Whether or not to produce warning log messages if the table is not exhaustive, and has to fallback to the default value function.")
    public boolean isWarnOnFallback() {
        return warnOnFallback;
    }

    public void setWarnOnFallback(final boolean warnOnFallback) {
        this.warnOnFallback = warnOnFallback;
    }

    public void setKeys(final List<XFunction> keys) {
        this.keys = keys;
    }

    public void setEntries(final List<XLookupEntry> entries) {
        this.entries = entries;
    }

    public void setDefaultFunction(final XNumber defaultFunction) {
        this.defaultFunction = defaultFunction;
    }

    @Override
    public List<IError> validate(final Deque<XElement> context) {
        final List<Function<LookupRule, String>> checks = new ArrayList<>(getKeys().size());
        final List<IError> errors = new ArrayList<>(getEntries().size());

        for (final XFunction key : getKeys()) {
            checks.add(makeCheckFunction(key));
        }

        for (final XLookupEntry e : getEntries()) {
            if (e.getCoordinates().size() != checks.size()) {
                errors.add(BasicError.at(
                        e.getLocation(),
                        String.format("lookup ought to have %d key values to match on, but there are %d",
                                checks.size(),
                                e.getCoordinates().size())
                ));
            } else {
                for (int i = 0; i < checks.size(); i++) {
                    final String err = checks.get(i).apply(e.getCoordinates().get(i));
                    if (err != null && !err.isEmpty()) {
                        errors.add(BasicError.at(e.getLocation(), err));
                    }
                }
            }
        }

        return errors;
    }

    /**
     * @return a function which returns a nonempty error string if the input
     * lookuprule is not a sensible value to compare with an output of f.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Function<LookupRule, String> makeCheckFunction(final XFunction f) {
        if (f.getClass().isAnnotationPresent(ReturnsEnum.class)
                || f instanceof XCategoryFunction
                || f instanceof XBoolean) {
            return new CheckEnumFunction(f, getEnumStrings(f));
        } else if (f instanceof XNumber) {
            return new CheckNumFunction(f);
        }

        return (Function) Functions.constant("");
    }

    private static Set<String> getEnumStrings(final XFunction key) {
        final Set<String> legalStrings = new HashSet<>();
        if (key.getClass().isAnnotationPresent(ReturnsEnum.class)) {
            final Class<?> c = key.getClass().getAnnotation(ReturnsEnum.class).value();
            for (final Object o : c.getEnumConstants()) {
                legalStrings.add(String.valueOf(o).toLowerCase());
            }
        } else if (key instanceof XBoolean) {
            legalStrings.add("true");
            legalStrings.add("false");
        }
        return legalStrings;
    }

    static abstract class CheckFunction implements Function<LookupRule, String> {

        private final String keyName;

        protected CheckFunction(final XFunction key) {
            keyName = key.getIdentifier().getName();
        }

        @Override
        public String apply(final LookupRule rule) {
            switch (rule.getType()) {
                case String:
                    return allowString(rule.getStringValue());
                case Number:
                case Range:
                    return allowNumber();
                case Wildcard:
                default:
                    return "";
            }
        }

        protected String allowString(String s) {
            return String.format("no value of %s can be matched by %s", keyName, s);
        }

        protected String allowNumber() {
            return String.format("%s is not a numeric function", keyName);
        }
    }

    static class CheckEnumFunction extends CheckFunction {

        private final Set<String> legalStrings = new HashSet<>();

        public CheckEnumFunction(final XFunction key, final Set<String> strings) {
            super(key);
            legalStrings.addAll(strings);

        }

        @Override
        protected String allowString(String s) {
            if (legalStrings.isEmpty() || legalStrings.contains(s.toLowerCase())) {
                return "";
            }
            return super.allowString(s);
        }
    }

    static class CheckNumFunction extends CheckFunction {

        public CheckNumFunction(final XFunction key) {
            super(key);
        }

        @Override
        protected String allowNumber() {
            return "";
        }
    }
}
