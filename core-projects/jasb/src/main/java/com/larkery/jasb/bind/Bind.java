package com.larkery.jasb.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a particular type should be bound to
 * s-expressions where the given string is at the head of the list, i.e.
 * a form like (some-string ...)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bind {
	/**
	 * @return name to bind the type to
	 */
	String value();
}
