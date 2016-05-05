package com.larkery.jasb.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a property (in the java beans sense) of something annotated with {@link Bind}
 * to a name. This corresponds to a key-value pairing in the s-expression, like
 * 
 * (bound-thing key: something ...)
 * 
 * If no value is specified, takes the name (key) from the property name.
 * 
 * Should be applied to property getter.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindNamedArgument {
	String value() default "#";
}
