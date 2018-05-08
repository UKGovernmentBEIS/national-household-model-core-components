package com.larkery.jasb.sexp;

import java.io.PrintWriter;
import java.util.Stack;

import com.google.common.base.CharMatcher;

class IndentedWriter {

    private final PrintWriter out;
    private final Stack<Integer> indents = new Stack<>();
    private String indent = "";
    private final int line = 0;
    private final int column = 0;
    private boolean isAtSpace = true;
    private boolean isAtNewline = true;

    public PrintWriter getOut() {
        return out;
    }

    IndentedWriter(final PrintWriter out) {
        super();
        this.out = out;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public void write(final String s) {
        if (s.equals("\n")) {
            out.print("\n");
            isAtNewline = isAtSpace = true;
            return;
        }
        int counter = 0;
        final String[] lines = s.split("\n");
        for (final String line : lines) {
            counter++;
            if (line.isEmpty()) {
                out.print("\n");
                isAtNewline = isAtSpace = true;
            } else {
                if (isAtNewline) {
                    out.print(indent);
                }
                out.print(line);
                if (counter != lines.length) {
                    out.print("\n");
                    isAtNewline = isAtSpace = true;
                } else {
                    isAtNewline = false;
                    final char c = line.charAt(line.length() - 1);
                    isAtSpace = CharMatcher.WHITESPACE.or(CharMatcher.anyOf("()"))
                            .matches(c);
                }
            }
        }
    }

    public void pushIndentation(int spaces) {
        indents.push(spaces);
        while (spaces > 0) {
            spaces--;
            indent = indent + " ";
        }
    }

    public void popIndentation() {
        indent = indent.substring(indents.pop());
    }

    public boolean isAtSpace() {
        return isAtSpace;
    }
}
