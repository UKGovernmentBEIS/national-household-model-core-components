package uk.org.cse.nhm.stockimport.simple.spss;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Sets;

import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.LuceneSedbukIndex;
import uk.org.cse.boilermatcher.lucene.SedbukFix;
import uk.org.cse.boilermatcher.lucene.SedbukIndexCache;
import uk.org.cse.boilermatcher.lucene.StopwordIndex;
import uk.org.cse.boilermatcher.sedbuk.Sedbuk;
import uk.org.cse.nhm.ehcs10.derived.impl.Dimensions_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.General_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Interview_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.fuel_poverty.impl.Fuel_Poverty_Dataset_2010EntryImpl;
import uk.org.cse.nhm.ehcs10.interview.impl.PeopleEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.AroundEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.DoorsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ElevateEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.Firstimp_PhysicalEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.InteriorEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.IntroomsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ShapeEntryImpl;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;
import uk.org.cse.nhm.spss.wrap.SavStreamWrapperBuilder;
import uk.org.cse.nhm.stockimport.simple.CSV;
import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;
import uk.org.cse.nhm.stockimport.simple.Metadata;
import uk.org.cse.nhm.stockimport.simple.Util;
import uk.org.cse.nhm.stockimport.simple.dto.AdditionalPropertiesDTOReader;
import uk.org.cse.nhm.stockimport.simple.dto.DTOImportPhase;
import uk.org.cse.nhm.stockimport.simple.logcapture.CapturingAppender;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.RoofDTO;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.LowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.domain.impl.PersonDTO;
import uk.org.cse.stockimport.domain.impl.VentilationDTO;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.impl.WaterHeatingDTO;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.*;
import uk.org.cse.stockimport.repository.HouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;
import uk.org.cse.stockimport.spss.elementreader.ISpssReader;

public class SPSSImportPhase {

    public static final String WARNINGS_CSV = "warnings.csv";

    private static final Map<String, Class<? extends SurveyEntryImpl>> REQUIRED_SPSS_FILES
            = ImmutableMap.<String, Class<? extends SurveyEntryImpl>>builder()
                    .put("derived/dimensions.sav", Dimensions_09Plus10EntryImpl.class)
                    .put("derived/general.sav", General_09Plus10EntryImpl.class)
                    .put("derived/interview.sav", Interview_09Plus10EntryImpl.class)
                    .put("derived/physical.sav", Physical_09Plus10EntryImpl.class)
                    .put("fuel_poverty/fuel_poverty_dataset.sav", Fuel_Poverty_Dataset_2010EntryImpl.class)
                    .put("interview/people.sav", PeopleEntryImpl.class)
                    .put("physical/doors.sav", DoorsEntryImpl.class)
                    .put("physical/elevate.sav", ElevateEntryImpl.class)
                    .put("physical/firstimp_physical.sav", Firstimp_PhysicalEntryImpl.class)
                    .put("physical/flatdets.sav", FlatdetsEntryImpl.class)
                    .put("physical/interior.sav", InteriorEntryImpl.class)
                    .put("physical/introoms.sav", IntroomsEntryImpl.class)
                    .put("physical/services.sav", ServicesEntryImpl.class)
                    .put("physical/shape.sav", ShapeEntryImpl.class)
                    .put("physical/around.sav", AroundEntryImpl.class)
                    .build();

    private static final DateTime SURVEY_DATE = new DateTime(2010, 1, 1, 0, 0);

    public static void main(final String[] args) throws Exception {
        final SPSSImportPhase phase = new SPSSImportPhase();
        final Path p = Paths.get(args[0]);
        final Path output = Paths.get(args[1]);
        try (final ImportErrorHandler e = Util.logFile(p.getParent().resolve("spss-errors.txt"))) {
            phase.run(p, output, e);
        }
    }

