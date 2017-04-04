package uk.org.cse.stockimport.ehcs2010.spss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import uk.org.cse.stockimport.domain.metadata.impl.EHSStockImportSource;
import uk.org.cse.stockimport.impl.StockImportItem;
import uk.org.cse.stockimport.request.IStockImportItem;
import uk.org.cse.stockimport.request.IStockImportRequest;

/**
 * SpssImportRequest. Used for the import from survey SPSS files to DTOs.
 *
 * @author richardt
 * @version $Id: SpssImportRequest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@XmlRootElement(name = "importRequest")
public class SpssImportStockRequest implements IStockImportRequest {

    private String executionId;
    private String jarLocation;
    private List<StockImportItem> importItems;
    private String[] houseCaseRef;
    private String userName;
	private EHSStockImportSource importSource;
	private String userDescription;

    /**
     * @since 1.0
     */
    public SpssImportStockRequest() {
        this(UUID.randomUUID().toString());
    }

    /**
     * @since 1.0
     */
    public SpssImportStockRequest(String executionId) {
        this.executionId = executionId;
        importItems = new ArrayList<StockImportItem>();
    }

    /**
     * @since 1.0
     */
    public String getKeyCollectionName() {
        // TODO: Need to get this from somewhere else, configurable as part of xml definition
        return getExecutionId() + "_" + "uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl";
    }

    /**
     * Return the jarLocation.
     * 
     * @return the jarLocation
     * @since 1.0
     */
    public String getJarLocation() {
        return jarLocation;
    }

    /**
     * Set the jarLocation.
     * 
     * @param jarLocation the jarLocation
     * @since 1.0
     */
    public void setJarLocation(String jarLocation) {
        this.jarLocation = jarLocation;
    }

    /**
     * Return the importItems.
     * 
     * @return the importItems
     * @since 1.0
     */
    @XmlElementWrapper
    @XmlElement(name = "importItem")
    public List<StockImportItem> getImportItems() {
        return importItems;
    }

    /**
     * Set the importItems.
     * 
     * @param importItems the importItems
     * @since 1.0
     */
    public void setImportItems(List<StockImportItem> importItems) {
        this.importItems = importItems;
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.request.IStockImportRequest#getExplodedImportItems()
     * @since 1.0
     */
    public List<IStockImportItem> getExplodedImportItems() {
        return new ArrayList<IStockImportItem>(importItems);
    }

    /**
     * @return
     * @see uk.org.cse.stockimport.request.IStockImportRequest#getExecutionId()
     * @since 1.0
     */
    @javax.xml.bind.annotation.XmlAttribute(name="id", required=false)
    public String getExecutionId() {
        return executionId;
    }

    /**
     * Set the executionId.
     * 
     * @param executionId the executionId
     * @since 1.0
     */
    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }
    
    /**
     * @since 3.5.0
     * @return The data source for that we should import from.
     */
    @XmlElementRef
    public EHSStockImportSource getImportSource() {
    	return importSource;
    }
    
    public void setImportSource(EHSStockImportSource importSource) {
    	this.importSource = importSource;
    }

    /**
     * Return the houseCaseRef.
     * 
     * @return the houseCaseRef
     * @since 1.0
     */
    public String[] getHouseCaseRef() {
        return houseCaseRef;
    }

    /**
     * Set the houseCaseRef.
     * 
     * @param houseCaseRef the houseCaseRef
     * @since 1.0
     */
    public void setHouseCaseRef(String[] houseCaseRef) {
        this.houseCaseRef = houseCaseRef;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlAttribute(name = "description")
	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	@Override
	public void removeImportItem(IStockImportItem importItem) {
		this.importItems.remove(importItem);
	}
}
