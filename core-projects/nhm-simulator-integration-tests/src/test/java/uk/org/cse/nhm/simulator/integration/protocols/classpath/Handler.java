package uk.org.cse.nhm.simulator.integration.protocols.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler {

    public static ClassLoader evilClassLoader;

    @Override
    protected URLConnection openConnection(final URL u) throws IOException {
        final String path = u.getPath();
        final URL resourceUrl;

        if (path.startsWith("/")) {
            resourceUrl = evilClassLoader.getResource(path.substring(1));
        } else {
            resourceUrl = evilClassLoader.getResource(path);
        }
        try {
            return resourceUrl.openConnection();
        } catch (final Exception e) {
            throw new RuntimeException("Could not load classpath resource " + u, e);
        }
    }
}
