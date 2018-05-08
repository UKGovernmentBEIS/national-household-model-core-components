package com.larkery.jasb.sexp.errors;

import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

public class UnfinishedExpressionException extends Exception {

    private static final long serialVersionUID = 1L;
    private final Node unclosed;
    private final Node mangled;

    public UnfinishedExpressionException(final Node unclosed, final Node mangled) {
        this.unclosed = unclosed;
        this.mangled = mangled;
    }

    public Node getUnclosed() {
        return unclosed;
    }

    public Node getBestEffort() {
        return mangled;
    }

    public IError getError() {
        return BasicError.at(unclosed, "Not enough closing parentheses");
    }
}
