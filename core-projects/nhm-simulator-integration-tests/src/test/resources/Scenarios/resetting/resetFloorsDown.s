(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400
          
    (def energy-before on:House)
    (def energy-after on:House)
          
    (def u-before on:House)
    (def u-after on:House)
          
	(policy
		(target name:a
			group:(group.all)
			exposure:(schedule.init)
			action:
				(do
					(set #energy-before (house.energy-use))
					(set #u-before (house.u-value of:Floors))
					
					(action.reset-floors u-values:0.5)
					
					(set #energy-after (house.energy-use))
					(set #u-after (house.u-value of:Floors))
				)
			)
		)
)