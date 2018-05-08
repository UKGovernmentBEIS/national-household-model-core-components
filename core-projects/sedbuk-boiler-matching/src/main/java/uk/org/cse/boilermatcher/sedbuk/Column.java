package uk.org.cse.boilermatcher.sedbuk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Annotation to mark getters for {@link AutoTable} to implement.
 *
 * This should be applied to methods which take a single argument, and return
 * something appropriate like an int, {@link Integer}, {@link Double}, double,
 * {@link String}, or {@link DateTime}.
 *
 * In the default behaviour {@link #value()} gives the column that will be
 * returned, and the method's argument should be the row for which the column is
 * being interrogated.
 *
 * If {@link #search()} is true, the method should instead return a {@link List}
 * of {@link Integer}, and the method will find all the rows where the given
 * column equals the argument.
 *
 * @author hinton
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Column {

    /**
     * The column number which this getter is getting; the given column will be
     * looked up for the row argument
     *
     * @return
     * @since 1.0
     */
    int value();

    /**
     * Whether this method is a search method, in which case it will return a
     * list of rows where the given column equals the method's argument
     *
     * @return
     * @since 1.0
     */
    boolean search() default false;
}
