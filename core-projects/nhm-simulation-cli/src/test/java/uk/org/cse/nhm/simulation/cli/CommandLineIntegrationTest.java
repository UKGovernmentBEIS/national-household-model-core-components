package uk.org.cse.nhm.simulation.cli;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandLineIntegrationTest {

    private final FileSystem fs = FileSystems.getDefault();
    private final String reportFileName = "src/test/resources/tmpReport.zip";

    @Before
    public void before() {
        cleanFiles();
    }

    @Test
    public void testSingleHouseCaseNoIncludesScenario() throws Exception {
        String stockSourceFile = fs.getPath("src/test/resources/singleHouseCase.json").toString();
        String scenarioDir = fs.getPath("src/test/resources/").toString();
        String scenarioName = fs.getPath("id/testScenario.s").toString();
        String reportDir = reportFileName;

        SimulationCommandLineInterface.main(new String[]{stockSourceFile, scenarioDir, scenarioName, reportDir});
        Assert.assertTrue("Report file not created", fs.getPath("src/test/resources/tmpReport.zip").toFile().exists());
    }

    @Test
    public void ShouldBeAbleToRunScenariosWithTemplatesDefineWithinInIt() throws Exception {
        String stockSourceFile = fs.getPath("src/test/resources/singleHouseCase.json").toString();
        String scenarioDir = fs.getPath("src/test/resources/").toString();
        String scenarioName = fs.getPath("id/scenarioWithTemplateInIt.s").toString();
        String reportDir = reportFileName;

        SimulationCommandLineInterface.main(new String[]{stockSourceFile, scenarioDir, scenarioName, reportDir});
        Assert.assertTrue("Report file not created", fs.getPath("src/test/resources/tmpReport.zip").toFile().exists());
    }

    @After
    public void cleanFiles() {
        File reportFile = fs.getPath(reportFileName).toFile();
        if (reportFile.exists()) {
            reportFile.delete();
        }
    }
}
