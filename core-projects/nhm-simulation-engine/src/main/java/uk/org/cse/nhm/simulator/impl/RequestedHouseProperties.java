package uk.org.cse.nhm.simulator.impl;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

public class RequestedHouseProperties {

    public static final String DEFAULT_PROPERTIES = "DEFAULT_PROPERTIES";

    private final Set<String> requested;

    @Inject
    public RequestedHouseProperties(@Named(DEFAULT_PROPERTIES) Set<String> defaultProperties) {
        requested = new HashSet<String>();
        requested.addAll(defaultProperties);
    }

    public void request(String property) {
        requested.add(property);
    }

    public Set<String> getRequested() {
        return requested;
    }
}
