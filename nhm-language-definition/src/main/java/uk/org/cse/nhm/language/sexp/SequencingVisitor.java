package uk.org.cse.nhm.language.sexp;

import java.util.IdentityHashMap;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.visit.SinglyVisitingVisitor;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.INodeVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

public class SequencingVisitor extends SinglyVisitingVisitor<XElement> implements INodeVisitor {
	public SequencingVisitor(final Node node) {
		super(XElement.class);
		node.accept(this);
	}

	private final IdentityHashMap<Node, Integer> sequence = new IdentityHashMap<>();
	
	int count = 0;
	
	private void put(final Node node) {
		sequence.put(node, count++);
	}
	
	@Override
	public void atom(final Atom arg0) {
		put(arg0);
	}

	@Override
	public void comment(final Comment arg0) {
		put(arg0);
	}

	@Override
	public boolean seq(final Seq arg0) {
		put(arg0);
		return true;
	}
	
	@Override
	public void visit(final XElement v) {
		final Integer i = sequence.get(v.getSourceNode());
		if (i != null) v.setSequence(i);
	}
}
