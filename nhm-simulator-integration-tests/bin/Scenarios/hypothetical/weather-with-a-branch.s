(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(def-snapshot before)
	
	(report.aggregate
		name:blah
		
		(aggregate.sum
			(do
				(take-snapshot #before)
				
				return:(under
					(counterfactual.weather
						(weather
								temperature: [0 0 0 0 0 0 0 0 0 0 0 0]
								windspeed: [0 0 0 0 0 0 0 0 0 0 0 0]
								insolation: [0 0 0 0 0 0 0 0 0 0 0 0]
						)	
					)
										; this all-of do nothing do nothing is here to ensure that if
										; we branch inside the under and then merge (which all-of does, to ensure correctness)
										; then stuff works even though we are doing weird things with counterfactuals
					(do all:true
					 (action.do-nothing)
					 (counterfactual.carbon)
					 (action.do-nothing))

					evaluate: (debug/function
						name:mangled-weather-is-mangled
						delegate:(house.energy-use)
					))
			)
		)
	)
)
