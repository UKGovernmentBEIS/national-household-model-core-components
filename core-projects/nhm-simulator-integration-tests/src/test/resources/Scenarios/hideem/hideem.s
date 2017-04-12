(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400
          
          (def-report report
                (column name: v value: (health-impact-of
			                				permeability-before: 20
			                				permeability-after: 19		
			                				
			                				temperature-before: 18
			                				temperature-after: 18
			                				
			                				heat-loss-before: 10
			                				heat-loss-after: 10
			                				
			                				had-extract-fans: (= 0 0)
			                				has-extract-fans: (= 0 0)
			                				
			                				had-trickle-vents: (= 0 0)
			                				has-trickle-vents: (= 0 0)
			                				
			                				horizon: 1
			                				offset: 0
			                				
			                				on: Mortality
			                				due-to: WCaV
                
                )))
              
          
    (on.dates
        (scenario-start)
        (apply
            to: (sample 1% (all-houses))
            (measure.wall-insulation 
                report: report
                type: External 
                thickness: 40 
                capex: 0)))
    )