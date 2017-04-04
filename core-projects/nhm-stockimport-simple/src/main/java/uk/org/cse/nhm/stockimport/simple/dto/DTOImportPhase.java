package uk.org.cse.nhm.stockimport.simple.dto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;
import uk.org.cse.nhm.stockimport.simple.Metadata;
import uk.org.cse.nhm.stockimport.simple.Stock;
import uk.org.cse.nhm.stockimport.simple.Util;
import uk.org.cse.nhm.stockimport.simple.logcapture.CapturingAppender;
import uk.org.cse.nhm.stockimport.simple.logcapture.CapturingAppender.IHandler;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.RoofDTO;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.LowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.domain.impl.PersonDTO;
import uk.org.cse.stockimport.domain.impl.VentilationDTO;
import uk.org.cse.stockimport.domain.importlog.WarningDTO;
import uk.org.cse.stockimport.domain.schema.IDTOReader;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.impl.WaterHeatingDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuilder;
import uk.org.cse.stockimport.hom.ISurveyCaseBuilderFactory;
import uk.org.cse.stockimport.hom.impl.DefaultSurveyCaseBuilderFactory;
import uk.org.cse.stockimport.imputation.ISchemaForImputation;
import uk.org.cse.stockimport.imputation.ImputationSchema;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.CeilingPropertyTableBuilder;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.DoorPropertyTablesBuilder;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.FloorPropertyTableBuilder;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.HousePropertyTablesBuilder;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.WallPropertyTablesBuilder;
import uk.org.cse.stockimport.imputation.lookupbuilders.excel.WindowPropertyTablesBuilder;
import uk.org.cse.stockimport.repository.HouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.NoSuchDTOException;

public class DTOImportPhase {
	final ISurveyCaseBuilderFactory caseBuilderFactory = new DefaultSurveyCaseBuilderFactory();
	
	private final static List<IDTOReader<? extends IBasicDTO>> allReaders = 
        ImmutableList.<IDTOReader<? extends IBasicDTO>>builder()
        .add(new MappableDTOReader<>(SpaceHeatingDTO.class))
        .add(new MappableDTOReader<>(WaterHeatingDTO.class))
        .add(new MappableDTOReader<>(ElevationDTO.class))
        .add(new MappableDTOReader<>(HouseCaseDTO.class))
        .add(new MappableDTOReader<>(LowEnergyLightingDTO.class))
        .add(new MappableDTOReader<>(OccupantDetailsDTO.class))
        .add(new MappableDTOReader<>(RoofDTO.class))
        .add(new MappableDTOReader<>(StoreyDTO.class))
        .add(new MappableDTOReader<>(WarningDTO.class))
        .add(new MappableDTOReader<>(PersonDTO.class))
        .add(new MappableDTOReader<>(VentilationDTO.class))
        .add(new AdditionalPropertiesDTOReader())
        .build();

	public static final String SCHEMA_FILE_NAME = "imputation-schema.xlsx";

	private static final int MAX_BAD_CASES = 100;
	
	public static void main(final String[] args) throws IOException, Exception {
		Preconditions.checkElementIndex(0, args.length, "At least one argument is required");
		
		final DTOImportPhase phase = new DTOImportPhase();

		final Path dir = Paths.get(args[0]);
		try (final ImportErrorHandler e = Util.logFile(args.length == 1 ? dir.getParent().resolve("errors.txt") : Paths.get(args[1]))) {			
			phase.run(dir, e);
		}
	}
	
	public Stock load(final Path zipfile, final ImportErrorHandler errors) throws IOException {
		final Path temporary = Files.createTempDirectory("stock-import-dto");
		final ImmutableList.Builder<SurveyCase> builder = ImmutableList.builder();
		
		int count = 0, good = 0, bad = 0;
		try {
            if (Util.unzip(zipfile, temporary, errors) && checkfiles(temporary, errors) && Util.sort(temporary, errors)) {
                try (final MultipleDTOIterator delegate = 
                     new MultipleDTOIterator(temporary,allReaders, errors)) {
                    if (delegate.validationFailed) {
                        count++;
                    }
			
                    final ISchemaForImputation imputationSchema;
                    final Path resolve = temporary.resolve(SCHEMA_FILE_NAME);
			
                    try {
                        imputationSchema = getImputationSchema(resolve);
                    } catch (final IOException e) {
                        errors.handle(resolve, 0, "unknown", "Error reading imputation schema: " + e.getMessage());
                        throw new IllegalArgumentException("Bad schema");
                    }
			
                    final Iterable<Optional<SurveyCase>> read = read(delegate, imputationSchema, errors);
                    for (final Optional<SurveyCase> sc : read) {
                        builder.addAll(sc.asSet());
                        count++;
                        if (sc.isPresent()) good++;
                        else bad++;
                        if (bad > MAX_BAD_CASES) {
                            errors.update("Over " + MAX_BAD_CASES + " cases have errors; terminating early.");
                            break;
                        }
                    }
                    errors.update(String.format("%d / %d cases imported OK", good, count));
                }
            } else {
                count = 1;
            }
		
            final Metadata md = Metadata.load(temporary.resolve(Metadata.PATH));
            return new Stock(md, builder.build(), count - good);
        } finally {
            try {
                Util.destroy(temporary);
            } catch (final Exception ex) {}
        }
	}
	
