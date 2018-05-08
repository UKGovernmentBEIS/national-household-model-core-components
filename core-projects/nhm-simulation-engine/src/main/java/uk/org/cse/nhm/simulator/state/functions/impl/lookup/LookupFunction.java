package uk.org.cse.nhm.simulator.state.functions.impl.lookup;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
 * This is a function which implements a quite-fast n-dimensional map lookup,
 * allowing for certain kinds of range query on number-valued parts of the key.
 *
 * To see how it works, head down to {@link #compute(IComponentsScope)}, as the
 * efficiency stuff in the implementation is slightly hard reading from the top.
 *
 * @author hinton
 *
 */
public class LookupFunction extends AbstractNamed implements IComponentsFunction<Number> {

    /**
     * Used for logging warnings if we hit the fallthrough
     */
    private final ILogEntryHandler log;
    /**
     * The functions which make up the compound key. When computing a value, we
     * evaluate each of these functions to produce a compound key; then we find
     * the first entry which matches the key.
     */
    private final List<IComponentsFunction<?>> keys;
    /**
     * The function to use when nothing matches one of the key tuples
     */
    private final IComponentsFunction<? extends Number> fallback;

    /**
     * These are the functions which each possible entry uses; they are indexed
     * by the entries, so a table with 100 entries will have 100 functions here.
     */
    private final IComponentsFunction<? extends Number>[] valueFunctions;

    /**
     * This is an efficiency hack; if populated with constant components
     * functions (i.e. simple numbers) then we will lookup into this table
     * rather than value functions. We mark the difference by putting Double.NaN
     * in here if the value function is non-constant.
     */
    private final double[] constantValues;

    /**
     * This is a bit-matrix which contains all the wildcards in the key table.
     * It is indexed by key function (the array index) and then by value
     * function (the index into the bitset), so if stars[i].get(j) is true then
     * we don't care what value key function i produces for the purposes of
     * selecting value function j.
     */
    private final BitSet[] stars;
    /**
     * This is a bunch of range sets, indexed by key table; each range set can
     * tell you whether a particular value function is acceptable for some value
     * of a given key;
     */
    private final RangeToEntry[] ranges;

    private final Class<?>[] convertKeysTo;

    /**
     * This is an array which takes us from key to map from value to entries
     * which accept that value for that part of the key.
     *
     * For example, if exactMatches[i].get("Widget").get(j) is true then value
     * function j is acceptable when the key has "Widget" in element i.
     */
    private final Map<Object, BitSet>[] exactMatches;

    private final boolean warnOnDefault;

    private final Set<IDimension<?>> dependencies;
    private Set<DateTime> changeDates;

    private Class<?> getConvertedType(final Class<?> inputType) {
        if (inputType.isEnum()) {
            return inputType;
        } else if (Double.class.isAssignableFrom(inputType)) {
            return Double.class;
        } else if (Integer.class.isAssignableFrom(inputType)) {
            return Integer.class;
        } else if (Number.class.isAssignableFrom(inputType)) {
            return Double.class;
        } else if (Boolean.class.isAssignableFrom(inputType)) {
            return Boolean.class;
        } else {
            return String.class;
        }
    }

    private Object convertKeyValue(final Object compute, final int i) {
        final Class<?> desiredType = convertKeysTo[i];
        if (desiredType.isInstance(compute)) {
            return compute;
        }

        if (desiredType == String.class) {
            return String.valueOf(compute);
        }

        if (compute instanceof Number) {
            if (desiredType == Double.class) {
                return ((Number) compute).doubleValue();
            } else if (desiredType == Integer.class) {
                return ((Number) compute).intValue();
            }
        }

        if (desiredType == Boolean.class) {
            return Boolean.valueOf(String.valueOf(compute));
        }

        return compute;
    }

    private Object convertInputObjectToEqualsType(final Object input, final Class<?> type) {
        try {
            if (type.isEnum()) {
                return tryFindingEnumValue(input, type);
            } else if (Boolean.class.isAssignableFrom(type)) {
                return Boolean.valueOf(String.valueOf(input));
            } else if (Double.class.isAssignableFrom(type)) {
                if (input instanceof Double) {
                    return input;
                } else {
                    return Double.valueOf(String.valueOf(input));
                }
            } else if (Integer.class.isAssignableFrom(type)) {
                if (input instanceof Integer) {
                    return input;
                } else {
                    return Integer.valueOf(String.valueOf(input));
                }
            } else if (Number.class.isAssignableFrom(type)) {
                if (input instanceof Double) {
                    return input;
                } else {
                    return Double.valueOf(String.valueOf(input));
                }
            }
        } catch (final Exception e) {
            // TODO produce warning about bad table row.
            System.err.println("Bad table row " + input);
            e.printStackTrace();
        }

        if (input instanceof Number) {
            return ((Number) input).doubleValue();
        }

        return String.valueOf(input);
    }

    @SuppressWarnings("unchecked")
    private Object tryFindingEnumValue(final Object input, @SuppressWarnings("rawtypes") final Class type) {
        try {
            return Enum.valueOf(type, String.valueOf(input));
        } catch (final IllegalArgumentException iae) {
            for (final Object o : type.getEnumConstants()) {
                if (o.toString().equalsIgnoreCase(String.valueOf(input))) {
                    return o;
                }
            }
            throw iae;
        }
    }

    @AssistedInject
    @SuppressWarnings({"unchecked", "rawtypes"})
    public LookupFunction(
            final ILogEntryHandler log,
            @Assisted final boolean warnOnDefault,
            @Assisted final List<IComponentsFunction<?>> keys,
            @Assisted final List<LookupEntry> entries,
            @Assisted final IComponentsFunction<? extends Number> fallback) {
        this.log = log;
        this.warnOnDefault = warnOnDefault;
        this.keys = ImmutableList.copyOf(keys);
        valueFunctions = new IComponentsFunction[entries.size()];
        constantValues = new double[entries.size()];

        this.fallback = fallback;

        stars = new BitSet[keys.size()];
        ranges = new RangeToEntry[keys.size()];
        exactMatches = new Map[keys.size()];

        convertKeysTo = new Class[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            final IComponentsFunction<?> key = keys.get(i);

            final Class<?> rawTypeOfKey;
            if (key instanceof ConstantComponentsFunction) {
                rawTypeOfKey = ((ConstantComponentsFunction) key).getValue().getClass();
            } else {
                @SuppressWarnings("rawtypes")
                final TypeToken<? extends IComponentsFunction> tt = TypeToken.of(key.getClass());
                final TypeToken<?> resolvedType = tt.resolveType(IComponentsFunction.class.getTypeParameters()[0]);
                rawTypeOfKey = resolvedType.getRawType();
            }

            convertKeysTo[i] = getConvertedType(rawTypeOfKey);

            final ImmutableList.Builder<LookupRule> rangeRules = ImmutableList.builder();
            final ImmutableList.Builder<Integer> rangeRuleEntries = ImmutableList.builder();
            stars[i] = new BitSet();
            exactMatches[i] = new HashMap<Object, BitSet>();
            int j = 0;
            for (final LookupEntry le : entries) {
                final LookupRule rule = le.getRules().get(i);
                switch (rule.getType()) {
                    case String:
                        addMatch(exactMatches[i], convertInputObjectToEqualsType(rule.getStringValue(), rawTypeOfKey), j);
                        break;
                    case Number:
                        addMatch(exactMatches[i], convertInputObjectToEqualsType(rule.getExact(), rawTypeOfKey), j);
                        break;
                    case Range:
                        rangeRules.add(rule);
                        rangeRuleEntries.add(j);
                        break;
                    case Wildcard:
                        stars[i].set(j);
                        break;
                }
                j++;
            }

            ranges[i] = new RangeToEntry(rangeRules.build(), rangeRuleEntries.build());
        }

        final ImmutableSet.Builder<IDimension<?>> db = ImmutableSet.builder();
        int i = 0;
        for (final LookupEntry entry : entries) {
            valueFunctions[i] = entry.getValue();
            if (valueFunctions[i] instanceof ConstantComponentsFunction) {
                constantValues[i] = ((ConstantComponentsFunction<Number>) valueFunctions[i]).getValue().doubleValue();
            } else {
                constantValues[i] = Double.NaN;
            }
            db.addAll(entry.getValue().getDependencies());
            i++;
        }

        for (final IComponentsFunction<?> lf : keys) {
            db.addAll(lf.getDependencies());
        }

        db.addAll(fallback.getDependencies());
        dependencies = db.build();
    }

    private static void addMatch(final Map<Object, BitSet> map, final Object value, final int index) {
        BitSet toModify = map.get(value);
        if (toModify == null) {
            toModify = new BitSet();
            map.put(value, toModify);
        }
        toModify.set(index);
    }

    /**
     * Compute the best value in the lookup on the given input
     *
     * @param scope
     * @return
     */
    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        // first we compute the compound key; this is a 
        // list where value 0 is the result of key-function 0
        // for scope, so this might be something like
        // [London, 50, true] if the key is
        // [region, insulation thickness, has central heating] or something
        final List<?> key = computeKeys(scope, lets);

        // this is the set of value functions for which key matches; these
        // are rows in the big input table. our definition is that we take the first
        // matching row. Anyway, to start with we say that all rows are matching.
        final BitSet matches = new BitSet(valueFunctions.length);
        matches.set(0, valueFunctions.length);

        // now we narrow down our matches;
        // for each element of the key, we want to retain only those values
        // for which the key value is acceptable
        // This is like ANDing together a bit mask which we compute for each key
        for (int i = 0; i < key.size(); i++) {
            matches.and(getEntriesMatchingValueAtPosition(key.get(i), i));
        }

        // finally we find the first remaining match if there is one.
        final int firstMatch = matches.nextSetBit(0);

        if (firstMatch == -1) {
            if (warnOnDefault) {
                log.acceptLogEntry(new WarningLogEntry(
                        "Lookup had to use default function.",
                        ImmutableMap.of(
                                "lookup", this.getIdentifier().getName(),
                                "unmatched", key.toString()
                        )));
            }
            return fallback.compute(scope, lets).doubleValue();
        } else {
            final double constant = constantValues[firstMatch];
            if (Double.isNaN(constant)) {
                return valueFunctions[firstMatch].compute(scope, lets).doubleValue();
            } else {
                return constant;
            }
        }
    }

    @Override
    public Set<DateTime> getChangeDates() {
        if (changeDates == null) {
            final ImmutableSet.Builder<DateTime> result = ImmutableSet.builder();

            for (final IComponentsFunction<?> v : keys) {
                result.addAll(v.getChangeDates());
            }

            for (final IComponentsFunction<? extends Number> v : valueFunctions) {
                result.addAll(v.getChangeDates());
            }

            result.addAll(fallback.getChangeDates());

            this.changeDates = result.build();
        }
        return changeDates;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return dependencies;
    }

    private BitSet getEntriesExactlyMatching(final Object object, final int i) {
        return exactMatches[i].get(object);
    }

    private BitSet getEntriesStarMatching(final int i) {
        return stars[i];
    }

    private BitSet getEntriesNumberMatching(final Number o, final int i) {
        return ranges[i].get(o.doubleValue());
    }

    /**
     * This returns a bitmask for the entries in the table which can have the
     * given object in the given key position.
     *
     * This is done by ORing wildcards, exact matches, and range matches.
     *
     * @param object
     * @param i
     * @return
     */
    private BitSet getEntriesMatchingValueAtPosition(final Object object, final int i) {
        final BitSet result = new BitSet();

        // first see whether there are any value functions which specify
        // that object is required at position i; they are clearly OK with this.
        final BitSet exact = getEntriesExactlyMatching(object, i);

        if (exact != null) {
            result.or(exact);
        }

        // next just or in the things which don't care what they see at position i
        result.or(getEntriesStarMatching(i));

        // finally if the value is numeric, check the various range functions.
        if (object instanceof Number) {
            result.or(getEntriesNumberMatching((Number) object, i));
        }

        // the things we can use are just all these things ored together so we are done
        return result;
    }

    private List<?> computeKeys(final IComponentsScope scope, final ILets lets) {
        // map f -> fx keys

        final List<Object> result = new ArrayList<>(keys.size());
        int i = 0;
        for (final IComponentsFunction<?> f : keys) {
            final Object compute = f.compute(scope, lets);

            result.add(convertKeyValue(compute, i));

            i++;
        }
        return result;

    }
}
