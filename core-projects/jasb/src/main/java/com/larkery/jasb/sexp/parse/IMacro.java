package com.larkery.jasb.sexp.parse;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;

/**
 * Implementors can be by {@link Expander} to do source transformations on
 * {@link Node}s. The expander will process each invocation it sees; if it sees
 * an invocation which matches a macro registered with it, it will use the
 * transform method to apply the macro. It will not expand the transformed
 * result, so the transform method is offered the expander so it can decide
 * whether to transform the output. This is only useful if you have some kind of
 * weird one-shot macro situation.
 *
 * An invocation matches if it has a matching name and pattern of arguments.
 */
public interface IMacro {

    /**
     * The name which any invocation of this macro must have
     */
    public String getName();

    /**
     * Invoked to rewrite an {@link Invocation}
     */
    public ISExpression transform(final Seq input, final IMacroExpander expander, final IErrorHandler errors);

    public MacroModel getModel();

    public Optional<Node> getDefiningNode();
}
