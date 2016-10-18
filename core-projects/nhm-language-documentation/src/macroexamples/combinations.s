(~combinations
	caption:"Generating packages using the combinations macro"
	description:"In this example we use the combinations macro to generate a set of packages
				 from a choice of two insulation measures and two heating measures or doing nothing."
	
	(~combinations
		template:(do all:true @rest)
		[(insulation-1) (insulation-2)]
		[(heating-1) (heating-2) (action.do-nothing)] 
	)
)