package uk.org.cse.nhm.ipc.api.reporting;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

@AutoProperty
public class ReportLocation implements IReportLocation {
	private final String url;
	private final Optional<String> view;
	
	@JsonCreator
	public ReportLocation(
			@JsonProperty("downloadAddress") final String url, 
			@JsonProperty("viewingAddress")  final Optional<String> view) {
		super();
		this.url = url;
		this.view = view;
	}

	@Override
	public String getDownloadAddress() {
		return url;
	}

	@Override
	public Optional<String> getViewingAddress() {
		return view;
	}
	
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}
