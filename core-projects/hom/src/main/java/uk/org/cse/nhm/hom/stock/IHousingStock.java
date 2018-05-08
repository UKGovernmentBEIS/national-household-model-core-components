package uk.org.cse.nhm.hom.stock;

import java.util.Map;

import org.joda.time.DateTime;

/**
 * @since 1.3.4
 */
public interface IHousingStock {

    public String getName();

    public String getAuthor();

    public String getImporter();

    public DateTime getCreationDate();

    public Map<String, String> getAdditionalMetadata();
}
