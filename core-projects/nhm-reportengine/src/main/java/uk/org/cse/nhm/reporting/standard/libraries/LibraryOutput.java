package uk.org.cse.nhm.reporting.standard.libraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.resources.ResourceFolderListing;
import uk.org.cse.nhm.reporting.standard.resources.ResourceOutput;

public class LibraryOutput implements ILibrariesOutput {

    public static LibraryOutput JQUERY = new LibraryOutput("lib/jquery");
    public static LibraryOutput D3 = new LibraryOutput("lib/d3");
    public static LibraryOutput D3Plugins = new LibraryOutput("lib/d3plugins", D3);
    public static LibraryOutput RICKSHAW = new LibraryOutput("lib/rickshaw", D3, JQUERY);
    public static LibraryOutput QUICKVIEW = new LibraryOutput("lib/quickview", D3, D3Plugins);

    private final String libraryPath;
    private final Set<ILibrariesOutput> dependencies;
    private Set<ILibrariesOutput> resolved;

    public LibraryOutput(final String libraryPath, final ILibrariesOutput... dependencies) {
        this.libraryPath = libraryPath;
        this.dependencies = new HashSet<ILibrariesOutput>(Arrays.asList(dependencies));
    }

    @Override
    public List<IReportOutput> buildResourceFiles(final ResourceFolderListing resourceFolderListing) {
        final List<IReportOutput> result = new ArrayList<IReportOutput>();
        for (final String path : resourceFolderListing.getListing(libraryPath)) {
            result.add(new ResourceOutput(path));
        }
        return result;
    }

    @Override
    public Set<ILibrariesOutput> getDependencies() {
        if (resolved == null) {
            resolved = new HashSet<ILibrariesOutput>();

            final Stack<ILibrariesOutput> toResolve = new Stack<ILibrariesOutput>();
            toResolve.addAll(dependencies);
            while (!toResolve.isEmpty()) {
                final ILibrariesOutput current = toResolve.pop();
                resolved.add(current);
                for (final ILibrariesOutput dependency : current.getDependencies()) {
                    if (!resolved.contains(dependency) && !toResolve.contains(dependency)) {
                        toResolve.push(dependency);
                    }
                }
            }
        }

        return resolved;
    }

    public String getPath() {
        return libraryPath;
    }
}
