package uk.org.cse.nhm.hom.emf.util;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This is a wrapper for arbitrary EObjects which makes equals & hashcode
 * semantics work like java, rather than as UML dictates.
 *
 * <p>
 * Java expects that {@link Object#equals(Object)} tests equality rather than
 * identity, whereas ECore follows the UML expectation that identity is tested
 * for objects, and deep equality is only used for datatypes.
 * <p>
 * Instantiating this with an EObject inside it will let you use the EObject as
 * a key in a map, for example, or place EObjects in sets correctly
 * <p>
 * If you modify the wrapped object, the hashcode for the wrapper may be
 * invalidated.
 *
 * @author hinton
 *
 */
public final class EObjectWrapper<T extends EObject> {

    private final T wrappedObject;
    private Integer hashCodeCache;

    public EObjectWrapper(final T wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof EObjectWrapper) {
            return EcoreUtil.equals(wrappedObject, ((EObjectWrapper<EObject>) obj).wrappedObject);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCodeCache == null) {
            hashCodeCache = computeHashCode(wrappedObject);
        }
        return hashCodeCache;
    }

    /**
     * TODO codegen by EClass might be worth considering here
     *
     * @param object
     * @return
     */
    private static int computeHashCode(final EObject object) {
        final int prime = 35353;
        int result = 1;

        if (object == null) {
            return 0;
        }
        final EClass eClass = object.eClass();
        for (final EAttribute attribute : eClass.getEAllAttributes()) {
            final Object o = object.eGet(attribute);
            if (o != null) {
                result = prime * result + o.hashCode();
            }
        }

        for (final EReference ref : eClass.getEAllContainments()) {
            if (ref.isMany()) {
                @SuppressWarnings("unchecked")
                final List<EObject> values = (List<EObject>) object.eGet(ref);
                for (final EObject o : values) {
                    result = prime * result + computeHashCode(o);
                }
            } else {
                final EObject o = (EObject) object.eGet(ref);
                if (o != null) {
                    result = prime * result + computeHashCode(o);
                }
            }
        }

        //TODO this hash code ignores cross references, which might be bad for dispersal or similar.
        return result;
    }

    @Override
    public String toString() {
        return "Wrapped " + wrappedObject;
    }

    public T unwrap() {
        return wrappedObject;
    }
}
