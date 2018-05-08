package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;

import uk.org.cse.nhm.spss.values.SpssValueReader;
import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.request.IStockImportItem;

public class HouseCasePropertyReader extends AbsSpssReader<AdditionalHousePropertiesDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HouseCasePropertyReader.class);
    private SpssValueReader reader;
    private List<IStockImportItem> imports;
    private final int CHUNK_SIZE = 1000;

    public HouseCasePropertyReader(String executionId, IHouseCaseSourcesRepositoryFactory mongoProviderFactory, SpssValueReader reader, List<IStockImportItem> imports) {
        super(executionId, mongoProviderFactory);
        this.reader = reader;
        this.imports = imports;
    }

    @Override
    public boolean hasNext() {
        return getProviderIterator().hasNext();
    }

    @Override
    public Collection<AdditionalHousePropertiesDTO> next() {
        SortedSet<String> aacodesForChunk = nextChunk();

        LOGGER.info("Started loading house case properties for chunk of {} houses", aacodesForChunk.size());

        SortedSet<AdditionalHousePropertiesDTO> result = new TreeSet<AdditionalHousePropertiesDTO>(new AACodeComparator());
        Map<String, AdditionalHousePropertiesDTO> resultByAACode = new HashMap<String, AdditionalHousePropertiesDTO>();

        Iterator<IStockImportItem> importsIterator = imports.iterator();

        while (importsIterator.hasNext()) {
            IStockImportItem item = importsIterator.next();
            LOGGER.trace("Loading house properties from file " + item.getFileName());

            Optional<Table<String, String, String>> maybeResult = Optional.absent();
            try {
                maybeResult = reader.load(item.getInputStream(), aacodesForChunk);
            } catch (FileNotFoundException e) {
                LOGGER.error("import", e);
            } catch (IOException e) {
                LOGGER.error("import", e);
            }
            if (maybeResult.isPresent()) {
                Table<String, String, String> valuesByPropertyThenAACode = maybeResult.get();

                for (String aacode : valuesByPropertyThenAACode.columnKeySet()) {
                    setupDTOIfFirstTimeAACodeSeen(result, resultByAACode, aacode);

                    resultByAACode.get(aacode).getValuesByProperty().putAll(valuesByPropertyThenAACode.column(aacode));
                }

                LOGGER.trace("Loaded housecase properties from file " + item.getFileName());
            } else {
                LOGGER.trace("File skipped because it contained multiple entries per aacode " + item.getFileName());
            }
        }

        return result;
    }

    private void setupDTOIfFirstTimeAACodeSeen(Set<AdditionalHousePropertiesDTO> result, Map<String, AdditionalHousePropertiesDTO> resultByAACode, String aacode) {
        if (!resultByAACode.containsKey(aacode)) {
            AdditionalHousePropertiesDTO dto = new AdditionalHousePropertiesDTO();
            dto.setAacode(aacode);
            result.add(dto);
            resultByAACode.put(aacode, dto);
        }
    }

    private SortedSet<String> nextChunk() {
        Iterator<IHouseCaseSources<Object>> providerIterator = getProviderIterator();
        SortedSet<String> aacodes = new TreeSet<String>();
        for (int i = 0; i < CHUNK_SIZE && providerIterator.hasNext(); i++) {
            aacodes.add(providerIterator.next().getAacode());
        }
        return aacodes;
    }

    @Override
    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(HouseCaseDTO.class);
    }

    @Override
    public List<AdditionalHousePropertiesDTO> read(IHouseCaseSources<Object> provider) {
        throw new UnsupportedOperationException("HouseCasePropertyReader needs to output data in chunks, so it does not implement this method.");
    }

    @Override
    protected Class<?> readClass() {
        return AdditionalHousePropertiesDTO.class;
    }

    class AACodeComparator implements Comparator<IBasicDTO> {

        @Override
        public int compare(IBasicDTO o1, IBasicDTO o2) {
            return o1.getAacode().compareTo(o2.getAacode());
        }

    }
}
