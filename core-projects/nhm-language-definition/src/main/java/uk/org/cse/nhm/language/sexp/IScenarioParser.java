package uk.org.cse.nhm.language.sexp;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.language.definition.XElement;

public interface IScenarioParser<X extends XElement> {
	public interface IResult<X extends XElement> {
		public Node getInput();
		public List<IError> getWarnings();
		public List<IError> getErrors();
		public Optional<X> getOutput();
		public boolean hasErrors();
		public boolean hasWarnings();
		Map<String, Object> getDefinitions();
	}
	public Class<X> getOutputClass();
	
	public IResult<X> parse(final IScenarioSnapshot expression);
	public IResult<X> parse(final ISExpression expression);
	public IResult<?> parseUnknown(final IScenarioSnapshot snapshot);
}
