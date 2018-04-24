package uk.org.cse.nhm.bundle.api;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

/**
 * Something which is like a path, as far as NHM is concerned
 */
public interface IFS<T> {
    // replace IPath with IFS
    // fix main bundle.
    public T deserialize(String Tstring);
    public T resolve(final String from, final String where);
    public Reader open(final T where) throws IOException;
    public Path filesystemPath(final T where);
}
