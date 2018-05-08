package uk.org.cse.nhm.simulator.state.components;

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.hom.ICopyable;

/**
 * Used to store "flags" associated with a dwelling, which are basically named
 * boolean variables accesible and creatable by end users through the scenario.
 *
 * @author hinton
 *
 */
public interface IFlags extends ICopyable<IFlags> {

    public Set<String> getFlags();

    /**
     * Puts the flag s onto these flags
     *
     * @param s
     */
    public boolean addFlag(final String s);

    /**
     * Takes the flag s away from these flags.
     *
     * @param s
     */
    public boolean removeFlag(final String s);

    /**
     * @return Whether or not the named flag is set..
     */
    public boolean testFlag(final String flag);

    /**
     * @return True if all the flags are set, false otherwise.
     */
    public boolean hasAllFlags(final Set<String> flags);

    /**
     * @return True if any of the flags is set, false if none of them are.
     */
    public boolean hasAnyFlag(final Set<String> flags);

    public boolean flagsMatch(final Collection<Glob> globs);

    public boolean modifyFlagsWith(final Collection<Glob> globs);

    /**
     * Sets the named register to the given value.
     */
    public boolean setRegister(String s, double value);

    /**
     * @return The value stored against the named register, or 0 if none could
     * be found.
     */
    public Optional<Double> getRegister(final String s);
}
