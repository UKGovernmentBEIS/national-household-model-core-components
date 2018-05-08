package uk.org.cse.nhm.language.definition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
/**
 * Put the @Obsolete annotation on a language element's class to mark it as
 * something that should no longer be used in the manual.
 */
public @interface Obsolete {

    /**
     * @return The reason we deprecated this language element.
     */
    String reason() default "";

    /**
     * @return The language elements which you can use instead.
     */
    Class<?>[] inFavourOf() default {};

    /**
     * @return How to change an existing scenario to use a different language
     * element.
     */
    String compatibility() default "";

    /**
     * @return The version in which this element was made obsolete.
     */
    String version() default "";
}
