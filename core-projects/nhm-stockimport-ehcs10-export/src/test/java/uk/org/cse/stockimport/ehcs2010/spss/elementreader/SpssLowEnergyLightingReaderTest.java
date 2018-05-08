/**
 *
 */
package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.IntroomsEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.IntroomsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * @author glenns
 */
@RunWith(MockitoJUnitRunner.class)
public class SpssLowEnergyLightingReaderTest {

    private SpssLowEnergyLightingReader reader;
    private final double errorDelta = 0.000001;

    private IntroomsEntry missing;
    private IntroomsEntry trueCase;
    private IntroomsEntry falseCase;
    private IntroomsEntry doubleWeightingLowEnergy;

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

    @Before
    public void setUp() {
        when(itrFactory.build(ImmutableSet.<Class<?>>of(IntroomsEntryImpl.class), "")).thenReturn(iteratorProvider);
        reader = new SpssLowEnergyLightingReader("", itrFactory);

        missing = mock(IntroomsEntry.class);
        setupEntry(missing, Enum10.__MISSING);
        trueCase = mock(IntroomsEntry.class);
        setupEntry(trueCase, Enum10.Yes);
        falseCase = mock(IntroomsEntry.class);
        setupEntry(falseCase, Enum10.No);
        doubleWeightingLowEnergy = mock(IntroomsEntry.class);
        setupEntry(doubleWeightingLowEnergy, Enum10.Yes, Enum1650.LivingRoom);

    }

    private void setupEntry(IntroomsEntry entry, Enum10 result) {
        setupEntry(entry, result, Enum1650.Bedroom);
    }

    private void setupEntry(IntroomsEntry entry, Enum10 result, Enum1650 room) {
        when(entry.getHEATING_SERVICES_FlourescentLowEnergyLighting()).thenReturn(result);
        when(entry.getRoom()).thenReturn(room);
    }

    @Test
    public void fractionForNoRoomsShouldBe1() {
        assertEquals("Reader should return a fraction of 1.0 for a house with no rooms",
                1.0,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>()),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void fractionForMissingRoomsOnlyShouldBe1() {
        assertEquals("Reader should return a fraction of 1.0 for a house with only missing rooms",
                1.0,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(missing);
                    }
                }),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void fractionForMissingAndLowEnergyRoomsOnlyShouldBe1() {
        assertEquals("Reader should return a fraction of 1.0 for a house with only low energy or missing rooms",
                1.0,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(missing);
                        add(trueCase);
                    }
                }),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void fractionWithNoLowEnergyRoomsShouldBe0() {
        assertEquals("Reader should return a fraction of 0.0 for a house without any low energy rooms",
                0.0,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(missing);
                        add(falseCase);
                    }
                }),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void fractionWithOneRoomOfEachKindShouldBeHalf() {
        assertEquals(
                "Reader should return a fraction of 0.5 for a house with an even mixture of low energy, not low energy, and missing (when weightings are even).",
                0.5,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(missing);
                        add(trueCase);
                        add(falseCase);
                    }
                }),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void fractionWithOneDoubleWeightRoomShouldBeUnaffected() {
        assertEquals("Reader should return a fraction of 1.0 for a house with one double-weight low energy room.",
                1.0,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(doubleWeightingLowEnergy);
                    }
                }),
                errorDelta);
    }

    @SuppressWarnings("serial")
    @Test
    public void oneDoubleWeightRoomShouldBeWorthTwoSingleWeightRooms() {
        assertEquals(
                "Reader should return a fraction of 0.5 for a house with one double-weight low energy and two single-weight high energy rooms.",
                0.5,
                reader.getRoomLightingFraction("", new ArrayList<IntroomsEntry>() {
                    {
                        add(doubleWeightingLowEnergy);
                        add(falseCase);
                        add(falseCase);
                    }
                }),
                errorDelta);
    }
}
