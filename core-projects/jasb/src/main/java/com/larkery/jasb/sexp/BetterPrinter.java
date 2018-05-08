package com.larkery.jasb.sexp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Objects;

import com.google.common.base.CharMatcher;

public class BetterPrinter implements ISExpressionVisitor, AutoCloseable {

    private final BufferedWriter output;

    private int indentation = 0;
    private Location whereAmI = null;
    private Location toShift = null;
    private boolean inSpace = true;
    private int column = 1;
    private int columnDelta = 0;
    private int line = 1;
    private boolean inComment = false;

    private final int maxSpacing;

    public BetterPrinter(final Writer output, final int maxSpacing) {
        this.maxSpacing = maxSpacing;
        this.output = new BufferedWriter(output);
    }

    public static void print(final ISExpression expression, final Writer output, final int maxLineSkip) {
        try (BetterPrinter p = new BetterPrinter(output, maxLineSkip)) {
            expression.accept(p);
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void locate(final Location loc) {
        toShift = loc;
    }

    private void doShift() {
        shift(toShift);
        inComment = false;
    }

    private void shift(final Location loc) {
        if (whereAmI != null && loc != null) {
            if (Objects.equals(whereAmI.name, loc.name)) {
                if (line == loc.line) {
                    shiftColumn(loc.column - columnDelta);
                } else if (line < loc.line) {
                    shiftLine(loc.line - line);
                    columnDelta = loc.column;
                } else {
                    shiftLine(1);
                    columnDelta = loc.column;
                }
            } else {
                shiftLine(1);
                columnDelta = loc.column;
            }

            line = loc.line;
        } else if (inComment) {
            shiftLine(1);
        } else if (loc != null) {
            shiftLine(loc.line - line);
        }

        whereAmI = loc;
    }

    private void shiftColumn(final int column) {
        try {
            while (this.column < column) {
                this.column++;
                output.write(" ");
                inSpace = true;
            }
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void shiftLine(int j) {
        line += j;
        j = Math.min(j, maxSpacing);
        try {
            while (j > 0) {
                output.write("\n");
                for (int i = 0; i < indentation; i++) {
                    output.write("\t");
                }
                j--;
                inSpace = true;
                column = 1;
            }
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void open(final Delim delimeter) {
        doShift();
        write("" + delimeter.open);
        indentation++;
    }

    private static int lines(final String s) {
        int c = 0;
        boolean wasR = false;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '\n':
                    c++;
                    wasR = false;
                    break;
                case '\r':
                    wasR = true;
                    break;
                default:
                    if (wasR) {
                        c++;
                    }
                    wasR = false;
            }
        }
        if (wasR) {
            c++;
        }
        return c;
    }

    private static final CharMatcher END_TOKEN = CharMatcher.WHITESPACE.or(
            CharMatcher.anyOf(":()[]"));

    private static final CharMatcher NO_PRESPACE_TOKEN = CharMatcher.WHITESPACE.or(
            CharMatcher.anyOf(")]"));

    private void write(final String string) {
        try {
            if (!inSpace && !NO_PRESPACE_TOKEN.matches(string.charAt(0))) {
                if (column > 100) {
                    shiftLine(1);
                } else {
                    shiftColumn(this.column + 1);
                }
            }

            if (string.contains("\n") || string.contains("\r")) {
                output.write(string);
                column = string.length() - (string.lastIndexOf("\n") + 1);
                line += lines(string);
            } else {
                output.write(string);
                column += string.length();
            }

            inSpace = END_TOKEN.matches(string.charAt(string.length() - 1));
        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void atom(final String string) {
        doShift();
        write(Atom.escape(string));
    }

    @Override
    public void comment(final String text) {
        doShift();
        if (whereAmI == null) {
            shiftLine(1);
        }
        write(";" + text);
        inComment = true;
    }

    @Override
    public void close(final Delim delimeter) {
        indentation--;
        doShift();
        write("" + delimeter.close);
    }

    @Override
    public void close() throws IOException {
        this.output.close();
    }

    public static String print(final ISExpression expand) {
        return print(expand, Integer.MAX_VALUE);
    }

    public static String print(final ISExpression expand, final int maxLineSkip) {
        final StringWriter sw = new StringWriter();
        print(expand, sw, maxLineSkip);
        return sw.toString();
    }
}
