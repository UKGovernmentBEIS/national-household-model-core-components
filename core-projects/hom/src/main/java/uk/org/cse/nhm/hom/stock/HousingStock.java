package uk.org.cse.nhm.hom.stock;

import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.SurveyCase;

/**
 * Represents a list of {@link SurveyCase} cases which belong together because
 * they were all part of the same import. Additionally contains some metadata
 * about them.
 * 
 * @author glenns
 * @since 1.3.4
 */
@JsonTypeInfo(use=Id.CLASS, property="_class")
public class HousingStock implements IHousingStock {
	private final String name;
	private final String author;
	private final String importer;
	private final DateTime creationDate;
	private final Map<String, String> additionalMetadata;
	
	@JsonCreator
	public HousingStock(
			@JsonProperty("name") final String name, 
			@JsonProperty("author") final String author, 
			@JsonProperty("importer") final String importer, 
			@JsonProperty("creationDate") final DateTime creationDate, 
			@JsonProperty("additionalMetadata") final Map<String, String> additionalMetadata) {
		super();
		this.name = name == null ? "Missing name" : name;
		this.author = author == null ? "Missing author" : author;
		this.importer = importer == null ? "Missing importer" : importer;
		this.creationDate = creationDate == null ? new DateTime(0) : creationDate;
		final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        if (additionalMetadata != null) {
            for (final Map.Entry<String, String> entry : additionalMetadata.entrySet()) {
                builder.put(entry.getKey().replace('.', '-'), entry.getValue());
            }
        }
		
		this.additionalMetadata = builder.build();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getImporter() {
		return importer;
	}

	@Override
	public DateTime getCreationDate() {
		return creationDate;
	}

	@Override
	public Map<String, String> getAdditionalMetadata() {
		return additionalMetadata;
	}
}
