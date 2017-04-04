package com.larkery.jasb.sexp.parse;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.Seq;

/**
 * A base class for things which do edits; it is like {@link Cutout} but easier to use
 * because it is less recursively weird.
 * 
 * @author hinton
 *
 */
public abstract class Editor implements ISExpressionVisitor {
	protected enum Action {
		/**
		 * Just pass this stuff on
		 */
		Pass,
		/**
		 * Like pass, but where we also don't look at what is inside
		 */
		Ignore,
		/**
		 * Cut this stuff out and throw it away
		 */
		Remove,
		/**
		 * Cut this stuff out, edit it using the edit() method, and then
		 * apply the editor to the result
		 */
		RecursiveEdit,
		/**
		 * Cut this stuff out, edit it with edit(), and then visit the result
		 * without applying more edits.
		 */
		SingleEdit
	}
	
	private int editDepth = 0;
	private boolean afterOpen = false;
	private Action action = Action.Pass;
	
	private Location currentLocation, openLocation;
	private final List<Location> commentLocationBuffer = new LinkedList<>();
	private final List<String> commentBuffer = new LinkedList<>();
	
	private final ISExpressionVisitor delegate;
	private ISExpressionVisitor activeDelegate;
	
	protected Editor(final ISExpressionVisitor delegate) {
		super();
		this.delegate = delegate;
		this.activeDelegate = delegate;
	}

	protected abstract Action act(final String name);
	protected abstract ISExpression edit(final Seq cut);
	
	protected boolean afterOpen() {
		return afterOpen;
	}

	protected boolean editing() {
		return action != Action.Pass;
	}
	
	@Override
	public void locate(final Location loc) {
		currentLocation = loc;
		if (editing()) {
			activeDelegate.locate(loc);
		}
	}
	
	private void shiftOpen() {
		if (afterOpen) {
			activeDelegate.locate(openLocation);
			activeDelegate.open(Delim.Paren);

			final Iterator<Location> loc = commentLocationBuffer.iterator();
			final Iterator<String> com = commentBuffer.iterator();
			while (loc.hasNext() && com.hasNext()) {
				activeDelegate.locate(loc.next());
				activeDelegate.comment(com.next());
				loc.remove();
				com.remove();
			}
			
			afterOpen = false;
		}
	}

	@Override
	public void open(final Delim delimeter) {
		if (editing()) {
			activeDelegate.open(delimeter);
			editDepth++;
		} else {
			shiftOpen();
			
			if (delimeter == Delim.Paren) {
				afterOpen = true;
				openLocation = currentLocation;
			} else {
				afterOpen = false;				
				activeDelegate.locate(currentLocation);
				activeDelegate.open(delimeter);
			}
		}
	}

	@Override
	public void atom(final String string) {
		if (!editing() && afterOpen) {
			final Action act = act(string);
			this.action = act;
			switch (act) {
			case RecursiveEdit:
			case SingleEdit:
				editDepth = 1;
				activeDelegate = NodeBuilder.create();
				break;
			case Ignore:
				editDepth = 1;
			case Pass:
				activeDelegate = delegate;
				break;
			case Remove:
				editDepth = 1;
				activeDelegate = ISExpressionVisitor.IGNORE;
				break;
			default:
				throw new NoSuchElementException("The action enum should not contain this value!");
			}
			
			shiftOpen();
		}
		
		activeDelegate.locate(currentLocation);
		activeDelegate.atom(string);
	}

	@Override
	public void comment(final String text) {
		if (editing()) {
			activeDelegate.comment(text);
		} else if (afterOpen) {
			commentLocationBuffer.add(currentLocation);
			commentBuffer.add(text);
		} else {
			activeDelegate.locate(currentLocation);
			activeDelegate.comment(text);
		}
	}

	@Override
	public void close(final Delim delimeter) {
		if (!editing()) {
			shiftOpen();
		}
		activeDelegate.locate(currentLocation);
		activeDelegate.close(delimeter);

		if (editing()) {
			editDepth--;
			if (editDepth == 0) {
				final Action old = this.action;
				this.action = Action.Pass;
				final ISExpressionVisitor oldDelegate = activeDelegate;
				activeDelegate = delegate;
				
				if (oldDelegate instanceof NodeBuilder && oldDelegate != delegate) {
					final NodeBuilder nb = (NodeBuilder) oldDelegate;
					final Node node = nb.getBestEffort();
					if (old == Action.RecursiveEdit) {
						edit((Seq) node).accept(this);
					} else if (old == Action.SingleEdit) {
						edit((Seq) node).accept(delegate);
					}
				}
			}
		}
	}
}
