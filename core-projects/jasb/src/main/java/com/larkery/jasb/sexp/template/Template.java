package com.larkery.jasb.sexp.template;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.SimpleMacro;

class Template extends SimpleMacro {

    static abstract class Argument {

        protected final String internalName;
        protected final String externalName;
        protected final Optional<? extends ISExpression> defaultValue;

        Argument(final String internalName, final String externalName, final Optional<? extends ISExpression> defaultValue) {
            super();
            this.internalName = internalName;
            this.externalName = externalName;
            this.defaultValue = defaultValue;
        }

        public abstract Optional<? extends ISExpression>
                read(final Map<String, Node> namedArguments, final Deque<Node> numberedArguments);

        public String getInternalName() {
            return internalName;
        }

        public String getExternalName() {
            return externalName;
        }

        public boolean hasDefault() {
            return defaultValue.isPresent();
        }

        public static Optional<? extends Argument> of(
                final Location location,
                final String externalName,
                final String internalName,
                final Optional<ISExpression> defaultValue,
                final IErrorHandler errors) {
            if (!externalName.startsWith("@")) {
                errors.error(location, "template argument %s does not start with @", externalName);
                return Optional.absent();
            }

            if (internalName.isEmpty()) {
                errors.error(location, "internal name for %s is empty, or missing and external name is empty", externalName);
                return Optional.absent();
            }

            final String externalNameWithoutAt = externalName.substring(1);
            try {
                final int position = Integer.parseInt(externalNameWithoutAt);
                if (position < 1) {
                    errors.error(location, "cannot have a numbered argument whose number is less than 1");
                    return Optional.absent();
                }
                return Optional.of(new NumberedArgument(internalName, externalNameWithoutAt, defaultValue, location));
            } catch (final NumberFormatException nfe) {
                if (externalNameWithoutAt.equals("rest")) {
                    return Optional.of(new RestArgument(internalName, defaultValue));
                } else {
                    return Optional.of(new NamedArgument(internalName, externalNameWithoutAt, defaultValue));
                }
            }
        }
    }

    static class NamedArgument extends Argument {

        NamedArgument(final String internalName, final String externalName, final Optional<? extends ISExpression> defaultValue) {
            super(internalName, externalName, defaultValue);
        }

        @Override
        public Optional<? extends ISExpression> read(final Map<String, Node> namedArguments, final Deque<Node> numberedArguments) {
            if (namedArguments.containsKey(externalName)) {
                return Optional.<ISExpression>of(namedArguments.get(externalName));
            }
            return defaultValue;
        }

        @Override
        public String toString() {
            return "argument named " + getExternalName();
        }

    }

    static class NumberedArgument extends Argument implements Comparable<NumberedArgument> {

        private final int index;
        private final Location location;

        public NumberedArgument(final String internalName, final String externalName, final Optional<? extends ISExpression> defaultValue, final Location location) {
            super(internalName, externalName, defaultValue);
            this.location = location;
            this.index = Integer.parseInt(externalName);
        }

        @Override
        public Optional<? extends ISExpression> read(final Map<String, Node> namedArguments, final Deque<Node> numberedArguments) {
            if (numberedArguments.isEmpty()) {
                return defaultValue;
            } else {
                return Optional.of(numberedArguments.pollFirst());
            }
        }

        public Location getLocation() {
            return location;
        }

        @Override
        public String toString() {
            final String th;
            switch (index) {
                case 1:
                    th = "st";
                    break;
                case 2:
                    th = "nd";
                    break;
                case 3:
                    th = "rd";
                    break;
                default:
                    th = "th";
                    break;
            }
            return String.format("a %d%s unnamed argument", index, th);
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(final NumberedArgument arg0) {
            return Integer.compare(index, arg0.index);
        }
    }

    static class RestArgument extends Argument {

        public RestArgument(final String internalName, final Optional<? extends ISExpression> defaultValue) {
            super(internalName, "rest", defaultValue);
        }

        @Override
        public Optional<? extends ISExpression> read(final Map<String, Node> namedArguments, final Deque<Node> numberedArguments) {
            final ISExpression result = SExpressions.inOrder(numberedArguments);
            numberedArguments.clear();
            return Optional.of(result);
        }
    }

    private final List<Argument> arguments;
    private final String templateName;
    private final ISExpression body;
    private final ImmutableSet<String> requiredNames;
    private final ImmutableSet<String> allowedNames;
    private final int minimumArgumentCount;
    private final int allowedArgumentCount;
    private final Seq definition;

    public Template(final Seq definition, final String name, final ISExpression body,
            final List<NamedArgument> named,
            final List<NumberedArgument> numbered,
            final Optional<RestArgument> rest) {
        this.definition = definition;
        this.templateName = name;
        this.body = body;

        final ImmutableList.Builder<Argument> arguments = ImmutableList.builder();

        final ImmutableSet.Builder<String> requiredArguments = ImmutableSet.builder();
        final ImmutableSet.Builder<String> allowedArguments = ImmutableSet.builder();
        int requiredNumberedArguments = 0;
        int allowedNumberedArguments = 0;

        for (final NamedArgument n : named) {
            arguments.add(n);
            allowedArguments.add(n.getExternalName());
            if (!n.hasDefault()) {
                requiredArguments.add(n.getExternalName());
            }
        }

        for (final NumberedArgument num : numbered) {
            arguments.add(num);
            allowedNumberedArguments++;
            if (!num.hasDefault()) {
                requiredNumberedArguments++;
            }
        }

        if (rest.isPresent()) {
            allowedNumberedArguments = Integer.MAX_VALUE;
            arguments.add(rest.get());
        }

        this.arguments = arguments.build();

        this.requiredNames = requiredArguments.build();
        this.allowedNames = allowedArguments.build();
        this.minimumArgumentCount = requiredNumberedArguments;
        this.allowedArgumentCount = allowedNumberedArguments;
    }

    @Override
    public String getName() {
        return templateName;
    }

    @Override
    protected Set<String> getRequiredArgumentNames() {
        return requiredNames;
    }

    @Override
    protected Set<String> getAllowedArgumentNames() {
        return allowedNames;
    }

    @Override
    protected int getMaximumArgumentCount() {
        return allowedArgumentCount;
    }

    @Override
    protected int getMinimumArgumentCount() {
        return minimumArgumentCount;
    }

    @Override
    protected ISExpression doTransform(final Invocation validated, final IMacroExpander expander, final IErrorHandler errors) {
        final Deque<Node> remainder = new LinkedList<>(validated.remainder);
        final ImmutableMap.Builder<String, ISExpression> internalNames = ImmutableMap.builder();
        boolean hasError = false;

        //TODO expand arguments before substituting them in
        // really we should probably go:
        // for each argument:
        //   copy the argument with substitutions of other arguments (recursively)
        // return the body with the arguments substituted in
        for (final Argument argument : arguments) {
            final Optional<? extends ISExpression> expression = argument.read(validated.arguments, remainder);
            if (expression.isPresent()) {
                final ISExpression e = expression.get();
                internalNames.put(
                        "@" + argument.getInternalName(),
                        e);
            } else {
                // error!!
                errors.handle(BasicError.at(validated.node.getLocation(),
                        String.format("template %s requires %s", templateName, argument)
                ));
                hasError = true;
            }
        }

        if (hasError) {
            return SExpressions.empty();
        } else {
            return new Substitution(validated.node.getLocation(), body, internalNames.build(), expander);
        }
    }

    @Override
    public Optional<Node> getDefiningNode() {
        return Optional.<Node>of(definition);
    }
}
