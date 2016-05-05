(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400

	(on.dates (scenario-start)
		(apply (house.flag hello) to:(sample 50%)))

	(on.change
		(apply (house.flag world) to:(affected-houses)))
		
	(on.change
		include-startup:true
		(apply (house.flag everyone) to:(affected-houses)))
)