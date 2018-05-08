package com.larkery.jasb.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a list-type property (in the java beans sense) to any anonymous
 * arguments which remain after things handled by
 * {@link BindPositionalArgument}.
 *
 * Should be applied to property getter.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindRemainingArguments {

}
