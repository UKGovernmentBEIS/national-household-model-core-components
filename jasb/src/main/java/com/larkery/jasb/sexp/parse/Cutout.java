package com.larkery.jasb.sexp.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;

public abstract class Cutout<Q extends ISExpressionVisitor> implements ISExpressionVisitor {
	private final Stack<BalancedVisitor> cutouts = new Stack<>();
	
	private Delim afterOpen = null;
	private Location openLocation;
	private Location location;
	private final List<Location> commentLocationsAfterOpen = new ArrayList<>();
	private final List<String> commentsAfterOpen = new ArrayList<>();
	
	protected Cutout(final ISExpressionVisitor delegate) {
		super();
		cutouts.push(new BalancedVisitor(delegate));
	}
	
	static class BalancedVisitor implements ISExpressionVisitor {
		private final ISExpressionVisitor delegate;
		
		BalancedVisitor(final ISExpressionVisitor delegate) {
			super();
			this.delegate = delegate;
		}

		private int depth = 0;
		@Override
		public void locate(final Location loc) {
			delegate.locate(loc);
		}

		@Override
		public void open(final Delim delimeter) {
			depth++;
			delegate.open(delimeter);
		}

		@Override
		public void atom(final String string) {
			delegate.atom(string);
		}

		@Override
		public void comment(final String text) {
			delegate.comment(text);
		}

		@Override
		public void close(final Delim delimeter) {
			delegate.close(delimeter);
			depth--;
		}
		
		@Override
		public String toString() {
			return String.format("BalancedVisitor(%d %s)", depth, delegate);
		}
	}

	protected boolean isAlreadyCuttingOut() {
		return cutouts.size() > 1;
	}
	
	protected abstract Optional<Q> cut(final String head);
	protected abstract void paste(final Q q);
	
	@Override
	public void locate(final Location loc) {
		this.location = loc;
	}

	private void shiftOpen() {
		if (afterOpen != null) {
			final ISExpressionVisitor peek = cutouts.peek();
			peek.locate(openLocation);
			peek.open(afterOpen);
			
			for (int i = 0; i<commentLocationsAfterOpen.size(); i++) {
				peek.locate(commentLocationsAfterOpen.get(i));
				peek.comment(commentsAfterOpen.get(i));
			}
			
			commentLocationsAfterOpen.clear();
			commentsAfterOpen.clear();
			
			afterOpen = null;
		}
	}
	
	@Override
	public void open(final Delim delimeter) {
		shiftOpen();
		afterOpen = delimeter;
		openLocation = location;
	}

	@Override
	public void atom(final String string) {
		if (afterOpen != null) {
			final Optional<Q> cutter = cut(string);
			
			if (cutter.isPresent()) {
				cutouts.push(new BalancedVisitor(cutter.get()));
			}
			
			shiftOpen();
		}
		
		cutouts.peek().locate(location);
		cutouts.peek().atom(string);
	}
	
	@Override
	public void comment(final String text) {
		if (afterOpen != null) {
			commentLocationsAfterOpen.add(location);
			commentsAfterOpen.add(text);
		} else {
			cutouts.peek().locate(location);
			cutouts.peek().comment(text);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void close(final Delim delimeter) {
		shiftOpen();
		cutouts.peek().close(delimeter);
		if (cutouts.peek().depth == 0 && cutouts.size() > 1) {
			final Q cutter = (Q) cutouts.pop().delegate;
			paste(cutter);
		}
	}
}