    /**
     * @param zipPath the path to an input zip of SPSS files
     * @param resultPath the path to an output zip of DTO files
     * @param errors an error handler to handle bad errors
     * @throws IOException
     */
    public void run(final Path zipPath, final Path resultPath, final ImportErrorHandler errors) throws IOException {
        final Path temporary = Files.createTempDirectory("stock-spss");
        final Path output = Files.createTempDirectory("stock-dto-output");
        try {
            if (Util.unzip(zipPath, temporary, errors) && Util.checkfiles(temporary,
                    Sets.union(REQUIRED_SPSS_FILES.keySet(),
                            ImmutableSet.of(DTOImportPhase.SCHEMA_FILE_NAME, Metadata.PATH)),
                    errors)) {

                errors.update("Creating additional properties file");

                AdditionalPropertiesTransfer.transfer(temporary,
                        output.resolve(AdditionalPropertiesDTOReader.ADDITIONAL_PROPERTIES_FILE + ".csv"));

                errors.update("Loading SPSS files for DTO creation");
                // load all the SPSS files that we care about
                final ListMultimap<String, Object> everything = loadRequiredSpssFiles(temporary, errors);

                errors.update("Creating DTOs");
                final Path outputWarningsPath = output.resolve(WARNINGS_CSV);

                try (final CSV.Writer errorWriter = CSV.writer(Files.newBufferedWriter(outputWarningsPath,
                        StandardCharsets.UTF_8))) {
                    errorWriter.write(new String[]{"aacode", "warning"});

                    final CapturingAppender appender = new CapturingAppender(
                            new CapturingAppender.IHandler() {
                        @Override
                        public void handle(final String code, final String warning) {
                            try {
                                errorWriter.write(new String[]{code, warning});
                            } catch (final IOException e) {
                                errors.handle(outputWarningsPath, 1, "n/a",
                                        "Error writing to warning log : " + e.getMessage());
                            }
                        }
                    }
                    );

                    try {
                        final IHouseCaseSourcesRepositoryFactory mongoProviderFactory = createMongoProviderFactory(everything, appender);

                        final ImmutableList<ISpssReader<? extends IBasicDTO>> readers = createReaders(mongoProviderFactory);

                        final ImmutableList<MappableDTOWriter<?>> writers = createWriters(output);

                        for (final ISpssReader<? extends IBasicDTO> reader : readers) {
                            // to get the current aacode, we have to plug into the mongo provider factory below
                            while (reader.hasNext()) {
                                final Collection<? extends IBasicDTO> result = reader.next();
                                try {
                                    for (final IBasicDTO dto : result) {
                                        // put DTO back into the output so that other things can use it
                                        everything.put(dto.getAacode(), dto);
                                        // write the DTO to the relevant file
                                        for (final MappableDTOWriter<?> writer : writers) {
                                            writer.write(dto);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        everything.clear();

                        for (final MappableDTOWriter<?> writer : writers) {
                            try {
                                writer.close();
                            } catch (final Exception e) {

                            }
                        }
                    } finally {
                        appender.unplug();
                    }
                }

                final Metadata metadata = Metadata.load(temporary.resolve(Metadata.PATH));
                Files.copy(temporary.resolve(DTOImportPhase.SCHEMA_FILE_NAME),
                        output.resolve(DTOImportPhase.SCHEMA_FILE_NAME));
                // could enhance metadata here?

                metadata
                        .replace("original ", "type", "dto")
                        .save(output.resolve(Metadata.PATH));

                Util.createZipFile(output, resultPath);
            }
        } finally {
            try {
                Util.destroy(output);
            } catch (final Exception e) {
            }
            try {
                Util.destroy(temporary);
            } catch (final Exception e) {
            }
        }
    }

    private ImmutableList<MappableDTOWriter<?>> createWriters(final Path output) throws IOException {
        return ImmutableList.<MappableDTOWriter<?>>of(
                new MappableDTOWriter<>(output, SpaceHeatingDTO.class),
                new MappableDTOWriter<>(output, WaterHeatingDTO.class),
                new MappableDTOWriter<>(output, ElevationDTO.class),
                new MappableDTOWriter<>(output, HouseCaseDTO.class),
                new MappableDTOWriter<>(output, LowEnergyLightingDTO.class),
                new MappableDTOWriter<>(output, OccupantDetailsDTO.class),
                new MappableDTOWriter<>(output, RoofDTO.class),
                new MappableDTOWriter<>(output, PersonDTO.class),
                new MappableDTOWriter<>(output, VentilationDTO.class),
                new MappableDTOWriter<>(output, StoreyDTO.class));
    }

    private ImmutableList<ISpssReader<? extends IBasicDTO>> createReaders(
            final IHouseCaseSourcesRepositoryFactory mongoProviderFactory) throws IOException {
        final String executionId = "no-execution-id";

        final Sedbuk sedbuk = new Sedbuk();
        final SedbukFix sedbukFix = new SedbukFix(sedbuk);
        final ISedbukIndex index = new StopwordIndex(new SedbukIndexCache(new LuceneSedbukIndex(sedbuk)));

        return ImmutableList.<ISpssReader<? extends IBasicDTO>>of(
                new SpssHouseCaseReader(executionId, mongoProviderFactory),
                new SpssStoreyReader(executionId, mongoProviderFactory),
                new SpssElevationReader(executionId, mongoProviderFactory),
                new SpssRoofReader(executionId, mongoProviderFactory),
                new SpssLowEnergyLightingReader(executionId, mongoProviderFactory),
                new SpssSpaceHeatingReader(executionId, mongoProviderFactory, sedbukFix, index),
                new SpssHotWaterReader(executionId, mongoProviderFactory),
                new SpssVentilationReader(executionId, mongoProviderFactory),
                new OccupantDetailsReader(executionId, mongoProviderFactory, SURVEY_DATE),
                new SpssPersonReader(executionId, mongoProviderFactory)
        );
    }

    private IHouseCaseSourcesRepositoryFactory createMongoProviderFactory(
            final ListMultimap<String, Object> everything, final CapturingAppender appender) {
        return new IHouseCaseSourcesRepositoryFactory() {
            @Override
            public IHouseCaseSourcesRespository<Object> build(final Iterable<Class<?>> objectsToProvide,
                    final String executionId) {
                return new IHouseCaseSourcesRespository<Object>() {
                    final Iterator<String> keyIterator = ImmutableSet.copyOf(everything.keySet()).iterator();

                    @Override
                    public Iterator<IHouseCaseSources<Object>> iterator() {
                        return new Iterator<IHouseCaseSources<Object>>() {
                            @Override
                            public boolean hasNext() {
                                final boolean b = keyIterator.hasNext();
                                if (!b) {
                                    appender.unplug();
                                }
                                return b;
                            }

                            @Override
                            public IHouseCaseSources<Object> next() {
                                final String code = keyIterator.next();
                                appender.plug(code);
                                return HouseCaseSources.withMutableList(code, 2010, everything.get(code));
                            }

                            @Override
                            public void remove() {
                                throw new UnsupportedOperationException();
                            }
                        };
                    }
                };
            }
        };
    }

    /**
     * Because our SPSS files are not in-order and I am too lazy to sort that
     * out, we just load everything and then grind through one at a time
     *
     * @param temporary
     * @param errors
     * @return
     * @throws IOException
     */
    private ListMultimap<String, Object> loadRequiredSpssFiles(final Path temporary, final ImportErrorHandler errors) {
        final ListMultimap<String, Object> loadedFiles = ArrayListMultimap.create();

        try {
            final SavStreamWrapperBuilder sswb = new SavStreamWrapperBuilder();
            for (final Map.Entry<String, Class<? extends SurveyEntryImpl>> c : REQUIRED_SPSS_FILES.entrySet()) {
                try (final java.io.InputStream stream = Files.newInputStream(temporary.resolve(c.getKey()))) {
                    final SavInputStreamImpl sis = new SavInputStreamImpl(stream);

                    final Iterator<? extends SurveyEntryImpl> beans = sswb.wrapBean(sis, c.getValue());
                    while (beans.hasNext()) {
                        final SurveyEntryImpl bean = beans.next();
                        loadedFiles.put(bean.getAacode(), bean);
                    }
                } catch (final IOException e) {
                    errors.handle(temporary.resolve(c.getKey()), 0, "n/a",
                            "IO error reading spss file " + e.getMessage());
                } catch (NoSuchMethodException | SecurityException e) {
                    throw new IllegalArgumentException("System misconfigured " + e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadedFiles.size();
        return loadedFiles;
    }
}
