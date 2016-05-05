package com.larkery.jasb.sexp;

import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.larkery.jasb.sexp.errors.ILocated;

/**
 * Represents a location for an event in an s-expression event stream,
 * or a node in an actual expression. Since an event may come from an
 * include, or a template expansion, the location can be in several
 * places.
 */
public class Location implements ILocated, Iterable<Location> {
	public static final Location NOWHERE = 
        new Location("nowhere", 1, 1, 0, Optional.<Via>absent());
	
	public static class Via {
		public enum Type {
			Normal,
			Include,
			Template
		}
		
		@JsonProperty public final Type type;
		@JsonProperty public final Location location;
		@JsonCreator
		public Via(@JsonProperty("type") final Type type, @JsonProperty("location") final Location location) {
			super();
			this.type = type;
			this.location = location;
		}
	}
	
	@JsonProperty public final String name;
	@JsonProperty public final int line;
	@JsonProperty public final int column;
    @JsonProperty public final long offset;
	@JsonProperty public final Optional<Via> via;
	@JsonIgnore public final Location sourceLocation;
	
	@JsonCreator
	public Location(
			@JsonProperty("name") final String name, 
			@JsonProperty("line") final int line, 
			@JsonProperty("column") final int column,
            @JsonProperty("offset") final long offset, 
			@JsonProperty("via") final Optional<Via> via) {
		super();
		this.name = name;
		this.line = line;
		this.column = column;
		this.via = via;
        this.offset = offset;
		if (this.via.isPresent()) {
			this.sourceLocation = this.via.get().location.sourceLocation;
		} else {
			this.sourceLocation = this;
		}
	}

	@JsonIgnore
	@Override
	public Location getLocation() {
		return this;
	}

	// public static Location of(final URI uri, final int line2, final int column2, final Via.Type via, final Location sourceLocation) {
	// 	return new Location(uri, line2, column2, Optional.of(new Via(via, sourceLocation)));
	// }

    public static Location of(final String uri, final int line, final int col, final long offset, final Optional<Via> via) {
        return new Location(uri, line, col, offset, via);
    }
    
	public static Location of(final String uri, final int line2, final int column2, final long offset) {
		return new Location(uri, line2, column2, offset, Optional.<Via>absent());
	}

	// public Location via(final Via.Type type, final Location baseLocation) {
	// 	return of(name, line, column, type, baseLocation);
	// }

    public Location via(final Optional<Via> v) {
        return of(name, line, column, offset, v);
    }
    
	@Override
	public String toString() {
		if (via.isPresent()) {
			return String.format("%s:%s:%s (from %s %s)", name, line, column, via.get().type, via.get().location);
		} else {
			return String.format("%s:%s:%s", name, line, column);
		}
	}

	@JsonIgnore
	public Via.Type getType() {
		if (via.isPresent()) {
			return via.get().type;
		} else {
			return Via.Type.Normal;
		}
	}
	
	@JsonIgnore
	@Override
	public Iterator<Location> iterator() {
		return new Iterator<Location>() {
			Location loc = Location.this;
			
			@Override
			public boolean hasNext() {
				return loc != null;
			}

			@Override
			public Location next() {
				final Location out = loc;
				loc = out.via.isPresent() ? out.via.get().location : null;
				return out;
			}

			@Override
			public void remove() {throw new UnsupportedOperationException();}
		};
	}
}
