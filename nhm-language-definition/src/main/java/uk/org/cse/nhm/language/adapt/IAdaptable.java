package uk.org.cse.nhm.language.adapt;

public interface IAdaptable {
	public boolean adapts(final Class<?> to);
	public <T> T adapt(final Class<T> to, IAdaptingScope scope);
}
