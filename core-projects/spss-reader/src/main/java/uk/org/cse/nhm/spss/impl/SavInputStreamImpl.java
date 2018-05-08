package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.io.InputStream;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavMetadata;

public class SavInputStreamImpl implements SavInputStream {

    private final SavDataStream stream;
    private final SavMetadataImpl metadata;
    private int iteratorCount = 0;

    /**
     * Create a SavInputStream which will stream from the given stream. Stream.
     *
     * @param stream
     * @throws IOException
     */
    public SavInputStreamImpl(final InputStream stream, final boolean longNames) throws IOException {
        // read meta-data, and prepare to read entries
        this.stream = new SavDataStream(stream);
        this.metadata = new SavMetadataImpl(this.stream, longNames);
    }

    public SavInputStreamImpl(final InputStream stream) throws IOException {
        this(stream, false);
    }

    @Override
    public SavMetadata getMetadata() {
        return metadata;
    }

    @Override
    public boolean hasNext() {
        return iteratorCount < metadata.getEntryCount();
    }

    @Override
    public SavEntry next() {
        try {
            final SavEntryImpl entry = new SavEntryImpl(metadata, stream);
            iteratorCount++;
            return entry;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove sav entries from a stream");
    }
}
