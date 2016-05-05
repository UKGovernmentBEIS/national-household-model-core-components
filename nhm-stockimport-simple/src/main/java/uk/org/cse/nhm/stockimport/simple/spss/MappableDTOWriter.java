package uk.org.cse.nhm.stockimport.simple.spss;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import uk.org.cse.nhm.stockimport.simple.CSV;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOMapper;

public class MappableDTOWriter<Q> implements AutoCloseable {
	final DTOMapper<Q> mapper;
	final Class<Q> clazz;
	final String filename;
	
	private final CSV.Writer writer;
	
	public MappableDTOWriter(final Path directory, final Class<Q> clazz) throws IOException {
		this.clazz = clazz;
		this.mapper = new DTOMapper<>(clazz);
		final DTO annotation = findAnnotation(clazz);
		
		if (annotation == null) {
			throw new IllegalArgumentException(clazz.getSimpleName() + " does not have a DTO annotation");			
		}
		
		filename = annotation.value();
		
		writer = CSV.writer(Files.newBufferedWriter(directory.resolve(filename + ".csv"), StandardCharsets.UTF_8));
	
		writer.write(mapper.writeHeader());
	}
	
	@Override
	public void close() throws Exception {
		writer.close();
	}
	
	private static DTO findAnnotation(final Class<?> clazz) {
		if (clazz.isAnnotationPresent(DTO.class)) {
			return clazz.getAnnotation(DTO.class);
		} else {
			for (final Class<?> c : clazz.getInterfaces()) {
				final DTO fn = findAnnotation(c);
				if (fn != null) return fn;
			}
			if (clazz.getSuperclass() != null) {
				return findAnnotation(clazz.getSuperclass());
			}
		}
		return null;
	}

	public void write(final Object value) throws IOException {
		if (clazz.isInstance(value)) {
			writer.write(mapper.write(clazz.cast(value)));
		}
	}
}
