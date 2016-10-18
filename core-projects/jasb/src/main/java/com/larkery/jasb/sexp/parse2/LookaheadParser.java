package com.larkery.jasb.sexp.parse2;

import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse2.Lexer.Lexeme;

abstract class LookaheadParser {
	private final LookaheadLexer lexer;
	
	public LookaheadParser(final LookaheadLexer lexer) {
		super();
		this.lexer = lexer;
	}

	Lexeme lookAhead(final int distance) {
		return lexer.lookAhead(distance).orNull();
	}
	
	boolean shiftIf(final String match) {
		final Lexeme l = lookAhead(0);
		if (l != null && l.value.equals(match)) {
			shift();
			return true;
		}
		return false;
	}
	
	public void shiftRequire(final String string) {
		final Lexeme eme = shift();
		if (eme == null || eme.value.equals(string) == false) {
			throw new JasbErrorException(
					BasicError.at(eme == null ? lexer.location() : eme.location, String.format("expected %s, but was %s",
							string, eme == null ? "end-of-file" : eme.toString())));
		}
	}
	
	Lexeme shift() {
		return lexer.next();
	}

	public LookaheadLexer getLexer() {
		return lexer;
	}
}
