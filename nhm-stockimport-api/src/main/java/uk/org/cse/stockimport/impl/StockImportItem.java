package uk.org.cse.stockimport.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.org.cse.stockimport.request.IStockImportItem;
import uk.org.cse.stockimport.util.Path;

/**
 * StockImportItem.
 * 
 * @author richardt
 * @version $Id: StockImportItem.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@XmlRootElement(name = "importItem")
public class StockImportItem implements IStockImportItem {
    private String fileName;
    private String className;
    private String shortFileName;

    /**
     * @since 1.0
     */
    public StockImportItem() {
        super();
    }

    /**
     * @since 1.0
     */
    public StockImportItem(String fileName, String className) {
        super();
        this.fileName = fileName;
        this.className = className;
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.request.IStockImportItem#getFileName()
     */
    @Override
    @XmlElement
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getSystemFilename() {
        return getFileName().replace("/", Path.SEPARATOR);
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.request.IStockImportItem#getClassName()
     */
    @Override
    @XmlElement
    public String getClassName() {
        return className;
    }

    /**
     * Set the fileName.
     * 
     * @param fileName the fileName
     * @since 1.0
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Set the className.
     * 
     * @param className the className
     * @since 1.0
     */
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getShortFileName() {
        return shortFileName;
    }

    @Override
    public void setSimpleFileName(String shortFileName) {
        this.shortFileName = shortFileName;

    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(getFileName());
    }
}
