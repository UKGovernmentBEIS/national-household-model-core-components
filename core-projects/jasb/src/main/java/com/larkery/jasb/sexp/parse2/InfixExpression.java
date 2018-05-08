package com.larkery.jasb.sexp.parse2;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

public abstract class InfixExpression implements ISExpression {

    public static class Just extends InfixExpression {

        private final Lexeme lexeme;

        public Just(final Lexeme lexeme) {
            this.lexeme = lexeme;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            lexeme.accept(visitor);
        }

        @Override
        public String toString() {
            return "`" + lexeme + "`";
        }

        public Lexeme getLexeme() {
            return lexeme;
        }
    }

    public static class Prefix extends InfixExpression {

        private final Lexeme lexeme;
        private final InfixExpression parse;

        public Prefix(final Lexeme lexeme, final InfixExpression parse) {
            this.lexeme = lexeme;
            this.parse = parse;

        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            visitor.open(Delim.Paren);
            lexeme.accept(visitor);
            parse.accept(visitor);
            visitor.close(Delim.Paren);
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", lexeme, parse);
        }
    }

    public static class Invocation extends InfixExpression {

        private final ImmutableList<InfixExpression> build;
        private final InfixExpression head;

        public Invocation(final InfixExpression head, final ImmutableList<InfixExpression> build) {
            this.head = head;
            this.build = build;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            visitor.open(Delim.Paren);

            head.accept(visitor);
            for (final InfixExpression e : build) {
                e.accept(visitor);
            }

            visitor.close(Delim.Paren);
        }

        @Override
        public String toString() {
            return String.format("(%s %s)", head, build);
        }
    }

    public static class LiteralList extends InfixExpression {

        private final ImmutableList<InfixExpression> build;

        public LiteralList(final ImmutableList<InfixExpression> build) {
            this.build = build;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            visitor.open(Delim.Bracket);
            for (final InfixExpression e : build) {
                e.accept(visitor);
            }
            visitor.close(Delim.Bracket);
        }

        @Override
        public String toString() {
            return "(" + build.toString() + ")";
        }
    }

    public static class KeywordArg extends InfixExpression {

        private final Lexeme left;
        private final InfixExpression right;

        public KeywordArg(final Lexeme left, final InfixExpression right) {
            super();
            this.left = left;
            this.right = right;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            visitor.locate(left.location);
            visitor.atom(left.value + ":");
            right.accept(visitor);
        }

        @Override
        public String toString() {
            return String.format("(%s:%s)", left, right);
        }
    }

    public static class Binary extends InfixExpression {

        private final InfixExpression left;
        private final Lexeme lexeme;
        private final InfixExpression right;

        public Binary(final InfixExpression left, final Lexeme lexeme, final InfixExpression right) {
            this.left = left;
            this.lexeme = lexeme;
            this.right = right;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            visitor.open(Delim.Paren);
            lexeme.accept(visitor);
            left.accept(visitor);
            right.accept(visitor);
            visitor.close(Delim.Paren);
        }

        @Override
        public String toString() {
            return "(" + left + " " + lexeme + " " + right + ")";
        }
    }

    @Override
    public abstract void accept(final ISExpressionVisitor visitor);
}
