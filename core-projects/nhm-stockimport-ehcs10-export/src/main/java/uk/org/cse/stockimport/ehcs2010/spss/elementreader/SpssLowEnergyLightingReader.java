/**
 * 
 */
package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.IntroomsEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.IntroomsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.domain.impl.LowEnergyLightingDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * SpssLowEnergyLightingReader calculates the fraction of LowEnergyLight used
 * for each house in the EHS survey. Does this as the proportion of rooms in the
 * house which have low energy lighting installed. Some rooms are weighted to be
 * more significant.
 * 
 * @author glenns
 * @since 1.0
 */
public class SpssLowEnergyLightingReader extends AbsSpssReader<ILowEnergyLightingDTO> {

	/**
	 * @since 1.0
	 */
	public SpssLowEnergyLightingReader(String executionId, IHouseCaseSourcesRepositoryFactory providerFactory) {
		super(executionId, providerFactory);
	}

	@Override
	protected Set<Class<?>> getSurveyEntryClasses() {
		return ImmutableSet.<Class<?>> of(IntroomsEntryImpl.class);
	}

	/**
	 * @since 1.0
	 */
	public List<ILowEnergyLightingDTO> read(IHouseCaseSources<Object> provider) {
		return Arrays.<ILowEnergyLightingDTO> asList(new LowEnergyLightingDTO(provider.getAacode(), getRoomLightingFraction(provider.getAacode(),
				provider.getAll(IntroomsEntry.class))));
	}

	/**
	 * Calculates the low energy lighting fraction for a house based on its
	 * rooms and the weighting assigned to each of them. Missing rooms are not
	 * counted. If, after missing rooms are discounted, there are 0 rooms for
	 * this house, returns 1.0 as the fraction.
	 * 
	 * @param aacode
	 *            The EHS Housing Code
	 * @param rooms
	 *            All Introoms entries associated with the aacode.
	 * @return A decimal <= 1.0 representing what proportion of the house is lit
	 *         by low energy lights.
	 * @since 1.0
	 */
	public double getRoomLightingFraction(final String aacode, final List<IntroomsEntry> entries) {
		double numerator = 0;
		double denominator = 0;
		for (IntroomsEntry room : entries) {
			if (room.getHEATING_SERVICES_FlourescentLowEnergyLighting() == Enum10.__MISSING) {
				// No-op
			} else {
				int weighting = getRoomWeighting(room.getRoom());
				denominator += weighting;
				if (room.getHEATING_SERVICES_FlourescentLowEnergyLighting() == Enum10.Yes) {
					numerator += weighting;
				}
			}
		}
		return denominator == 0 ? 1.0 : numerator / denominator;
	}

	private Map<Enum1650, Integer> roomWeightings = ImmutableMap
			.of(Enum1650.LivingRoom, 2, Enum1650.Bedroom, 1, Enum1650.Circulation, 2, Enum1650.Bathroom, 1, Enum1650.Kitchen, 2);

	/**
	 * Gets the weighting for a room to be used in the Low Energy Lighting
	 * calculation, as specified in Bredem 8 section 4.2.
	 * 
	 * @throws IllegalArgumentException
	 *             for unknown room types.
	 * @param roomType
	 * @return An integer weighting.
	 * @since 1.0
	 */
	public int getRoomWeighting(Enum1650 roomType) {
		return Lookup(roomType, roomWeightings);
	}

	@Override
	protected Class<?> readClass() {
		return LowEnergyLightingDTO.class;
	}
}
