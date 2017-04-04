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

public class ErrorMacro implements IMacro {
	private final String name;
	private final boolean isWarning;

	public ErrorMacro(final String name, final boolean isWarning) {
		this.name = name;
		this.isWarning = isWarning;
	}
	
	public ErrorMacro() {
		this("macro.error", false);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ISExpression transform(final Seq input, final IMacroExpander expander, final IErrorHandler errors) {
		if (isWarning) {
			return SExpressions.warning(input, String.valueOf(input).replace("%", "%%"), errors);
		} else {
			return SExpressions.error(input, String.valueOf(input).replace("%", "%%"), errors);
		}
	}
	
	@Override
	public MacroModel getModel() {
		return MacroModel.builder()
				.desc(String.format("A special macro which produces %s if it appears in the final, expanded output.", isWarning ? "a warning" : "an error"))
				.pos().remainder("Any arguments are emitted in the error message").and()
				.build();
	}
	
	@Override
	public Optional<Node> getDefiningNode() {
		return Optional.absent();
	}
}
