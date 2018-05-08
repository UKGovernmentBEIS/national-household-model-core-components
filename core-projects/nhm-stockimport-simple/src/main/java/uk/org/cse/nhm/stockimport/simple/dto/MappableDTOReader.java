package uk.org.cse.nhm.stockimport.simple.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOMapper;
import uk.org.cse.stockimport.domain.schema.IDTOReader;
import uk.org.cse.stockimport.domain.schema.IKeyValue;
import uk.org.cse.stockimport.domain.schema.InvalidHeaderException;
import uk.org.cse.stockimport.domain.schema.InvalidRowException;

public class MappableDTOReader<Q> implements IDTOReader<Q> {

    final DTOMapper<Q> mapper;
    final Class<Q> clazz;
    final String filename;
    private final boolean required;

    public MappableDTOReader(final Class<Q> clazz) {
        this.clazz = clazz;
        this.mapper = new DTOMapper<>(clazz);
        final DTO annotation = findAnnotation(clazz);

        if (annotation == null) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " does not have a DTO annotation");
        }

        filename = annotation.value();
        required = annotation.required();
    }

    private static DTO findAnnotation(final Class<?> clazz) {
        if (clazz.isAnnotationPresent(DTO.class)) {
            return clazz.getAnnotation(DTO.class);
        } else {
            for (final Class<?> c : clazz.getInterfaces()) {
                final DTO fn = findAnnotation(c);
                if (fn != null) {
                    return fn;
                }
            }
            if (clazz.getSuperclass() != null) {
                return findAnnotation(clazz.getSuperclass());
            }
        }
        return null;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public void checkHeaders(final List<String> headers) throws InvalidHeaderException {
        final List<String> errors = mapper.checkHeader(ImmutableSet.copyOf(headers));
        if (errors.isEmpty() == false) {
            throw new InvalidHeaderException(errors);
        }
    }

    @Override
    public Q read(final IKeyValue row) throws InvalidRowException {
        final Q object = newInstance();

        try {
            mapper.read(object, row);
        } catch (final IllegalArgumentException iae) {
            throw new InvalidRowException(iae.getMessage(), iae);
        }

        return object;
    }

    private Q newInstance() {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(clazz.getSimpleName() + " has no usable constructor (" + e.getMessage() + ")", e);
        }
    }

    @Override
    public boolean isRequired() {
        return required;
    }
}
