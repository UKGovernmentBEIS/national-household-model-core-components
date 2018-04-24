package uk.org.cse.nhm.language.validate;

import java.util.Deque;
import java.util.List;

import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.XElement;

public interface ISelfValidating {
	public List<IError> validate(final Deque<XElement> context);
}
