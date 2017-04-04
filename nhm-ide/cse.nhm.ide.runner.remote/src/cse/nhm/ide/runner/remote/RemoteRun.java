package cse.nhm.ide.runner.remote;

import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import com.google.common.base.Optional;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRunner;

class RemoteRun implements IScenarioRun {
	private final RemoteRunner runner;
	
	private final String id;
	private final String name;
	private final String user;
	private final Date queuedDate;
	private final String version;
	
	private Date changeDate;
	private State state;
	private double progress;

	private final int parts;
	private final Type type;
	
	RemoteRun(final RemoteRunner runner, 
			final String id, 
			final String version, 
			final String name, 
			final String user,
			final int parts,
			final Date queuedDate, 
			final Date changeDate,
			final State state, 
			final double progress) {
		super();
		this.runner = runner;
		this.id = id;
		this.version = version;
		this.name = name;
		this.user = user;
		this.parts = Math.max(1, parts);
		this.queuedDate = queuedDate;
		this.changeDate = changeDate;
		this.state = state;
		this.progress = progress;
		
		this.type = parts == 0 ? Type.Scenario : Type.Batch;
	}

	@Override
	public String getVersion() {
		return version;
	}
	
	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public Date getQueuedDate() {
		return queuedDate;
	}

	@Override
	public Date getChangeDate() {
		return changeDate;
	}

	@Override
	public double getProgress() {
		return progress;
	}
	
	@Override
	public State getState() {
		return state;
	}
	

	@Override
	public Reader getLogText() {
		return runner.getLogText(this);
	}

	@Override
	public int getNumberOfParts() {
		return parts;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}
	
	@Override
	public Optional<InputStream> getPartStream(final int part) {
		return runner.getPartStream(this, part);
	}

	@Override
	public void delete() {
		runner.delete(this);
	}

	@Override
	public IScenarioRunner getRunner() {
		return runner;
	}

	public boolean update(final State state, final double prog, final Date date) {
		boolean change = false;
		if (this.state != state) {
			change = true;
			this.state = state;
		}
		if (this.progress != prog) {
			change = true;
			this.progress = prog;
		}
		if (!this.changeDate.equals(date)) {
			change = true;
			this.changeDate = date;
		}
		return change;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteRun other = (RemoteRun) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
