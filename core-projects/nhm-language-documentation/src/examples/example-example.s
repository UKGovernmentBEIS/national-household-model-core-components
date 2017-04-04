(example
	caption: "An example example"
	description: "<emphasis>After the argument description,
		there may be some examples of how to use the element listed.
		each of those should have a description, like this,
		and some actual example code which will be presented
		in a source listing box</emphasis>"
	
	(example-element
		
		; the example may contain some comments like this
		; typically it will also show how to use the arguments
		; this is the first named argument
		a-named-argument: my-name-goes-here
		
		; next we have the 0th positional argument
		; in this case a list of two of the possible values.
		; the list is encased in square brackets here
		; to avoid any ambiguity about whether it is
		; a single invocation or a list of other values.
		; if you are providing a singular value to a many-valued
		; argument you can omit the square brackets.
		[(house.region-is London) (house.morphology-is Urban)]
		
		; finally all the remainder arguments go here one after another
		0 1 2 3))