(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400


  (on.dates
  	(scenario-start)
  	
  	(apply
  		(house.flag hello)
  		to: (sample 10%)
  	)
  	
  	(apply
  		(house.flag london)
  		to: (filter (house.region-is London))
  	)
  	
  	(apply
  		(house.flag hello-london)
  		to: (sample 4000 (filter (house.region-is London)))
  	)
  )
)