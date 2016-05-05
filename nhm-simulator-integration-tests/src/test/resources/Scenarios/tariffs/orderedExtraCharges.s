(scenario
 start-date: 01/01/2012
 end-date: 01/01/2013
 stock-id: test-survey-cases
 quantum: 1000

(report.aggregate name: house-money
	mode: (mode.dates (scenario-end))

	(aggregate.mean 
		name: average-energy-spend
		(house.sum-transactions counterparty: ":energy companies"))
)

 (policy
  	(target
		name: add-taxed-flat-charge
	    group: (group.all)
	    exposure: (schedule.on-group-entry)
	    action: (action.extra-fuel-charge
    		(extra-charge
			     name: taxed-flat-charge
			     tags: taxed-flat-charge
			     1000)))
 
 
  	(target
   		name: add-untaxed-flat-charge
		group: (group.all)
		exposure: (schedule.on-group-entry)
		action: (action.extra-fuel-charge
	    	(extra-charge
			     name: untaxed-flat-charge
			     tags: untaxed-flat-charge
			     depends-on: #tax
			     899)))
	     
 	 (target
	    name: add-tax
	    group: (group.all)
	    exposure: (schedule.on-group-entry)
	    action: (action.extra-fuel-charge 
	    (extra-charge
	    	name: tax
	    	tags: [tax]
	    	depends-on: #taxed-flat-charge
	    	(* (cost.sum) 10%))))
))