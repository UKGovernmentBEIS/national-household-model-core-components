package uk.org.cse.stockimport.domain.schema;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DTO {
	String value();
	String[] description() default {};
	boolean required() default false;
}
