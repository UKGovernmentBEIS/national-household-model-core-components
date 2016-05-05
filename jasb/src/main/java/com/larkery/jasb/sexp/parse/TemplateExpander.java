package com.larkery.jasb.sexp.parse;

import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.template.Templates;

public class TemplateExpander {
	public static ISExpression expand(final ISExpression source, final IErrorHandler errors) {
		return Templates.expand(source, errors);
	}
}
