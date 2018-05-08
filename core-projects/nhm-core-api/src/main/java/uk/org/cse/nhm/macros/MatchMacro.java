package uk.org.cse.nhm.macros;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.SimpleMacro;

/**
 * A macro which lets you do conditional compilation
 *
 * It works like
 *
 * (macro.match expression [thing-to-match thing-to-produce] [thing-to-match
 * thing-to-produce] [thing-to-ignore] default:thing-to-produce)
 *
 * @author hinton
 *
 */
public class MatchMacro extends SimpleMacro {

    private static final String DEFAULT = "default";
    private static final String INVALID_MATCH = " should contain pairs to match after the first argument, like [key value], or [key] if there is no value";
    private final String name;

    public MatchMacro(final String name) {
        this.name = name;
    }

    public MatchMacro() {
        this("macro.match");
    }

    @Override
    public Set<String> getAllowedArgumentNames() {
        return ImmutableSet.of(DEFAULT);
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
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getRequiredArgumentNames() {
        return Collections.emptySet();
    }

    @Override
    public ISExpression doTransform(final Invocation in, final IMacroExpander e, final IErrorHandler err) {
        ISExpression result = SExpressions.empty();
        try {
            // first macroexpand our argument in case it is interesting
            final List<Node> input = Node.copyAll(e.expand(in.remainder.get(0)));

            for (final Node n : in.remainder.subList(1, in.remainder.size())) {
                if (n instanceof Seq) {
                    final Seq s = (Seq) n;
                    if (s.getDelimeter() == Delim.Bracket) {
                        if (s.size() < 1) {
                            err.handle(BasicError.at(n, getName() + INVALID_MATCH));
                        } else {
                            final List<Node> left = Node.copyAll(e.expand(s.get(0)));
                            if (left.equals(input)) {
                                if (s.size() == 1) {
                                    result = SExpressions.empty();
                                } else {
                                    result = SExpressions.inOrder(s.getTail());
                                }

                                return result;
                            }
                        }
                    } else {
                        err.handle(BasicError.at(n, getName() + INVALID_MATCH));
                    }
                } else {
                    err.handle(BasicError.at(n, getName() + INVALID_MATCH));
                }
            }

            if (in.arguments.get(DEFAULT) != null) {
                result = in.arguments.get(DEFAULT);
            }

        } catch (final UnfinishedExpressionException e2) {
            err.handle(e2.getError());
        }

        return result;
    }

    @Override
    public MacroModel getModel() {
        return MacroModel.builder()
                .desc("The match macro allows different bits of scenario to be generated depending on the value of its first argument.")
                .pos().require("The first argument (matching argument) of the match should always be the thing to match against.")
                .remainder("Subsequent arguments (alternatives) should be lists of at leat one element; the first should be the value to compare to, and the remainder the values to use if the match's matching argument equals the first element.")
                .and()
                .keys().allow(DEFAULT, "The default argument is what the match will expand to if none of the alternatives are equal to the matching argument.")
                .and()
                .build();
    }
}
