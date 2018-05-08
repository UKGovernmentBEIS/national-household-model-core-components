package com.larkery.jasb.sexp;

public class SimplePrinter implements ISExpressionVisitor {

    private final StringBuffer buffer = new StringBuffer();

    public String getString() {
        return buffer.toString();
    }

    @Override
    public void locate(final Location loc) {

    }

    @Override
    public void open(final Delim delimeter) {
        buffer.append(delimeter.open);
    }

    @Override
    public void atom(final String string) {
        buffer.append(' ');
        buffer.append(Atom.escape(string));
    }

    @Override
    public void comment(final String text) {

    }

    @Override
    public void close(final Delim delimeter) {
        buffer.append(delimeter.close);
        buffer.append('\n');
    }

    public static String toString(final ISExpression expression) {
        final SimplePrinter printer = new SimplePrinter();
        expression.accept(printer);
        return printer.getString();
    }
}
