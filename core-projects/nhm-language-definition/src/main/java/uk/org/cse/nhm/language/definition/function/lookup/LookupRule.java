package uk.org.cse.nhm.language.definition.function.lookup;

import uk.org.cse.nhm.language.definition.Doc;

@Doc(
        {"Lookup rules are elements which can occur in a lookup function.",
            "There are several possible kinds of lookup rule allowed:",
            "<itemizedlist>",
            "<listitem>",
            "Constants - any text or number defines a constant. For the lookup entry to",
            "match, the relevant key function must produce exactly that text or number",
            "</listitem>",
            "<listitem>",
            "Wildcards - the special text * (star) indicates that any value for the relevant key",
            "suffices in this location",
            "</listitem>",
            "<listitem>",
            "Ranges - ranges like &gt;50, &lt;=10, or 8..10 indicate that number values",
            "must lie in a certain range. Closed ranges (like 8..10) include their",
            "endpoints.",
            "</listitem>",
            "</itemizedlist>"
        }
)
public class LookupRule {

    private final Type type;
    private final String stringValue;
    private final double lowerBound, upperBound;
    private final boolean includesLower, includesUpper;
    private final Number exact;

    LookupRule(final Type type, final String stringValue, final double lowerBound,
            final double upperBound, final boolean includesLower, final boolean includesUpper) {
        super();
        this.type = type;
        this.stringValue = stringValue;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.includesLower = includesLower;
        this.includesUpper = includesUpper;
        this.exact = null;
    }

    public LookupRule(final String s, final Number exact2) {
        this.type = Type.Number;
        this.stringValue = s;
        this.lowerBound = this.upperBound = exact2.doubleValue();
        this.includesLower = true;
        this.includesUpper = true;
        this.exact = exact2;
    }

    public Number getExact() {
        expectType(Type.Number);
        return exact;
    }

    public static LookupRule of(final String s_) {
        final String s = s_.trim();

        if (s.equals("*")) {
            return new LookupRule(Type.Wildcard, s, 0, 0, false, false);
        } else if (s.length() > 1) {
            final char c0 = s.charAt(0);
            final char c1 = s.charAt(1);
            try {
                if (c0 == '<') {
                    if (c1 == '=') {
                        final double d = Double.parseDouble(s.substring(2));
                        return new LookupRule(Type.Range, s, Double.NEGATIVE_INFINITY, d, true, true);
                    } else {
                        final double d = Double.parseDouble(s.substring(1));
                        return new LookupRule(Type.Range, s, Double.NEGATIVE_INFINITY, d, true, false);
                    }
                } else if (c0 == '>') {
                    if (c1 == '=') {
                        final double d = Double.parseDouble(s.substring(2));
                        return new LookupRule(Type.Range, s, d, Double.POSITIVE_INFINITY, true, true);
                    } else {
                        final double d = Double.parseDouble(s.substring(1));
                        return new LookupRule(Type.Range, s, d, Double.POSITIVE_INFINITY, false, true);
                    }
                }
            } catch (final NumberFormatException nfe) {
            }
            if (s.contains("..")) {
                final String[] parts = s.split("\\.\\.");
                if (parts.length == 2) {
                    try {
                        final double lower = Double.parseDouble(parts[0]);
                        final double upper = Double.parseDouble(parts[1]);
                        return new LookupRule(Type.Range, s, lower, upper, true, true);
                    } catch (final NumberFormatException nfe) {
                    }
                }
            }
        }

        try {
            return new LookupRule(s, Integer.parseInt(s));
        } catch (final NumberFormatException nfe) {
        }

        try {
            final Double exact = Double.parseDouble(s);
            return new LookupRule(s, exact);
        } catch (final NumberFormatException nfe2) {
        }

        return new LookupRule(Type.String, s, 0, 0, false, false);
    }

    public enum Type {
        String,
        Number,
        Wildcard,
        Range
    }

    private void expectType(final Type t) {
        if (type != t) {
            throw new UnsupportedOperationException("This lookup rule is not of type " + t + ", but of type " + type);
        }
    }

    public Type getType() {
        return type;
    }

    public double getLowerBound() {
        expectType(Type.Range);
        return lowerBound;
    }

    public double getUpperBound() {
        expectType(Type.Range);
        return upperBound;
    }

    public boolean isLowerBoundIncluded() {
        expectType(Type.Range);
        return includesLower;
    }

    public boolean isUpperBoundIncluded() {
        expectType(Type.Range);
        return includesUpper;
    }

    public String getStringValue() {
        expectType(Type.String);
        return stringValue;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public String toString() {
        switch (type) {
            case Number:
                return String.format("%f", lowerBound);
            case Range:
                return String.format("%f..%f", lowerBound, upperBound);
            case Wildcard:
                return "*";
        }
        return stringValue;
    }
}
