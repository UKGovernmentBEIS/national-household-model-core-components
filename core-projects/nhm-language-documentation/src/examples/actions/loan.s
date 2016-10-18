(example
	caption: "Loans with a Chance of Failure"
	description: "In this example, we will install a boiler and finance it using a loan. 
		However, there will be an arbitrary 10% failure chance to represent credit check failures."

	(action.sometimes
		chance: 90%
	
	(example
		caption:"Financing a boiler with a loan"
		description:"In this example we install a boiler, and then finance the
			full capital cost of that boiler with a ten-year loan at five percent
			interest."
			
		(finance.with-loan
			term:10
			rate:5%
			principal:(capital-cost)
			(measure.standard-boiler fuel:MainsGas winter-efficiency:85%))
	)))
