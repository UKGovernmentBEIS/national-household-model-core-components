(
; there is a paren around this whole file for ease of reading
; contains the atom documentation for plain java types
; each of these expressions has a list of types as qualified classnames, and then a docstring to use
; because the docstring will be shoved straight into the docbook output, you must use docbook markup inside it
; in particular to escape entities.

(atom types: (java.lang.Integer java.lang.Long)
	  doc: "Whole numbers are as you might expect, for example 1, 0, -9 and so on.")

(atom types: (java.lang.Double java.lang.Float)
	  doc: "Real numbers can be written as decimals, or whole numbers, like 0.2, -4, and so on.
			As a convenience, proportions may be written as percentages; this is useful for expressing
			efficiencies. For example, the proportion 0.5 may also be expressed by writing 50%, or
			0.01 as 1%.")
	  
(atom types: (java.lang.String)
	  doc: "Text values are sequences of characters, mostly used for naming things; any text which
	  		contains no white space, semicolons, colons or parentheses can be written out directly,
	  		but to prevent ambiguity any text which does contain these characters must be contained 
	  		within double quotes (\").")
	  		
(atom types: (org.joda.time.Period)
	  doc: "Periods of time are used to specify delays or intervals between repeating events. A period
	  		is expressed as a number and a unit separated by a space. Because of the separating space,
	  		the period must be enclosed in quotes to prevent ambiguity. For example, \"1 year\",
	  		\"2 days\"; and \"6 months\"; are all valid time periods.")
	  		
(atom types: (org.joda.time.DateTime)
	  doc: "Date values are expressed using the normal British little-endian form with slashes separating
	  		the components of the date; for example the 23rd of December 1983 would be written 23/12/1983.
            As a convenience, you can write the first of January in any year by just giving the year, so
            2015 is the same as 01/01/2015.")

(atom types: (java.lang.Boolean)
	  doc: "Boolean (logical) values are simply true or false. true is the true value (that is to say, writing
	  		true corresponds to expressing the boolean value that is true), and anything other than true is the
	  		false value.")

(atom types: (uk.org.cse.commons.Glob)
      doc: "Patterns are rules for matching text values. The simplest patterns are just strings: for example, the pattern an-example-pattern
            matches only the string 'an-example-pattern'. More complex patterns use the following special characters:
            an exclamation mark (!) at the start of a pattern negates it, so the pattern !an-example-pattern matches any string except 'an-example-pattern'.
            The asterisk or star (*) matches any text (including no text), so the pattern * matches any string, and the pattern hello* matches any string which starts with hello (including 'hello').
            As a result, the pattern !* matches nothing, and the pattern !hello* matches any string except those starting with hello.
            The question mark (?) matches exactly one character, so the pattern ? matches any single-letter string (like 'a', 'b', or 'c').
            Similarly, the pattern ??? matches any three-letter string (like 'abc', or 'aaa').
            If you want a pattern to include the literal string * or ?, you can write the special characters by preceding them with a backslash.
            Patterns can match multiple options as a comma-separated list in angle brackets, for example the pattern
            <a,b,c>; will match either 'a', 'b', or 'c'
            All patterns are case-insensitive."
 )
)

