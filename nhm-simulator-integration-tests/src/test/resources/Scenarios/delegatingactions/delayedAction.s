(scenario 
	start-date: 01/01/2012 
	end-date: 01/01/2014 
	stock-id: test-survey-cases
	
	(policy
		name: delayed-flag-for-all-houses
		
		(target
			; We want to ensure that the flags are set delayed rather than immediately.
			; To achieve this we clear them after setting up the delayed action, but before its delay has expired. 
			name: clear-flags-after-1-month
			group: (group.all)
			exposure: (schedule.on-group-entry delay: "1 month")
			action: (house.flag !flagged))
		
		(target
			name: set-flags-after-1-year
			group: (group.all)
			exposure: (schedule.on-group-entry)
			
			action: (action.delayed
				delay: "1 year"
				action: (house.flag flagged)))))