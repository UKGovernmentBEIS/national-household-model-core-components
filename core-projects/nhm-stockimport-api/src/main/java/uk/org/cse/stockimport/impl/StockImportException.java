package uk.org.cse.stockimport.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * StockImportException.
 *
 * @author richardt
 * @version $Id: StockImportException.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StockImportException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<Exception> exceptions;

    /**
     * @since 1.0
     */
    public StockImportException() {
        super();
    }

    /**
     * @since 1.0
     */
    public StockImportException(List<Exception> exceptions) {
        this();
        this.exceptions = exceptions;
    }

    public StockImportException(String string, ImmutableList<Exception> of) {
        super(string);
        this.exceptions = of;
    }

    /**
     * Return the exceptions.
     *
     * @return the exceptions
     * @since 1.0
     */
    public List<Exception> getExceptions() {
        return exceptions;
    }
}
