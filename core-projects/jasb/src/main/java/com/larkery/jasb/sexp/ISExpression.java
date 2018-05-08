package com.larkery.jasb.sexp;

public interface ISExpression {

    public void accept(final ISExpressionVisitor visitor);
}
