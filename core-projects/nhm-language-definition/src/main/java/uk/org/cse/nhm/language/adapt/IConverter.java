package uk.org.cse.nhm.language.adapt;

/**
 * Marker interface for converters
 * @author hinton
 *
 */
public interface IConverter extends IAdapter {
	public Class<?> getAdaptableSupertype(final Class<?> clazz);
}
