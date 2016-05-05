(scenario
    start-date: 01/01/1
    end-date: 01/01/10
    stock-id: test-survey-cases
    quantum: 4000
    
    (def-action insulate
    	(measure.wall-insulation name:n type:cavity thickness:1 resistance: 0.1 report: my-report))
    
    (def-report my-report
    	(cut name:by-region region)
    	(cut name:summary)
    	
    	(column name: region value:house.region)
    	(column name: energy-use value: house.energy-use summary: [min max mean])
    )
    
    (on.dates (scenario-start)
    	(apply
	    	(do
	    		(send-to-report my-report from:before)
	    		(measure.standard-boiler winter-efficiency:99%)
	    		(send-to-report my-report from:after)))
	    		
	    (apply
	    	insulate
	    ))
)
