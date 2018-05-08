package uk.org.cse.stockimport.util;

import java.util.NoSuchElementException;

import com.google.common.base.Optional;

public class OptionalUtil {

    public static <T> T get(final Optional<T> thing, final String name) {
        if (thing.isPresent()) {
            return thing.get();
        } else {
            throw new NoSuchElementException(name + " is not present, but is required");
        }
    }
}
