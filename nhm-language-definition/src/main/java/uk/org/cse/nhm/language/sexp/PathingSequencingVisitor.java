package uk.org.cse.nhm.language.sexp;

import java.util.IdentityHashMap;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.visit.SinglyVisitingVisitor;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.INodeVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

import uk.org.cse.commons.names.Path;

public class PathingSequencingVisitor implements INodeVisitor {	
	final IdentityHashMap<Node, XElement> elementsByNode = new IdentityHashMap<>();
	int counter = 0;
	Path currentPath = null;
	
	public static void applyTo(final XElement root) {
		new PathingSequencingVisitor(root);
	}
	
	PathingSequencingVisitor(final XElement root) {
		root.accept(new SinglyVisitingVisitor<XElement>(XElement.class) {
			@Override
			public void visit(final XElement v) {
				if (v.getSourceNode() != null) {
					elementsByNode.put(v.getSourceNode(), v);
				}
			}
		});
		
		root.getSourceNode().accept(this);
	}
	
	private void sequence(final Node n) {
		final XElement e = elementsByNode.get(n);
		if (e != null) {
			e.setSequence(counter++);
			if (currentPath != null) {
				e.setPath(currentPath);
			}
		}
	}
	
	private void pushPath(final String s) {
		currentPath = Path.get(s, currentPath);
	}
	
	private void popPath() {
		if (currentPath != null) {
			currentPath = currentPath.getParent();
		}
	}
	
	@Override
	public void atom(final Atom atom) {
		pushPath(atom.getValue());
		sequence(atom);
		popPath();
	}

	@Override
	public boolean seq(final Seq seq) {
		sequence(seq);
		
		final Optional<Node> n = seq.exceptComments(0);
		if (n.isPresent() && n.get() instanceof Atom) {
			pushPath(((Atom) n.get()).getValue());
		}
		
		for (final Node c : seq) {
			c.accept(this);
		}
		
		if (n.isPresent() && n.get() instanceof Atom) {
			popPath();
		}
		
		return false;
	}
	
	@Override public void comment(final Comment arg0) {}
}
