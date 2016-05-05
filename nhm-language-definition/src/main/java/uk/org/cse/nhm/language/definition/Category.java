package uk.org.cse.nhm.language.definition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to define a category of elements in the documentation,
 * or to place an element into a category.
 * 
 * Elements will end up in the category of their superclass, if they have no category
 * statement on themselves.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface Category {
	public enum CategoryType implements Comparable<CategoryType> {
		EXAMPLE("Example category",
				"<emphasis>Elements are listed under categories; at the start of each category's section"+
					"is a description of the category, like this. This category contains an example element,"+
					"for explanatory purposes."+
					"The elements in the category are listed after the category description, with each element's"+
					"name being a heading.</emphasis>"
				),
		
		MAIN("Main elements", "These are the elements which you can put at the top level of a model run."),
		
		DECLARATIONS("Declarations and variables", "Terms about defining variables or actions."),
		REPORTING("Reporting and output",
				"Commands which create output, or are only useful for output."),
		
		AGGREGATE_VALUES("Aggregate values",
						"Terms relating to computing or using aggregate values."),
				
		SCHEDULING("Scheduling events",
				"These are terms which cause things to happen on particular dates, and some of the common things you might want to make happen."),
		
		SETSANDFLAGS("Selecting houses", "Terms for selecting subsets of the housing stock. See also logical values."),
		
		MEASURES("Measures",
						"Measures are actions which install technologies in a house, like insulation or heating systems."),
						
		ACTIONS("Actions", ""),
		ACTIONCOMBINATIONS("Action combinations",
				"These terms let you put together other actions to more complex compound operations."),
		RESETACTIONS("Reset actions", "Actions which reset aspects of the house using rules."),
		
		MONEY("Money", "Paying for things with loans and subsidies."),		
		TARIFFS("Tariffs",
				"Terms for defining tariffs and changing people's tariffs."),
		
		HOUSEPROPERTIES("Properties of houses", "Categorical or numeric values about houses."),
		LOGIC("Logical values", ""),
		LOGICCOMB("Logical combinations", "Terms for combining logical values."),
		LOGICHOUSE("Logical tests for houses", "Logical tests about houses."),
		
		CATEGORIES("Categorical values", ""),
		NUMBERS("Number values", ""),
		ARITHMETIC("Number operations", ""),		
		
		COUNTERFACTUALS("Counterfactual conditions", "Terms which use or manipulate counterfactual states."),
		
		BATCH("Batch runs", "Contains elements to run a scenario multiple times whilst varying its parameters."),
		
		WEATHER("Weather", "Things about the weather"),
		CARBON("Carbon factors", "Setting up and manipulating carbon factors"),

		
		CALIBRATION("Energy calculator controls",
				"The energy calculator can be 'calibrated' so that it makes the number you want. These terms help with that. In addition, some internal parameters can be adjusted, although who knows what they mean."),
		
		MISC("Other elements", "Anything which is not in all the other categories"),
		OBSOLETE("Obsolete language terms",
				"Commands that are no longer supported, and will eventually be removed from the model.")
				;
		
		public final String display;
		public final String description;
		
		private CategoryType(String display, String description) {
			this.display = display;
			this.description = description;
		}
	}
	
	public CategoryType value();
}
