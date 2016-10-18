(scenario
	start-date: 01/01/2013
	end-date: 01/01/2014
	stock-id: my-lovely-stock
	quantum: 400
	weighting:uniform
	
	(on.dates scenario-start
		(set (def count on:Simulation) (summarize (aggregate.count)))
	)
)