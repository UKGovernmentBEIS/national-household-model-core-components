package com.larkery.jasb.sexp;

public enum Delim {
    Paren('(', ')'), Bracket('[', ']');

    public final char open, close;

    private Delim(final char open, final char close) {
        this.open = open;
        this.close = close;
    }

    public static Delim of(final char c) {
        switch (c) {
            case '(':
            case ')':
                return Paren;
            case '[':
            case ']':
                return Bracket;
            default:
                throw new IllegalArgumentException("No delimeter marked by " + c);
        }
    }
}
