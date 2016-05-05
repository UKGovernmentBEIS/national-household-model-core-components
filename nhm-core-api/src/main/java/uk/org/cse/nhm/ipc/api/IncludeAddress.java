package uk.org.cse.nhm.ipc.api;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Objects;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public class IncludeAddress {
	private static final String NHM = "nhm";

	private final String name;
	private final String version;
	private final String id;

    @Override
    public boolean equals(final Object other) {
        if (other instanceof IncludeAddress) {
            final IncludeAddress _other = (IncludeAddress) other;
            return
                Objects.equals(name, _other.name) &&
                Objects.equals(version, _other.version) &&
                Objects.equals(id, _other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version, id);
    }

	IncludeAddress(final String name, final String version, final String id) {
		super();
		this.name = name;
		this.version = version;
		this.id = id;
	}
	
	private IncludeAddress(final URI uri) {
		if (uri.getScheme().equals(NHM)) {
			final String path = uri.getPath();
			if (path != null) {
				if (path.startsWith("/name/")) {
					this.name = path.substring("/name/".length());
					this.version = uri.getFragment();
					this.id = null;
					return;
				} else if (path.startsWith("/id/")) {
					this.id = path.substring("/id/".length());
					this.name = null;
					this.version = null;
					return;
				} else {
					this.id = null;
					this.name = null;
					this.version = null;
				}
			} else {
				this.id = null;
				this.name = null;
				this.version = null;
			}
		} else {
			this.id = null;
			this.name = null;
			this.version = null;
		}
	}

    private IncludeAddress(final Seq seq) {
		final Invocation invocation = Invocation.of(seq, IErrorHandler.SLF4J);
		if (invocation != null) {
			final Node name;
			final Node ver;
            final Node id = invocation.arguments.get("id");

            if (invocation.arguments.containsKey("name")) {
				name = invocation.arguments.get("name");					
			} else if (invocation.remainder.size() > 0) {
				name = invocation.remainder.get(0);
			} else {
				name = null;
			}
			
			if (invocation.arguments.containsKey("version")) {
				ver = invocation.arguments.get("version");					
			} else if (invocation.remainder.size() > 1) {
                ver = invocation.remainder.get(1);
            } else if (invocation.arguments.containsKey("name") &&
                       invocation.remainder.size() == 1) {
                ver = invocation.remainder.get(0);
			} else {
				ver = null;
			}
			
			this.name = (name instanceof Atom) ? ((Atom) name).getValue() : null;
			this.version = (ver instanceof Atom) ? ((Atom) ver).getValue() : null;
			this.id = (id instanceof Atom) ? ((Atom) id).getValue() : null;
			return;
		} else {
			this.name = null;
			this.version = null;
			this.id = null;
		}
	}

	public static URI toURI(final Seq seq) {
		return parse(seq).toURI();
	}
	
	public String makeFileName() {
		if (id == null) {
			return String.format("name/%s%s.s", name, version == null ? "" : "-v" + version);
		} else {
			return String.format("id/%s.s", id);
		}
	}
	
	public static Seq toSeq(final URI uri) {
		return parse(uri).toSeq();
	}

	public static URI forID(final String scenarioID) {
		try {
			return new URI(NHM, null, String.format("/id/%s", scenarioID), null, null);
		} catch (final URISyntaxException e) {
			throw new RuntimeException("This should not happen", e);
		}
	}
	
	public static URI forEmpty() {
		try {
			return new URI(NHM, null, "/hypothesis", null, null);
		} catch (URISyntaxException e) {
			throw new RuntimeException("This should not happen", e);
		}
	}
	
	public URI toURI() {
		if (name != null && version != null) {
			try {
				return new URI(NHM, null, String.format("/name/%s", name), null, version.toString());
			} catch (final URISyntaxException e) {
				throw new RuntimeException("This should not happen", e);
			}
		} else if (id != null) {
			return forID(id.toString());
		} else {
			return forEmpty();
		}
	}
	
	public Seq toSeq() {
		return toSeq(null, null);
	}
	
	public Seq toSeq(final Location start, final Location end) {
		if (name != null && version != null) {
			return Seq.builder(start, Delim.Paren)
					.add("include")
					.add("name", name)
					.add("version", version)
					.build(end);
		} else if (id != null) {
			return Seq.builder(start, Delim.Paren)
					.add("include")
					.add("id", id)
					.build(end);
		} else {
			return Seq.builder(start, Delim.Paren)
					.add("include")
					.add("missing")
					.build(end);
		}
	}
	
	public static IncludeAddress parse(final URI uri) {
		return new IncludeAddress(uri);
	}
	
	public static IncludeAddress parse(final Seq seq) {
		return new IncludeAddress(seq);
	}

	public boolean isID() {
		return id != null;
	}

	public boolean isNamedVersion() {
		return name != null && version != null;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getVersion() {
		return Integer.parseInt(version);
	}

	public static IncludeAddress forNameVersion(final String name, final int version) {
		return new IncludeAddress(name, ""+version, null);
	}
	
	public static IncludeAddress addressForID(final String id) {
		return new IncludeAddress(null, null, id);
	}
	
	public static IncludeAddress empty() {
		return new IncludeAddress(null, null, null);
	}
	
	@Override
	public String toString() {
		return toURI().toString();
	}

	public boolean isEmpty() {
		return name == null && version == null && id == null;
	}
}
