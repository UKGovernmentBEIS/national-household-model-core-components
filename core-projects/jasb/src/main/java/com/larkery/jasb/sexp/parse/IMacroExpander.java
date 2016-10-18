package com.larkery.jasb.sexp.parse;

import com.larkery.jasb.sexp.ISExpression;

public interface IMacroExpander {

	ISExpression expand(final ISExpression input);

	ISExpression expandContents(final ISExpression transformed);

}
