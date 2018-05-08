package uk.org.cse.stockimport.ehcs2010.spss;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Iterables;

import au.com.bytecode.opencsv.CSVWriter;
import uk.org.cse.stockimport.IHomElementBuilder;

/**
 * AbstractIHomElementBuilder.
 *
 * @author richardt
 * @version $Id: AbstractIHomElementBuilder.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 1.0.2
 */
public abstract class AbstractCSVHomElementBuilder<T> implements IHomElementBuilder<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCSVHomElementBuilder.class);

    /**
     * @since 1.0.2
     */
    public static final String FILE_EXTENSION = "csv";
    /**
     * @since 1.0.2
     */
    public static final String NULL = "NULL";
    /**
     * Default line terminator uses platform encoding.
     */
    public static final String DEFAULT_LINE_END = "\n";

    /**
     * Returns the name of the file to be exported
     *
     * @return String
     * @since 1.0.2
     */
    @Override
    public abstract String getFileName();

    /**
     * Returns the file extension of the file to be returned.
     *
     * @return file extension
     * @since 1.0.2
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION;
    }

    /**
     * Build the header row (column names) based on an example element.
     *
     * @return Sting[] of column names
     * @since 2.0
     */
    public abstract String[] buildHeader(T exampleElement);

    /**
     * Build an individual row in the output file.
     *
     * @param element to build row from
     * @return String[] data for the row
     * @since 1.0.2
     */
    public abstract String[] buildRow(T element);

    @Override
    public void writeChunk(final OutputStream outputStream, final Collection<T> elements, final boolean includeHeaders) throws IOException {
        final CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream));

        if (includeHeaders) {
            LOGGER.debug("Building header...");
            if (elements.size() > 0) {
                csvWriter.writeNext(buildHeader(Iterables.get(elements, 0)));
            }
        }
        LOGGER.debug("Writing elements...");
        writeElements(csvWriter, elements);

        outputStream.flush();
    }

    protected void writeElements(final CSVWriter writer, final Collection<T> elements) throws IOException {
        int ct = 0;
        String[] row;
        for (final T element : elements) {
            row = buildRow(element);
            LOGGER.trace("Row built = {}", (Object[]) row);
            writer.writeNext(row);
            writer.flush();
            ct++;
        }
        LOGGER.debug("{} elements written", ct);
    }

    /**
     * Returns {@link Class#getName()}
     *
     * @return
     * @see uk.org.cse.stockimport.IHomElementBuilder#getBuiltClassName()
     * @since 1.0.2
     */
    @Override
    public abstract String getBuiltClassName();

    protected String toStringOrEmpty(final Object anything) {
        return toStringOrDefault(anything, "");
    }

    protected String toStringOrDefault(final Object anything, final String defaultTo) {
        if (anything == null) {
            return defaultTo;
        }
        return anything.toString();
    }
}
