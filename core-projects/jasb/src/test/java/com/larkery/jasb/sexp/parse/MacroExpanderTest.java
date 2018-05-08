package com.larkery.jasb.sexp.parse;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.template.Templates;

public class MacroExpanderTest extends VisitingTest {

    @Test
    public void macroExpanderExpandsMacros() throws Exception {
        final NodeBuilder nb = NodeBuilder.create();
        final List<IMacro> macs = Templates.extract(source("macExTest",
                "(top (template test [@a [@b]] (@a @b @a)) (test a:(this is some a)))"),
                nb, IErrorHandler.RAISE);

        Node.copy(MacroExpander.expand(macs, nb.get(), IErrorHandler.RAISE));
    }

    static class TestMacro implements IMacro {

        @Override
        public MacroModel getModel() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName() {
            return "test-macro";
        }

        @Override
        public ISExpression transform(final Seq expanded, final IMacroExpander e, final IErrorHandler errors) {
            return new ISExpression() {
                @Override
                public void accept(final ISExpressionVisitor v) {
                    v.atom("a:");
                    v.atom("b");
                }
            };
        }

        @Override
        public Optional<Node> getDefiningNode() {
            return Optional.absent();
        }
    }
}
