package uk.org.cse.stockimport.impl;

public class HousingStockConstructionError {
	private String id;
	private String error;
	private String details;

	protected HousingStockConstructionError() {}
	
	public HousingStockConstructionError(final String id, final String err, String details) {
		this.setDetails(details);
		this.setId(id);
		this.setError(err);
	}

	public String getId() {
		return id;
	}

	public String getError() {
		return error;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
