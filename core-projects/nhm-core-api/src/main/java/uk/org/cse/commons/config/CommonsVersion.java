package uk.org.cse.commons.config;

public class CommonsVersion {

    String version;

    public CommonsVersion() {
        version = getClass().getPackage().getImplementationVersion();
    }

    public String getVersion() {
        return version;
    }
}
