package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parboiled.common.ImmutableList;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import uk.org.cse.nhm.spss.values.SpssValueReader;
import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;
import uk.org.cse.stockimport.request.IStockImportItem;

public class HouseCasePropertyReaderTest {

	private TreeBasedTable<String, String, String> valueReaderTable;
	private SpssValueReader valueReader;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		valueReaderTable = TreeBasedTable.<String, String, String>create();
		valueReader = mock(SpssValueReader.class);

		when(valueReader.load((InputStream) any(), (Set<String>) any())).thenReturn(Optional.<Table<String, String, String>>of(valueReaderTable));
	}

	@SuppressWarnings("unchecked")
	private Iterator<IHouseCaseSources<Object>> getHouseIterator(String...aacodes) {
		List<IHouseCaseSources<Object>> result =  new ArrayList<IHouseCaseSources<Object>>();
		for(String aacode : aacodes) {
			IHouseCaseSources<Object> houseCase = mock(IHouseCaseSources.class);
			when(houseCase.getAacode()).thenReturn(aacode);
			result.add(houseCase);
		}

		return result.iterator();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private IHouseCaseSourcesRepositoryFactory buildFactory(String...aacodes) {
		IHouseCaseSourcesRepositoryFactory houseCaseFactory = mock(IHouseCaseSourcesRepositoryFactory.class);

		IHouseCaseSourcesRespository houseCaseSources = mock(IHouseCaseSourcesRespository.class);
		Iterator<IHouseCaseSources<Object>> houseIterator = getHouseIterator(aacodes);
		when(houseCaseSources.iterator()).thenReturn(houseIterator);

		when(houseCaseFactory.build((Iterable<Class<?>>) any(), (String)any())).thenReturn(houseCaseSources);
		return houseCaseFactory;
	}

	@Test
	public void shouldReturnNoDTOsWhenNoFiles() {
		HouseCasePropertyReader reader = new HouseCasePropertyReader("test", buildFactory(), null, Collections.<IStockImportItem>emptyList());

		Collection<AdditionalHousePropertiesDTO> result = reader.next();
		Assert.assertEquals("Should not return any DTOs if not given any import files.", 0, result.size());
	}

	@Test
	public void shouldReturnOneDTOForEveryAACodeInFile() {
		HouseCasePropertyReader reader = new HouseCasePropertyReader("test", buildFactory("H01", "H01"), valueReader, ImmutableList.of(mock(IStockImportItem.class)));

		valueReaderTable.put("aacode", "H01", "H01");
		valueReaderTable.put("aacode", "H02", "H02");
		Collection<AdditionalHousePropertiesDTO> result = reader.next();

		Assert.assertEquals("Should return one DTO for every aacode.", 2, result.size());
		Assert.assertEquals("DTOs returned should have the same aacodes as were read from the file", ImmutableSet.of("H01", "H02"), ImmutableSet.of(Iterables.get(result, 0).getAacode(), Iterables.get(result, 1).getAacode()));
	}

	@Test
	public void dtoShouldMergePropertiesFromAllFiles() throws FileNotFoundException, IOException {
		IStockImportItem first = mock(IStockImportItem.class);
		Table<String, String, String> firstResult = resultForFile(first);
		firstResult.put("aacode", "H01", "H01");
		firstResult.put("firstProperty", "H01", "first");

		IStockImportItem second = mock(IStockImportItem.class);
		Table<String, String, String> secondResult = resultForFile(second);
		secondResult.put("aacode", "H01", "H01");
		secondResult.put("secondProperty", "H01", "second");

		HouseCasePropertyReader reader = new HouseCasePropertyReader("test", buildFactory(), valueReader, ImmutableList.of(first, second));
		Collection<AdditionalHousePropertiesDTO> result = reader.next();

		Assert.assertEquals("Should return one DTO for every aacode.", 1, result.size());

		Map<String, String> valuesByProperty = Iterables.get(result, 0).getValuesByProperty();
		Assert.assertEquals("Should have included property from first file", "first", valuesByProperty.get("firstProperty"));
		Assert.assertEquals("Should have included property from second file", "second", valuesByProperty.get("secondProperty"));
	}

	@SuppressWarnings("unchecked")
	private Table<String, String, String> resultForFile(IStockImportItem file) throws FileNotFoundException, IOException {
		InputStream stream = mock(InputStream.class);
		when(file.getInputStream()).thenReturn(stream);

		Table<String, String, String> result = TreeBasedTable.create();
		when(valueReader.load(eq(stream), (Set<String>) any())).thenReturn(Optional.<Table<String, String, String>>of(result));

		return result;
	}

}
