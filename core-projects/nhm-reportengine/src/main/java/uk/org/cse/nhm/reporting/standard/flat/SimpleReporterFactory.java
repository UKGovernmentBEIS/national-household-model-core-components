package uk.org.cse.nhm.reporting.standard.flat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import uk.org.cse.nhm.reporting.standard.IReporterFactory;

public class SimpleReporterFactory implements IReporterFactory {
	private Constructor<? extends IReporter> constructor;

	@Inject
	public SimpleReporterFactory(final Class<? extends IReporter> reporterClass) {
		try {
			this.constructor = 
					reporterClass.getConstructor(IOutputStreamFactory.class);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Cannot create a simple tabular reporter factory for reporter class "+reporterClass.getSimpleName(), e);
		}
	}
	
	@Override
	public IReporter startReporting(final IOutputStreamFactory factory) {
		try {
			return constructor.newInstance(factory);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Error constructing " + constructor, e);
		}
	}

}
