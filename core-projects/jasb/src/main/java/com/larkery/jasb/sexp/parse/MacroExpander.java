package com.larkery.jasb.sexp.parse;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.JasbErrorException;

public class MacroExpander implements IMacroExpander {

    private final Map<String, IMacro> macros;
    private final IErrorHandler errors;

    public static ISExpression expand(final List<IMacro> macros, final ISExpression input, final IErrorHandler errors) {
        return new MacroExpander(macros, errors).expand(input);
    }

    @Override
    public ISExpression expandContents(final ISExpression transformed) {
        return new ExpandedExpression(transformed, 1);
    }

    private MacroExpander(final List<IMacro> macros, final IErrorHandler errors) {
        this.errors = errors;

        final ImmutableMap.Builder<String, IMacro> b = ImmutableMap.builder();

        for (final IMacro m : macros) {
            b.put(m.getName(), m);
        }

        this.macros = b.build();
    }

    @Override
    public ISExpression expand(final ISExpression input) {
        if (Thread.interrupted()) {
            throw new JasbErrorException(BasicError.nowhere("Validation terminated"));
        }
        return new ExpandedExpression(input, 0);
    }

    class ExpandedExpression implements ISExpression {

        final ISExpression unexpanded;
        private final int atDepth;

        public ExpandedExpression(final ISExpression unexpanded, final int atDepth) {
            this.unexpanded = unexpanded;
            this.atDepth = atDepth;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            unexpanded.accept(new Editor(visitor) {
                int depth = 0;

                @Override
                public void open(final Delim delimeter) {
                    depth++;
                    super.open(delimeter);
                }

                @Override
                public void close(final Delim delimeter) {
                    depth--;
                    super.close(delimeter);
                }

                @Override
                protected Action act(final String name) {
                    // (   thingy     ( another
                    //   ^depth = 1    ^depth = 2
                    if (Thread.interrupted()) {
                        throw new JasbErrorException(BasicError.nowhere("Validation terminated"));
                    }
                    if (macros.containsKey(name) && (depth > ExpandedExpression.this.atDepth)) {
                        return Action.SingleEdit;
                    } else {
                        return Action.Pass;
                    }
                }

                @Override
                protected ISExpression edit(final Seq unexpanded) {
                    if (unexpanded.isEmpty()) {
                        throw new RuntimeException("This should never happen - if pasting part of a macro, it should at least have a macro name");
                    } else {
                        final Node first = unexpanded.get(0);
                        if (first instanceof Atom) {
                            final String s = ((Atom) first).getValue();

                            final IMacro macro = macros.get(s);

                            try {
                                return macro.transform(unexpanded, MacroExpander.this, errors);
                            } catch (final StackOverflowError soe) {
                                errors.handle(BasicError.at(first, "Maximum macro expansion depth reached within " + s));
                            }
                        }
                    }

                    return SExpressions.empty();
                }
            });
        }
    }
}
