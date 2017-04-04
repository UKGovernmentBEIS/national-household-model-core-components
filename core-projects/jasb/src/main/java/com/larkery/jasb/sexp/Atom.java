package com.larkery.jasb.sexp;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoDetectPolicy;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

@AutoProperty(autoDetect=AutoDetectPolicy.NONE)
public class Atom extends Node {
	private static final CharMatcher ESCAPE_PLEASE = CharMatcher.WHITESPACE.or(CharMatcher.anyOf("()[]{},"));
	private final String value;
	private final boolean quoted;

    private static final Interner<String> values =
        Interners.newWeakInterner();
    
	Atom(final Location location, final String value) {
		super(location);
		this.value = values.intern(value);
		this.quoted = ESCAPE_PLEASE.matchesAnyOf(value) || value.isEmpty() ||
				value.substring(0, value.length()-1).contains(":");
	}

	public static String escape(final String s) {
		return new Atom(null, s).toString();
	}
	
	@Property(policy=PojomaticPolicy.HASHCODE_EQUALS)
	public String getValue() {
		return value;
	}
	
	public boolean isQuoted() {
		return quoted;
	}
	
	@Override
	public void accept(final ISExpressionVisitor visitor) {
		super.accept(visitor);
		visitor.atom(value);
	}
	
	@Override
	public String toString() {
		if (quoted) {
			return "\"" + value.replace("\"", "\\\"") + "\"";
		} else {
			return value;
		}
	}
	
	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.atom(this);
	}
	
	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	public static Node create(final String string, final Location location) {
		return new Atom(location, string);
	}
	
	public static Node create(final String string) {
		return new Atom(null,string);
	}
	
	@Override
	protected Node removeComments() {
		return this;
	}
}
