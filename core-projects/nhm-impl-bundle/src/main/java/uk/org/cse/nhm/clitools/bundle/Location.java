package uk.org.cse.nhm.clitools.bundle;

import uk.org.cse.nhm.bundle.api.ILocation;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Location.Via;

public class Location<P> implements ILocation<P> {
	private P path;
	private com.larkery.jasb.sexp.Location loc;
    private final int length;

	public Location(final P path, com.larkery.jasb.sexp.Location loc, int length) {
		this.path = path;
		this.loc = loc;
        this.length = length;
	}

    public Location(final P path, com.larkery.jasb.sexp.Location loc) {
		this(path, loc, 0);
	}

	public Location(P input) {
		this(input, 
             new com.larkery.jasb.sexp.Location(String.valueOf(input), 1, 1, 0, Optional.<Via>absent())
				);
	}

	@Override
	public P path() {
		return path;
	}

	@Override
	public int line() {
		return loc.line;
	}

	@Override
	public int column() {
		return loc.column;
	}

	@Override
	public int offset() {
		return (int) loc.offset;
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public LocationType type() {
		switch (loc.getType()) {
		case Include:return LocationType.Include;
		case Template:return LocationType.Template;
		case Normal:
		default:return LocationType.Source;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", path, loc.line);
	}
}
