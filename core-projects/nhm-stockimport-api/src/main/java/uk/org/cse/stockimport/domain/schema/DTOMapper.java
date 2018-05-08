package uk.org.cse.stockimport.domain.schema;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.reflect.TypeToken;

import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.schema.Constraint.Type;

public class DTOMapper<T> {

    private final ImmutableList<IDTOProperty> properties;
    private final String name;
    private final String description;

    public interface IDTOProperty {

        public List<String> getFields();

        public List<String> getRequiredFields();

        public Optional<String> validate(final IKeyValue fields);

        public void set(final Object output, final IKeyValue fields);

        public List<String> get(final Object input);

        public String getFieldDescription(final String field);

        public String getDefaultValue(final String field);

        public String getSQLType(final String field);

        public String getSQLTypeDef(final String field);
    }

    static class SimpleDTOProperty implements IDTOProperty {

        private final PropertyDescriptor descriptor;
        private final FieldReader<?> reader;
        private final String fieldName;
        private final String missingValue;
        private final boolean allowEmptyValues;
        @SuppressWarnings("unused")
        private final boolean warnOnEmptyValues;

        private SimpleDTOProperty(final PropertyDescriptor d, final DTOField f) {
            this.descriptor = d;
            this.fieldName = f.value()[0].toLowerCase();
            this.missingValue = f.constraint().missing();
            this.allowEmptyValues = f.constraint().value() != Type.REQUIRED;
            this.warnOnEmptyValues = f.constraint().value() == Type.AUTOMATIC;

            this.reader = FieldReader.of(TypeToken.of(d.getReadMethod().getGenericReturnType()));
        }

        @Override
        public List<String> getRequiredFields() {
            if (allowEmptyValues) {
                return Collections.emptyList();
            } else {
                return getFields();
            }
        }

        @Override
        public List<String> getFields() {
            return ImmutableList.of(fieldName);
        }

        @Override
        public Optional<String> validate(final IKeyValue fields) {
            final String value = fields.get(fieldName);

            if (allowEmptyValues && (value == null || value.isEmpty())) {
                return Optional.absent();
            } else {
                if (value == null) {
                    throw new NullPointerException(fieldName + " is null");
                }
                final Optional<String> s = reader.validate(value);
                if (s.isPresent()) {
                    return Optional.of(fieldName + ": " + s.get());
                } else {
                    return s;
                }
            }
        }

