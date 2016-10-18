(scenario
	start-date: 01/01/2013
	end-date: 01/01/2014
	stock-id: my-lovely-stock
	quantum: 500
	
	(on.dates
		(scenario-start)
		(apply
			to: (all-houses)
			(set (def quantum on: House) (sim.quantum)))))