package uk.org.cse.boilermatcher.lucene;

import java.util.List;
import java.util.Map;

import uk.org.cse.boilermatcher.sedbuk.IBoilerTable;
import uk.org.cse.boilermatcher.sedbuk.ISedbuk;

import com.google.common.collect.ImmutableMap;

/**
 * For some boilers we are failing to make a Sedbuk match. However, we have
 * manually identified a row in the Sedbuk table which we would like to use.
 * 
 * This class lets us lookup those.
 * 
 * @author glenns
 * @since 1.0
 */
public class SedbukFix {
	private final IBoilerTable boilerTable;
	
	/**
     * @since 1.0
     */
	public SedbukFix(final ISedbuk sedbuk) {
		this.boilerTable = sedbuk.getTable(IBoilerTable.class, IBoilerTable.ID);
	}
	
	private final Map<String, Integer> lookup = ImmutableMap.<String, Integer>builder()
		.put("vaillant ecotec plus", 10324)
		.put("vallient ecotec plus", 10324)
		.put("vaillant turbomax", 1822)
		.put("worcester heat slave 15/19", 1736)
		.put("ideal classic lxrs", 5925)
		.put("ideal classic lxrs or rs", 5925)
		.put("ideal classic lxrs + rs", 5925)
		.put("potterton statesman", 8769)
		.put("potterton statesman utility", 8769)
		.put("potterton statesman flowsure plus", 8769)
		.put("remeha avanta plus", 10546)
		.put("broag remeha avanta plus", 10546)
		.put("baxi solo 3 pfl", 8406)
			.build();

	/**
	 * @assumption  Vaillant Ecotec Plus models all have a similar efficiency.
	 *             We've chosen the 630, as it has the same efficiency that CHM
	 *             have used in most cases.
	 * @assumption  Vaillant Turboxmax models all have a similar efficiency.
	 *             We've chosen the VUW 282 EH, as it has the same efficiency
	 *             that CHM have used in most cases.
	 * @assumption  Worcester Heat Slave 15/19 could match 15/19.OSO or
	 *             15/19.RSO. These have the same efficiency so we have chosen
	 *             OSO.
	 * @assumption  Ideal Classic LXRS could match several models with similar
	 *             efficiency. We've chosen LX RS250 as it has the median
	 *             efficiency of those models.
	 * @assumption  Potterton Statesman could match several models with similar
	 *             efficiency. We've chosen 45/50L as it has the same efficiency
	 *             that CHM have used in most cases.
	 * @assumption  Avanta plus could match several models with similar
	 *             efficiency. We've chosen 24s as it has the same efficiency
	 *             that CHM have used in most cases.
	 * @assumption  Solo 3 pfl could match several models with similar
	 *             efficiency. We've chosen 30 as it has the median efficiency
	 *             of those models.
	 * 
	 * @param brand
	 * @param model
	 * @return
	 * @since 1.0
	 */
	public IBoilerTableEntry find(final String brand, final String model) {
		final String lookupKey = (brand + " " + model).toLowerCase().replace(",", "").replaceAll(" +", " ");
		if (lookup.containsKey(lookupKey)) {
			final List<Integer> row = boilerTable.findByProductIndexNumber(lookup.get(lookupKey));
			if (row.size() != 1) {
				throw new RuntimeException("A unique id in SedbukFix was missing from the boiler table: " + lookup.get(lookupKey));
			}
			final IBoilerTableEntry entry = new LuceneSedbukIndex.BoilerTableEntry(boilerTable, row.get(0));
			return entry;
		} else {
			return null;
		}
	}
}
