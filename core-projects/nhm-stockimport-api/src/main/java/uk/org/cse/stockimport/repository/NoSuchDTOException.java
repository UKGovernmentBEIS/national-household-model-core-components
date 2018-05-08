package uk.org.cse.stockimport.repository;

import java.util.NoSuchElementException;

public class NoSuchDTOException extends NoSuchElementException {

    private static final long serialVersionUID = 1L;
    private String aacode;
    private Class<?> clazz;

    NoSuchDTOException(final String aacode, final Class<?> clazz) {
        super("No " + clazz.getSimpleName() + " for " + aacode);
        this.aacode = aacode;
        this.clazz = clazz;
    }

    public String getAacode() {
        return aacode;
    }

    public Class<?> getDTOClass() {
        return clazz;
    }
}
