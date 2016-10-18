package uk.org.cse.nhm.macros;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;

public class CommentMacro implements IMacro {
	private final String name;

	public CommentMacro(final String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ISExpression transform(final Seq input, final IMacroExpander expander, final IErrorHandler errors) {
		return SExpressions.empty();
	}
	
	@Override
	public MacroModel getModel() {
		return MacroModel.builder().
				desc("A macro which excises its contents from the scenario.")
				.keys().allow("any value", "all keyword arguments are ignored").and()
				.pos().remainder("all numbered arguments are ignored").and()
				.build();
	}
	
	@Override
	public Optional<Node> getDefiningNode() {
		return Optional.absent();
	}
}
