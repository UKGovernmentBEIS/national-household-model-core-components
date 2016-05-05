(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                 name: "Total energy use goes up"
                                 group: (group.all)
                                 type: Increasing
                   )

		(on.dates
			[(scenario-end)]

			(apply
				(action.change-efficiency
					winter-efficiency: 70%
					summer-efficiency: -1%
					direction: Decrease))))