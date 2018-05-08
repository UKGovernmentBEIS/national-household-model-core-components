package uk.org.cse.nhm.ipc.api.about;

import java.util.Map;

/**
 * An interface for things which can provide about information
 *
 * @author hinton
 *
 */
public interface IAboutable {

    public String getName();

    public Map<String, Class<?>> getDependencyIndicators();
}
