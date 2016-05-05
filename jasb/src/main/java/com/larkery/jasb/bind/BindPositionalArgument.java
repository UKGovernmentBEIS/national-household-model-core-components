package com.larkery.jasb.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a property (in the java beans sense) to the nth unnamed argument, so
 * 
 * (some-thing x y z) would map x into argument 0, y into argument 1 and so on.
 * Should be applied to property getter.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindPositionalArgument {
	int value();
}
