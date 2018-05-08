package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.impl.Interview_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.fuel_poverty.impl.Fuel_Poverty_Dataset_2010EntryImpl;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * OccupantDetailsReaderTest.
 *
 * @author richardt
 * @version $Id: OccupantDetailsReaderTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.0.1
 */
@RunWith(MockitoJUnitRunner.class)
public class OccupantDetailsReaderTest extends Mockito {

    private OccupantDetailsReader reader;
    private String executionId = "";

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

    @Before
    public void setUp() throws Exception {
        DateTime surveyDate = new DateTime(2010, 1, 1, 0, 0);

        when(itrFactory.build(ImmutableSet.<Class<?>>of(
                Fuel_Poverty_Dataset_2010EntryImpl.class,
                Interview_09Plus10EntryImpl.class), "")).thenReturn(iteratorProvider);

        reader = new OccupantDetailsReader(executionId, itrFactory, surveyDate);
    }

    @Test
    public void testcalcDateMovedInCreatedCorrectMovedInDate() throws Exception {
        DateTime expectedMovedInDate = new DateTime(2000, 1, 1, 0, 0);
        assertEquals(expectedMovedInDate, reader.calcDateMovedIn(10));
    }
}
