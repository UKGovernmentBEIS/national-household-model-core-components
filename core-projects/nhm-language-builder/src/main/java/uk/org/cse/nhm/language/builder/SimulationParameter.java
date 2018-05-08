package uk.org.cse.nhm.language.builder;

import com.google.inject.Key;

public class SimulationParameter<T> {

    private final Key<T> key;
    private final T value;

    private SimulationParameter(Key<T> key, T variable) {
        this.key = key;
        this.value = variable;
    }

    public Key<T> getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public final static <Q> SimulationParameter<Q> of(final Key<Q> key, final Q variable) {
        return new SimulationParameter<Q>(key, variable);
    }
}
