package uk.org.cse.nhm.clitools.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

class VersionUtil {
    public static final String VERSION = getVersionFromProperties();
    private static String getVersionFromProperties(final String name) {
        try {
            final Properties properties = new Properties();
            Enumeration<URL> resources = VersionUtil.class.getClassLoader().getResources(name);
            while (resources.hasMoreElements()) {
                try (final InputStream is = resources.nextElement().openStream()) {
                    properties.load(is);
                } catch (final IOException ex) {}
                final String version = properties.getProperty("buildnumber");
                if (version != null) {
                    return version;
                } else {
                    return "buildnumber missing: " + String.valueOf(properties);
                }
            }
        } catch (final IOException ex) {}
        return null;
    }
    
    static String getVersionFromProperties() {
        String result = getVersionFromProperties("uk/org/cse/nhm/clitools/bundle/nhm-impl-bundle.properties");
        if (result == null) result = "unknown";
        return result;
    }
}
