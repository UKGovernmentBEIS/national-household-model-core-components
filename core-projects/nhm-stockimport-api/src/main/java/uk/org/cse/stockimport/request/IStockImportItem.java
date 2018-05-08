package uk.org.cse.stockimport.request;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * IStockImportItem.
 *
 * @author richardt
 * @version $Id: IStockImportItem.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public interface IStockImportItem {

    /**
     * @since 1.0
     */
    String getFileName();

    /**
     * @since 1.0
     * @return The part of the filename which meaningfully distinguishes this
     * import item from the other items being imported. That is, without the
     * directory in which they all live.
     */
    String getShortFileName();

    /**
     * @since 1.0
     */
    void setFileName(String fileName);

    /**
     * @since 1.0
     */
    String getClassName();

    /**
     * @return the filename with system-specific path separators
     * @since 1.0
     */
    String getSystemFilename();

    InputStream getInputStream() throws FileNotFoundException;

    void setSimpleFileName(String fileName);
}
