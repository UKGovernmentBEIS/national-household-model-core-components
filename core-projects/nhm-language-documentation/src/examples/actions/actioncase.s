(example
	caption: "Conditionally applying a has loft flag to survey case."
	description: "In this example, we will apply a flag named has-loft to only house cases that have a loft."

	;Can be used where action is required
	(action.case
    	;first condition, if the survey case has a loft, apply flag
        (when (house.has-loft) 
        	(house.flag has-loft))
        	
        ;default condition here would be survey case has no loft so remove the flag
        default: (house.flag !has-loft)
    )
)