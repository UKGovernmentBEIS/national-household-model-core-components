package uk.org.cse.nhm.language.definition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to describe the default transaction tags from a tag-producing thing
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface ProducesTags {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Tag {

        public String value();

        public String[] detail() default {};
    }

    public Tag[] value();
}
