package uk.org.cse.nhm.bundle.api;

import java.util.List;

public interface ILocated<P> {
    public List<ILocation<P>> locations();
    public ILocation<P> sourceLocation();
}
