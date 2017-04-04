package uk.org.cse.nhm.simulator.guice;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Use this with a multibinder to require things to be constructed by the injector
 *
 * @author hinton
 *
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Eager {

}
