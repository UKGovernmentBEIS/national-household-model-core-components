package uk.org.cse.nhm.bundle.api;

import com.google.common.base.Optional;

/**
 * The coordinate of an argument to a thing
 */
public interface IArgument {
    /**
     * @return empty if not a named argument
     */
    public Optional<String> name();
    
    /**
     * @return -1 if not a positional argument
     */
    public Optional<Integer> position();
}
