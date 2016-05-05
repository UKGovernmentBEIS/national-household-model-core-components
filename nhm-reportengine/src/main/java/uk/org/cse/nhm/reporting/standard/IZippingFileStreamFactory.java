package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.nio.file.Path;

import org.joda.time.DateTime;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public interface IZippingFileStreamFactory extends Closeable, IOutputStreamFactory {
	public abstract Path getZipFilePath() throws IllegalStateException;
	public abstract void setStartAndEndDates(DateTime creationDate, DateTime endDate);
}