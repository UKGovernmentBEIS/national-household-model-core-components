package uk.org.cse.nhm.language.adapt.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.org.cse.nhm.language.adapt.IAdaptingScope;

/**
 * A convenience which indicates that a particular adapter method's parameter should be put into the
 * {@link IAdaptingScope}. Parameters will be put in the {@link IAdaptingScope} in the order that they
 * occur, and before the adapter method itself is called. Consequently this can be used as a way of passing
 * values down the tree without having to write your own adapt methods which know how to do that.
 * 
 * @author hinton
 *
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PutScope {
	public String value();
}
