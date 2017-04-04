package cse.nhm.ide.ui.editor.outline;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import cse.nhm.ide.ui.editor.ScenarioModel;
import cse.nhm.ide.ui.editor.ScenarioModel.Node;

public class ScenarioOutlineFilter extends ViewerFilter {
	private String text = "";
	private String[] parts;
	
	public void setFilterText(final String text) {
		this.text = text.toLowerCase().trim();
		this.parts = text.split(" ");
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (this.text.isEmpty()) return true;
		
		if (element instanceof ScenarioModel.Node) {
			final ScenarioModel.Node node = (ScenarioModel.Node) element;
			if (matches(node)) return true;
			
			for (final Node c : node.children) {
				if (select(viewer, node, c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean matches(final Node node) {
		for (final String s : this.parts) {
			if (!(node.type.contains(s) || node.name.contains(s))) {
				return false;
			}
		}
		return true;
	}
	
	public ScenarioModel.Node firstMatch(final List<ScenarioModel.Node> nodes) {
		for (final Node node : nodes) {
			if (matches(node)) {
				return node;
			}
		}
		for (final Node node : nodes) {
			final Node n = firstMatch(node.children);
			if (n != null) return n;
		}
		return null;
	}

	public boolean isEmpty() {
		return this.text.isEmpty();
	}
}
