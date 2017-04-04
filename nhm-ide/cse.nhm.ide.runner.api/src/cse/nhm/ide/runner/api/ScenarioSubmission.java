package cse.nhm.ide.runner.api;

public class ScenarioSubmission {
    public final String inputName;
    public final IScenarioRun run;
    public final boolean duplicate;

    protected ScenarioSubmission(final String inputName, final IScenarioRun run,final boolean duplicate) {
        this.inputName = inputName;
        this.run = run;
        this.duplicate = duplicate;
    }
}
