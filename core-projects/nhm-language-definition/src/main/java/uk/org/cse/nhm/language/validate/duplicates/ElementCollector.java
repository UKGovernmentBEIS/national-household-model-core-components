package uk.org.cse.nhm.language.validate.duplicates;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import javax.xml.bind.Unmarshaller.Listener;

public class ElementCollector<T> extends Listener {

    private final Class<T> clazz;
    private final Set<T> instances = Collections.newSetFromMap(new IdentityHashMap<T, Boolean>());

    public ElementCollector(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void afterUnmarshal(Object target, Object parent) {
        if (clazz.isInstance(target)) {
            instances.add(clazz.cast(target));
        }
    }

    public Set<T> getCollectedElements() {
        return instances;
    }
}
