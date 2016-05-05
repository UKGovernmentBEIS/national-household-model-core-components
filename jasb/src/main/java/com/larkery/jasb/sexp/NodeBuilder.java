package com.larkery.jasb.sexp;

import java.util.List;
import java.util.Stack;

import com.larkery.jasb.sexp.Seq.Builder;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class NodeBuilder implements ISExpressionVisitor {
	static int howmany = 0;
	final int me = howmany++;
	private Location here;
	private final Stack<Seq.Builder> inprogress = new Stack<>();
	private final Builder top;
	private Node lastNode;
	private final boolean includeComments;
	
	protected NodeBuilder(final boolean includeComments) {
		this.includeComments = includeComments;
		top = Seq.builder(null, Delim.Paren);
		inprogress.push(top);
	}
	
	public static NodeBuilder create() {
		return new NodeBuilder(true);
	}
	
	public static NodeBuilder withoutComments() {
		return new NodeBuilder(false);
	}
	
	@Override
	public void open(final Delim delimeter) {
		inprogress.add(Seq.builder(here, delimeter));
	}
	
	@Override
	public void locate(final Location loc) {
		here = loc;
	}
	
	@Override
	public void close(final Delim delimeter) {
		final Seq seq = inprogress.pop().build(here);
		push(seq);
	}

	private void push(final Node seq) {
		if (inprogress.isEmpty()) {
			throw new JasbErrorException(BasicError.at(seq, "Too many closing parentheses or brackets"));
		}
		inprogress.peek().add(seq);
		lastNode = seq;
	}
	
	@Override
	public void atom(final String string) {
		push(new Atom(here, string));
	}
	
	@Override
	public void comment(final String text) {
		if (includeComments) {
			push(new Comment(here, text));
		}
	}
	
	public ISExpression getOrEmpty() throws UnfinishedExpressionException {
		if (inprogress.size() > 1) {
			Node badNode = null;
			while (inprogress.size() > 1){
				close(Delim.Paren);
				if (badNode == null) badNode = getLastNode();
			}
			final Seq build = top.build(null);
			throw new UnfinishedExpressionException(badNode, build.isEmpty() ? build : build.getHead());
		}
		final Seq build = top.build(null);
		if (build.isEmpty()) {
			return SExpressions.empty();
		}
		return build.getHead();
	}
	
	public Node get() throws UnfinishedExpressionException {
		if (inprogress.size() > 1) {
			Node badNode = null;
			while (inprogress.size() > 1){
				close(Delim.Paren);
				if (badNode == null) badNode = getLastNode();
			}
			final Seq build = top.build(null);
			throw new UnfinishedExpressionException(badNode, build.isEmpty() ? build : build.getHead());
		}
		final Seq build = top.build(null);
		if (build.isEmpty()) {
			throw new UnfinishedExpressionException(build, build);
		}
		return build.getHead();
	}

	public List<Node> getAll() throws UnfinishedExpressionException {
		if (inprogress.size() > 1) {
			Node badNode = null;
			while (inprogress.size() > 1){
				close(Delim.Paren);
				if (badNode == null) badNode = getLastNode();
			}
			final Seq build = top.build(null);
			throw new UnfinishedExpressionException(badNode, build.isEmpty() ? build : build.getHead());
		}
		final Seq build = top.build(null);
		return build.getNodes();
	}
	
	public Node getBestEffort() {
		try {
			return get();
		} catch (final UnfinishedExpressionException e) {
			return e.getBestEffort();
		}
	}

	/**
	 * @return the last node created by an event this saw
	 */
	public Node getLastNode() {
		return lastNode;
	}

	
}
