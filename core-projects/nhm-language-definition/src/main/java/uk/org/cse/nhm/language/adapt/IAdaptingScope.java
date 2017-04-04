package uk.org.cse.nhm.language.adapt;

import java.util.List;

import com.google.common.base.Optional;

/**
 * The {@link IAdaptingScope} is used during object adaptation - it is managed using the call
 * stack of adapt methods. Each time an adapt method wants to provide information "downstream",
 *
 * 
 * @author hinton
 *
 */
public interface IAdaptingScope {
	
	/**
	 * Corresponds to {@link #getLocal(String)}
	 * @param scopeKey
	 * @param from
	 */
	void putLocal(String scopeKey, Object from);
	
	/**
	 * Get a value stored in the scope with the given key. This will
	 * return whichever value was most recently put in the scope closest
	 * to this one in the hierarchy of scopes.
	 * 
	 * @param scopeKey
	 * @return
	 */
	Object getLocal(String scopeKey);
	
	/**
	 * Corresponds to {@link #getGlobal(String)}
	 * @param scopeKey
	 * @param from
	 */
	void putGlobal(String scopeKey, Object from);
	
	/**
	 * Get a value stored in the scope with the given key from anywhere
	 * 
	 * @param scopeKey
	 * @return
	 */
	Object getGlobal(String scopeKey);

	/**
	 * Store an object into the global adapting cache for this scope.
	 * If this is called repeatedly, the results are appended to a list 
	 * rather than overwritten.
	 * 
	 * @param from
	 * @param result
	 */
	void putInCache(Object from, Object result);

	/**
	 * Get all the values that have been passed to {@link #putInCache(Object, Object)} with 
	 * from as the first argument in any scope derived from this scope's root scope
	 * 
	 * @param from
	 * @return
	 */
	List<Object> getFromCache(Object from);
	
	/**
	 * Try and get something from the cache as the right type; this will return something if
	 * 
	 * <ol>
	 *  <li> a value has been passed to {@link #putInCache(Object, Object)} with
	 *  the same key (from), which is an instanceof as</li>
	 *  <li>a value has been passed to {@link #putInCache(Object, Object)} with the same key
	 *  (from), which can be converted to as by an {@link IConverter} that the scope knows about</li>
	 * </ol>
	 * 
	 * @param from
	 * @param as
	 * @return
	 */
	public <T> Optional<T> getFromCache(Object from, Class<T> as);
	
	/**
	 * @return a new adapting scope which inherits its local variables and cache from this one.
	 */
	IAdaptingScope createChildScope();
}
