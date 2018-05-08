package uk.org.cse.nhm.reporting.standard.libraries;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.resources.ResourceFolderListing;

/**
 * These represent shared components which may be used by many reports. They may
 * in turn use other shared components.
 *
 * @author glenns
 * @since 3.3.0
 */
public interface ILibrariesOutput {

    List<IReportOutput> buildResourceFiles(ResourceFolderListing resourceFolderListing);

    Set<ILibrariesOutput> getDependencies();
}
