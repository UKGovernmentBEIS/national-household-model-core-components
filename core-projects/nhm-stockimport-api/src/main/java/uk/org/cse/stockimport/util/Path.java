package uk.org.cse.stockimport.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 1.0
 */
public class Path {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Path.class);
    /**
     * @since 1.0
     */
    public static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * @since 1.0
     */
    public static final String file(final String... fragments) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (final String s : fragments) {
            if (first) {
                first = false;
            } else {
                sb.append(SEPARATOR);
            }
            sb.append(s);
        }

        return sb.toString();
    }

    /**
     * @since 1.0
     */
    public static final String dir(final String... fragments) {
        return file(fragments) + SEPARATOR;
    }

    /**
     * @since 1.0
     */
    public static final String getTemporaryDirectory(final String name) {
        if (SEPARATOR.equals("/")) {
            return dir("/tmp", name);
            /*			File tempFile;
			
			try {
				tempFile = File.createTempFile(name, "dir");
				tempFile.delete();
				final File tempdir = new File(tempFile, name);
				tempdir.mkdirs();
				log.debug("created temp dir {} for {}", tempdir, name);
				return tempdir.getAbsolutePath();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}*/
        } else {
            return dir("E:", "Temp", name);
        }
    }
}
