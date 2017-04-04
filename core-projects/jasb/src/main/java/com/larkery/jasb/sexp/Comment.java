package com.larkery.jasb.sexp;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoDetectPolicy;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

@AutoProperty(autoDetect=AutoDetectPolicy.NONE)
public class Comment extends Node {
	private final String text;
	Comment(final Location location, final String text) {
		super(location);
		this.text = text;
	}
	@Override
	public String toString() {
		return String.format(" ;; %s\n", text);
	}
	@Override
	public void accept(final ISExpressionVisitor visitor) {
		super.accept(visitor);
		visitor.comment(text);
	}
	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.comment(this);;
	}
	
	@Property(policy=PojomaticPolicy.HASHCODE_EQUALS)
	public String getText() {
		return text;
	}
	
	@Override
	public boolean equals(final Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	public static Comment create(final String string) {
		return new Comment(null, string.replace("\n", " ").replace("\r", " "));
	}
	
	public static Comment create(final Location loc, final String string) {
		return new Comment(loc, string.replace("\n", " ").replace("\r", " "));
	}
	
	@Override
	protected Node removeComments() {
		return null;
	}
}