	public List<SurveyCase> run(final Path zipfile, final ImportErrorHandler errors) throws IOException {
		return load(zipfile, errors).cases;
	}
	
	private boolean checkfiles(final Path temporary, final ImportErrorHandler errors) {
		final Set<String> toCheck = new HashSet<String>();
		for (final IDTOReader<?> reader : allReaders) {
			if (reader.isRequired()) {
				toCheck.add(reader.getFilename() + ".csv");
			}
		}
		toCheck.add(SCHEMA_FILE_NAME);
		toCheck.add(Metadata.PATH);
		return Util.checkfiles(temporary, toCheck, errors);
	}

	private ISchemaForImputation getImputationSchema(final Path directory) throws IOException {
		final ImputationSchema imputationSchema = new ImputationSchema(directory.toString());
		final XSSFWorkbook workbook = new XSSFWorkbook(
					Files.newInputStream(directory)
				);
		
		//Build the imputation tables
		imputationSchema.setHousePropertyTables(new HousePropertyTablesBuilder().buildTables(workbook));
		imputationSchema.setCeilingUValueTables(new CeilingPropertyTableBuilder().buildTables(workbook));
		imputationSchema.setDoorPropertyImputer(new DoorPropertyTablesBuilder().buildTables(workbook));
		imputationSchema.setFloorPropertyTables(new FloorPropertyTableBuilder().buildTables(workbook));
		imputationSchema.setWallPropertyTables(new WallPropertyTablesBuilder().buildTables(workbook));
		imputationSchema.setWindowPropertyTables(new WindowPropertyTablesBuilder().buildTables(workbook));
		
		return imputationSchema;
	}
	
	private Iterable<Optional<SurveyCase>> read(final Iterator<List<IBasicDTO>> delegate, 
			final ISchemaForImputation imputationSchema, final ImportErrorHandler errors) {
		return new Iterable<Optional<SurveyCase>>() {
			@Override
			public Iterator<Optional<SurveyCase>> iterator() {
				final ISurveyCaseBuilder surveyCaseBuilder = caseBuilderFactory.getSurveyCaseBuilder(imputationSchema);
				
				return new Iterator<Optional<SurveyCase>>() {
					@Override
					public boolean hasNext() {
						return delegate.hasNext();
					}

					@Override
					public Optional<SurveyCase> next() {
						final List<IBasicDTO> dtos = delegate.next();
						
						final LinkedList<IBasicDTO> modifiabledtos = new LinkedList<>(dtos);
						
						if (!dtos.isEmpty()) {
							final String aacode = dtos.get(0).getAacode();
							try {
								final CapturingAppender appender = new CapturingAppender(new IHandler() {
									@Override
									public void handle(final String code, final String warning) {
										final WarningDTO warningDTO = new WarningDTO();
										warningDTO.setAacode(code);
										warningDTO.setMessage(warning);
										modifiabledtos.add(warningDTO);
									}
								});
								final IHouseCaseSources<?> provider = 
										HouseCaseSources.withMutableList(aacode, 2010, modifiabledtos);

								try {
									appender.plug(aacode);
									return Optional.of(surveyCaseBuilder.build(provider));
								} finally {
									appender.unplug();
								}
							} catch (final NoSuchDTOException dto) {
								errors.handle(Paths.get("unknown"), 1, aacode, "No " + dto.getDTOClass().getSimpleName() + " for " + dto.getAacode());
							} catch (final Exception ex) {
								errors.handle(Paths.get("unknown"), 1, aacode, "Error: " + ex.getMessage());
								ex.printStackTrace();
							}
						}
						return Optional.absent();
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
