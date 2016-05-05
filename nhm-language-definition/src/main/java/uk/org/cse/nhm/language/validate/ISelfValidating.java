package uk.org.cse.nhm.language.validate;

import java.util.Deque;
import java.util.List;

import uk.org.cse.nhm.language.definition.XElement;

import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

public interface ISelfValidating {
	public List<IError> validate(final Deque<XElement> context);
}
