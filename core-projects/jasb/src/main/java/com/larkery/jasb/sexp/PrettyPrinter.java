package com.larkery.jasb.sexp;

import java.io.StringWriter;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.commons.lang3.text.WordUtils;

import com.google.common.base.CharMatcher;

public class PrettyPrinter implements ISExpressionVisitor {
	int maxWidth = 80;
	
	class W {
		final StringWriter o = new StringWriter();
		final StringBuffer line = new StringBuffer();
		
		final Deque<String> indents = new LinkedList<>();
		final StringBuffer comments = new StringBuffer();
		
		int indentation = 0;
		
		public void push(final String tab) {
			indentation+=tab.length();
			indents.push(tab);
		}
		
		public void pop() {
			if (indents.isEmpty()) {
				return;
			}
			indentation-=indents.pop().length();
		}
		
		public void writeWord(final String string) {
			flushComments();
			if (line.length() > maxWidth) {
				newline();
			} else if (needSpace(string)) {
				line.append(" ");
			} 
			if (line.length() == 0) {
				for (final String s : indents) line.append(s);
			}
			line.append(string);
		}
		
		public void newline() {
			o.append('\n');
			o.append(line);
			line.setLength(0);
		}
		
		private boolean needSpace(final String put) {
			if (")".equals(put)) return false;
			
			if (line.length() == 0) return false;
			final char c = line.charAt(line.length()-1);
			
			if (c == '(') return false;
			
			if (CharMatcher.WHITESPACE.matches(c)) return false;
			
			return true;
		}

		public void flushComments() {
			if (comments.length() > 0) {
				writeComment(comments.toString());
				comments.setLength(0);
			}
		}
		
		public void writeComment(final String string) {
			final StringBuffer tabs = new StringBuffer();
			for (final String s : indents) tabs.append(s);
			newline();
			line.append(WordUtils.wrap(string.trim(), Math.max(30, maxWidth-indentation))
					.replaceAll("(?m)^", tabs.toString() + "; "));
			newline();
		}
		
		public void addComment(final String string) {
			if (comments.length() > 0 && !CharMatcher.WHITESPACE.matches(comments.charAt(comments.length()-1))) {
				comments.append(" ");
			}
			comments.append(string);
		}
		
		public void flush() {
			flushComments();
			newline();
		}
	}
	
	final W w = new W();
	
	enum Head {
		TOP, EXPECT_NAME, EXPECT_KEY, EXPECT_VAL, EXPECT_REST
	}
	
	final Deque<Head> state = new LinkedList<>();
	
	public PrettyPrinter() {
		state.push(Head.TOP);
	}
	
	public static String print(final ISExpression expression) {
		final PrettyPrinter pp = new PrettyPrinter();
		expression.accept(pp);
		return pp.toString();
	}

	@Override
	public void locate(final Location loc) {
		
	}

	@Override
	public void open(final Delim delimeter) {
		switch (state.pop()) {
		case EXPECT_KEY:
			state.push(Head.EXPECT_REST);
			w.newline();
			break;
		case EXPECT_NAME:
			state.push(Head.EXPECT_REST);
			break;
		case EXPECT_REST:
			state.push(Head.EXPECT_REST);
			w.newline();
			break;
		case EXPECT_VAL:
			state.push(Head.EXPECT_KEY);
			break;
		case TOP:
			state.push(Head.TOP);
			break;
		default:
			break;
		
		}
		state.push(Head.EXPECT_NAME);
		w.writeWord(""+delimeter.open);
		w.push("    ");
	}

	@Override
	public void atom(final String atom) {
		final String string = Atom.escape(atom);
		boolean pushKeyWidth = false;
		boolean popKeyWidth = false;
		final boolean isKey = string.endsWith(":");
		switch (state.pop()) {
		case EXPECT_KEY:
			if (isKey) {
				w.newline();
				state.push(Head.EXPECT_VAL);
				pushKeyWidth = true;
			} else {
				state.push(Head.EXPECT_REST);
			}
			break;
		case EXPECT_NAME:
			state.push(Head.EXPECT_KEY);
			w.push("");
			break;
		case EXPECT_REST:
			state.push(Head.EXPECT_REST);
			break;
		case EXPECT_VAL:
			state.push(Head.EXPECT_KEY);
			popKeyWidth = true;
			break;
		case TOP:
			state.push(Head.TOP);
			break;
		default:
			break;
		}
		w.writeWord(string);
		if (pushKeyWidth) {
			w.push(new String(new char[string.length()+2]).replace('\0', ' '));
		} else if (popKeyWidth) {
			w.pop();
		}
	}

	@Override
	public void comment(final String text) {
		w.addComment(text);
	}

	@Override
	public void close(final Delim delimeter) {
		w.writeWord(""+delimeter.close);
		w.pop();
		w.pop();
		state.pop();
		if (state.peek() == Head.EXPECT_KEY) {
			w.pop();
		}
	}
	
	@Override
	public String toString() {
		w.flush();
		return w.o.toString();
	}
}
