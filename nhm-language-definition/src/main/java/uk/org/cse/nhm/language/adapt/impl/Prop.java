package uk.org.cse.nhm.language.adapt.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Associates an {@link Adapt} annotated method's parameter with a property on
 * the associated adaptable type. The property will be retrieved, and if necessary adapted to the 
 * type required for the method.
 * 
 * @author hinton
 * 
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Prop {
	/**
	 * The property name (for example, if there is a getFoo() method, the property is called "foo"
	 * @return
	 */
	public String value();
}
