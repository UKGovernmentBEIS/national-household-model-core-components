(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
	(policy
		(target
			name:x
			group:(group.all)
			exposure:(schedule.on-group-entry)
			action:
				(action.case
					(when (house.region-is London)
						  (house.flag in-london))
					
					(when (house.built-form-is Detached)
						  (house.flag detached))
					
					default: (house.flag others))
			)
	) 
)