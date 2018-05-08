package uk.org.cse.nhm.reporting.standard.flat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter.Field;
import uk.org.cse.nhm.reporting.standard.libraries.ILibrariesOutput;
import uk.org.cse.nhm.reporting.standard.libraries.LibraryOutput;

/**
 * A descriptor for tabular reports
 *
 * @author hinton
 *
 */
public class TabularDescriptor implements IReportDescriptor {

    private final String description;
    private final Map<String, String> columns;
    private final Type type;

    private TabularDescriptor(final String description, final Map<String, String> columns, final Type type) {
        super();
        this.description = description;
        this.columns = columns;
        this.type = type;
    }

    public static TabularDescriptor of(final String description, final List<Field> fields) {
        return of(description, fields, Type.Data);
    }

    public static TabularDescriptor of(final String description, final List<Field> fields, final Type type) {
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        for (final Field f : fields) {
            builder.put(f.name, f.description);
        }

        return new TabularDescriptor(description, builder.build(), type);
    }

    @Override
    public String getIndexTemplate() {
        return "table.vm";
    }

    @Override
    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getColumnsAndDescriptions() {
        return columns;
    }

    @Override
    public Set<ILibrariesOutput> getLibraries() {
        return ImmutableSet.<ILibrariesOutput>of(LibraryOutput.QUICKVIEW);
    }
}
