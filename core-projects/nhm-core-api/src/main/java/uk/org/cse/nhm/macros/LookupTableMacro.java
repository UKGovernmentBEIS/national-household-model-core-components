package uk.org.cse.nhm.macros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.Seq.Builder;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.SimpleMacro;

public class LookupTableMacro extends SimpleMacro {

    public static final String NAME_ARG = "name";
    public static final String COLUMN_KEY_ARG = "column-key";
    public static final String DEFAULT_ARG = "default";
    public static final String LOG_ARG = "log-warnings";
    public static final String ROW_KEYS_ARG = "row-keys";
    public static final String LOOKUP_TABLE_NAME = "lookup-table";

    private static final String INTERPOLATE = "interpolate";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private final String name;

    public LookupTableMacro(final String string) {
        this.name = string;
    }

    public LookupTableMacro() {
        this(LOOKUP_TABLE_NAME);
    }

    protected String generateIdentifier() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getRequiredArgumentNames() {
        return ImmutableSet.of();
    }

    @Override
    public Set<String> getAllowedArgumentNames() {
        return ImmutableSet.of(DEFAULT_ARG, ROW_KEYS_ARG, COLUMN_KEY_ARG, NAME_ARG, INTERPOLATE, LOG_ARG);
    }

    @Override
    public int getMaximumArgumentCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMinimumArgumentCount() {
        return 1;
    }

    @Override
    protected ISExpression doTransform(final Invocation validated, final IMacroExpander expander, final IErrorHandler errors) {
        return new Lookup(validated, errors, generateIdentifier()).build();
    }

    static class Lookup {

        private final Invocation expanded;
        private final IErrorHandler errors;
        private final boolean interpolate;
        private final String interpolationKey;
        private final Location start;
        private final Location end;

        Lookup(final Invocation expanded, final IErrorHandler errors, final String identifier) {
            this.expanded = expanded;
            this.errors = errors;
            this.interpolate = isInterpolate(expanded.arguments, errors);
            if (interpolate) {
                interpolationKey = identifier;
            } else {
                interpolationKey = null;
            }
            this.start = expanded.node.getLocation();
            this.end = expanded.node.getEndLocation();
        }

        public ISExpression build() {
            final Seq.Builder output = Seq.builder(start, Delim.Paren);

            if (interpolate) {
                if (!expanded.arguments.containsKey(ROW_KEYS_ARG)) {
                    errors.handle(BasicError.at(start, "lookup-table has interpolate set to true, so it must have exactly one row key."));
                    return output.build(end);
                }
                final Node rowKeys = expanded.arguments.get(ROW_KEYS_ARG);
                final Node firstRowKey;

                final Node getInterpolationKey = Atom.create("#" + interpolationKey);

                if (rowKeys instanceof Seq) {
                    final Seq rowKeysSeq = (Seq) rowKeys;
                    if (rowKeysSeq.getDelimeter() == Delim.Bracket) {
                        if (rowKeysSeq.size() != 1) {
                            errors.handle(BasicError.at(rowKeysSeq, "lookup-table has interpolate set to true, so it must have exactly one row key."));
                            return output.build(end);
                        } else {
                            firstRowKey = rowKeysSeq.get(0);
                        }
                    } else {
                        firstRowKey = rowKeysSeq;
                    }
                } else {
                    errors.handle(BasicError.at(rowKeys, "lookup-table has interpolate set to true, so it must have exactly one row key."));
                    return output.build(end);
                }

                output.add("do");

                final Builder interpolationVar = Seq.builder(start, Delim.Paren);
                interpolationVar.add("set");
                interpolationVar.add(Seq.builder(start, Delim.Paren).add("def").add(interpolationKey).add("on", "Event").build(start));
                interpolationVar.add(firstRowKey);

                output.add(interpolationVar.build(start));

                final Builder lookupAction = Seq.builder(start, Delim.Paren);

                outputLookup(lookupAction, getInterpolationKey);

                output.add("return", lookupAction.build(end));

            } else {
                if (expanded.arguments.containsKey(ROW_KEYS_ARG)) {
                    final Node rowKeys = expanded.arguments.get(ROW_KEYS_ARG);
                    outputLookup(output, rowKeys);
                } else {
                    outputLookup(output, null);
                }
            }

            return output.build(end);
        }

