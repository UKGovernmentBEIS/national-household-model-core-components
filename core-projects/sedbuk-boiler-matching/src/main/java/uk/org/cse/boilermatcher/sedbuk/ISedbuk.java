package uk.org.cse.boilermatcher.sedbuk;



/**
 * ISedbuk.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 1.0
 */
public interface ISedbuk {

    /**
     * @since 1.0
     */
    public abstract boolean hasTable(int tableCode);

    /**
     * @since 1.0
     */
    public abstract int getRevision();

    /**
     * @since 1.0
     */
    public abstract ITable getTable(int i);

    /**
     * @since 1.0
     */
    public abstract <T extends ITable> T getTable(Class<T> tableClass, int i);

}