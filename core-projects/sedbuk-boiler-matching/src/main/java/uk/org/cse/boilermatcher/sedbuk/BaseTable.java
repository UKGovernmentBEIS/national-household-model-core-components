package uk.org.cse.boilermatcher.sedbuk;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @since 1.0
 */
public class BaseTable implements ITable {

    private static final String EMPTY = "";
    int columnCount = 0;
    private List<String[]> rows = new ArrayList<String[]>();
    private static final DateTimeFormatter fullFormat = DateTimeFormat.forPattern("yyyy/MMM/dd HH:mm");

    /**
     * @since 1.0
     */
    public void handle(final String thisLine) throws SedbukFormatException {
        // since SEDBUK spec dictates that there can be no embedded commas, this is safe:
        final String[] parts = thisLine.split(",");
        if (columnCount < parts.length) {
            columnCount = parts.length;
        }
        handleRow(parts);
    }

    protected void handleRow(final String[] row) {
        rows.add(row);
    }

    /**
     * @since 1.0
     */
    public void close() {

    }

    @Override
    public int getNumberOfColumns() {
        return columnCount;
    }

    @Override
    public int getNumberOfRows() {
        return rows.size();
    }

    @Override
    public String getString(int row, int column) {
        final String[] cells = rows.get(row);
        if (cells.length <= column) {
            return EMPTY;
        }
        return cells[column];
    }

    /**
     * @since 1.0
     */
    @SuppressWarnings("unchecked")// due to TYPE rather than class.
    public <T> T get(final Class<T> clazz, final int row, final int column) {
        final String s = getString(row, column);
        if (clazz.isAssignableFrom(String.class)) {
            return clazz.cast(s);
        }
        if (s.equals(EMPTY)) {
            return null;
        }
        if (clazz.isAssignableFrom(DateTime.class)) {
            try {
                return clazz.cast(fullFormat.parseDateTime(s));
            } catch (IllegalArgumentException ex) {
                return null;
            }
        } else if (clazz.isAssignableFrom(Double.class) || clazz == Double.TYPE) {
            return (T) (Double) Double.parseDouble(s);
        } else if (clazz.isAssignableFrom(Integer.class) || clazz == Integer.TYPE) {
            return (T) (Integer) Integer.parseInt(s);
        } else {
            throw new RuntimeException("Cannot get field as " + clazz);
        }
    }
}
