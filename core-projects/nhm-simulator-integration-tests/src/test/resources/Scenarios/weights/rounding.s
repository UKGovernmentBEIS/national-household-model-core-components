(scenario
	start-date: 01/01/2013
	end-date: 01/01/2014
	stock-id: my-lovely-stock
	quantum: 1000
	weighting:round
	
	(on.dates scenario-start
		(set (def count on:Simulation) (summarize (aggregate.count)))
	)
)