package com.larkery.jasb.sexp.template;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Location.Via;
import com.larkery.jasb.sexp.parse.IMacroExpander;

class Substitution implements ISExpression {

    private final ISExpression body;
    private final Map<String, ISExpression> arguments;
    private final Map<String, ISExpression> expandedArguments = new HashMap<>();
    private final Optional<Via> via;
    private final IMacroExpander expander;

    public Substitution(final Location baseLocation, final ISExpression body, final Map<String, ISExpression> arguments, final IMacroExpander expander) {
        this.body = body;
        this.arguments = arguments;
        this.expander = expander;
        this.via = Optional.of(new Via(Via.Type.Template, baseLocation));
    }

    @Override
    public void accept(final ISExpressionVisitor visitor) {
        final SubbingVisitor sv = new SubbingVisitor(visitor);
        body.accept(sv);
    }

    class SubbingVisitor implements ISExpressionVisitor {

        private final ISExpressionVisitor delegate;
        private final boolean rewritingLocation = true;

        public SubbingVisitor(final ISExpressionVisitor delegate) {
            this.delegate = delegate;
        }

        @Override
        public void locate(final Location loc) {
            // rewrite location to give location of error within template usage
            // so that errors associate to the place the template is used, not where
            // it is defined
            if (rewritingLocation && loc != null) {
                delegate.locate(loc.via(via));
            } else {
                delegate.locate(loc);
            }
        }

        @Override
        public void open(final Delim delimeter) {
            delegate.open(delimeter);
        }

        @Override
        public void atom(final String string) {
            if (arguments.containsKey(string)) {
                // this is a template parameter for this template,
                // so we want to put that in for where we are; its
                // source location is al ready OK so we don't need
                // to rewrite it.

                if (!expandedArguments.containsKey(string)) {
                    final ISExpression unexpanded = arguments.get(string);
                    final ISExpression expanded = expander.expand(
                            new ISExpression() {
                        @Override
                        public void accept(final ISExpressionVisitor visitor) {
                            unexpanded.accept(new SubbingVisitor(visitor));
                        }
                    }
                    );
                    expandedArguments.put(string, expanded);
                }

                final ISExpression value = expandedArguments.get(string);

                // disable location rewriting because we are visiting the argument and we want the error there
                // actually, don't do that.
//				rewritingLocation = false;
                value.accept(this);
//				rewritingLocation = true;
            } else {
                delegate.atom(string);
            }
        }

        @Override
        public void comment(final String text) {
            delegate.comment(text);
        }

        @Override
        public void close(final Delim delimeter) {
            delegate.close(delimeter);
        }
    }
}
