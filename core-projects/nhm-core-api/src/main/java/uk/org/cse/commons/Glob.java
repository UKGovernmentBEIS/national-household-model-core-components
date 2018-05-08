package uk.org.cse.commons;

import java.util.Collection;
import java.util.regex.Pattern;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class Glob implements Predicate<String> {

    private final String globString;
    private final String literal;
    private final Pattern pattern;
    private final boolean negated;
    private final boolean wild;

    private static final Interner<Glob> interner = Interners.newWeakInterner();

    private Glob(final String glob, final String literal, final Pattern pattern, final boolean negated, final boolean wild) {
        this.globString = (negated ? "!" : "") + glob;
        this.literal = literal;
        this.pattern = pattern;
        this.negated = negated;
        this.wild = wild;
    }

    public static Glob of(String glob) {
        glob = glob.trim();
        boolean negate = false;
        boolean wild = false;

        if (glob.startsWith("!")) {
            negate = true;
            glob = glob.substring(1);
        } else {
            negate = false;
        }

        int strLen = glob.length();
        StringBuilder sb = new StringBuilder(strLen);
        StringBuilder literalValue = new StringBuilder(strLen);

        boolean escaping = false;
        int inCurlies = 0;
        for (char currentChar : glob.toCharArray()) {
            switch (currentChar) {
                case '*':
                    if (escaping) {
                        sb.append("\\*");
                        literalValue.append(currentChar);
                    } else {
                        sb.append(".*");
                        wild = true;
                    }
                    escaping = false;

                    break;
                case '?':
                    if (escaping) {
                        sb.append("\\?");
                        literalValue.append(currentChar);
                    } else {
                        sb.append('.');
                        wild = true;
                    }
                    escaping = false;
                    break;
                case '.':
                case '(':
                case ')':
                case '+':
                case '|':
                case '^':
                case '$':
                case '@':
                case '%':
                case '{':
                case '}':
                    sb.append('\\');
                    sb.append(currentChar);
                    literalValue.append(currentChar);
                    escaping = false;
                    break;
                case '\\':
                    if (escaping) {
                        sb.append("\\\\");
                        escaping = false;
                        literalValue.append(currentChar);
                    } else {
                        escaping = true;
                    }
                    break;
                case '<':
                    if (escaping) {
                        sb.append("<");
                        literalValue.append(currentChar);
                    } else {
                        sb.append('(');
                        wild = true;
                        inCurlies++;
                    }
                    escaping = false;
                    break;
                case '>':
                    if (inCurlies > 0 && !escaping) {
                        sb.append(')');
                        inCurlies--;
                    } else if (escaping) {
                        sb.append(">");
                        literalValue.append(currentChar);
                    } else {
                        sb.append(">");
                    }
                    escaping = false;
                    break;
                case ',':
                    if (inCurlies > 0 && !escaping) {
                        sb.append('|');
                        wild = true;
                    } else if (escaping) {
                        sb.append("\\,");
                        literalValue.append(currentChar);
                    } else {
                        sb.append(",");
                        literalValue.append(currentChar);
                    }
                    break;
                default:
                    escaping = false;
                    sb.append(currentChar);
                    literalValue.append(currentChar);
            }
        }

        try {
            final Pattern p = Pattern.compile(sb.toString(),
                    Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

            return interner.intern(new Glob(glob, literalValue.toString(), p, negate, wild));
        } catch (Throwable e) {
            throw new IllegalArgumentException(String.format("%s is not a legal pattern", glob));
        }
    }

    public boolean removes(final String input) {
        return negated && matches(input, false);
    }

    public boolean matches(final String input) {
        return matches(input, true);
    }

    public boolean matches(final String input, final boolean doNegate) {
        final boolean baseMatch = pattern.matcher(input).matches();
        if (doNegate && negated) {
            return !baseMatch;
        }
        return baseMatch;
    }

    public Optional<String> getLiteralValue() {
        if (isWild()) {
            return Optional.absent();
        }
        return Optional.of(literal);
    }

    public boolean isWild() {
        return wild;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public String toString() {
        return this.globString;
    }

    @Override
    public boolean apply(final String input) {
        return matches(input);
    }

    public static boolean requireAndForbid(final Collection<Glob> globs, final Collection<String> input) {
        glob_loop:
        for (final Glob glob : globs) {
            if (glob.isNegated()) {
                // we must check that every bad string is disallowed
                for (final String s : input) {
                    if (glob.matches(s, false)) {
                        return false;
                    }
                }
            } else {
                for (final String s : input) {
                    if (glob.matches(s)) {
                        continue glob_loop;
                    }
                }
                return false;
            }

        }

        return true;
    }

    /**
     * Given a collection of globs, produce a predicate which will only pass a
     * set of strings if:
     *
     * - every positive glob has a matching string in the set - no negative glob
     * has a matching string in the set
     *
     * @param globs
     * @return
     */
    public static Predicate<Collection<String>> requireAndForbid(final Collection<Glob> globs) {
        if (globs.isEmpty()) {
            return Predicates.alwaysTrue();
        }

        boolean hasRequirement = false;
        for (final Glob g : globs) {
            if (!g.isNegated()) {
                hasRequirement = true;
                break;
            }
        }

        if (hasRequirement) {
            return new Predicate<Collection<String>>() {
                @Override
                public boolean apply(Collection<String> input) {
                    if (input.isEmpty()) {
                        return false;
                    }
                    return requireAndForbid(globs, input);
                }
            };
        } else {
            return new Predicate<Collection<String>>() {
                @Override
                public boolean apply(Collection<String> input) {
                    return requireAndForbid(globs, input);
                }
            };
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof Glob) {
            return ((Glob) other).globString.equals(globString);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return globString.hashCode();
    }
}
