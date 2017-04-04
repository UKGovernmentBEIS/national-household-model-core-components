package uk.org.cse.nhm.ipc.api.tasks;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;
import com.larkery.jasb.sexp.parse.IDataSource;
import com.larkery.jasb.sexp.parse.IMacro;

@JsonTypeInfo(use=Id.CLASS)
public interface IScenarioSnapshot extends ISExpression, IDataSource<String> {
	ISExpression getUntemplated();
	/**
	 * Try to get a string which is the head of the first
	 * element in the fully expanded form of this scenario
	 * @return
	 */
	public Optional<String> getExpandedFirstElement();
	public List<IError> getProblems();
	public ISExpression withErrorHandler(final IErrorHandler collector);
	public DataSourceSnapshot contents();
	public interface IExpansion {
		public List<IMacro> macros();
		public List<Node> nodes();
		public List<IError> errors();
	}
	
	public IExpansion expand();
}
