package uk.org.cse.nhm.reporting.report;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Date;
import java.util.zip.ZipFile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class of this type is written into a special file in each report. It
 * contains information about the report, like when it was made
 */
public class ReportMetadata {

    public static final String REPORT_ENTRY_NAME = "metadata.json";

    private final Date startDate, endDate;

    @JsonCreator
    public ReportMetadata(
            @JsonProperty("startDate") final Date startDate,
            @JsonProperty("endDate") final Date endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void write(final OutputStream out) throws IOException {
        out.write(new ObjectMapper().writeValueAsBytes(this));
    }

    public static ReportMetadata from(final InputStream stream) throws IOException {
        return new ObjectMapper().readValue(stream, ReportMetadata.class);
    }

    public static ReportMetadata fromZip(final Path zipFile) throws IOException {
        try (final ZipFile zf = new ZipFile(zipFile.toFile())) {
            return from(zf.getInputStream(zf.getEntry(REPORT_ENTRY_NAME)));
        }
    }
}
