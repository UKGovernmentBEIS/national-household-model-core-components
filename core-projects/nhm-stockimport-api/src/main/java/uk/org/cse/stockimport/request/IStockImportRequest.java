package uk.org.cse.stockimport.request;

import java.util.List;


/**
 * I_ImportRequest.
 *
 * @author richardt
 * @version $Id: I_ImportRequest.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0
 */
public interface IStockImportRequest {
    /**
     * @since 1.0
     */
    String getExecutionId();
    /**
     * Returns a COPY of the import items within this request.
     * @since 1.0
     */
    List<IStockImportItem> getExplodedImportItems();
    
    /**
     * @since 1.5.0
     */
    String getUserName();
    
    /**
     * @since 1.5.0
     * @param userName The user who made the request.
     */
    void setUserName(String userName);
    
    /**
     * @since 3.5.0
     * @return A user description of the request.
     */
    String getUserDescription();
    
    /**
     * @since 3.5.0
     * @param userDescription A user description of the request.
     */
    void setUserDescription(String userDescription);
    
    /**
     * Removes the given import item from the current list of import items.
     * @param importItem
     * @since 3.0
     */
    void removeImportItem(IStockImportItem importItem);
}