        private boolean isInterpolate(final Map<String, Node> arguments, final IErrorHandler errors) {
            if (arguments.containsKey(INTERPOLATE)) {
                final Node node = arguments.get(INTERPOLATE);
                if (node instanceof Atom) {
                    final String value = ((Atom) node).getValue();
                    switch (value) {
                        case TRUE:
                            return true;
                        case FALSE:
                            return false;
                        default:
                            errors.handle(BasicError.at(node, String.format("lookup-table has an unknown value for interpolate: should be %s or %s.", TRUE, FALSE)));
                            return false;
                    }

                } else {
                    errors.handle(BasicError.at(node, String.format("lookup-table has an unknown value for interpolate: should be %s or %s.", TRUE, FALSE)));
                    return false;
                }
            } else {
                return false;
            }
        }

        private void passArguments(final Invocation expanded, final Builder output, final ImmutableSet<String> of) {
            for (final String s : of) {
                if (expanded.arguments.containsKey(s)) {
                    output.add(s, expanded.arguments.get(s));
                }
            }
        }

        public void outputLookup(final Seq.Builder output, final Node rowKeys) {
            output.add("lookup");
            passArguments(expanded, output, ImmutableSet.of(DEFAULT_ARG, NAME_ARG, LOG_ARG));

            final Node columnKey = expanded.arguments.get(COLUMN_KEY_ARG);

            final Seq.Builder joinedKeys = Seq.builder(expanded.node.getLocation(), Delim.Bracket);

            final int rowKeyCount;
            if (rowKeys instanceof Seq && ((Seq) rowKeys).getDelimeter() == Delim.Bracket) {
                joinedKeys.addAll(((Seq) rowKeys).getNodes());
                rowKeyCount = ((Seq) rowKeys).size();
            } else if (rowKeys != null) {
                joinedKeys.add(rowKeys);
                rowKeyCount = 1;
            } else {
                // there are no row keys
                rowKeyCount = 0;
            }

            if (columnKey != null) {
                joinedKeys.add(columnKey);
            }

            output.add("keys", joinedKeys.build(expanded.node.getLocation()));

            final ArrayList<Seq> seqs = new ArrayList<>();
            final ArrayList<List<Node>> rows = new ArrayList<>();

            for (final Node n : expanded.remainder) {
                if (n instanceof Comment) {
                    continue;
                }
                if (n instanceof Seq) {
                    rows.add(((Seq) n).exceptComments());
                    seqs.add((Seq) n);
                } else {
                    errors.handle(BasicError.at(n, "lookup-table should contain a sequence of lists, one for each row."));
                }
            }

            if (rows.size() < 1) {
                errors.handle(BasicError.at(expanded.node, "lookup-table should have at least a header row"));
            } else {
                final int headerSize = rows.get(0).size();

                if (headerSize < rowKeyCount + 1) {
                    errors.handle(BasicError.at(expanded.node,
                            String.format("the header of this lookup-table should have at least %d entries (one for each row key, and a value for the column key)",
                                    rowKeyCount + 1)));
                } else if (columnKey == null && headerSize > (rowKeyCount + 1)) {
                    errors.handle(BasicError.at(expanded.node,
                            String.format("because this lookup-table has no column-key, it should only have one more column than the number of row keys.")));
                } else {
                    if (interpolate) {
                        generateInterpolatedLookup(output, columnKey, rowKeyCount, seqs, rows, headerSize);
                    } else {
                        generateNormalLookup(output, columnKey, rowKeyCount, seqs, rows, headerSize);
                    }
                }
            }
        }