        @Override
        public void set(final Object output, final IKeyValue fields) {
            String value = fields.get(fieldName);

            if ((value == null || value.isEmpty()) && allowEmptyValues) {
                value = missingValue;
            }

            Preconditions.checkNotNull(value, "value of " + fieldName + " should not be null");
            final Object readValue = reader.read(value);
            try {
                descriptor.getWriteMethod().invoke(output, readValue);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public List<String> get(final Object input) {
            try {
                final String val = ((FieldReader) reader).write(descriptor.getReadMethod().invoke(input));
                if (allowEmptyValues && (val == null || val.equals(missingValue))) {
                    return ImmutableList.of("");
                } else {
                    return ImmutableList.of(val);
                }
            } catch (final Exception e) {
                throw new IllegalArgumentException(fieldName + " : " + e, e);
            }
        }

        @Override
        public String getFieldDescription(final String field) {
            return reader.getDescription();
        }

        @Override
        public String getDefaultValue(final String field) {
            return allowEmptyValues ? missingValue : "";
        }

        @Override
        public String getSQLType(final String field) {
            return reader.getSQLType();
        }

        @Override
        public String getSQLTypeDef(final String field) {
            return reader.getSQLTypeDef();
        }
    }

    static class PolygonProperty implements IDTOProperty {

        private final String xField, yField, countField;
        private final PropertyDescriptor descriptor;

        public PolygonProperty(final PropertyDescriptor d, final DTOField annotation) {
            Preconditions.checkElementIndex(2, annotation.value().length, "A polygon property must specify 2 fields");
            xField = annotation.value()[0].toLowerCase();
            yField = annotation.value()[1].toLowerCase();
            countField = annotation.value()[2].toLowerCase();
            this.descriptor = d;
        }

        @Override
        public List<String> getFields() {
            return ImmutableList.of(xField, yField, countField);
        }

        @Override
        public List<String> getRequiredFields() {
            return getFields();
        }

        private double[] disect(final String input) {
            final String[] parts = input.replace("{", "").replace("}", "").split(",");
            final double[] result = new double[parts.length];
            for (int i = 0; i < parts.length; i++) {
                result[i] = Double.parseDouble(parts[i].trim());
            }
            return result;
        }

        @Override
        public Optional<String> validate(final IKeyValue fields) {
            final double[] xs;
            final double[] ys;
            try {
                xs = disect(fields.get(xField));
            } catch (final NumberFormatException nfe) {
                return Optional.of(fields.get(xField) + " is not a comma-separated list of numbers");
            }
            try {
                ys = disect(fields.get(yField));
            } catch (final NumberFormatException nfe) {
                return Optional.of(fields.get(yField) + " is not a comma-separated list of numbers");
            }

            if (xs.length != ys.length) {
                return Optional.of("unequal number of x and y points in polygon");
            }

            if (xs.length < 3) {
                return Optional.of("polygon has no interior area");
            }

            SimplePolygon.Builder b = SimplePolygon.builder();
            for (int point = 0; point <= xs.length - 1; point++) {
                b.add(xs[point], ys[point]);
            }
            SimplePolygon p = b.build();
            Storey storey = new Storey();
            storey.setPerimeter(p.toSillyPolygon(), 100);
            if (storey.getArea() <= 0) {
                return Optional.of("Cannot build floor plan");
            }

            return Optional.absent();
        }

        @Override
        public void set(final Object output, final IKeyValue fields) {
            final double[] xs = disect(fields.get(xField));
            final double[] ys = disect(fields.get(yField));

            final SimplePolygon.Builder b = SimplePolygon.builder();
            for (int i = 0; i < xs.length && i < ys.length; i++) {
                b.add(xs[i], ys[i]);
            }

            final SimplePolygon value = b.build();
            try {
                descriptor.getWriteMethod().invoke(output, value);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public List<String> get(final Object input) {
            // return x, y, length

            try {
                final SimplePolygon value = (SimplePolygon) descriptor.getReadMethod().invoke(input);

                return ImmutableList.of(
                        format(value.xs()),
                        format(value.ys()),
                        String.valueOf(value.size())
                );
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        private String format(final double[] ypoints) {
            final StringBuffer out = new StringBuffer();
            for (int i = 0; i < ypoints.length; i++) {
                if (out.length() > 0) {
                    out.append(", ");
                }
                out.append(String.valueOf(ypoints[i]));
            }
            return "{" + out.toString() + "}";
        }

        @Override
        public String getFieldDescription(final String field) {
            if (field.equals(xField)) {
                return "A comma-separated list of numbers, enclosed in braces (x-coordinates)";
            } else if (field.equals(yField)) {
                return "A comma-separated list of numbers, enclosed in braces (y-coordinates)";
            } else if (field.equals(countField)) {
                return "An integer (number of coordinates)";
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public String getDefaultValue(final String field) {
            if (field.equals(xField)) {
                return "{}";
            } else if (field.equals(yField)) {
                return "{}";
            } else if (field.equals(countField)) {
                return "0";
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public String getSQLType(final String field) {
            if (field.equals(countField)) {
                return "integer NOT NULL";
            } else {
                return "varchar(100)";
            }
        }

        @Override
        public String getSQLTypeDef(final String field) {
            return "";
        }
    }

    static class TableProperty implements IDTOProperty {

        final FieldReader<?> valueReader;

        final List<String> fields;
        final List<Object[]> cells;

        private final String missingValue;

        private final boolean allowEmptyValues;

        private final PropertyDescriptor descriptor;

        @SuppressWarnings("rawtypes")
        public TableProperty(final PropertyDescriptor d, final DTOField annotation) {
            this.descriptor = d;
            final String rowName, colName;

            this.missingValue = annotation.constraint().missing();
            this.allowEmptyValues = annotation.constraint().value() != Type.REQUIRED;

            rowName = annotation.value()[0].toLowerCase();
            colName = annotation.value()[1].toLowerCase();
            final TypeToken<?> tt = TypeToken.of(d.getReadMethod().getGenericReturnType());
            try {
                final java.lang.reflect.Type[] types = Table.class.getMethod("put", Object.class, Object.class, Object.class).getGenericParameterTypes();
                final TypeToken<?> rowType = tt.resolveType(types[0]);
                final TypeToken<?> colType = tt.resolveType(types[1]);
                final TypeToken<?> valueType = tt.resolveType(types[2]);

                final ImmutableList.Builder<String> b = ImmutableList.builder();
                final ImmutableList.Builder<Object[]> b2 = ImmutableList.builder();
                if (rowType.getRawType().isEnum() && colType.getRawType().isEnum()) {
                    for (final Object r : rowType.getRawType().getEnumConstants()) {
                        for (final Object c : colType.getRawType().getEnumConstants()) {
                            b.add((rowName + ":" + ((Enum) r).name() + "," + colName + ":" + ((Enum) c).name()).toLowerCase());
                            b2.add(new Object[]{r, c});
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Tables must be keyed on enum");
                }

                fields = b.build();
                cells = b2.build();

                valueReader = FieldReader.of(valueType);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public List<String> getFields() {
            return fields;
        }

        @Override
        public List<String> getRequiredFields() {
            return getFields();
        }

        @Override
        public Optional<String> validate(final IKeyValue fields) {
            for (final String s : getFields()) {
                final String v = fields.get(s);
                if (allowEmptyValues && (v == null || v.isEmpty())) {
                    // fine
                } else {
                    final Optional<String> ve = valueReader.validate(v);
                    if (ve.isPresent()) {
                        return Optional.of(s + ": " + ve.get());
                    }
                }
            }
            return Optional.absent();
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void set(final Object output, final IKeyValue fields) {
            final Iterator<Object[]> rci = cells.iterator();
            final Iterator<String> fi = this.fields.iterator();
            final Table<?, ?, ?> t = HashBasedTable.create();

            while (fi.hasNext() && rci.hasNext()) {
                final String f = fi.next();
                final Object[] rc = rci.next();

                String v = fields.get(f);
                if (allowEmptyValues && (v == null || v.isEmpty())) {
                    v = missingValue;
                }

                ((Table) t).put(rc[0], rc[1], valueReader.read(v));
            }

            try {
                descriptor.getWriteMethod().invoke(output, t);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public List<String> get(final Object input) {

            Table<?, ?, ?> t;
            try {
                t = (Table<?, ?, ?>) descriptor.getReadMethod().invoke(input);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }

            final Iterator<Object[]> rci = cells.iterator();
            final ImmutableList.Builder<String> out = ImmutableList.builder();

            while (rci.hasNext()) {
                final Object[] rc = rci.next();

                final Object v = t.get(rc[0], rc[1]);
                final String s;
                if (v == null) {
                    s = missingValue;
                } else {
                    s = ((FieldReader) valueReader).write(v);
                }

                if (s.equals(missingValue) && allowEmptyValues) {
                    out.add("");
                } else {
                    out.add(s);
                }
            }

            return out.build();
        }

        @Override
        public String getFieldDescription(final String field) {
            return valueReader.getDescription();
        }

        @Override
        public String getDefaultValue(final String field) {
            return allowEmptyValues ? missingValue : "";
        }

        @Override
        public String getSQLType(final String field) {
            return valueReader.getSQLType();
        }

        @Override
        public String getSQLTypeDef(final String field) {
            return valueReader.getSQLTypeDef();
        }
    }

    private static IDTOProperty createProperty(final PropertyDescriptor d, final DTOField annotation) {
        if (annotation.value().length == 1) {
            return new SimpleDTOProperty(d, annotation);
        } else if (d.getPropertyType().isAssignableFrom(SimplePolygon.class)) {
            return new PolygonProperty(d, annotation);
//		} else if (d.getPropertyType().isAssignableFrom(Map.class)) {
//			return new MapProperty(d, annotation);
        } else if (d.getPropertyType().isAssignableFrom(Table.class)) {
            return new TableProperty(d, annotation);
        } else {
            throw new IllegalArgumentException("bad annotation " + annotation);
        }
    }

    public static void getPropertyDescriptors(final Class<?> clazz, final Builder<PropertyDescriptor> output, final Set<Class<?>> seen, final String[] name) {
        if (seen.contains(clazz)) {
            return;
        }
        seen.add(clazz);

        if (clazz.isAnnotationPresent(DTO.class)) {
            if (name[0] == null) {
                name[0] = clazz.getAnnotation(DTO.class).value();
                name[1] = Joiner.on('\n').join(clazz.getAnnotation(DTO.class).description());
            } else {
                throw new IllegalArgumentException("More than one DTO annotation in hierarchy of " + clazz.getName());
            }
        }

        for (final Class<?> c : clazz.getInterfaces()) {
            getPropertyDescriptors(c, output, seen, name);
        }

        if (clazz.getSuperclass() != null) {
            getPropertyDescriptors(clazz.getSuperclass(), output, seen, name);
        }

        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            for (final PropertyDescriptor d : beanInfo.getPropertyDescriptors()) {
                if (d.getReadMethod() == null || d.getWriteMethod() == null) {
                    if (d.getReadMethod() != null && d.getReadMethod().isAnnotationPresent(DTOField.class)) {
                        throw new IllegalArgumentException("Programmer error - DTOField annotation present, but property write method is missing " + d);
                    } else if (d.getWriteMethod() != null && d.getWriteMethod().isAnnotationPresent(DTOField.class)) {
                        throw new IllegalArgumentException("Programmer error - DTOField annotation present, but property read method is missing " + d);
                    }
                    continue;
                }

                if (d.getReadMethod().isAnnotationPresent(DTOField.class) || d.getWriteMethod().isAnnotationPresent(DTOField.class)) {
                    output.add(d);
                }
            }
        } catch (final IntrospectionException e) {
            throw new IllegalArgumentException("Cannot introspect " + clazz.getSimpleName(), e);
        }
    }

    private static void getProperties(final Class<?> clazz, final Builder<IDTOProperty> properties, final String[] name) {
        final Builder<PropertyDescriptor> descriptors = ImmutableList.builder();
        getPropertyDescriptors(clazz, descriptors, new HashSet<Class<?>>(), name);

        for (final PropertyDescriptor d : descriptors.build()) {
            if (d.getReadMethod().isAnnotationPresent(DTOField.class)) {
                properties.add(createProperty(d, d.getReadMethod().getAnnotation(DTOField.class)));
            } else if (d.getWriteMethod().isAnnotationPresent(DTOField.class)) {
                properties.add(createProperty(d, d.getWriteMethod().getAnnotation(DTOField.class)));
            }
        }
    }

    public DTOMapper(final Class<? extends T> clazz) {
        final ImmutableList.Builder<IDTOProperty> properties = ImmutableList.builder();

        final String[] s = new String[2];
        getProperties(clazz, properties, s);
        this.name = s[0];
        this.description = s[1];
        this.properties = properties.build();
    }

    public String getName() {
        return name;
    }

    public List<String> checkHeader(final Set<String> names) {
        final ImmutableList.Builder<String> out = ImmutableList.builder();

        for (final IDTOProperty p : properties) {
            for (final String s : Sets.difference(ImmutableSet.copyOf(p.getRequiredFields()), names)) {
                out.add(s + " is missing");
            }
        }
        return out.build();
    }

    public List<String> checkHeader(final IKeyValue fields) {
        return checkHeader(fields.getKeys());
    }

    public void read(final T instance, final IKeyValue fields) {
        final ImmutableList.Builder<String> errors = ImmutableList.builder();
        for (final IDTOProperty p : properties) {
            final Optional<String> error = p.validate(fields);
            if (error.isPresent()) {
                errors.add(error.get());
            }
        }
        if (errors.build().isEmpty()) {
            for (final IDTOProperty p : properties) {
                p.set(instance, fields);
            }
        } else {
            throw new IllegalArgumentException(Joiner.on(" and ").join(errors.build()));
        }
    }

    public String[] writeHeader() {
        final List<String> out = new ArrayList<>();

        for (final IDTOProperty p : properties) {
            out.addAll(ImmutableList.copyOf(p.getFields()));
        }

        final String[] h = out.toArray(new String[out.size()]);

        return h;
    }

    public String[] write(final T instance) {
        final List<String> out = new ArrayList<>();

        for (final IDTOProperty p : properties) {
            out.addAll(p.get(instance));
        }

        return out.toArray(new String[out.size()]);
    }

    public String getDescription() {
        return description;
    }

    public ImmutableList<IDTOProperty> getProperties() {
        return properties;
    }
}
