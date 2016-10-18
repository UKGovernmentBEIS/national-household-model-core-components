package uk.org.cse.boilermatcher.lucene;

import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;


/**
 * An interface to sedbuk which provides some fuzzy searching
 * 
 * @author hinton
 * @since 1.0
 */
public interface ISedbukIndex {
	/**
	 * Find the "best" boiler table entry in the sedbuk matching on the given fields; if any fields are null
	 * they will be excluded from the match.
	 * 
	 * @param manufacturer
	 * @param brand
	 * @param model
	 * @param qualifier
	 * @param fuelType
	 * @param flueType
	 * @param boilerType
	 * @return the best match, or null if there is no acceptably good match
	 * @since 1.0
	 */
	public IBoilerTableEntry find(
			final String brand,
			final String model,
			final FuelType fuelType,
			final FlueType flueType,
			final BoilerType boilerType
			);
}
