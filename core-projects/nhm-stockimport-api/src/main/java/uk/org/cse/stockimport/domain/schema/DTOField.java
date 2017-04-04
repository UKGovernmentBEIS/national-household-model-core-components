package uk.org.cse.stockimport.domain.schema;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DTOField {
	String[] value();
	String description() default "Mysteriously undocumented";
	Constraint constraint() default @Constraint();
}
