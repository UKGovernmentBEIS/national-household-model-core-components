package uk.org.cse.nhm.language.adapt.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.org.cse.nhm.language.adapt.IAdaptingScope;

/**
 * Indicates that the parameter value should be retrieved from the {@link IAdaptingScope}; this
 * is sugar for having the scope itself as a parameter
 * 
 * See also {@link PutScope}
 * 
 * @author hinton
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FromScope {
	public String value();
}
