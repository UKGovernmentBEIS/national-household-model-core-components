package uk.org.cse.nhm.spss.wrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is intended to go on interface methods for interfaces that
 * will go into {@link SavStreamWrapperBuilder} - they tell a getter method what
 * SAV file variable they correspond to
 *
 * @author hinton
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface SavVariableMapping {

    /**
     * @return the SPSS variable name (<= 8 chars, something like FINGASMS) that
     * this getter is getting. Not case-sensitive This may have more than one
     * value, in which case the values will be tried in order until we find one
     * that works. If a value starts with a ~, the rest of the value is taken to
     * be a regular expression, and will be mapped to the first matching
     * variable.
     */
    public String[] value();
}
