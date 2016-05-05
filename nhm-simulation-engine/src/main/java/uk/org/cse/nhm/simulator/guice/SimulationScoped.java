package uk.org.cse.nhm.simulator.guice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.ScopeAnnotation;

/**
 * This is a guice scoping annotation which is intended to indicate that particular
 * types or bindings are scoped per-simulation (i.e. they are singletons within
 * a simulation)
 * 
 * @author hinton
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ScopeAnnotation
public @interface SimulationScoped {

}
