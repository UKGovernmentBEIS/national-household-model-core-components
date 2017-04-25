(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 1000
          weighting:round

  (on.dates
  	(scenario-start)
  	
  	(apply
  		(do
			(measure.set-adjustment-terms
				adjustment-type: Appliances
				constant-term: 1
				linear-factor: 2
			)
	  	)
  	)
  )
)