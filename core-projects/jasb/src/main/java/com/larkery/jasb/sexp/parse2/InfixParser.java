package com.larkery.jasb.sexp.parse2;

import java.util.Map;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

public class InfixParser extends LookaheadParser {

    interface InfixOp {

        public InfixExpression parse(final InfixParser parser, final InfixExpression left, final Lexeme lexeme);

        public int precedence();
    }

    interface PrefixOp {

        InfixExpression parse(final InfixParser parser, final Lexeme lexeme);
    }

    /**
     * Handler for most binary operators (*, / etc)
     *
     * @author hinton
     *
     */
    static class BinaryInfix implements InfixOp {

        private final int precedence;
        private final boolean leftAssociative;

        public BinaryInfix(final int precedence, final boolean leftAssociative) {
            super();
            this.precedence = precedence;
            this.leftAssociative = leftAssociative;
        }

        @Override
        public InfixExpression parse(final InfixParser parser, final InfixExpression left, final Lexeme lexeme) {
            return new InfixExpression.Binary(left, lexeme, parser.parse(precedence - (leftAssociative ? 0 : 1)));
        }

        @Override
        public int precedence() {
            return precedence;
        }
    }

    /**
     * Parses (...) as ordering of evaluation
     *
     * @author hinton
     *
     */
    static class Paren implements PrefixOp {

        @Override
        public InfixExpression parse(final InfixParser parser, final Lexeme lexeme) {
            final InfixExpression subExpression = parser.parse();
            parser.shiftRequire(")");
            return subExpression;
        }
    }

    static class Bracket implements PrefixOp {

        @Override
        public InfixExpression parse(final InfixParser parser, final Lexeme lexeme) {
            final ImmutableList.Builder<InfixExpression> contents = ImmutableList.builder();

            if (!parser.shiftIf("]")) {
                do {
                    contents.add(parser.parse());
                } while (parser.shiftIf(","));
                parser.shiftRequire("]");
            }

            return new InfixExpression.LiteralList(contents.build());
        }
    }

    static class Prefix implements PrefixOp {

        private final int precedence;

        public Prefix(final int precedence) {
            super();
            this.precedence = precedence;
        }

        @Override
        public InfixExpression parse(final InfixParser parser, final Lexeme lexeme) {
            return new InfixExpression.Prefix(lexeme, parser.parse(precedence));
        }
    }

    static class Colon implements InfixOp {

        @Override
        public InfixExpression parse(final InfixParser parser, final InfixExpression left, final Lexeme lexeme) {
            if (left instanceof InfixExpression.Just) {
                return new InfixExpression.KeywordArg(((InfixExpression.Just) left).getLexeme(), parser.parse());
            } else {
                throw new JasbErrorException(BasicError.at(lexeme.location, "In an infix expression, ':' should only be used to the right of a word"));
            }
        }

        @Override
        public int precedence() {
            return 1;
        }

    }

    /**
     * Parses my_function(...); a lot like bracket actually but it needs 1 token
     * of extra lookahead
     */
    static class Call implements InfixOp {

        private final int precedence;

        public Call(final int precedence) {
            super();
            this.precedence = precedence;
        }

        @Override
        public InfixExpression parse(final InfixParser parser, final InfixExpression left, final Lexeme lexeme) {
            final ImmutableList.Builder<InfixExpression> contents = ImmutableList.builder();

            if (!parser.shiftIf(")")) {
                do {
                    contents.add(parser.parse());
                } while (parser.shiftIf(","));
                parser.shiftRequire(")");
            }

            return new InfixExpression.Invocation(left, contents.build());
        }

        @Override
        public int precedence() {
            return precedence;
        }

    }

    public InfixParser(final LookaheadLexer lexer) {
        super(lexer);
    }

    private final Map<String, PrefixOp> prefixes = ImmutableMap.<String, PrefixOp>builder()
            .put("(", new Paren())
            .put("[", new Bracket())
            .put("-", new Prefix(1))
            .build();

    private final Map<String, InfixOp> infixes = ImmutableMap.<String, InfixOp>builder()
            .put("+", new BinaryInfix(3, true))
            .put("-", new BinaryInfix(3, true))
            .put("*", new BinaryInfix(4, true))
            .put("/", new BinaryInfix(4, true))
            .put("=", new BinaryInfix(2, true))
            .put(">", new BinaryInfix(2, true))
            .put("<", new BinaryInfix(2, true))
            .put(">=", new BinaryInfix(2, true))
            .put("<=", new BinaryInfix(2, true))
            .put(":", new Colon())
            .put("(", new Call(50))
            .build();

    private static final CharMatcher NON_OPERATOR_CHARACTERS = CharMatcher.JAVA_LETTER_OR_DIGIT
            .or(CharMatcher.anyOf("()[]{},:;"));

    ;
	
	public InfixExpression parse(final int precedence) {
        Lexeme lexeme = shift();
        while (lexeme.isComment) {
            // we need to shift out all the comments until we are good to actually work
            lexeme = shift();
        }
        InfixExpression left;
        if (prefixes.containsKey(lexeme.value)) {
            final PrefixOp op = prefixes.get(lexeme.value);
            left = op.parse(this, lexeme);
        } else {
            left = new InfixExpression.Just(lexeme);
        }

        while (precedence < currentPrecedence()) {
            lexeme = shift();

            if (infixes.containsKey(lexeme.value)) {
                final InfixOp op = infixes.get(lexeme.value);
                left = op.parse(this, left, lexeme);
            } else {
                // presume a generic infix op
                left = new InfixExpression.Binary(left, lexeme, parse());
            }
        }

        return left;
    }

    private int currentPrecedence() {

        final Lexeme lexeme = lookAhead(0);
        if (lexeme == null) {
            return -Integer.MAX_VALUE;
        }
        if (infixes.containsKey(lexeme.value)) {
            return infixes.get(lexeme.value).precedence();
        } else if (NON_OPERATOR_CHARACTERS.matchesNoneOf(lexeme.value)) {
            return 1;
        } else {
            return 0;
        }
    }

    public InfixExpression parse() {
        return parse(0);
    }

}
