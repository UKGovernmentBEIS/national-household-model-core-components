package uk.org.cse.boilermatcher.lucene;

import java.util.concurrent.ExecutionException;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @since 1.0
 */
public class SedbukIndexCache implements ISedbukIndex {
	private LoadingCache<Key, Optional<IBoilerTableEntry>> cache;

	/**
     * @since 1.0
     */
    public SedbukIndexCache(final ISedbukIndex delegate) {
		cache = CacheBuilder.newBuilder().softValues().build(new CacheLoader<Key, Optional<IBoilerTableEntry>>() {
			@Override
			public Optional<IBoilerTableEntry> load(Key key) throws Exception {
				 IBoilerTableEntry find = delegate.find(key.brand, key.model, key.fuelType, key.flueType, key.boilerType);
				 if (find == null) {
					 return Optional.absent();
				 } else {
					 return Optional.of(find);
				 }
			}
		});
	}
	
	@AutoProperty
	private static class Key {
		public final String brand;
		public final String model;
		public final FuelType fuelType;
		public final FlueType flueType;
		public final BoilerType boilerType;
		
		public Key(String brand, String model, FuelType fuelType, FlueType flueType, BoilerType boilerType) {
			this.brand = brand;
			this.model = model;
			this.fuelType = fuelType;
			this.flueType = flueType;
			this.boilerType = boilerType;
		}
		
		@Override
		public int hashCode() {
			return Pojomatic.hashCode(this);
		}
		
		@Override
		public boolean equals(Object obj) {
			return Pojomatic.equals(this, obj);
		}
	}
	
	@Override
	public IBoilerTableEntry find(String brand, String model, FuelType fuelType, FlueType flueType,BoilerType boilerType) {
		try {
			return cache.get(new Key(brand, model, fuelType,flueType, boilerType)).orNull();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
