package uk.org.cse.nhm.language.definition.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
/**
 * A list of the suitablity rejection conditions for an {@link XDwellingAction} in the order that they are applied.
 * Suitability is a concept which is inherited: any tool which consumes this tag should search for all Suitablity tags in the inheritance hierarchy of the class being inspected, with tags from super-classes being applied first.
 * Each value represents a rejection condition: if any of these are met, the action is unsuitable for a dwelling, otherwise it is suitable.
 * 
 * @author glenns
 * @since 3.4.0
 */
public @interface Unsuitability {
	public String[] value() default {};
	public boolean alwaysSuitable() default false;
	public boolean overrideParents() default false;
}
