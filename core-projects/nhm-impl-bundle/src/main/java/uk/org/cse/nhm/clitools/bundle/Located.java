package uk.org.cse.nhm.clitools.bundle;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.bundle.api.ILocated;
import uk.org.cse.nhm.bundle.api.ILocation;

abstract class Located<P> implements ILocated<P> {

    private final List<ILocation<P>> locations;
    private final ILocation<P> sourceLocation;

    Located(final List<ILocation<P>> locations) {
        super();
        this.locations = ImmutableList.copyOf(locations);
        sourceLocation = locations.get(locations.size() - 1);
    }

    @Override
    public List<ILocation<P>> locations() {
        return locations;
    }

    @Override
    public ILocation<P> sourceLocation() {
        return sourceLocation;
    }

    @Override
    public String toString() {
        return String.valueOf(sourceLocation);
    }
}
