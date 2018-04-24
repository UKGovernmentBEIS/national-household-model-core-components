package uk.org.cse.nhm.documentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.reflections.Reflections;

import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOMapper;
import uk.org.cse.stockimport.domain.schema.DTOMapper.IDTOProperty;

/**
 * Creates the documentation for the tables in the standard stock package
 * 
 * @author hinton
 *
 */
public class StockReferenceGenerator {
	public static void main(final String[] args) throws FileNotFoundException, IOException {
		final Reflections r = new Reflections("uk.org.cse.stockimport");
		Files.createDirectories(Paths.get(args[0]).getParent());
		try (final PrintWriter pw = new PrintWriter(Paths.get(args[0]).toFile())) {
			final DocbookWriter writer = new DocbookWriter(pw);
			
			writer.startSection("DTO Import Format");
			
			writer.writeParagraph("The DTO import package should contain the following files.");
			
			for (final Class<?> c : r.getSubTypesOf(IBasicDTO.class)) {
				if (c.isAnnotationPresent(DTO.class)) {
					document(c, writer);
				}
			}
			
			writer.endSection();
		}
		
		
	}

	private static void document(final Class<?> c, final DocbookWriter writer) {
		// we construct a mapper because it already has all the crud about reading annotations and so on.
		final DTOMapper<?> mapper = new DTOMapper<>(c);
		writer.startSection(mapper.getName() + ".csv");
		
		if (!mapper.getDescription().trim().isEmpty()) {
			writer.writeParagraph(mapper.getDescription());
		}
		
		final StringBuffer types = new StringBuffer();
		
		final StringBuffer createTable = new StringBuffer();
		
		createTable.append("CREATE TABLE ");
		createTable.append(mapper.getName());
		createTable.append(" (");
		
		boolean firstField = true;
		for (final IDTOProperty property : mapper.getProperties()) {
			for (final String field : property.getFields()) {
				final String typedef = property.getSQLTypeDef(field);
				
				if (!typedef.isEmpty()) {
					types.append(typedef + "\n");
				}
				
				final String description = property.getFieldDescription(field);
				final String defv = property.getDefaultValue(field);
				
				if (firstField) {
					firstField = false;
				} else {
					createTable.append(",");
				}
				
				createTable.append("\n\t\"");
				createTable.append(field);
				createTable.append("\"\t\t");
				createTable.append(property.getSQLType(field));
				
				writer.writeFormalParagraph(field, description + (defv.isEmpty() ? "" : ". Default is " + defv));
			}
		}
		
		createTable.append("\n);");
		
		writer.startFormalParagraph("SQL");
		writer.writeProgramListing("sql", wrap(types.toString() + createTable.toString(), 90));
		System.out.println(types.toString() + createTable.toString());
		writer.endFormalParagraph();
		
		writer.endSection();
	}

	private static String wrap(final String string, final int cols) {
		final StringBuffer out = new StringBuffer();
		final String[] lines = string.split("\n");
		for (final String line : lines) {		
			final String[] parts = line.split(" ");
			int lw = 0;
			for (final String p : parts) {
				if (lw + p.length() + 1 > cols) {
					out.append("\n    ");
					lw = 4;
				} else {
					out.append(" ");
					lw++;
				}
				out.append(p);
				lw += p.length();
			}
			out.append("\n");
		}
		return out.toString();
	}
}
