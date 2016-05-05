package uk.org.cse.nhm.simulation.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Provider;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.stock.IHousingStock;
import uk.org.cse.nhm.ipc.api.scenario.IImportStatus;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.stock.io.StockJacksonModule;

/**
 * Simple implementation of stock service, only holds one housing stock, builds stock from a file in Json format.
 * 
 * @author richardt
 * @since 4.0
 */
public class CliStockService implements IStockService {

    public static final String STOCKFILENAME = "stockFileName";

    final ObjectMapper objectMapper;
    final Provider<InputStream> streamProvider;

    final List<SurveyCase> surveyCaseCache;
    private boolean stockCachePopulated = false;

    @Inject
    public CliStockService(@Named(StockJacksonModule.NAME) final ObjectMapper mapper, @Named(STOCKFILENAME) final String stockJsonFile) {
        objectMapper = mapper;

        final Path jsonFilePath = FileSystems.getDefault().getPath(stockJsonFile);

        streamProvider = new Provider<InputStream>() {
            @Override
            public InputStream get() {
                try {
                    return Files.newInputStream(jsonFilePath);
                } catch (final IOException e) {
                    throw new RuntimeException("Could not open " + jsonFilePath, e);
                }
            }
        };

        surveyCaseCache = new ArrayList<>();
    }

    @Override
    public List<? extends IImportStatus> getImportStatuses() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends IHousingStock> getMetadata() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void completeImport(final String name, final IHousingStock metadata, final List<SurveyCase> cases) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void failImport(final String name, final String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean startImport(final String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateImport(final String name, final String message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getImportIDs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IImportStatus getImportStatus(final String id) {
        throw new UnsupportedOperationException();
    }

    public CliStockService(final ObjectMapper mapper, final Provider<InputStream> streamProvider) {
        objectMapper = mapper;
        this.streamProvider = streamProvider;
        surveyCaseCache = new ArrayList<>();
    }

    @Override
    public Set<String> getStockIDs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IHousingStock getMetadata(final String stockID) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SurveyCase> getSurveyCases(final String stockID,
            final Set<String> additionalProperties) {
        List<SurveyCase> surveyCases;
        if (stockCachePopulated) {
            surveyCases = surveyCaseCache;
        } else {
            try {
                buildStockFromJsonFile();
                stockCachePopulated = true;
                surveyCases = surveyCaseCache;
            } catch (final IOException e) {
                throw new RuntimeException("Could not create housing stock", e);
            }
        }

        return surveyCases;
    }

    protected void buildStockFromJsonFile() throws JsonMappingException, JsonParseException, IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(streamProvider.get()));

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            if (StringUtils.isEmpty(line) == false) {
                surveyCaseCache.add(objectMapper.readValue(line, SurveyCase.class));
            }
        }

        reader.close();
    }
}
