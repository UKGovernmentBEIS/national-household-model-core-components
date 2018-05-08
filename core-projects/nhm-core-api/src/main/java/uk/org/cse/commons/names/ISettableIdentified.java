package uk.org.cse.commons.names;

/**
 * To avoid passing name down the constructor stack when it is a pain to do so,
 * I am allowing use of the hated mutable state in this one instance; this
 * interface will be detected by
 *
 * @author hinton
 *
 */
public interface ISettableIdentified extends IIdentified {

    public void setIdentifier(final Name newName);
}
