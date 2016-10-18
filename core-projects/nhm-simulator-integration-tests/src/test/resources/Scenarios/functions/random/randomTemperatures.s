(scenario
	start-date: 01/01/2013
	end-date: 01/01/2014
	stock-id: my-lovely-stock
	
	(policy
		(target
			name: "randomize demand temperature"
			group: (group.all)
			exposure: (schedule.on-group-entry
				delay: "6 months")
			
			action: (action.set-heating-temperatures
				living-area-temperature: (random.gaussian 
					mean: 20 
					standard-deviation: 2)))
			
		(target
			name: "randomize difference between zone 1 and zone 2 temperatures"
			group: (group.all)
			exposure: (schedule.on-group-entry
				delay: "6 months")
			
			action: (action.set-heating-temperatures
				temperature-difference: (random.uniform
					start: 0
					end: 5)))))