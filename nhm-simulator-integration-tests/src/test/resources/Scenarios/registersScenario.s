(scenario stock-id: test-survey-cases
          end-date: 31/12/2014
          start-date: 01/01/2012
          quantum: 400

	(def accumulator on:House default:50)

	(template += [@1 @2] (increase @1 @2))

	(on.dates
		01/01/2012
		(apply
			(do
				{#accumulator += #accumulator}
				{#accumulator += 100}
				{#accumulator += #accumulator})
		)
	)

)