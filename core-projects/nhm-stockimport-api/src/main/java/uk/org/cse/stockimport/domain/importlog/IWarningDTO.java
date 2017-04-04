package uk.org.cse.stockimport.domain.importlog;

import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

@DTO(value = "warnings", required=false,
 description = {
		"This file is used to add log messages that will be held against the imported Survey Case, these will be in addition to any generated during the import from DTO to Survey Case in either the import or imputation phases.",
		"This file may be left with just column titles if no log entries need to be added to the Survey Case." })
public interface IWarningDTO extends IBasicDTO {

	@DTOField("warning")
	String getMessage();

	void setMessage(final String message);
	
}
