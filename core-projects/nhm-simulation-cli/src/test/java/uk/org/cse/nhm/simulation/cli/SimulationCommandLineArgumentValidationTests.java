package uk.org.cse.nhm.simulation.cli;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Test;

public class SimulationCommandLineArgumentValidationTests {

    private final String scenarioFile = "src/test/resources/testScenario.xml";
    private final String reportFile = "src/test/resources/tempReport.zip";
    private String jsonFile = "src/test/resources/singleHouseCase.json";

    @Test(expected = IllegalArgumentException.class)
    public void MainMethodThrowsIllegalArugumentExceptionIfNoArgsSupplied() throws Exception {
        SimulationCommandLineInterface.main(null);
    }

    @Test(expected = Exception.class)
    public void TestIfScenarioFileNotFoundExceptionIsShown() throws Exception {
        SimulationCommandLineInterface.main(new String[]{jsonFile, "E:\\invalidFile.xml", reportFile});
    }

    @Test(expected = Exception.class)
    public void TestIfREportFileNotValidExceptionIsShown() throws Exception {
        SimulationCommandLineInterface.main(new String[]{jsonFile, scenarioFile, "E:\\SomethingEvil\\"});
    }

    @After
    public void afterTest() {
        Path reportPath = FileSystems.getDefault().getPath(reportFile);
        File reportFile = reportPath.toFile();
        if (reportFile.exists()) {
            reportFile.delete();
        }
    }
}
