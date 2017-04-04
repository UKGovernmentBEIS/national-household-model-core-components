package uk.org.cse.nhm.stockimport.simple.dto;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;
import uk.org.cse.stockimport.domain.schema.IDTOReader;
import uk.org.cse.stockimport.domain.schema.IKeyValue;
import uk.org.cse.stockimport.domain.schema.InvalidHeaderException;
import uk.org.cse.stockimport.domain.schema.InvalidRowException;

public class AdditionalPropertiesDTOReader implements IDTOReader<AdditionalHousePropertiesDTO> {
	public static final String ADDITIONAL_PROPERTIES_FILE = "additional-properties";
	@Override
	public String getFilename() {
		return ADDITIONAL_PROPERTIES_FILE;
	}

	@Override
	public void checkHeaders(final List<String> headers) throws InvalidHeaderException {
		if (!headers.contains("aacode")) {
			throw new InvalidHeaderException(ImmutableList.of("Additional properties file requires an aacode field"));
		}
	}

	@Override
	public AdditionalHousePropertiesDTO read(final IKeyValue row) throws InvalidRowException {
		final AdditionalHousePropertiesDTO dto = new AdditionalHousePropertiesDTO();
		
		dto.setAacode(row.get("aacode"));
		final HashMap<String, String> vals = new HashMap<>();
		
		for (final String k : row.getKeys()) {
			vals.put(k, row.get(k));
		}
		
		dto.setValuesByProperty(vals);
		
		return dto;
	}

	@Override
	public boolean isRequired() {
		return false;
	}
}
