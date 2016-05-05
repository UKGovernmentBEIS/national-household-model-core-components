(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
          weighting:round

  (def energy-before on:Event)
  (def delta on:House)
  (def delta2 on:House)
  
  (def-adjustment less-electric Electricity -1)
  (def-adjustment less-electric-more-gains missed-gains:10 Electricity -10)


  (template difference-under [@1]
  	(/ (- 
  		(under
  			(measure.add-adjustment @1) 
  			evaluate:house.energy-use)
  		house.energy-use
  	) 12)
  )

  (on.dates
  	(scenario-start)
  	
  	(apply
  		(do
  			(set #delta (difference-under #less-electric))
  			(set #delta2 (difference-under #less-electric-more-gains))
	  	)
  	)
  )
)