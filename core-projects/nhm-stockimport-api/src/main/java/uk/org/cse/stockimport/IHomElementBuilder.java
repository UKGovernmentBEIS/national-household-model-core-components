package uk.org.cse.stockimport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * IHomElementBuilder.
 *
 * @author richardt
 * @version $Id: IHomElementBuilder.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public interface IHomElementBuilder<T> {

    /**
     * @since 1.0
     */
    String getBuiltClassName();

    void writeChunk(OutputStream outputStream, Collection<T> elements, boolean includeHeaders) throws IOException;

    String getFileExtension();

    String getFileName();
}
