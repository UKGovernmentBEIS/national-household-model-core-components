package uk.org.cse.nhm.reporting.report;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * Represents a file which will be emitted into the report zip bundle. Some of these files will be
 * presented to the user, and some will not; these are emitted by an {@link IFileOutputReportBuilder}.
 * Those which represent non-visible files come from the {@link IFileOutputReportBuilder#buildResourceFiles()} method.
 * </p>
 * <p>
 * Because reports can involve the loading and transformation of a lot of data, memory overhead is a concern.
 * Report outputs should ideally only have lots of live objects on the heap within the {@link #doWriteContent(OutputStream)} method
 * </p>
 * 
 * 
 * @author hinton
 *
 */
public interface IReportOutput {
	public static final String RESOURCES = "resources/";
    public static final String PROFILE = "profile/";
	public static final String DATA = "aggregate/";
	public static final String DWELLING = "dwelling/";
	public static final String CHARTS = "charts/";
	public static final String LOOKUPS = "lookups/";
	
	public static String GENERIC_TEMPLATE = "generic.vm";
	
	public enum Type {
		Chart("Charts and figures"),
		Data("Tables and structured data"), 
		Input("Inputs");
		
		private final String description;

		private Type(final String description) {
			this.description = description;
		}
		
		@Override
		public String toString() {
			return description;
		}
	}
	
	/**
	 * @return the location in the zip where this file will be placed
	 */
	public abstract String getPath();
	
	/**
	 * this method will be invoked to get the report to write its content to the given stream.
	 * 
	 * please try not to have a lot of live objects on the heap outside this method.
	 * 
	 * @param outputStream
	 * @throws IOException
	 */
	public abstract void writeContent(final OutputStream outputStream) throws IOException;
	
	/**
	 * @return the path of a VM template to be emitted into the index page of the report
	 */
	public abstract String getTemplate();
	
	/**
	 * @return the type of report
	 */
	public abstract Type getType();
	
	/**
	 * @return a string which describes how big the size is
	 */
	public abstract String getHumanReadableSize();
}
