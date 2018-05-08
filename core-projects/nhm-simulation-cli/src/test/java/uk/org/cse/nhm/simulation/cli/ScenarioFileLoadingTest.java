package uk.org.cse.nhm.simulation.cli;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.xml.transform.TransformerException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.SimplePrinter;

public class ScenarioFileLoadingTest {

    private final FileSystem fs = FileSystems.getDefault();
    private final Path scenarioRootDirectory = fs.getPath("src/test/resources");
    private final Path includesPath = fs.getPath("src/test/resources/alternateIncludeDir");

    @Test
    public void canCreateScenarioWithoutIncludes() throws Exception {
        final ISExpression snapshot = SimulationCommandLineInterface.loadScenario(scenarioRootDirectory, null, "id/testScenario.s");

        System.out.println(SimplePrinter.toString(snapshot));

        assertThat("No snapshot returned", snapshot, notNullValue());
    }

    @Test
    public void canCreateScenarioWithIncludes() throws IOException, TransformerException, SAXException {
        final ISExpression snapshot = SimulationCommandLineInterface.loadScenario(scenarioRootDirectory, null, "id/testScenarioWithIncludes.s");

        System.out.println(SimplePrinter.toString(snapshot));

        assertThat("No snapshot returned", snapshot, notNullValue());
    }

    @Test
    public void canCreateScenarioWithNamedIncludeInAnotherDirectory() throws Exception {
        final ISExpression snapshot = SimulationCommandLineInterface.loadScenario(scenarioRootDirectory, includesPath, "id/includesAsNameFromAlternateDir.s");

        System.out.println(SimplePrinter.toString(snapshot));

        assertThat("No snapshot returned", snapshot, notNullValue());
    }

    @Test
    public void canCreateScenarioWithIDRefIncludeInAnotherDirectory() throws Exception {
        final ISExpression snapshot = SimulationCommandLineInterface.loadScenario(scenarioRootDirectory, includesPath, "id/includesAsIdFromAlternateDir.s");

        System.out.println(SimplePrinter.toString(snapshot));

        assertThat("No snapshot returned", snapshot, notNullValue());
    }
}
