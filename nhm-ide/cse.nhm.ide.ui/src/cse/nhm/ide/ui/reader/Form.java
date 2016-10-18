package cse.nhm.ide.ui.reader;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.ImmutableList;

public abstract class Form {
	public final long offset, eoffset;
	
	public Expr up;
	public Form prev, next;
	
	Form(final long offset, final long eoffset) {
		super();
		this.offset = offset;
		this.eoffset = eoffset;
	}
	
	static class FormReader extends PushbackReader {
		public final List<Long> errorOffsets;
		public long offset = -1;
		public long eoffset;
		private final boolean dieOfSyntaxErrors;
		
		public FormReader(final Reader in, final boolean dieOfSyntaxErrors, final List<Long> offsets) {
			super(in);
			this.dieOfSyntaxErrors = dieOfSyntaxErrors;
			this.errorOffsets = offsets;
		}
		
		@Override
		public int read() throws IOException {
			final int result = super.read();
			if (result != -1) this.offset++;
			return result;
		}
		
		@Override
		public void unread(final int c) throws IOException {
			super.unread(c);
			this.offset--;
		}
		
		void die(final long offset, final String s, final Object... rest) throws IOException {
			final String message = String.format(s, rest);
			if (this.dieOfSyntaxErrors) {
				throw new IOException(message);
			} else if (errorOffsets != null) {
				errorOffsets.add(offset);
			}
		}
		
		public Form readForm() throws IOException {
	        skipWhitespace();
	        final int j = read();
	        switch (j) {
	        case -1:
	            return null;
	        case '(':
	        case '[':
	        case '{':
	            return readExpr((char) j);
	        case ')':
	        case ']':
	        case '}':
	            die(offset, "Unexpected %s", (char) j);
	        case '"':
	            return readString(true);
	        case ';':
	            skipLine();
	            return readForm();
	        default:
	            unread(j);
	            return readString(false);
	        }
	    }

	    private Atom readString(final boolean quote) throws IOException {
	        final StringBuffer buffer = new StringBuffer();
	        final long offset = quote ? this.offset : this.offset + 1;
	        boolean escape = false;
	        while (true) {
        		final int j_ = read();
        	
	            if (j_ == -1) break;
	            
	            final char j = (char) j_;
	            if (quote) {
	                if (escape || j != '"') {
	                    buffer.append(j);
	                    escape = false;
	                } else {
	                    // finished, consume quote
	                    break;
	                }
	            } else {
	                if (atomCharacter(j)) {
	                    buffer.append(j);
	                } else {
	                    if (j == ':') {
	                        buffer.append(j);
	                    } else {
	                        unread(j);
	                    }
	                    
	                    break;
	                }
	            }
	        }

	        return new Atom(offset, quote, buffer.toString());
	    }

	    private static boolean atomCharacter(final char c) {
	        return "()[]{}: \r\n;".indexOf(c) == -1;
	    }

	    private Expr readExpr(final char delim) throws IOException {
	        final LinkedList<Form> children = new LinkedList<>();
	        final long offset = this.offset;
	        while (true) {
	            skipWhitespace();
	            final int j = read();
	            if (j == -1) {
	                // this is an error condition
	                die(offset, "Unclosed %s at end of input", delim);
	                return new Expr(offset, this.offset, delim, children.toArray(new Form[children.size()]));
	            }

	            if (isClosing((char) j)) {
	                if (j == opposite(delim)) {
	                    return new Expr(offset, this.offset, delim, children.toArray(new Form[children.size()]));
	                } else {
	                    die(this.offset, "Mismatched %s with %s", delim, (char) j);
	                    return new Expr(offset, this.offset, delim, children.toArray(new Form[children.size()]));
	                }
	            } else {
	                unread(j);
	                children.add(readForm());
	            }
	        }
	    }

	    static boolean isClosing(final char in) {
	        return in == ')' || in == '}' || in == ']';
	    }
	    
	    
	    
	    private void skipWhitespace() throws IOException {
	        int j;
	        while ((j = read()) != -1) {
	        	if (j == ';') {
	        		skipLine();
	        	} else if (!Character.isWhitespace(j)) {
	                unread(j);
	                return;
	            }
	        }
	    }

	    private void skipLine() throws IOException {
	        int j;
	        while ((j = read()) != -1 && (j != '\n') && (j != '\r'));
	    }
	}
	
	public static boolean isOpening(final char in) {
		switch (in) {
        case '(':
        case '{':
        case '[':
        return true;
        }
		return false;
	}
	
	public static boolean isClosing(final char in) {
		switch (in) {
        case ')':
        case '}':
        case ']':
        return true;
        }
		return false;
	}
	
	public static boolean isStructural(final char in) {
		switch (in) {
        case '(':
        case '{':
        case '[':
        case ')':
        case '}':
        case ']':
        return true;
        }
		return false;
	}
	
	public static char opposite(final char in) {
        switch (in) {
        case '(': return ')';
        case '{': return '}';
        case '[': return ']';
        case ')': return '(';
        case '}': return '{';
        case ']': return '[';
        default: return in;
        }
    }

	public static List<Form> readAll(final StringReader stringReader, final List<Long> errs) throws IOException {
		try (final FormReader fr = new FormReader(stringReader, false, errs)) {
			Form out = null;
			final ImmutableList.Builder<Form> result = ImmutableList.builder();
			while ((out = fr.readForm()) != null) {
				result.add(out);
			}
			return result.build();
		}
	}
	
	public static List<Form> readAll(final StringReader stringReader, final boolean die) throws IOException {
		try (final FormReader fr = new FormReader(stringReader, die, null)) {
			Form out = null;
			final ImmutableList.Builder<Form> result = ImmutableList.builder();
			while ((out = fr.readForm()) != null) {
				result.add(out);
			}
			return result.build();
		}
	}

	public boolean contains(int offset2) {
		offset2--;
		return this.offset <= offset2 && this.eoffset > offset2;
	}
	
	public abstract Form findContainer(final int offset);

	public static Expr readAllAsOne(final StringReader stringReader, final boolean b) throws IOException {
		try (final FormReader fr = new FormReader(stringReader, b, null)) {
			Form out = null;
			final LinkedList<Form> result = new LinkedList<>();
			while ((out = fr.readForm()) != null) {
				result.add(out);
			}
			return new Expr(0, 
					fr.offset
					, '!', result.toArray(new Form[result.size()]));
		}
	}

	public static boolean isBalanced(final String string) {
		// oh the memory.
		final Stack<Character> brackets = new Stack<>();
		for (int i = 0; i<string.length(); i++) {
			final char c = string.charAt(i);
			switch (c) {
			case '(':
			case '{':
			case '[':
				brackets.push(c);
				break;
			case ']':
			case '}':
			case ')':
				if (brackets.isEmpty()) return false;
				if (opposite(brackets.pop()) != c) {
					return false;
				}
				break;
			case ';':
				while (i < string.length() && string.charAt(i) != '\n') {
					i++;
				}
				break;
			}
		}
		return brackets.isEmpty();
	}

	public abstract boolean isEmpty();
}


