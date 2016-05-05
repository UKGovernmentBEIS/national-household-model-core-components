package uk.org.cse.stockimport.repository;


/**
 * IHouseCaseElementProviderFactory.
 *
 * @author richardt
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z richardt $
 * @since 2.0
 */
public interface IHouseCaseSourcesRepositoryFactory {
    /**
     * Returns a provider containing collections of objects specified in objectsToProvide, stored under AA code keys.
     * 
     * @param objectsToProvide a set of object defined by class name that should be provided within the iterator
     * @param executionId unique key that all objects are stored under
     * @return
     */
    IHouseCaseSourcesRespository<Object> build(Iterable<Class<?>> objectsToProvide, String executionId);
}
