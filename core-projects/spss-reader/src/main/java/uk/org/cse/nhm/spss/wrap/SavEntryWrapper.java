package uk.org.cse.nhm.spss.wrap;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavMetadata;

/**
 * This is the base class which things from the {@link SavStreamWrapperBuilder}
 * will extend;
 *
 * It is basically an internal detail of how that class works. It contains a
 * {@link SavMetadata} and a {@link SavEntry} which the proxied interfaces that
 * the stream wrapper builder returns need to be able to access.
 *
 * @author hinton
 *
 */
public class SavEntryWrapper {

    private SavEntry entry;
    private SavMetadata metadata;

    /**
     * Create a new sav entry wrapper for the given entry + metadata
     *
     * @param metadata
     * @param entry
     */
    public SavEntryWrapper(final SavMetadata metadata, final SavEntry entry) {
        this.metadata = metadata;
        this.entry = entry;
    }

    public SavEntry getEntry() {
        return entry;
    }

    public SavMetadata getMetadata() {
        return metadata;
    }
}
