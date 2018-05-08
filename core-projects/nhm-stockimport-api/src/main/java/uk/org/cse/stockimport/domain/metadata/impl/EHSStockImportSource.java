package uk.org.cse.stockimport.domain.metadata.impl;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import uk.org.cse.stockimport.domain.metadata.IStockImportSource;

@XmlRootElement(name = "ehs")
public class EHSStockImportSource implements IStockImportSource {

    private String version;
    private String name = "English Housing Survey";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
