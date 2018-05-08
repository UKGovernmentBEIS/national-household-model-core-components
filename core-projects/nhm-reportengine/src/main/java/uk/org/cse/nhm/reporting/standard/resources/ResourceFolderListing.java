package uk.org.cse.nhm.reporting.standard.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lists the contents of a resource folder recursively.
 */
public class ResourceFolderListing {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceFolderListing.class);
    private final Class<?> clazz;

    public ResourceFolderListing(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public ResourceFolderListing() {
        this(ResourceFolderListing.class);
    }

    public Set<String> getListing(final String path) {
        try {
            return getListing(clazz, path);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Error reading JAR files to look for resources. " + e.getMessage(), e);
        } catch (final IOException e) {
            throw new RuntimeException("Error listing resources. " + e.getMessage(), e);
        } catch (final URISyntaxException e) {
            throw new RuntimeException("Error listing resources from file system. " + e.getMessage(), e);
        }
    }

    private Set<String> getListing(final Class<?> clazz, final String path) throws UnsupportedEncodingException, IOException, URISyntaxException {
        final URL resourceUrl = clazz.getClassLoader().getResource(path);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource does not exist: " + path);
        }

        if (resourceUrl.getProtocol().equals("jar")) {
            return getListingFromJAR(clazz, path);
        } else if (resourceUrl.getProtocol().equals("file")) {
            return getListingFromFileSystem(clazz, path);
            // bundles, waargh, this is awful, I hate you velocity engine
        } else {
            LOGGER.error("Attempted to get resource folder listing for {}, but encountered unhandled protocol {}.", path, resourceUrl.getProtocol());
            return Collections.emptySet();
        }
    }

    private Set<String> getListingFromJAR(final Class<?> clazz, final String path) throws UnsupportedEncodingException, IOException {
        final CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            throw new RuntimeException("Could not find code source when looking for resources.");
        }

        final URL codeLocation = codeSource.getLocation();
        final JarFile jar = new JarFile(URLDecoder.decode(codeLocation.getFile(), "UTF-8"));
        final Set<String> result = new HashSet<String>();
        try {
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry current = entries.nextElement();
                if (!current.isDirectory()) {
                    if (current.getName().startsWith(path)) {
                        LOGGER.info("Searching JAR for resources under {}, found {}", path, current.getName());
                        result.add(current.getName());
                    }
                }
            }
        } finally {
            jar.close();
        }
        return result;
    }

    private Set<String> getListingFromFileSystem(final Class<?> clazz, String path) throws IOException, UnsupportedEncodingException, URISyntaxException {
        path = withLeadingSlash(path);

        final InputStream resourceStream = clazz.getResourceAsStream(path);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream));
        final Set<String> children = new HashSet<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            final String maybeChildPath = path + "/" + line;
            if (isResourcePath(clazz, maybeChildPath)) {
                children.add(maybeChildPath);
            } else {
                // The resource is not a directory
                return Collections.singleton(withoutLeadingSlash(path));
            }
        }

        final Set<String> results = new HashSet<String>();
        for (final String childPath : children) {
            results.addAll(getListingFromFileSystem(clazz, childPath));
        }

        return results;
    }

    private String withLeadingSlash(final String path) {
        if (path.startsWith("/")) {
            return path;
        } else {
            return "/" + path;
        }
    }

    private String withoutLeadingSlash(final String path) {
        if (path.startsWith("/")) {
            return path.substring(1);
        } else {
            return path;
        }
    }

    private boolean isResourcePath(final Class<?> clazz, final String path) {
        return clazz.getResourceAsStream(path) != null;
    }
}
