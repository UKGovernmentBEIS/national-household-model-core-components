package uk.org.cse.nhm.hom;

/**
 *
 * @author glenns
 * @since 1.3.2
 */
public class HomVersion {

    private String version;

    public HomVersion() {
        version = getClass().getPackage().getImplementationVersion();
    }

    public String getVersion() {
        return version;
    }
}
