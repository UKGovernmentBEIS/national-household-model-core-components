package com.larkery.jasb.sexp.parse2;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;

public class Lexer {

    static class Lexeme {

        public final Location location;
        public final String value;
        public final Optional<Lexeme> comment;
        public final boolean isComment;

        public Lexeme(final Location location, final String value, final Optional<Lexeme> quoted, final boolean isComment) {
            super();
            this.location = location;
            this.value = value;
            this.comment = quoted;
            this.isComment = isComment;
        }

        @Override
        public String toString() {
            return (isComment ? "; " : "") + value;
        }

        public void comment(final ISExpressionVisitor visitor) {
            visitor.locate(location);
            visitor.comment(value);
            if (comment.isPresent()) {
                comment.get().accept(visitor);
            }
        }

        public void accept(final ISExpressionVisitor visitor) {
            if (isComment) {
                comment(visitor);
            } else {
                visitor.locate(location);

                switch (value) {
                    case "{":
                    case "}":
                        // this is in error
                        break;
                    case ",":// ignore as whitespace
                        break;
                    case "(":
                    case "[":
                        visitor.open(Delim.of(value.charAt(0)));
                        break;
                    case ")":
                    case "]":
                        visitor.close(Delim.of(value.charAt(0)));
                        break;
                    default:
                        if (isComment) {
                            visitor.comment(value);
                        } else {
                            visitor.atom(value);
                        }
                }
                if (comment.isPresent()) {
                    comment.get().comment(visitor);
                }
            }
        }
    }

    private final Optional<Location.Via> viaSourceLocation;
    private final String uri;
    private int line = 1;
    private int column = 0;
    private int offset = -1;
    private final PushbackReader reader;
    private Lexeme next = null;
    private boolean separateColons = false;

    public Lexer(final Location sourceLocation, final String uri, final Reader reader) {
        super();

        this.viaSourceLocation = sourceLocation == null
                ? Optional.<Location.Via>absent()
                : Optional.of(new Location.Via(Location.Via.Type.Include, sourceLocation));
        this.uri = uri;
        this.reader = new PushbackReader(reader, 1);
        advance();
    }

    private static final CharMatcher BREAKS = CharMatcher.WHITESPACE.or(CharMatcher.anyOf(",(){}[]:"));

    enum LexState {
        None,
        Quoted,
        Escaped
    }

    private int read() {
        int c;
        try {
            c = reader.read();
            offset++;
            column++;
            if (c == '\n') {
                line++;
                column = 0;
            }
            return c;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void unread(final int c) {
        try {
            reader.unread(c);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        offset--;
        column--;
        // cannot safely unread a newline (column ends up wrong), but it doesn't matter because we will just read it off next anyway
        if (c == '\n') {
            line--;
        }
    }

    private Optional<Lexeme> readComment() {
        boolean commentStarted = false;
        final StringBuffer comment = new StringBuffer();
        int i;
        Location location = null;
        while ((i = read()) >= 0) {
            if (i == '\n') {
                if (commentStarted) {
                    break;
                }
            } else if (CharMatcher.WHITESPACE.matches((char) i)) {
                if (commentStarted) {
                    comment.append((char) i);
                }
            } else if (i == ';') {
                if (commentStarted) {
                    comment.append((char) i);
                } else {
                    location = location();
                }
                commentStarted = true;
            } else {
                if (!commentStarted) {
                    unread(i);
                    return Optional.absent();
                } else {
                    comment.append((char) i);
                }
            }
        }

        if (commentStarted) {
            return Optional.of(new Lexeme(location, comment.toString(), readComment(), true));
        } else {
            return Optional.absent();
        }
    }

    private Lexeme readChunk() {
        LexState state = LexState.None;
        final StringBuffer sb = new StringBuffer();
        Location where = null;
        int i;
        int bytesRead = 0;
        while ((i = read()) >= 0) {
            bytesRead++;
            if (where == null && !CharMatcher.WHITESPACE.matches((char) i)) {
                where = location();
            }

            switch (state) {
                case None:
                    if (BREAKS.matches((char) i)) {
                        if (!separateColons && ((char) i) == ':') {
                            sb.append(":");
                            return new Lexeme(where, sb.toString(), readComment(), false);
                        } else if (sb.length() > 0) {
                            unread(i);
                            return new Lexeme(where, sb.toString(), readComment(), false);
                        } else if (!CharMatcher.WHITESPACE.matches((char) i)) {
                            sb.append((char) i);
                            return new Lexeme(where, sb.toString(), readComment(), false);
                        }
                    } else if (i == ';') {
                        unread(i);
                        if (sb.length() > 0) {
                            return new Lexeme(where, sb.toString(), readComment(), false);
                        } else {
                            return readComment().get();
                        }
                    } else if (i == '"') {
                        state = LexState.Quoted;
                    } else {
                        sb.append((char) i);
                    }
                    break;
                case Quoted:
                    if (i == '"') {
                        state = LexState.None;
                        return new Lexeme(where, sb.toString(), readComment(), false);
                    } else if (i == '\\') {
                        state = LexState.Escaped;
                    } else {
                        sb.append((char) i);
                    }
                    break;
                case Escaped:
                    sb.append((char) i);
                    if (i != '\\') {
                        state = LexState.Quoted;
                    }
                    break;
            }
        }

        if (bytesRead == 0) {
            return null;
        }
        return new Lexeme(where, sb.toString(), readComment(), false);
    }

    private void advance() {
        next = readChunk();
    }

    public Location location() {
        return Location.of(uri, line, column, offset, viaSourceLocation);
    }

    public boolean hasNext() {
        return next != null;
    }

    public Lexeme next() {
        try {
            return next;
        } finally {
            advance();
        }
    }

    public void setSeparateColons(final boolean b) {
        this.separateColons = b;
    }

    public boolean isSeparateColons() {
        return separateColons;
    }
}
