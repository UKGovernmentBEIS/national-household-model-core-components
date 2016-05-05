package uk.org.cse.nhm.macros;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.JoinMacro;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.UniqueNameMacro;

public class ExtraMacros {
	public static final String T = "~";
	private static final String COMMENT = T + "comment";
	private static final String UNIQUE = T + "unique";
	private static final String ERROR = T + "error";
	private static final String WARNING = T+"warning";
	private static final String MATCH = T + "match";
	private static final String MAYBE = T + "maybe";
	private static final String COMBINATIONS = T + "combinations";
	private static final String JOIN = T + "join";
	private static final String LOOKUP_TABLE = T + "lookup-table";
    private static final String TRANSPOSE = T + "transpose";

	private static class DeprecatedMacro implements IMacro {
		private final String newName;
		private final IMacro delegate;
		
		public DeprecatedMacro(final IMacro delegate, final String newName) {
			super();
			this.delegate = delegate;
			this.newName = newName;
		}

		@Override
		public String getName() {
			return delegate.getName();
		}

		@Override
		public ISExpression transform(final Seq input, final IMacroExpander expander, final IErrorHandler errors) {
			errors.warn(input.getLocation(), "the macro name %s is obsolete, and has been replaced with %s", delegate.getName(), newName);
			return delegate.transform(input, expander, errors);
		}

		@Override
		public MacroModel getModel() {
			return delegate.getModel();
		}
		
		@Override
		public Optional<Node> getDefiningNode() {
			return delegate.getDefiningNode();
		}
	}
	
	public static final IMacro[] DEPRECATED = new IMacro[] {
		new DeprecatedMacro(new LookupTableMacro(),LOOKUP_TABLE),
		new DeprecatedMacro(new JoinMacro(), JOIN),
		new DeprecatedMacro(new CombinationsMacro(), COMBINATIONS),
		new DeprecatedMacro(new OptionalArgumentMacro(), MAYBE),
		new DeprecatedMacro(new MatchMacro(), MATCH),
		new DeprecatedMacro(new ErrorMacro(), ERROR),
	};
	
	
	public static final IMacro[] CURRENT = new IMacro[] {
		new LookupTableMacro(LOOKUP_TABLE),
		new JoinMacro(JOIN),
		new CombinationsMacro(COMBINATIONS),
		new OptionalArgumentMacro(MAYBE),
		new MatchMacro(MATCH),
		new ErrorMacro(ERROR, false),
		new ErrorMacro(WARNING, true),
		new UniqueNameMacro(UNIQUE),
		new CommentMacro(COMMENT),
        new TransposeMacro(TRANSPOSE)
	};
	
	public static final IMacro[] DEFAULT = combine(DEPRECATED, CURRENT);

	private static IMacro[] combine(final IMacro[] a, final IMacro[] b) {
		final IMacro[] result = new IMacro[a.length + b.length];
		for (int i = 0; i<a.length; i++) {
			result[i] = a[i];
		}
		for (int i = 0; i<b.length;i++) {
			result[i+a.length] = b[i];
		}
		return result;
	}
}
