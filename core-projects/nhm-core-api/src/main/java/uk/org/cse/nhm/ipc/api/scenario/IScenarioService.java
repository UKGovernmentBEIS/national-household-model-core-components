package uk.org.cse.nhm.ipc.api.scenario;

import java.io.IOException;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.ipc.api.tasks.sim.IScenario;
import uk.org.cse.nhm.ipc.api.tasks.sim.IScenarioMetadata;
import uk.org.cse.nhm.ipc.api.tasks.sim.IScenarioTree;

/**
 *
 * A handle to this should be used to acquire scenarios from wherever they are
 * stored.
 *
 * @since 3.7.0
 */
public interface IScenarioService {

    public void addTag(final String scenarioID, final String tag);

    public void removeTag(final String scenarioID, final String tag);

    public IScenario getScenario(final String uuid);

    public IScenarioTree getTreeContaining(final String uuid);

    public IScenarioTree getTreeRootedAt(final String uuid);

    public String getRootElementName(final String scenarioID);

    /**
     * Create a new scenario
     *
     * @since 4.0.0
     * @param parent
     * @param tags
     * @param name
     * @param username
     * @param rawSource
     * @param versionMessage
     * @return
     */
    public IScenario createScenario(
            final Optional<String> parent,
            final Set<String> tags,
            final String name,
            final String username,
            final String rawSource,
            final String versionMessage);

    /**
     * Helper method for creating a new scenario without a version message
     *
     * @since 4.0.0
     * @param parent
     * @param tags
     * @param name
     * @param username
     * @param rawSource
     * @return
     */
    public IScenario createScenario(
            final Optional<String> parent,
            final Set<String> tags,
            final String name,
            final String username,
            final String rawSource);

    /**
     * Given the provided scenario ID, construct a snapshot containing the
     * scenario and all of its includes bundled up together.
     *
     * @param scenarioID
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws TransformerException
     */
    public IScenarioSnapshot getSnapshot(final String scenarioID) throws IOException;

    /**
     * @param name a scenario name to think about
     * @return Optional.absent if the name is OK; otherwise an error message
     */
    public Optional<String> validateName(final String name);

    /**
     * Uses entity resolver to produce a snapshot of the given XML with the
     * includes resolved ready for validation.
     *
     * @since 3.7.0
     * @param scenarioXML
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws SAXException
     */
    public IScenarioSnapshot getHypotheticalSnapshot(final String scenarioXML);

    /**
     * Get (possibly creating and storing) a unique identifier which can be used
     * with the name for a given scenario ID;
     *
     * this method is idempotent.
     *
     * @since 4.0.1
     * @param scenarioID
     * @return A unique versioning number
     */
    public int getNameVersion(final String scenarioID);

    public enum HistoryType {
        Latest,
        Versions,
        All
    }

    public Set<IScenarioMetadata> findScenarios(
            final Set<String> generalWords,
            final Set<String> requiredTags,
            final Set<String> forbiddenTags,
            final HistoryType history,
            final String xmlContents);

    public Optional<IScenario> getScenario(final String string, final int versionNumber);
}
