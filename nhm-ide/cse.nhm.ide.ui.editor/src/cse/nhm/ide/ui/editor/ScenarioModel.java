package cse.nhm.ide.ui.editor;

import java.util.LinkedList;
import java.util.List;

import cse.nhm.ide.ui.reader.Atom;
import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;


public class ScenarioModel {
	public static class Node {
		public final String type;
		public final String name;
		public final long position;
		public final List<Node> children;
		
		public Node(final String type, final String name, final long position) {
			super();
			this.type = type;
			this.name = name;
			this.position = position;
			this.children = new LinkedList<Node>();
		}
		
		@Override
		public String toString() {
			return String.format("%s[%s]%d %s", type, name, position, children);
		}
	}

	final List<Node> nodes;
	final List<Long> unclosed;
	final List<Long> unopened;
	
	public ScenarioModel(final List<Form> forms, final List<Long> unclosed, final List<Long> unopened) {
		this.unclosed = unclosed;
		this.unopened = unopened;
		this.nodes = create(forms);
	}
	
	@Override
	public String toString() {
		return nodes.toString();
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	static List<Node> create(final List<Form> forms) {
		final List<Node> out = new LinkedList<Node>();
		
		for (final Form f : forms) {
			if (f instanceof Expr) {
				create(out, ((Expr) f));
			}
		}
		
		return out;
	}
	
	static void create(final List<Node> out, final Expr expr) {
		if (expr.delimiter == '(' && expr.children.length > 0 && expr.children[0] instanceof Atom) {
			final String type = ((Atom) expr.children[0]).string;
			final String name = nameOf(type, expr);
			final Node node = new Node(type, name, expr.offset);
			out.add(node);
			for (final Form f : expr.children) {
				if (f instanceof Expr) {
					create(node.children, ((Expr) f));
				}
			}
		} else {
			for (final Form f : expr.children) {
				if (f instanceof Expr) {
					create(out, ((Expr) f));
				}
			}
		}
	}

	private static String nameOf(final String type, final Expr expr) {
		switch (type) {
		case "template":
		case "~module":
		case "def":
		case "def-action":
		case "def-function":
		case "def-test":
		case "include":
		case "include-modules":
		if (expr.children.length > 1 && expr.children[1] instanceof Atom) {
			return ((Atom)expr.children[1]).string;
		}
		break;
		default:
			boolean afterNameKey = false;
			for (final Form f : expr.children) {
				if (f instanceof Atom && ((Atom)f).string.equals("name:")) {
					afterNameKey = true;
				} else if (afterNameKey) {
					if (f instanceof Atom) {
						return ((Atom) f).string;
					}
					break;
				}
			}
		}
		
		return "";
	}
}
