package uk.org.cse.nhm.language.validate.contents;

import java.util.Set;

/**
 * To help {@link ContainmentValidator}, for elements which can only be used in
 * a certain place some of the time.
 *
 * @author hinton
 *
 */
public interface ISpecialContentsForValidation {

    /**
     * This allows things to specify that, for some reason, they require a
     * particular extra kind of containment (e.g. functions that sometimes need
     * to have a house around)
     */
    public Set<Class<?>> getAdditionalRequirements();

    /**
     * This allows things to specify that, for some reason, they sometimes
     * provide a particular kind of containment (e.g. if you sometimes have a
     * house around for your children.)
     */
    public Set<Class<?>> getAdditionalProvisions();
}
