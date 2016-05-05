(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (on.dates (scenario-start)
          	(apply
          		(house.flag hello)
          		to: (filter (house.any-wall has-construction:Cavity has-cavity-insulation:false))))
)