(scenario
	start-date: 01/01/2013
	end-date: 01/01/2016
	stock-id: some-stock
	
	(on.change
		(transitions
			name: transitions
			divide-by: (house.main-heating-fuel)))
			
	(on.dates
		(regularly)
		
		(apply
			to: (sample 5% (all-houses))
			(measure.standard-boiler fuel: BiomassPellets winter-efficiency: 90%))))
			
		