package com.larkery.jasb.sexp.parse;

import org.junit.Test;

public class ParserTest extends VisitingTest {

    @Test
    public void singleAtom() {
        check("single atom", "atom", e("atom"));
    }

    @Test
    public void atomAndComment() {
        check("atom and comment", "atom ; a comment",
                e("atom"),
                e("; a comment"));
    }

    @Test
    public void openClose() {
        check("open close", "()",
                e("("), e(")"));
    }

    @Test
    public void openCloseOpenClose() {
        check("open close open close", "() ()",
                e("("), e(")"), e("("), e(")"));
    }

    @Test
    public void openOpenOpenCloseClose() {
        check("open open close close", "(())",
                e("("), e("("), e(")"), e(")"));
    }

    @Test
    public void openAtomClose() {
        check("open atom close", "(thing)",
                e("("), e("thing"), e(")"));
    }

    @Test
    public void openAtomCommentClose() {
        check("open atom comment close", "(thing;blah\n)",
                e("("), e("thing"), e(";blah"), e(")"));
    }

    @Test
    public void openAtomCommentedCloseClose() {
        check("open atom comment close", "(thing;blah)\n)",
                e("("), e("thing"), e(";blah)"), e(")"));
    }

    @Test
    public void openCommentClose() {
        check("open comment close", "(;asdf qwer\n)", e("("), e(";asdf qwer"), e(")"));
    }

    @Test
    public void commentedLines() {
        check("commented lines", "(;asdf qwer\n;bibble\n)", e("("), e(";asdf qwer"), e(";bibble"), e(")"));
    }
}
