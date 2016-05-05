package uk.org.cse.nhm.ipc.api.scenario;

@SuppressWarnings("serial")
public class ScenarioNotFoundException extends RuntimeException {
	public ScenarioNotFoundException(final String id) {
		super("Scenario not found with id " + id);
	}
}
