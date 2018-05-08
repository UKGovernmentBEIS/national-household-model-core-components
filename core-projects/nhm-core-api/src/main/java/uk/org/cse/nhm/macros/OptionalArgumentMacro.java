package uk.org.cse.nhm.macros;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;

/**
 * This is a macro which has a simple behaviour:
 *
 * if you write (maybe name x) it expands to name:x
 *
 * if you write (maybe name) it expands to nothing.
 *
 * @author hinton
 *
 */
public class OptionalArgumentMacro implements IMacro {

    private final String name;

    public OptionalArgumentMacro(final String name) {
        this.name = name;
    }

    public OptionalArgumentMacro() {
        this("macro.maybe");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ISExpression transform(final Seq input, final IMacroExpander expander, final IErrorHandler errors) {
        if (input.size() > 3 || input.size() < 2) {
            errors.handle(BasicError.at(input, getName() + " requires one or two arguments"));
        } else {
            if (input.size() == 3) {
                final Node key = input.get(1);
                final Node value = input.get(2);

                return expander.expand(new Pair(input.getLocation(), expander.expand(key), value));
            }
        }
        return SExpressions.empty();
    }

    private static final class Pair implements ISExpression {

        private final Location location;

        private final ISExpression key;
        private final ISExpression value;

        Pair(final Location location, final ISExpression key, final ISExpression value) {
            super();
            this.location = location;
            this.key = key;
            this.value = value;
        }

        @Override
        public void accept(final ISExpressionVisitor arg0) {
            arg0.locate(location);
            key.accept(arg0);
            value.accept(arg0);
        }
    }

    @Override
    public MacroModel getModel() {
        return MacroModel.builder()
                .desc("The maybe macro allows you to pass an optional name on from a template (for example).")
                .desc("It expands to nothing, if it has no second argument, or the first and second arguments if it has both.")
                .pos().require("The first argument is required, and will be emitted directly if the second is present.")
                .allow("The second argument, if provided, will be emitted after the first").and().build();
    }

    @Override
    public Optional<Node> getDefiningNode() {
        return Optional.absent();
    }
}
