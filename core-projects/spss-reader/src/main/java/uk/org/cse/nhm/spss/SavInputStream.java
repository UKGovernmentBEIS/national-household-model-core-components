package uk.org.cse.nhm.spss;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Confusingly this is not an {@link InputStream}, but a thing from which you
 * can read {@link SavEntry} instances (through the iterator interface it
 * extends).
 *
 * Also provides access to the {@link SavMetadata} for the stream
 *
 * @author hinton
 *
 */
public interface SavInputStream extends Iterator<SavEntry> {

    /**
     * @return The {@link SavMetadata} for this file.
     */
    public SavMetadata getMetadata();
}
