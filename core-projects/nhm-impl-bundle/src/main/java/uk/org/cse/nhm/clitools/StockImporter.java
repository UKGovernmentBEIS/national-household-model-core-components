package uk.org.cse.nhm.clitools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.stock.io.StandardJacksonModule;
import uk.org.cse.nhm.stockimport.simple.CSV;
import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;
import uk.org.cse.nhm.stockimport.simple.dto.DTOImportPhase;
import uk.org.cse.nhm.stockimport.simple.spss.SPSSImportPhase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Guice;

public class StockImporter {
    public static final String USAGE = " spss <spss zip file> <output dto zip> | dto <do zip file> <output json.gz file>\n\t run the stock importer";

	public static void main(final String[] args) throws Exception {
		final ImportErrorHandler errors = new ImportErrorHandler() {
                @Override
                public void close() throws Exception {}
			
                @Override
                public void update(final String message) {
                    System.out.println(String.format("INFO: %s", message));
                }
			
                @Override
                public void handle(final Path file, final int line, final String newParam, final String error) {
                    System.err.println(String.format("ERROR: %s:%d (%s) - %s", file, line, newParam, error));
                }
            };

        if (args.length < 3) throw new IllegalArgumentException("");
		switch (args[0]) {
		case "spss":
			final SPSSImportPhase phase = new SPSSImportPhase();

			phase.run(Paths.get(args[1]), Paths.get(args[2]), errors);
			
			break;
		case "dto":
			
			final DTOImportPhase phase2 = new DTOImportPhase();
			
			final List<SurveyCase> run = phase2.run(Paths.get(args[1]), errors);
			
			final ObjectMapper om = Guice.createInjector(new StandardJacksonModule()).getInstance(ObjectMapper.class);
			
			try (final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(Files.newOutputStream(Paths.get(args[2]))), StandardCharsets.UTF_8))) {
				for (final SurveyCase sc : run) {
					bw.write(om.writeValueAsString(sc));
					bw.write("\n");
				}
			}
            break;
		}
	}
}
