package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.io.OutputStream;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.reporting.standard.libraries.ILibrariesOutput;

/**
 * Each report builder may produce charts, data and resources. 
 * It may also depend on {@link ILiberariesOutput} objects.
 * 
 * @author richardt
 * @since 3.3.0
 */
public interface IReporterFactory {
	
	public IReporter startReporting(IOutputStreamFactory factory);
	
	public interface IOutputStreamFactory {
		public OutputStream createReportFile(final String name, final Optional<IReportDescriptor> descriptor);
	}
	
	public interface IReportDescriptor {
		public String getIndexTemplate();
		
		public Type getType();
		public Set<ILibrariesOutput> getLibraries();
		
		public enum Type {
            Problems("Problems"),
            Profiling("Profiling"),
			Chart("Charts and Figures"),
			Data("Tables and Structured Data"), 
			Input("Inputs");
			
			private final String description;

			private Type(String description) {
				this.description = description;
			}
			
			@Override
			public String toString() {
				return description;
			}
		}
	}
	
	public interface IReporter extends Closeable {
		/**
		 * @return the set of simulation log entry classes which this report cares about
		 */
    	Set<Class<? extends ISimulationLogEntry>> getEntryClasses();
    	
    	/**
    	 * @param entry an entry which should go into this report.
    	 */
    	void handle(final ISimulationLogEntry entry);
    }
}
