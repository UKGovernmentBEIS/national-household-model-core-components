package com.larkery.jasb.sexp.module;

import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;

public class AtomFilter implements ISExpression {
	private final ISExpression delegate;

	public AtomFilter(final ISExpression delegate) {
		this.delegate = delegate;
	}

	public void accept(final ISExpressionVisitor visitor) {
		this.delegate.accept(new ISExpressionVisitor() {
				@Override
				public void locate(final Location loc) {
					visitor.locate(loc);
				}

				@Override
				public void open(final Delim delim) {
					visitor.open(delim);
				}

				@Override
				public void close(final Delim delim) {
					visitor.close(delim);
				}

				@Override
				public void comment(final String text) {
					visitor.comment(text);
				}

				@Override
				public void atom(final String atom) {
					AtomFilter.this.atom(visitor, atom);
				}
			});
	}

	protected void atom(final ISExpressionVisitor visitor, final String atom) {
		visitor.atom(atom);
	}
}
