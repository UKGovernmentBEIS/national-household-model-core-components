package cse.nhm.ide.ui.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import cse.nhm.ide.ui.NHMUIPlugin;

class ScenarioDocumentProvider extends FileDocumentProvider {
	/*
	 * This is the thing which chops a document up into segments; there are four kinds of segment that come out
	 *  - comment (text following ; to end of line)
	 *  - doc comment (text following ;;; to end of line)
	 *  - string (text in "quotes")
	 *  - default (everything else)
	 */
	static class PartitionScanner extends RuleBasedPartitionScanner {
		public static final String COMMENT = k("COMMENT");
		public static final String DOC = k("DOC");
		public static final String STR = k("STRING");
		public static final String[] TYPES = {
			COMMENT, DOC, STR
		};
		private static String k(String string) {
			return NHMUIPlugin.PLUGIN_ID + ".partitions." + string;
		}

		public PartitionScanner() {
			setPredicateRules(new IPredicateRule[] {
					new EndOfLineRule(";;;", new Token(DOC)),
					new EndOfLineRule(";", new Token(COMMENT)),
					new MultiLineRule("\"", "\"", new Token(STR), '\\') });
		}
	}
	
  	@Override
  	protected IDocument createDocument(Object element) throws CoreException {
  		final IDocument d = super.createDocument(element);
  		if (d != null) {
  			final IDocumentPartitioner p = new FastPartitioner(new PartitionScanner(), PartitionScanner.TYPES);
  			p.connect(d);
  			d.setDocumentPartitioner(p);
  		}
  		return d;
  	}
}
