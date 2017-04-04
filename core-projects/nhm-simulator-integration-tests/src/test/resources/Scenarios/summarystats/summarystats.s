(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400

  (template aggregate.median [@fun]
  	(aggregate.n-tile 
  		n:0.5 
  		@fun))

  (template foo []
  	(lookup-table
  		row-keys:(house.region)
  		[region value]
  		[WesternScotland 0]
    	[EasternScotland 1]
    	[NorthEast 2]
    	[YorkshireAndHumber 3]
    	[NorthWest 4]
    	[EastMidlands 5]
    	[WestMidlands 6]
    	[SouthWest 7]
    	[EastOfEngland 8]
    	[SouthEast 9]
    	[London 10]
    	[NorthernScotland 11]
		[Wales 12]
	)
  )

  (on.dates
  	(scenario-start)
  	
  	(set 
  		(def mean on:Simulation)
  		(summarize
  			(aggregate.mean (foo))))
  			
  	(set 
  		(def median on:Simulation) 
  		(summarize
  			group: (all-houses)
  			(aggregate.median fun:(foo))))  			
  )
)
  	