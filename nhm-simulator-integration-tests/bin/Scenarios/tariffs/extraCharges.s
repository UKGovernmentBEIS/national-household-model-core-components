(scenario
 start-date: 01/01/2012
 end-date: 01/01/2013
 stock-id: test-survey-cases

(report.aggregate name: house-money
	mode: (mode.dates (scenario-end))

	division: (division.by-combination (house.is-on-gas name: on-gas))

	(aggregate.mean 
		name: average-energy-spend
		(house.sum-transactions tags: warm-homes-discount))
)

 (policy
 
  	(target
   		name: grant-warm-homes-discount
		   group: (group.all)
		   exposure: (schedule.on-group-entry)
		   action: (action.extra-fuel-charge
		    	(extra-charge
				     name: warm-homes-discount
				     tags: [warm-homes-discount]
				     -135)))
		     
	     
 	 (target
	    name: remove-warm-homes-discount-if-on-gas-grid
	    group: (group.filter 
	             (house.is-on-gas))
	    exposure: (schedule.on-group-entry)
	    action: (action.remove-fuel-charge #warm-homes-discount))

))