package uk.org.cse.nhm.reporting.standard.timeseries.model;

import java.util.Map.Entry;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoProperty
public class GroupAndVariable {
	private ImmutableMap<String, String> group;
	private String variable;

	public GroupAndVariable(ImmutableMap<String, String> groupParts, String variable) {
		this.group = groupParts;
		this.variable = variable;
	}

	public ImmutableMap<String, String> getGroup() {
		return group;
	}

	public String getVariable() {
		return variable;
	}

	@Override
	public String toString() {
		return String.format("%s for %s", variable, stringifyGroup());
	}

	private String stringifyGroup() {
		ImmutableList.Builder<String> groupValues = ImmutableList.builder();
		
		for (Entry<String, String> e : group.entrySet()) {
			groupValues.add(e.getValue());
		}
		
		return Joiner.on('-').join(groupValues.build());
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}
}
