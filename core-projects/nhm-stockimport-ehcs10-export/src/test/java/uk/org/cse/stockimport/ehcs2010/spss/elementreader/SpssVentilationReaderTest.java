package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.ehcs10.physical.ChimneyEntry;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

@RunWith(MockitoJUnitRunner.class)
public class SpssVentilationReaderTest {

    private SpssVentilationReader reader;

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

	@SuppressWarnings("unchecked")
	@Before
    public void setUp() {
		when(itrFactory.build((Iterable<Class<?>>) any(), anyString())).thenReturn(
                iteratorProvider);
        
        reader = new SpssVentilationReader("", itrFactory);
    }

    @Test
    public void getTotalChimneysNotGreaterThan1() {
        ChimneyEntry first = mock(ChimneyEntry.class);
        when(first.getFRONT_Number()).thenReturn(2);

        ChimneyEntry second = mock(ChimneyEntry.class);
        List<ChimneyEntry> chimneys = ImmutableList.of(first, second);
        reader.getTotalChimneys(chimneys);
    }
}
