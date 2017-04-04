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
  		(house.flag hello-london)
  		to: (sample 
  			{summarize(aggregate.count(), group:filter(house.region-is(London))) / 2}
  		
  		
  			(filter (house.region-is London)))
  	)
  )
)