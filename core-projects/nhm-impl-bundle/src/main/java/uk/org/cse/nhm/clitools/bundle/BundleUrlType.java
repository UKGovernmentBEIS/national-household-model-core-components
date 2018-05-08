package uk.org.cse.nhm.clitools.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.reflections.vfs.Vfs;
import org.reflections.vfs.Vfs.Dir;
import org.reflections.vfs.Vfs.File;
import org.reflections.vfs.Vfs.UrlType;

import com.google.common.collect.AbstractIterator;

public class BundleUrlType implements UrlType {

    public static final String BUNDLE_PROTOCOL = "bundleresource";

    private final Bundle bundle;

    public BundleUrlType(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public Dir createDir(URL url) {
        return new BundleDir(bundle, url);
    }

    @Override
    public boolean matches(URL url) {
        return BUNDLE_PROTOCOL.equals(url.getProtocol());
    }

    public static class BundleDir implements Dir {

        private String path;
        private final Bundle bundle;

        private static String urlPath(Bundle bundle, URL url) {
            try {
                URL resolvedURL = FileLocator.resolve(url);
                String resolvedURLAsfile = resolvedURL.getFile();

                URL bundleRootURL = bundle.getEntry("/");
                URL resolvedBundleRootURL = FileLocator.resolve(bundleRootURL);
                String resolvedBundleRootURLAsfile = resolvedBundleRootURL
                        .getFile();
                return ("/" + resolvedURLAsfile
                        .substring(resolvedBundleRootURLAsfile.length()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public BundleDir(Bundle bundle, URL url) {
            // this(bundle, url.getPath());
            this(bundle, urlPath(bundle, url));
        }

        public BundleDir(Bundle bundle, String p) {
            this.bundle = bundle;
            this.path = p;
            if (path.startsWith(BUNDLE_PROTOCOL + ":")) {
                path = path.substring((BUNDLE_PROTOCOL + ":").length());
            }
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Iterable<File> getFiles() {
            return new Iterable<Vfs.File>() {
                public Iterator<Vfs.File> iterator() {
                    return new AbstractIterator<Vfs.File>() {
                        final Enumeration<URL> entries = bundle.findEntries(
                                path, "*.class", true);

                        protected Vfs.File computeNext() {
                            return entries.hasMoreElements() ? new BundleFile(
                                    BundleDir.this, entries.nextElement())
                                    : endOfData();
                        }
                    };
                }
            };
        }

        @Override
        public void close() {
        }
    }

    public static class BundleFile implements File {

        private final BundleDir dir;
        private final String name;
        private final URL url;

        public BundleFile(BundleDir dir, URL url) {
            this.dir = dir;
            this.url = url;
            String path = url.getFile();
            this.name = path.substring(path.lastIndexOf("/") + 1);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getRelativePath() {
            return getFullPath().substring(dir.getPath().length());
        }

        public String getFullPath() {
            return url.getFile();
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return url.openStream();
        }
    }
}
