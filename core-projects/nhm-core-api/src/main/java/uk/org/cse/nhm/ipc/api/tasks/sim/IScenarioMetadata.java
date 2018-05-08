package uk.org.cse.nhm.ipc.api.tasks.sim;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

public interface IScenarioMetadata {

    public interface SystemTags {

        public static final String ARCHIVED = "archived";
        public static final String RUN = "run";
    }

    /**
     * Get scenario ID
     *
     * @since 3.7.0
     * @return
     */
    String getID();

    /**
     * @since 4.0.1
     * @return an integer, which together with the name uniquely identifies this
     * scenario as well as the scenario ID does.
     */
    public Optional<Integer> getVersionOfName();

    /**
     * @since 4.0.0
     * @return the tags that are on this scenario (an unmodifiable view)
     */
    public Set<String> getTags();

    /**
     * @since 4.0.0
     * @return the name of the scenario
     */
    public String getName();

    /**
     * @return the username of the author
     */
    public String getAuthor();

    /**
     * @return the date when this scenario was stored
     */
    public DateTime getCreationDate();

    /**
     * @return the uuid of the parent, if this has a parent.
     */
    Optional<String> getParentID();

    /**
     * @return true if the scenario has had a report at some point.
     */
    public boolean isReportedOn();

    /**
     * @return a message about this scenario's version
     */
    public String getVersionMessage();
}
