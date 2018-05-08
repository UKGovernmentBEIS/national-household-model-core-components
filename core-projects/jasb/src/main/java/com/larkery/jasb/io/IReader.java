package com.larkery.jasb.io;

import java.util.Map;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public interface IReader {

    public interface IResult<T> {

        public Optional<T> getValue();

        public Node getNode();

        public Map<String, Object> getCrossReferences();
    }

    public abstract <T> IResult<T> read(Class<T> output, ISExpression input,
            IErrorHandler errors);

    /**
     * Convert a Node into an instance of the given class, if possible
     *
     * @param output the type to try and make
     * @param input the node to read; this should not have comments in it (see
     * {@link Node#copyStructure(ISExpression)})
     * @param errors an error handle to put errors into
     * @return the value, if read did not fail
     */
    public abstract <T> Optional<T> readNode(Class<T> output, Node input,
            IErrorHandler errors);

}
