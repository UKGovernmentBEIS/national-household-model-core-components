package uk.org.cse.boilermatcher.sedbuk;

/**
 * A sedbuk data table.
 *
 * @author hinton
 * @since 1.0
 */
public interface ITable {

    /**
     * @since 1.0
     */
    public int getNumberOfColumns();

    /**
     * @since 1.0
     */
    public int getNumberOfRows();

    /**
     * @since 1.0
     */
    public String getString(final int row, final int column);

    /**
     * @since 1.0
     */
    public void handle(final String thisLine) throws SedbukFormatException;
}
