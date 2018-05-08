package uk.org.cse.nhm.clitools.bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.bundle.api.IFS;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.stock.IHousingStock;
import uk.org.cse.nhm.ipc.api.scenario.IImportStatus;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;

class FSStockService<P> implements IStockService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FSStockService.class);

    private final IFS<P> fs;
    private final ObjectMapper mapper;
    private final Map<String, P> restrict;

    private P top;

    public FSStockService(
            IFS<P> fs,
            P top,
            ObjectMapper mapper) {
        super();
        this.fs = fs;
        this.top = top;
        this.mapper = mapper;
        this.restrict = ImmutableMap.of();
    }

    public FSStockService(
            IFS<P> fs,
            Map<String, P> restrict,
            ObjectMapper mapper) {
        this.fs = fs;
        this.top = null;
        this.mapper = mapper;
        this.restrict = restrict;
    }

    @Override
    public List<SurveyCase> getSurveyCases(final String stockName, Set<String> extraVars) {
        final Path path;
        if (restrict.isEmpty()) {
            path = fs.filesystemPath(fs.resolve(String.valueOf(top), stockName));
        } else {
            if (restrict.containsKey(stockName)) {
                path = fs.filesystemPath(restrict.get(stockName));
            } else {
                throw new IllegalArgumentException("Stock " + stockName + " not in restrict set " + restrict);
            }
        }

        final ImmutableList.Builder<SurveyCase> builder = ImmutableList.builder();
        if (Files.exists(path)) {
            try (final BufferedReader r = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(path)),
                    StandardCharsets.UTF_8))) {
                String line;
                final Set<String> lcExtraVars = new HashSet<>();
                for (final String ev : extraVars) {
                    lcExtraVars.add(ev.toLowerCase());
                }

                final Set<String> missingProperties = new HashSet<>();
                final ClassLoader ccl = Thread.currentThread().getContextClassLoader();

                try {
                    Thread.currentThread().setContextClassLoader(SurveyCase.class.getClassLoader());

                    while ((line = r.readLine()) != null) {
                        if (!line.isEmpty()) {

                            final SurveyCase sc = mapper.readValue(line, SurveyCase.class);

                            Map<String, String> additionalProperties = sc.getAdditionalProperties();
                            if (additionalProperties == null) {
                                additionalProperties = Collections.emptyMap();
                            }

                            final ImmutableMap.Builder<String, String> b = ImmutableMap.builder();
                            for (final Map.Entry<String, String> e : additionalProperties.entrySet()) {
                                if (lcExtraVars.contains(e.getKey().toLowerCase())) {
                                    b.put(e.getKey().toLowerCase(), e.getValue());
                                }
                            }

                            sc.setAdditionalProperties(b.build());

                            missingProperties.addAll(Sets.difference(lcExtraVars, sc.getAdditionalProperties().keySet()));

                            builder.add(sc);
                        }
                    }
                } finally {
                    Thread.currentThread().setContextClassLoader(ccl);
                }

                if (!missingProperties.isEmpty()) {
                    log.warn("Stock {} missing properties {} on some cases", path, missingProperties);
                }

                return builder.build();
            } catch (final Exception e) {
                log.error("Error reading {}", path, e);
                throw new RuntimeException(e.getMessage() + " reading " + path, e);
            }
        } else {
            throw new RuntimeException("No stock at " + path);
        }
    }

    private void die() {
        throw new UnsupportedOperationException("This stock service can only read stocks from disk");
    }

    @Override
    public void completeImport(String arg0, IHousingStock arg1, List<SurveyCase> arg2) {
        die();
    }

    @Override
    public void failImport(String arg0, String arg1) {
        die();
    }

    @Override
    public Set<String> getImportIDs() {
        die();
        return null;
    }

    @Override
    public IImportStatus getImportStatus(String arg0) {
        die();
        return null;
    }

    @Override
    public List<? extends IImportStatus> getImportStatuses() {
        die();
        return null;
    }

    @Override
    public List<? extends IHousingStock> getMetadata() {
        die();
        return null;
    }

    @Override
    public IHousingStock getMetadata(String arg0) {
        die();
        return null;
    }

    @Override
    public Set<String> getStockIDs() {
        die();
        return null;
    }

    @Override
    public boolean startImport(String arg0) {
        die();
        return false;
    }

    @Override
    public void updateImport(String arg0, String arg1) {
        die();
    }
}
