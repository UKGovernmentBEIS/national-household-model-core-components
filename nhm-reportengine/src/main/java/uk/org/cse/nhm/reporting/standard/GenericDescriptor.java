package uk.org.cse.nhm.reporting.standard;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.libraries.ILibrariesOutput;

public class GenericDescriptor implements IReportDescriptor {
	private ImmutableSet<ILibrariesOutput> libs;

	GenericDescriptor(Type type, ILibrariesOutput[] libs) {
		this.type = type;
		this.libs = ImmutableSet.copyOf(libs);
	}

	public static GenericDescriptor of(final Type type, final ILibrariesOutput... libs) {
		return new GenericDescriptor(type, libs);
	}

	private Type type;
	
	@Override
	public String getIndexTemplate() {
		return "generic.vm";
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Set<ILibrariesOutput> getLibraries() {
		return libs;
	}

}
