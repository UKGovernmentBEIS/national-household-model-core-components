package com.larkery.jasb.sexp.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.common.collect.ImmutableList;

/**
 * Utility that indents text.
 */
public class Indenter {

    /**
     * This stores the current set of tab stops; each time we look at a line, we
     * see what characters are on it and twiddle the tab stops
     */
    static class Tabstops {

        /**
         * The stack of tabstops. The current indent is the top of the stack
         */
        final Deque<Integer> stops = new LinkedList<Integer>();
        /**
         * This is a helpful string containing as many spaces as the top of the
         * tabstops stack
         */
        String string = "";

        public Tabstops() {
            this.stops.push(0);
        }

        /**
         * Change the current topmost tabstop - this happens when we have pushed
         * a tab on this line, and then there are some words after it so we get
         *
         * (do something ^ ^ this is the new stop | this was the stop
         */
        public void amend(final int x) {
            this.stops.poll();
            this.stops.push(x);
            this.string = rep(x);
        }

        /**
         * Add a new tabstop; we have seen a ( or something
         */
        public void push(final int x) {
            this.stops.push(x);
            this.string = rep(x);
        }

        /**
         * @param x
         * @return x spaces
         */
        public static String rep(final int x) {
            final StringBuffer b = new StringBuffer();
            for (int i = 0; i < x; i++) {
                b.append(' ');
            }
            return b.toString();
        }

        /**
         * We have seen a ) or ], so we want to deindent.
         */
        public void pop() {
            this.stops.poll();
            final Integer val = this.stops.peek();
            if (val == null) {
                this.string = "";
            } else {
                this.string = rep(val);
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer();
            int k = 0;
            final Iterator<Integer> it = this.stops.descendingIterator();
            while (it.hasNext()) {
                final int j = it.next();
                while (k < j) {
                    sb.append(" ");
                    k++;
                }
                sb.append("^");
                k++;
            }
            return sb.toString();
        }

        /**
         * This is the main thing - we have seen a new line and we want to know
         * what the indent should be on the next line when we come to it.
         *
         * This works by: looping through each char in the line; if it changes
         * indentation, we modify the indentation we also keep a within-line
         * stack of whether we are after a start-paren and so whether we need to
         * amend the last tabstop
         *
         * @param trim the new line, with leading and trailing WS removed
         */
        public void eat(final String trim) {
            // all our tabstops have at least this much on them
            final int start = this.string.length();

            // we need to know whether, on this line
            // we have to shift the tabstop because there
            // is something after the start of the last unclosed
            // parenthesis.
            final Deque<LevelState> state = new LinkedList<>();

            for (int i = 0; i < trim.length(); i++) {
                final char c = trim.charAt(i);

                switch (c) {
                    case ';':
                        return;
                    case '(':
                    case '{':
                    case '[':
                        // deal with the outer thing
                        if (state.peek() == LevelState.SpaceAfterHead) {
                            // amendment is required
                            amend(start + i);
                            state.pop();
                            state.push(LevelState.WordsAfterHead);
                        }

                        // add a tabstop
                        push(start + i + (c == '[' ? 1 : 4));
                        state.push(LevelState.BeforeHead);
                        break;
                    case ')':
                    case ']':
                    case '}':
                        pop();
                        state.poll();
                        break;
                    case ' ':
                    case ':':
                    case '\t':
                        if (state.peek() == LevelState.WithinHead) {
                            state.pop();
                            state.push(LevelState.SpaceAfterHead);
                        }
                        break;
                    default:
                        // a character of a word
                        // if this is the start of a word
                        // and it is not the head of the
                        // enclosing expression, we want to indent
                        // some more
                        if (state.peek() == LevelState.BeforeHead) {
                            state.pop();
                            state.push(LevelState.WithinHead);
                        } else if (state.peek() == LevelState.SpaceAfterHead) {
                            // amendment is required
                            amend(start + i);
                            state.pop();
                            state.push(LevelState.WordsAfterHead);
                        }
                }
            }
        }

        enum LevelState {
            BeforeHead,
            WithinHead,
            SpaceAfterHead,
            WordsAfterHead
        }
    }

    public static String indent(final ImmutableList<String> previousLines, final ImmutableList<String> lines) {
        final StringBuffer result = new StringBuffer();

        // accumulate the existing indentation information
        final Tabstops stops = new Tabstops();
        for (final String line : previousLines) {
            stops.eat(line.trim());
        }

        boolean first = true;
        for (final String line : lines) {
            if (first) {
                first = false;
            } else {
                result.append('\n');
            }

            final String trim = line.trim();
            result.append(stops.string);
            result.append(trim);
            stops.eat(trim);
        }

        return result.toString();
    }

    public static String indent(final String input) {
        final StringBuffer sb = new StringBuffer();

        BufferedReader in = new BufferedReader(new StringReader(input));

        final Tabstops stops = new Tabstops();
        String s;
        try {
            while ((s = in.readLine()) != null) {
                stops.eat(s.trim());
                sb.append(s.trim());
                sb.append("\n");
                sb.append(stops.string);
            }
        } catch (IOException e) {
            // never happen
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        final Tabstops stops = new Tabstops();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        final StringBuffer sb = new StringBuffer();
        while ((s = in.readLine()) != null && s.length() != 0) {
            stops.eat(s.trim());
            sb.append(s.trim());
            sb.append("\n");
            sb.append(stops.string);
        }
        System.err.println(sb);
    }
}
