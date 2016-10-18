package uk.org.cse.boilermatcher.lucene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import uk.org.cse.boilermatcher.sedbuk.ISedbuk;
import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

/**
 * @since 1.0
 */
public class StopwordIndex implements ISedbukIndex {
	private static final String BRAND_STOPWORD_FILE = "/sedbuk/brand_stopwords";
	private static final String MODEL_STOPWORD_FILE = "/sedbuk/model_stopwords";

	final ISedbukIndex delegate;
	
	final HashSet<String> brandStopwords = new HashSet<String>();
	final HashSet<String> modelStopwords = new HashSet<String>();
	
	/**
	 * @since 1.0
	 */
	public StopwordIndex(final ISedbuk sedbuk) throws IOException {
		this(new SedbukIndexCache(new LuceneSedbukIndex(sedbuk)));
	}

	/**
     * @since 1.0
     */
    public StopwordIndex(final ISedbukIndex delegate) throws IOException {
		this.delegate = delegate;
		populate(brandStopwords, BRAND_STOPWORD_FILE);
		populate(modelStopwords, MODEL_STOPWORD_FILE);
	}

	private void populate(final HashSet<String> output, final String resourceName)
			throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream(BRAND_STOPWORD_FILE)));
		try {
			String line = null;

			while ((line = reader.readLine()) != null) {
				output.add(line);
			}
		} finally {
			reader.close();
		}
	}

	@Override
	public IBoilerTableEntry find(String brand, String model,
			final FuelType fuelType, final FlueType flueType, BoilerType boilerType) {
		
		if (boilerType == BoilerType.UNKNOWN) boilerType = null;
		
		brand = clean(brand);
		model = clean(model);
		if (accept(brand, model)) {
			return delegate.find(brand, model, fuelType, flueType, boilerType);
		} else {
			return null;
		}
	}

	private static String clean(final String string) {
		if (string == null) {
			return "";
		}
		
		return string.trim().toLowerCase().replaceAll("[, ]+", " ");
	}

	private boolean accept(final String brand, final String model) {
		if (brand.isEmpty() || model.isEmpty()) return false;
	
		for (final String sw : brandStopwords) {
			if (brand.equals(sw) || (sw.length() > 2 && brand.contains(sw))) {
				return false;
			}
		}
		
		for (final String part : brand.split(" +")) {
			if (brandStopwords.contains(part)) {
				return false;
			}
		}
		
		for (final String part : model.split(" +")) {
			if (modelStopwords.contains(part)) {
				return false;
			}
		}
		
		return true;
	}
}
