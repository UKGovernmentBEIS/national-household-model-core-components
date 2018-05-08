package uk.org.cse.nhm.ipc.api.reporting;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.base.Optional;

@JsonTypeInfo(use = Id.CLASS)
public interface IReportLocation {

    public String getDownloadAddress();

    public Optional<String> getViewingAddress();
}
