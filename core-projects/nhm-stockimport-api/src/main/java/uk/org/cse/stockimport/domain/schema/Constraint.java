package uk.org.cse.stockimport.domain.schema;

public @interface Constraint {

    public enum Type {
        REQUIRED,
        AUTOMATIC,
        OPTIONAL
    }

    Type value() default Type.REQUIRED;

    String missing() default "null";
}
