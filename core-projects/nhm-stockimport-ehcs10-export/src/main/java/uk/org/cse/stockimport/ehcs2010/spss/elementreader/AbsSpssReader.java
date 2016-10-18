package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.stockimport.spss.MongoRepositoryHelper.getCollectionName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.spss.elementreader.ISpssReader;

/**
 * Abstract SpssReader provides some helper methods for tasks commonly performed
 * by Spss readers.
 * 
 * @author glenns
 * @version 3.0.0
 * @since 1.0.2
 */
public abstract class AbsSpssReader<DTO extends IBasicDTO> implements ISpssReader<DTO> {
	protected final String executionId;
	private Iterator<IHouseCaseSources<Object>> providerIterator;
	private Iterator<Collection<DTO>> iterator;
	private IHouseCaseSourcesRepositoryFactory houseCasesRepositoryFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbsSpssReader.class);

	/**
	 * Default Constructor.
	 * 
	 * @since 1.0.2
	 */
	public AbsSpssReader(String executionId, IHouseCaseSourcesRepositoryFactory mongoProviderFactory) {
		this.executionId = executionId;
		this.houseCasesRepositoryFactory = mongoProviderFactory;
	}

	public String getName() {
		return getCollectionName(executionId, readClass());
	}

	protected abstract Set<Class<?>> getSurveyEntryClasses();

	/**
	 * Converts an element of one type into another based on a supplied Map.
	 * 
	 * @param element
	 *            element to be looked up
	 * @param conversion
	 *            Lookup table to be queried against
	 * @throws IllegalArgumentException
	 *             if element is not found in conversion
	 * @return value found in the lookup table for the element
	 */
	protected <FROM, TO> TO Lookup(FROM element, Map<FROM, TO> conversion) {
		if (!conversion.containsKey(element)) {
			throw new IllegalArgumentException("Conversion case missing when converting " + element);
		}
		return conversion.get(element);
	}

	public abstract Collection<DTO> read(IHouseCaseSources<Object> provider);

	protected abstract Class<?> readClass();

	/**
	 * Classes that this reader depends on must have been built and saved to the
	 * database before this method is called.
	 */
	protected Iterator<IHouseCaseSources<Object>> getProviderIterator() {
		if (providerIterator == null) {
			providerIterator = houseCasesRepositoryFactory.build(getSurveyEntryClasses(), executionId).iterator();
		}
		return providerIterator;
	}

	/**
	 * Returns all the results in one chunk. Classes which inherit from this
	 * have no need for streaming/lazy behaviour, but want to save in as a big a
	 * batch as possible.
	 */
	protected Iterator<Collection<DTO>> getIterator() {
		if (iterator == null) {
			Builder<DTO> builder = ImmutableList.<DTO> builder();

			while (getProviderIterator().hasNext()) {
				builder.addAll(read(getProviderIterator().next()));
			}
			List<DTO> results = builder.build();
			LOGGER.debug("{} DTO's built by builder {}", results.size(), builder.getClass().getName());

			List<Collection<DTO>> resultCollection = new ArrayList<Collection<DTO>>();
			resultCollection.add(results);
			iterator = resultCollection.iterator();
		}
		return iterator;
	}

	@Override
	public boolean hasNext() {
		return getIterator().hasNext();
	}

	@Override
	public Collection<DTO> next() {
		return getIterator().next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from an Spss reader.");
	}
}
