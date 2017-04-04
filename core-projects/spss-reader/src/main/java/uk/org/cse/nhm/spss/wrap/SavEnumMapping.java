package uk.org.cse.nhm.spss.wrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is intended to be used on enum fields which are being returned
 * from a method annotated with {@link SavVariableMapping}. It tells the {@link SavStreamWrapperBuilder}
 * how to convert an SPSS value into an enum value.
 * 
 * @author hinton
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SavEnumMapping {
	/**
	 * an array of strings where each entry is an SPSS variable label. Any variable in an
	 * entry whose value goes to one of these labels will be returned as this enum value
	 * from the wrapper builder generated class.
	 * 
	 * @return
	 */
	public String[] value();
}
