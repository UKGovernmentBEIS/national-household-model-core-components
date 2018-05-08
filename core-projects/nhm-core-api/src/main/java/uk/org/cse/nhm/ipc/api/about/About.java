package uk.org.cse.nhm.ipc.api.about;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

public class About {

    private final String name;
    private final String machineName;
    private final Map<String, String> dependencyVersions;

    @JsonCreator
    About(@JsonProperty("dependencyVersions") final Map<String, String> dependencyVersions,
            @JsonProperty("name") final String name,
            @JsonProperty("machineName") final String machineName
    ) {
        super();
        this.name = name;
        this.machineName = machineName;
        this.dependencyVersions = ImmutableMap.copyOf(dependencyVersions);
    }

    public Map<String, String> getDependencyVersions() {
        return dependencyVersions;
    }

    public String getName() {
        return name;
    }

    public String getMachineName() {
        return machineName;
    }

    public static class Builder {

        private static final String UNKNOWN = "Unknown";
        private final Map<String, String> dependencyVersions = new HashMap<>();
        private String machineName = "Unknown Machine";
        private String name = "Unknown Service";

        public Builder withDependency(final String name, final Class<?> indicator) {
            final String ver = indicator.getPackage().getImplementationVersion();
            if (ver == null || ver.isEmpty()) {
                dependencyVersions.put(name, UNKNOWN);
            } else {
                dependencyVersions.put(name, ver);
            }
            return this;
        }

        public Builder withMachineName(final String s) {
            this.machineName = s;
            return this;
        }

        public Builder withName(final String s) {
            this.name = s;
            return this;
        }

        public About build() {
            return new About(dependencyVersions, name, machineName);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
