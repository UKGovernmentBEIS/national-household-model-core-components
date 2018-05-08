package uk.org.cse.stockimport.domain.metadata;

import org.joda.time.DateTime;

/**
 * Contains information about a stock import run.
 *
 * Associated with the import itself, not a particular house.
 *
 * Only applies to the EHCS to DTO phase.
 *
 * @author glenns
 * @since 1.3.4
 */
public interface IStockImportMetadataDTO {

    public String getStockImporterVersion();

    public DateTime getDate();

    public String getUserName();

    public String getSourceName();

    public String getSourceVersion();

    public String getDescriptionByUser();
}
