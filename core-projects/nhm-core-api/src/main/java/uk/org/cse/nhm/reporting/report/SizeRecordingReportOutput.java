package uk.org.cse.nhm.reporting.report;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.io.CountingOutputStream;

/**
 * Keeps track of the number of bytes written to the output stream in
 * {@link #writeContent(OutputStream)}, and then presents that as a nice string
 * through {@link #getHumanReadableSize()}.
 *
 * @author hinton
 *
 */
public abstract class SizeRecordingReportOutput implements IReportOutput {

    private CountingOutputStream countingOutputStream;

    @Override
    public final void writeContent(final OutputStream outputStream) throws IOException {
        countingOutputStream = new CountingOutputStream(outputStream);
        doWriteContent(countingOutputStream);
    }

    protected abstract void doWriteContent(final OutputStream outputStream) throws IOException;

    @Override
    public final String getHumanReadableSize() {
        final long byteCount
                = countingOutputStream == null ? 0
                        : countingOutputStream.getCount();

        return FileUtils.byteCountToDisplaySize(byteCount);
    }
}
