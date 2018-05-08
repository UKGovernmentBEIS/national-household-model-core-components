package com.larkery.jasb.sexp.parse2;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;

public class PolishParserTest {

    private void check(final String in, final String out) throws Exception {
        final LookaheadLexer lexer = new LookaheadLexer(new Lexer(null, ("test://test"), new StringReader(in)));
        final PolishParser pp = new PolishParser(lexer);
        final NodeBuilder b = NodeBuilder.create();
        pp.parse(b);

        final Node n = Node.copy(b.get());
        Assert.assertEquals(out, n.toString());
    }

    @Test
    public void normalSexpressions() throws Exception {
        check("(hello world: here is \"an s-expression\" (with [a list]))",
                "(hello world: here is \"an s-expression\" (with [a list]))");
    }

    @Test
    public void withInfixInside() throws Exception {
        check("(hello {1 + 2 * 3})", "(hello (+ 1 (* 2 3)))");
    }

    @Test
    public void withInfixInsideAndColonsOutside() throws Exception {
        check("(hello a:{1 + 2 * 3} b:{9})", "(hello a: (+ 1 (* 2 3)) b: 9)");
    }
}
