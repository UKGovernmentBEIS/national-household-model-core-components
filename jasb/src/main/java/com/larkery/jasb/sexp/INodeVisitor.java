package com.larkery.jasb.sexp;

public interface INodeVisitor {
	public boolean seq(final Seq seq);
	public void atom(final Atom atom);
	public void comment(final Comment comment);
}
