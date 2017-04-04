(scenario
	start-date: 01/01/2014
	end-date: 01/01/2015
	stock-id: my-lovely-stock
	
	(report.aggregate
		name: "Average u-values"
		
		(aggregate.mean
			name: "mean"
			(house.heating-responsiveness
				of: PrimarySpaceHeating)))
	
	(policy
		(target
			name: "Increase responsiveness"
			group: (group.all)
			exposure: (schedule.on-group-entry 
				delay: "12 months")
			
			action: (scale.responsiveness
				of: PrimarySpaceHeating
				10%))))