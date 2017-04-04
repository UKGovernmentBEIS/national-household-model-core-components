package cse.nhm.ide.ui.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.bundle.api.IArgument;
import uk.org.cse.nhm.bundle.api.ILanguage.ICursor;

import com.google.common.base.Optional;

public class Cursor implements ICursor {
	final String command, left;
	final Argument arg;
	public final Set<String> keysHere;
	Cursor previous;
	private boolean head;
	private String module;
	
	protected Cursor(String command, String left, Argument arg, Set<String> keysHere) {
		this("", command, left, arg, keysHere);
	}
	
	protected Cursor(String module, String command, String left, Argument arg, Set<String> keysHere) {
		super();
		this.module = module;
		this.command = command;
		this.left = left;
		this.arg = arg;
		this.keysHere = keysHere;
	}

	static class Argument implements IArgument {
		final Optional<String> name;
		final Optional<Integer> position;
				
		protected Argument(Optional<String> name, Optional<Integer> position) {
			super();
			this.name = name;
			this.position = position;
		}

		public Argument() {
			this(Optional.<String>absent(), Optional.<Integer>absent());
		}
		
		public Argument(final String s) {
			this(Optional.of(s.substring(0, s.length()-1)), Optional.<Integer>absent());
		}
		
		public Argument(final int p) {
			this(Optional.<String>absent(), Optional.<Integer>of(p));
		}

		@Override
		public Optional<String> name() {
			return name;
		}

		@Override
		public Optional<Integer> position() {
			return position;
		}
		
		public String fmt(final Cursor c) {
			if (name.isPresent()) {
				return c.command +" "+ name.get() + ": " + c.left;
			} else if (position.isPresent()) {
				return c.command +" ["+ position.get() +"] " + c.left;
			} else {
				return c.command;
			}
		}

		public boolean isEmpty() {
			return !(name.isPresent() || position.isPresent());
		}
	}
	
	@Override
	public String command() {
		return command;
	}

	@Override
	public String left() {
		return left;
	}

	@Override
	public IArgument argument() {
		return arg;
	}

	@Override
	public Optional<ICursor> previous() {
		return Optional.<ICursor>fromNullable(previous);
	}
	
	public static Cursor get(final String s, final int offset) {
		try {
			final List<Form> forms = Form.readAll(new StringReader(s), false);
			for (final Form f : forms) {
				if (f.contains(offset) && offset != f.eoffset) {
					return get(f, offset);
				}
			}
		} catch (IOException e) {
		}
		return new Cursor("", "", new Argument(), Collections.<String>emptySet());
	}
	
	public static void main(String[] args) {
		final long l = System.currentTimeMillis();
		for (int i = 1; i<args[0].length()+1; i++) {
			System.out.println(i + " " + Cursor.get(args[0], i));
		}
		System.out.println(System.currentTimeMillis() - l);
	}
	
	@Override
	public String toString() {
		return (previous == null ? "" : 
			(String.valueOf(previous)) + " » ") +
			arg.fmt(this);
	}

	private static Cursor get(final Form f, int offset) {
		if (f instanceof Atom) {
			final Atom a = (Atom) f;
			
			final String left = a.string.substring(0, (int) (offset - a.offset));
			if (left.endsWith(":") && left.equals(a.string)) {
				return new Cursor("", "", new Argument(left), Collections.<String>emptySet());
			} else {
				return new Cursor("", left, new Argument(), Collections.<String>emptySet());
			}
		} else if (f instanceof Expr) {
			final Expr e = (Expr) f;
			if (e.delimiter == '(') {
				// looks like a command
				final Set<String> keys = new HashSet<String>();
				String head = "";
				String moduleName = "";
				int position = 0;
				boolean atHead = true;
				boolean atModuleName = false;
				String key = null;
				long lasteoffset = -1;
				for (final Form c : e.children) {
					if (lasteoffset <= offset && c.offset > offset) {
						// it was in the whitespace before this thing
						final Argument arg = key == null ?
								(atHead ? new Argument() : new Argument(position)) 
								: new Argument(key);
						return new Cursor(head, "", arg, keys);
					}
					
					String nextKey = null;
					if (c instanceof Atom) {
						if (atHead) {
							head = ((Atom) c).string;
							if (head.equals("~module")) {
								// get module name out
								atModuleName = true;
							}
						} else if (atModuleName) {
							moduleName = ((Atom) c).string;
						} else if (((Atom) c).string.endsWith(":")) {
							nextKey = ((Atom) c).string;
							keys.add(nextKey);
						}
					}
					
					if (c.contains(offset) || c.eoffset == offset && c instanceof Atom) {
						final Argument arg = key == null ?
								(atHead ? new Argument() : new Argument(position)) 
								: new Argument(key);
						final Cursor here = new Cursor(moduleName, head, "", arg, keys);
						final Cursor child = get(c, offset);
						return Cursor.coalesce(here, child);
					} else {
						lasteoffset = c.eoffset;
					}
					
					if (!atHead && key == null && nextKey == null) {
						position++;
					}
					
					atHead = false;
					key = nextKey;
				}
				final Argument arg = key == null ? (atHead ? new Argument() : new Argument(position)) : new Argument(key);
				final Cursor result = new Cursor(head,"", arg, keys);
				result.head = true;
				return result;
			} else {
				for (final Form c : e.children) {
					if (c.contains(offset) || c.eoffset == offset && c instanceof Atom) {
						return get(c, offset);
					}
				}
			}
		}
		return new Cursor("", "", new Argument(), Collections.<String>emptySet());
	}

	private static Cursor coalesce(final Cursor here, final Cursor child) {
		if (child.command.isEmpty() && child.arg.isEmpty() && !child.head) {
			final Cursor here2 = new Cursor(here.command, child.left, here.arg, here.keysHere);
			here2.previous = here.previous;
			return here2;
		} else if (child.arg.name.isPresent() && child.command.isEmpty() && child.left.isEmpty()) {
			final Cursor here2 = new Cursor(here.command, here.left, child.arg, here.keysHere);
			here2.previous = here.previous;
			return here2;
		} else  if (!child.isEmpty() || child.head) {
			Cursor child_ = child;
			while (child_.previous != null) {
				child_ = child_.previous;
			}
			child_.previous = here;
			return child;
		}
		return here;
	}

	private boolean isEmpty() {
		return command.isEmpty() && left.isEmpty() && arg.isEmpty();
	}
	
	public Optional<String> module() {
		if (module.isEmpty()) {
			if (previous == null) {
				return Optional.absent();
			} else {
				return previous.module();
			}
		} else {
			return Optional.of(module);
		}
	}
}
