package uk.org.cse.nhm.ipc.api.reporting;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import uk.org.cse.nhm.reporting.repo.IReportRepository;

public interface IReportService {
	/**
	 * This registers a repository with the report service; it will be queried
	 * during calls to {@link #locateReport(UUID)}, and it will be told about reports to download
	 * during calls to {@link #storeReport(String, UUID)}
	 * 
	 * @param repository
	 * @throws IOException
	 */
    public void register(IReportRepository repository) throws IOException;
    
    /**
     * This is a client method which will query all {@link IReportRepository} instances
     * in the system to see if they have a report for the given ID.
     * 
     * If they do, it will come back.
     * 
     * @param reportID
     * @return
     * @throws IOException
     */
    public Set<IReportLocation> locateReport(final String scenarioID) throws IOException;

    /**
     * This is a largely internal method to tell the system that a report is being hosted at the given temporary
     * location, and so it should be ingested by some report repositories and written down in the metadata
     * store
     * 
     * @param scenarioID
     * @param metadata
     * @return 
     * @throws IOException 
     */
    public boolean storeReport(final String scenarioID, final String temporaryLocation) throws IOException;
}
