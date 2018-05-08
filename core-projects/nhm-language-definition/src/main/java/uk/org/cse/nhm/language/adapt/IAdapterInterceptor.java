package uk.org.cse.nhm.language.adapt;

public interface IAdapterInterceptor {

    public <T> boolean transforms(
            final Object input,
            final T adapted,
            final Class<T> clazz);

    public <T> T transform(
            final Object input,
            final T adapted,
            final Class<T> clazz, IAdaptingScope scope);
}
