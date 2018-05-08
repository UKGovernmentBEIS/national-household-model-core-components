package uk.org.cse.nhm.language.parse;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.definition.XElement;

public class LanguageElements {

    private final Class<?>[] classes;
    private final Reflections reflections;
    private static final LanguageElements VALUE = new LanguageElements();

    public static LanguageElements get() {
        return VALUE;
    }

    public LanguageElements() {
        reflections = new Reflections(
                ConfigurationBuilder
                        .build("uk.org.cse.nhm.language.definition")
                        .addClassLoader(getClass().getClassLoader()));
        classes = reflections.getSubTypesOf(XElement.class).toArray(new Class[0]);
    }

    public Class<?>[] all() {
        return classes;
    }

    public Set<Class<?>> allAsSet() {
        return ImmutableSet.copyOf(classes);
    }

    public <T> Set<Class<? extends T>> getSubTypesOf(final Class<T> superType) {
        return reflections.getSubTypesOf(superType);
    }
}
