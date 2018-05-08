package uk.org.cse.nhm.reporting.repo;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.reporting.IReportLocation;

/**
 * Provides methods to store and access reports
 *
 * @since 3.7.0
 */
public interface IReportRepository {

    boolean getAndStoreReport(String reportURL, String scenarioID);

    Optional<IReportLocation> locateReport(String scenarioID);
}
