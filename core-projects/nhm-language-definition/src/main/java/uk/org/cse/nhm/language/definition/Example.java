package uk.org.cse.nhm.language.definition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Example {

    public String title();

    public String[] doc() default {};

    public String[] source();

}
