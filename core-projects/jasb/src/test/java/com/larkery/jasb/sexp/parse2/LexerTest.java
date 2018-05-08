package com.larkery.jasb.sexp.parse2;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

public class LexerTest {

    private void check(final String s, final String[][] lexemes) {
        final Lexer l = new Lexer(null, ("test://test.test"), new StringReader(s));
        int i = 0;

        final List<Lexeme> values = new ArrayList<>();
        while (l.hasNext()) {
            values.add(l.next());
        }

        final Iterator<Lexeme> it = values.iterator();
        try {
            while (it.hasNext()) {
                Lexeme lexeme = it.next();
                if (i >= lexemes.length) {
                    throw new AssertionError("Unexpected lexeme " + lexeme);
                }
                final String[] expect = lexemes[i++];

                int k;
                if (expect[0] == null) {
                    k = 1;
                } else {
                    k = 0;
                }

                while (lexeme != null) {
                    if (k > 0) {
                        Assert.assertTrue("should be a comment", lexeme.isComment);
                    }
                    if (k >= expect.length) {
                        Assert.fail("Unexpected lexeme " + lexeme + " amongst " + Arrays.toString(expect));
                    }
                    Assert.assertEquals(expect[k++], lexeme.value);

                    lexeme = lexeme.comment.orNull();
                }

                if (k < expect.length) {
                    Assert.fail("Missed off " + Arrays.toString(Arrays.copyOfRange(expect, k - 1, expect.length)));
                }
            }
            if (i < lexemes.length) {
                Assert.fail("Missed off " + Arrays.toString(lexemes[i]) + " and the rest");
            }
        } catch (final AssertionError ae) {
            throw new AssertionError(ae.getMessage() + " (lexemes = " + values + ")", ae);
        }
    }

    private static String[] s(final String... strings) {
        return strings;
    }

    private static String[][] e(final String[]... strings) {
        return strings;
    }

    @Test
    public void lexesOneWord() {
        check("blah", e(s("blah")));
    }

    @Test
    public void oneComment() {
        check("; blah", e(s(null, " blah")));
    }

    @Test
    public void wordWithComments() {
        check("blah ; blah\n;blah", e(s("blah", " blah", "blah")));
    }

    @Test
    public void parens() {
        check("(blah ( stuff )[])", e(s("("), s("blah"), s("("), s("stuff"), s(")"), s("["), s("]"), s(")")));
    }

    @Test
    public void colon() {
        check("a:b", e(s("a:"), s("b")));
    }

    @Test
    public void comma() {
        check("a,b , c", e(s("a"), s(","), s("b"), s(","), s("c")));
    }

    @Test
    public void openAtomCommentClose() {
        check("(thing;blah\n)", e(s("("), s("thing", "blah"), s(")")));
    }

    @Test
    public void commentedLines() {
        check("(;asdf qwer\n;bibble\n)", e(s("(", "asdf qwer", "bibble"), s(")")));
    }

    @Test
    public void emptyString() {
        check("thing:\"\"", e(s("thing:"), s("")));
    }
}
