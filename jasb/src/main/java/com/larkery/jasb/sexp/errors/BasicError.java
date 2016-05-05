package com.larkery.jasb.sexp.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

@JsonTypeInfo(use=Id.CLASS)
public class BasicError implements IError {
	private final Location location;
	private final String message;
	private final Type type;
	
	@JsonCreator
	public BasicError(
			@JsonProperty("location") final Location location, 
			@JsonProperty("message") final String message,
			@JsonProperty("type") final Type type) {
		super();
		this.location = location == null ? Location.NOWHERE : location;
		this.message = message;
		this.type = type;
	}
	
	@JsonProperty
	@Override
	public Location getLocation() {
		return location;
	}

	@JsonProperty
	@Override
	public String getMessage() {
		return message;
	}

	@JsonProperty
	@Override
	public Type getType() {
		return type;
	}

	public static IError at(final Node node, final String string) {
		return new BasicError(node.getLocation(), string, Type.ERROR);
	}

	public static IError nowhere(final String message) {
		return new BasicError(Location.NOWHERE, message, Type.ERROR);
	}

	public static IError at(final Location location, final String message) {
		return new BasicError(location, message, Type.ERROR);
	}
	
	@Override
	public String toString() {
		return location + " " + getMessage();
	}

	public static IError warningAt(final Location location, final String format) {
		return new BasicError(location, format, Type.WARNING);
	}
}
