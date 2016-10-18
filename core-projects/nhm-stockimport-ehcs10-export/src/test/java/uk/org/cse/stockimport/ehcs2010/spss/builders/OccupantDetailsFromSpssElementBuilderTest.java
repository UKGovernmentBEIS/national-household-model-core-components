package uk.org.cse.stockimport.ehcs2010.spss.builders;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.domain.IOccupantDetailsDTO;

/**
 * OccupantDetailsFromSpssElementBuilderTest.
 *
 * @author richardt
 * @version $Id: OccupantDetailsFromSpssElementBuilderTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class OccupantDetailsFromSpssElementBuilderTest extends Mockito {

    private OccupantDetailsFromSpssElementBuilder builder;
    private final int expectedColumns = 7;

    @Before
    public void setUp() throws Exception {
        builder = new OccupantDetailsFromSpssElementBuilder();
    }

    @Test
    public void testBuilderHasExpectedNumberOfColumnsInHeader() throws Exception {
        final String[] header = builder.buildHeader(null);
        assertEquals("Incorrect Number of columns", expectedColumns, header.length);
    }

    @Test
    public void testRowValuesArePulledFromDTOInCorrectPosition() throws Exception {
        int column = 0;
        final IOccupantDetailsDTO occDetailsDTO = mock(IOccupantDetailsDTO.class);
        when(occDetailsDTO.getAacode()).thenReturn("H00000");
        when(occDetailsDTO.getChiefIncomeEarnersAge()).thenReturn(Optional.of(36));
        when(occDetailsDTO.getDateMovedIn()).thenReturn(Optional.of(new DateTime(2012, 1, 1, 0, 0)));
        when(occDetailsDTO.getHasDisabledOrSickOccupant()).thenReturn(Optional.of(false));
        when(occDetailsDTO.getHasOccupantOnBenefits()).thenReturn(Optional.of(true));
        when(occDetailsDTO.getHouseHoldIncomeBeforeTax()).thenReturn(Optional.of(20000.53));
        when(occDetailsDTO.getWorkingHours()).thenReturn("");

        final String[] row = builder.buildRow(occDetailsDTO);
        assertEquals("Incorrect Number of cells", expectedColumns, row.length);
        assertEquals(String.format("row:%s", column + 1), "H00000", row[column++]);
        assertEquals(String.format("row:%s", column + 1), "36", row[column++]);
        assertEquals(String.format("row:%s", column + 1), "1325376000000", row[column++]);
        assertEquals(String.format("row:%s", column + 1), "false", row[column++]);
        assertEquals(String.format("row:%s", column + 1), "true", row[column++]);
        assertEquals(String.format("row:%s", column + 1), "20000.53", row[column++]);
    }
}
