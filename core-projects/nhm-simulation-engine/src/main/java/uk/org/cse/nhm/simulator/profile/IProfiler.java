package uk.org.cse.nhm.simulator.profile;

public interface IProfiler {
	public void start(final String msg, final String category);
	public void stop();
}
