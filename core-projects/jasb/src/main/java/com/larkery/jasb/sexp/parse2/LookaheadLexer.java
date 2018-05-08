package com.larkery.jasb.sexp.parse2;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

public class LookaheadLexer {

    private final Lexer delegate;
    private final LinkedList<Lexeme> buffer = new LinkedList<>();

    public LookaheadLexer(final Lexer delegate) {
        super();
        this.delegate = delegate;
    }

    public boolean hasNext() {
        return !buffer.isEmpty() || delegate.hasNext();
    }

    public Lexeme next() {
        if (lookAhead(0).isPresent()) {
            return buffer.remove();
        } else {
            return null;
        }
    }

    public Optional<Lexeme> lookAhead(final int count) {
        while (buffer.size() <= count && delegate.hasNext()) {
            buffer.add(delegate.next());
        }

        if (buffer.size() > count) {
            return Optional.of(buffer.get(count));
        } else {
            return Optional.absent();
        }
    }

    public void setSeparateColons(final boolean b) {
        if (delegate.isSeparateColons() != b) {
            delegate.setSeparateColons(b);

            if (b) {
                splitColons();
            } else {
                lookAhead(buffer.size() + 1); // read one more lexeme in case it is a : which we stopped at when we weren't splitting colons
                squashColons();
            }
        }
    }

    private void squashColons() {
        final ImmutableList<Lexeme> old = ImmutableList.copyOf(buffer);
        buffer.clear();
        final Iterator<Lexeme> ri = old.reverse().iterator();
        while (ri.hasNext()) {
            final Lexeme eme = ri.next();
            if (eme.isComment == false && eme.value.equals(":")) {
                if (ri.hasNext()) {
                    final Lexeme next = ri.next();
                    if (next.isComment || ImmutableSet.of(",()[]{}").contains(next.value)) {
                        buffer.addFirst(eme);
                        buffer.addFirst(next);
                    } else {
                        final Lexeme joined = new Lexeme(next.location, next.value + ":", next.comment, next.isComment);
                        buffer.addFirst(joined);
                    }
                } else {
                    buffer.addFirst(eme);
                }
            } else {
                buffer.addFirst(eme);
            }
        }
    }

    private void splitColons() {
        final ImmutableList<Lexeme> old = ImmutableList.copyOf(buffer);
        buffer.clear();
        for (final Lexeme le : old) {
            if (!le.isComment && le.value.length() > 1 && le.value.endsWith(":")) {
                final Lexeme first = new Lexeme(le.location, le.value.substring(0, le.value.length() - 1), Optional.<Lexeme>absent(), false);
                final Lexeme second = new Lexeme(le.location, ":", le.comment, false);
                buffer.add(first);
                buffer.add(second);
            } else {
                buffer.add(le);
            }
        }
    }

    public Location location() {
        return delegate.location();
    }
}
