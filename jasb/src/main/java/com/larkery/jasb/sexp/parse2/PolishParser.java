package com.larkery.jasb.sexp.parse2;

import java.io.Reader;
import java.util.Deque;
import java.util.LinkedList;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

public class PolishParser extends LookaheadParser {
	public PolishParser(final LookaheadLexer lexer) {
		super(lexer);
	}
	
	public PolishParser(final String location, final Reader reader) {
		this(null, location, reader);
	}

	public PolishParser(final Location location, final String uri, final Reader reader) {
		this(new LookaheadLexer(new Lexer(location, uri, reader)));
	}

	public void parse(final ISExpressionVisitor output) {
		final Deque<Lexeme> parens = new LinkedList<>();
		while (true) {
			final Lexeme next = shift();
			if (next == null) {
				break;
			}
			boolean open = false;
			switch (next.value) {
			case "(":
			case "[":
				open = true;
			case ")":
			case "]":
				if (open) {
					parens.push(next);
				} else if (parens.isEmpty()) {
					throw new JasbErrorException(BasicError.at(next.location, "Too many closing brackets or parentheses"));
				} else {
					final Lexeme opener = parens.pop();
					final Delim openingDelim = Delim.of(opener.value.charAt(0));
					final Delim closingDelim = Delim.of(next.value.charAt(0));
					if (openingDelim != closingDelim) {
						throw new JasbErrorException(BasicError.at(opener.location, String.format("Opening %c closed with %c", openingDelim.open, closingDelim.close)));
					}
				}
			default:
				next.accept(output);
				break;
			case "{":
				final Lexeme head = lookAhead(0);
				if (head != null && head.value.equals("}")) {
					continue;
				}
				final LookaheadLexer lexer = getLexer();
				lexer.setSeparateColons(true);
				final InfixParser infix = new InfixParser(getLexer());
				do {
					final InfixExpression parse = infix.parse();
					parse.accept(output);
				} while (shiftIf(","));
				lexer.setSeparateColons(false);
				shiftRequire("}");
				break;
			}
		}
		
		if (!parens.isEmpty()) {
			final ImmutableList.Builder<IError> errors = ImmutableList.builder();
			for (final Lexeme l : parens) {
				errors.add(BasicError.at(l.location, "Unclosed " + l.value));
			}
			throw new JasbErrorException(errors.build());
		}
	}
}
