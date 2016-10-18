(scenario
	start-date: 01/01/2013
	end-date: 01/01/2014
	stock-id: my-stock
	
	(on.dates
		(scenario-start)
		
		(aggregate
			name: count-by-elecmop
			divide-by: (house.static-property elecmop)
			(aggregate.count))))