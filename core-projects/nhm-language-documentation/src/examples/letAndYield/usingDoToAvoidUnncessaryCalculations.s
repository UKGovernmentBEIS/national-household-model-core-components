(example
 caption:"Re-using a Calculated Number"
 description:"Sometimes we will have have a complicated function which we wish to use in multiple places.
		In such a case, rather than writing it out multiple times, we usually prefer to compute the function once and then pass its result around. 
		This example illustrates how to use set for this purpose."
 class: "uk.org.cse.nhm.language.definition.sequence.XSequenceFunction" 

 (do
 	; First we use set to pre-compute and store a value.  
 	(set 
 		; We must define our variable exactly once in the scenario. For this example, we've defined it where we first use it, but we could instead define it at the top of our scenario and refer to it by name. 
 		(def x)
 		; The calculation which will produce the value to be stored. This calculation will only be run once.
 		(+ 4 5 6))
 	
 	; This is the actual function that we will be computing.
 	return: {#x * (#x + 5)}
 )
)