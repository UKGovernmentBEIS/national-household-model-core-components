package uk.org.cse.stockimport.domain.importlog;

import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

public class WarningDTO extends AbsDTO implements IWarningDTO {
	private String message = "";

	public WarningDTO() {
		
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	@Override
	public void setMessage(final String message) {
		this.message = message;
	}
}
