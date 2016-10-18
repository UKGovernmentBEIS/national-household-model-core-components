(example
 caption:"Using a transition report"
 description:"In this example, we define a transition report which
		divides houses by energy use. As houses' energy uses are changed by
		the operation of policies, if the change moves them into a different
		category of the report that change will be attributed to the relevant
		policies in the resulting diagram."

 (transitions name:energy-use
 	source: (all-houses)
 	divide-by: (function.steps
    	round: up
    	steps: [ 0 10 20 30 40 50 1000]
    	value: (house.energy-use))))