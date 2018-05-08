package uk.org.cse.nhm.logging.logentry.batch;

import com.google.common.collect.ImmutableMap;

/**
 * This is an interface which can be associated with anything that has a
 * key-value like property that is sensible to aggregate up in a batch.
 *
 * @author hinton
 *
 */
public interface IKeyValueLogEntry {

    /**
     * @return All the different categories that make up the row key for this
     * log entry. Keys in the map are column headers, values are the values for
     * a particular row.
     */
    public ImmutableMap<String, String> getFullRowKey();

    /**
     * @return A subset of the full row key. This excludes any other properties
     * which may be accessed directly on the log entry.
     */
    public ImmutableMap<String, String> getReducedRowKey();

    /**
     * @return The dependent values (those which do not make up the row key).
     * Keys in the map are column headers, values are the values for a paticular
     * row.
     */
    public ImmutableMap<String, Double> getColumns();
}
