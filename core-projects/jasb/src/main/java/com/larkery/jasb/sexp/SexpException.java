package com.larkery.jasb.sexp;

public class SexpException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final Location location;
	private final String message;
	public SexpException(Location location, String message) {
		super();
		this.location = location;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", location, message);
	}
}
