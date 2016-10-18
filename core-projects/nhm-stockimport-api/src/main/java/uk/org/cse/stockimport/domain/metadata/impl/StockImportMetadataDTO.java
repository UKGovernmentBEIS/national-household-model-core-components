package uk.org.cse.stockimport.domain.metadata.impl;

import org.joda.time.DateTime;

import uk.org.cse.stockimport.domain.metadata.IStockImportMetadataDTO;

public class StockImportMetadataDTO implements IStockImportMetadataDTO {
	private String stockImporterVersion;
	private DateTime date;
	private String userName;
	private String descriptionByUser;
	private String sourceName;
	private String sourceVersion;
	
	public StockImportMetadataDTO(){
		super();
	}
	
	public StockImportMetadataDTO(DateTime date, String stockImporterVersion, String userName, 
			String sourceName, String sourceVersion, String descriptionByUser){
		this();
		this.date = date;
		this.stockImporterVersion = stockImporterVersion;
		this.userName = userName;
		this.sourceName = sourceName;
		this.sourceVersion = sourceVersion;
		this.descriptionByUser = descriptionByUser;
	}
	
	public String getStockImporterVersion() {
		return stockImporterVersion;
	}
	public void setStockImporterVersion(String stockImporterVersion) {
		this.stockImporterVersion = stockImporterVersion;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescriptionByUser() {
		return descriptionByUser;
	}
	public void setDescriptionByUser(String descriptionByUser) {
		this.descriptionByUser = descriptionByUser;
	}

	@Override
	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public String getSourceVersion() {
		return sourceVersion;
	}
	
	public void setSourceVersion(String sourceVersion) {
		this.sourceVersion = sourceVersion;
	}
}
