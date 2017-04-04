package com.larkery.jasb.sexp.parse;

import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class NodeCopyTest extends ParserTest {
	@Override
	protected ISExpression source(final String name, final String src) {
		try {
			return Node.copy(super.source(name, src));
		} catch (final UnfinishedExpressionException e) {
			throw new RuntimeException(e);
		}
	}
}
