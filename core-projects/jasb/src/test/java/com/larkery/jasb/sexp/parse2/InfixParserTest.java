package com.larkery.jasb.sexp.parse2;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class InfixParserTest {

    private void check(final String in, final String out) {
        final LookaheadLexer lexer = new LookaheadLexer(new Lexer(null, ("test://test"), new StringReader(in)));
        lexer.setSeparateColons(true);
        final InfixParser p = new InfixParser(lexer);
        final InfixExpression parse = p.parse();
        try {
            final Node n = Node.copy(parse);
            Assert.assertEquals("Parse was " + parse, out, n.toString());
        } catch (final UnfinishedExpressionException e) {
            Assert.fail("Bad parse " + parse);
        }
    }

    @Test
    public void simpleBinaryExpression() throws Exception {
        check("a + b", "(+ a b)");
    }

    @Test
    public void expressionWithPrecedence() throws Exception {
        check("a + b * c", "(+ a (* b c))");
    }

    @Test
    public void call() throws Exception {
        check("house.energy-use()", "(house.energy-use)");
    }

    @Test
    public void callArgs() throws Exception {
        check("house.energy-use(123, 345)", "(house.energy-use 123 345)");
    }

    @Test
    public void callKwArgs() throws Exception {
        check("house.energy-use(by-fuel:MainsGas, 100 + 9 / 3)", "(house.energy-use by-fuel: MainsGas (+ 100 (/ 9 3)))");
    }

    @Test
    public void literalList() {
        check("[a, b, c, d]", "[a b c d]");
    }

    @Test
    public void writingTemplate() {
        check("template([@1], @1, @1)", "(template [@1] @1 @1)");
    }

    @Test
    public void guessesOtherOperatorTypeThings() {
        check("a & b", "(& a b)");
    }

    @Test
    public void guessesOtherOperatorTypeThings2() {
        check("a & b | c", "(& a (| b c))");
    }
}
