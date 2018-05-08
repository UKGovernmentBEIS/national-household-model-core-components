package com.larkery.jasb.sexp.parse2;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

public class LookaheadLexerTest {

    @Test
    public void lookahead() {
        final Lexer l = new Lexer(null, ("test://test.test"), new StringReader("a b c"));
        final LookaheadLexer lal = new LookaheadLexer(l);

        Assert.assertTrue(lal.hasNext());
        Assert.assertEquals("a", lal.lookAhead(0).get().value);
        Assert.assertEquals("a", lal.lookAhead(0).get().value);
        Assert.assertEquals("b", lal.lookAhead(1).get().value);
        Assert.assertEquals("a", lal.next().value);
        Assert.assertTrue(lal.hasNext());
        Assert.assertEquals("b", lal.lookAhead(0).get().value);
        Assert.assertEquals("b", lal.lookAhead(0).get().value);
        Assert.assertEquals("b", lal.next().value);
    }
}
