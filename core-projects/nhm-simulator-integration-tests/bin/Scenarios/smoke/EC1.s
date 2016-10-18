(scenario stock-id: test-survey-cases
          end-date: 31/12/2018
          start-date: 01/01/2013
          quantum: 400
          
          (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                    name: "Total energy goes down for all houses"
                                    group: (group.all)
                                    type: Decreasing
                      )
                      (report.state)
                      (include href: ../common/fuelproperties.s)
                      
          (context.weather
          	(weather.case
          		(when (sim.year-is below: 2014)
          			(weather
          				name:y0
          				temperature: [10 10 10 10 10 10 10 10 10 10 10 10]
          				windspeed:   [10 10 10 10 10 10 10 10 10 10 10 10]
          				insolation:  [10 10 10 10 10 10 10 10 10 10 10 10])
          		)
          		(when (sim.year-is exactly:2014)
          			(weather.perturb
          				#y0
          				offset-temperature:1))
          		default:
          		(weather.perturb #y0 offset-temperature:2)
          	)
          )
          	
          (include href: ../common/carbon.s))