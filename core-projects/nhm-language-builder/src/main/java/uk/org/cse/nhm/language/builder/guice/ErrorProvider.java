package uk.org.cse.nhm.language.builder.guice;

import com.google.inject.Provider;

public class ErrorProvider<T> implements Provider<T> {

    private final String name;

    private ErrorProvider(String name) {
        this.name = name;
    }

    public final static <T> ErrorProvider<T> named(final String name) {
        return new ErrorProvider<T>(name);
    }

    @Override
    public T get() {
        throw new RuntimeException(
                String.format("%s was injected without being bound properly - pass in a SimulationParameter to the simulator builder to fix this", name)
        );
    }
}
