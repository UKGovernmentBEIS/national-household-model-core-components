package cse.nhm.ide.runner.api;

import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import com.google.common.base.Optional;

public interface IScenarioRun {
	/**
	 * @return A unique ID for this job; any job with the same ID, on any installation
	 * 		   of the NHM anywhere in the world should have the same results.
	 */
	public String getID();

	/**
	 * @return A name for this job
	 */
	public String getName();
	/**
	 * @return A name for the author of this job
	 */
	public String getUser();
	
	/**
	 * @return A date when this job was enqueued
	 */
	public Date getQueuedDate();
	
	/**
	 * @return A date when this job's status last changed
	 */
	public Date getChangeDate();
	
	/**
	 * @return A reader which will produce the log output for the job
	 */
	public Reader getLogText();
	
	/**
	 * @return a number from 0 to 1
	 */
	public double getProgress();
	
	/**
	 * @param part TODO
	 * @return if there is a zip file of results, an inputstream for it.
	 */
	public Optional<InputStream> getPartStream(int part);
	
	public int getNumberOfParts();
	
	public Type getType();
	
	public enum Type {
		Scenario,
		Batch
	}
	
	/**
	 * @return how the job is at the moment
	 */
	public State getState();
	
	public enum State {
		QUEUED,
		STARTED,
		FINISHED,
		ERROR
	}
	
	/**
	 * Cancel this job and forget about it entirely
	 */
	public void delete();
	
	public IScenarioRunner getRunner();

	public String getVersion();
}