        private void generateInterpolatedLookup(final Builder output, final Node columnKey,
                final int rowKeyCount, final ArrayList<Seq> seqs,
                final ArrayList<List<Node>> rows, final int headerSize) {
            int index = 0;

            boolean happy = true;

            for (final List<Node> thisRow : rows) {
                if (thisRow.size() != headerSize) {
                    errors.handle(BasicError.at(seqs.get(index),
                            String.format("This row has %d elements, but the header row has %d. Every row must have a value for every column, but no more.",
                                    thisRow.size(), headerSize)));
                    happy = false;
                } else if (index > 0) {
                    if (thisRow.isEmpty()) {
                        errors.handle(BasicError.at(seqs.get(index),
                                "Unexpected empty row!"));
                        happy = false;
                    } else {
                        final Node n = thisRow.get(0);
                        if (n instanceof Atom) {
                            final String s = ((Atom) n).getValue();
                            try {
                                Double.parseDouble(s);
                            } catch (final NumberFormatException nfe) {
                                errors.handle(BasicError.at(n, "In an interpolated lookup-table, the first entry in each row must be a number"));
                                happy = false;
                            }
                        } else {
                            errors.handle(BasicError.at(n, "In an interpolated lookup-table, the first entry in each row must be a number"));
                            happy = false;
                        }
                    }
                }
                index++;
            }

            if (happy) {
                final List<Node> header = rows.get(0);
                final List<List<Node>> remainder = rows.subList(1, rows.size());

                Collections.sort(remainder, new Comparator<List<Node>>() {
                    @Override
                    public int compare(final List<Node> o1, final List<Node> o2) {
                        final Atom n1 = (Atom) o1.get(0);
                        final Atom n2 = (Atom) o2.get(0);

                        final double d1 = Double.parseDouble(n1.getValue());
                        final double d2 = Double.parseDouble(n2.getValue());

                        return Double.compare(d1, d2);
                    }
                });

                final List<List<Node>> sortedAndMangled = new ArrayList<>();

                sortedAndMangled.add(header);
                for (int i = 0; i < remainder.size() - 1; i++) {
                    final List<Node> thisRow = remainder.get(i);
                    final List<Node> nextRow = remainder.get(i + 1);
                    final List<Node> syntheticRow = new ArrayList<>();

                    final Atom r1 = (Atom) thisRow.get(0);
                    final Atom r2 = (Atom) nextRow.get(0);

                    syntheticRow.add(Atom.create(r1.getValue() + ".." + r2.getValue(), r1.getLocation()));

                    for (int col = 1; col < thisRow.size(); col++) {
                        final Node here = thisRow.get(col);
                        final Node below = nextRow.get(col);

                        final Seq.Builder interpolate = Seq.builder(here.getLocation(), Delim.Paren);

                        interpolate.add("interpolate");
                        interpolate.add("#" + interpolationKey);

                        {
                            final Seq.Builder xs = Seq.builder(here.getLocation(), Delim.Bracket);
                            xs.add(r1.getValue());
                            xs.add(r2.getValue());
                            interpolate.add(xs.build(here.getLocation()));
                        }

                        {
                            final Seq.Builder ys = Seq.builder(here.getLocation(), Delim.Bracket);
                            ys.add(here);
                            ys.add(below);
                            interpolate.add(ys.build(here.getLocation()));
                        }

                        syntheticRow.add(interpolate.build(here.getLocation()));
                    }

                    sortedAndMangled.add(syntheticRow);
                }

                generateNormalLookup(output, columnKey, rowKeyCount, seqs, sortedAndMangled, headerSize);
            }
        }

        private void generateNormalLookup(final Seq.Builder output, final Node columnKey,
                final int rowKeyCount, final ArrayList<Seq> seqs,
                final List<List<Node>> rows, final int headerSize) {
            int index = 0;
            final List<Node> header = rows.get(0);
            for (final List<Node> thisRow : rows) {
                if (thisRow.size() != headerSize) {
                    errors.handle(BasicError.at(seqs.get(index),
                            String.format("This row has %d elements, but the header row has %d. Every row must have a value for every column, but no more.",
                                    thisRow.size(), headerSize)));
                } else if (index > 0) {
                    for (int j = rowKeyCount; j < thisRow.size(); j++) {
                        final Location location = thisRow.get(j).getLocation();
                        final Seq.Builder entry = Seq.builder(location, Delim.Paren);

                        entry.add("entry");

                        final Seq.Builder key = Seq.builder(location, Delim.Bracket);

                        for (int k = 0; k < rowKeyCount; k++) {
                            key.add(thisRow.get(k));
                        }

                        if (columnKey != null) {
                            key.add(header.get(j));
                        }

                        entry.add(key.build(location));

                        entry.add(thisRow.get(j));

                        output.add(entry.build(location));
                    }
                }

                index++;
            }
        }
    }

    @Override
    public MacroModel getModel() {
        return MacroModel.builder()
                .desc("A convenience for generating usages of the lookup function.")
                .desc("This converts a two-dimensional lookup table into a flat list of lookups.")
                .keys()
                .allow(NAME_ARG, "The name will be passed onto the lookup function, and also used in the lookup table report which lists all defined tables.")
                .allow(COLUMN_KEY_ARG, "The column key is used to place a house in a particular column of the lookup table")
                .require(ROW_KEYS_ARG, "The row keys are used to place a house in a particular row of the lookup table")
                .allow(INTERPOLATE, "If true, the lookup function will be constructed so as to interpolate values between adjacent rows")
                .and()
                .pos()
                .require("The first argument in a lookup table should be a list, containing a name for each row key, and then the values for matching the column key")
                .remainder("Subsequent arguments should be lists containing values for matching each row key, and then the values to use when both the row and column keys are matched")
                .and()
                .build();
    }
}
