(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400

              ;;Define the variables required for function on each house, these will be set before and after measure
    (def temperature-before on: House default: 0)
    (def temperature-after on: House default: 0)
    
    (def permeability-before on:House default: 20)
    (def permeability-after on:House default: 19)
    
    (def heat-loss-before on:House default: 10)
    (def heat-loss-after on:House default: 10)
    
    (def double-glazed-before on:House default: 0.8)
    (def double-glazed-after on:House default: 0.8)
    
    ;;Report on the state of a house/houses at some point
    (def-report hideem-report
                (column name: aacode value: (house.survey-code))
                (column name: floor-area value: (house.total-floor-area))
                (column name: temperature-before value: temperature-before)
                (column name: temperature-after value: temperature-after)
                (column name: permeability-before value: permeability-before)
                (column name: permeability-after value: permeability-after)
                (column name: heat-loss-before value: heat-loss-before)
                (column name: heat-loss-after value: heat-loss-after)
                (column name: extract-fans value: (house.buildyear-is above: 1977))
                (column name: trickle-vents value: (house.buildyear-is above: 1977))
                (column name: double-glazed-before value: double-glazed-before)
                (column name: double-glazed-after value: double-glazed-after)
                (column name: health-sit value: (house.sit))
                (column name: health-sit-rebate value: (house.sit-rebate rebate: 500 temperature: 18))
                (column name: health-impact-mortality value: (health-impact-of
                                                                 permeability-before: (get permeability-before)
                                                                 permeability-after: (get permeability-after)
                                                                 
                                                                 temperature-before: (get temperature-before)
                                                                 temperature-after: (get temperature-after)
                                                                 
                                                                 heat-loss-before: (get heat-loss-before)
                                                                 heat-loss-after: (get heat-loss-after)
                                                                 
                                                                 had-extract-fans: (house.buildyear-is above: 1977) ;We could get this from a categorical house property if required
                                                                 has-extract-fans: (house.buildyear-is above: 1977) ;We could get this from a categorical house property if required
                                                                 
                                                                 had-trickle-vents: (house.buildyear-is above: 1977) ;We now know about passive vents in a house and could expose if required
                                                                 has-trickle-vents: (house.buildyear-is above: 1977) ;We now know about passive vents in a house and could expose if required
                                                                 
                                                                 horizon: 1
                                                                 offset: 0
                                                                 
                                                                 on: Mortality
                                                                 due-to: [WCaV, CP, CaV, LC, MI, WCV, WMI, Asthma, CMD]
                                                                 
                                                                 double-glazed-before: (get double-glazed-before)
                                                                 double-glazed-after: (get double-glazed-after))
                        ))
    
    (on.dates
        (scenario-start)
        (apply
            to: (filter
                    (all
                        (house.built-form-is MidTerrace)
                        (house.region-is SouthEast)
                        )
                    (all-houses))
            (do
                ;;Set values before making a change to the house
                (set temperature-before (house.living-area-temperature))
                (set permeability-before (house.permeability))
                (set heat-loss-before (house.heat-loss Total))
                (set double-glazed-before (house.double-glazed-proportion))
                
                ;; Change the house in some-way
                (measure.wall-insulation
                    report: hideem-report ;states that the hideem-report should be run before/after this measure is applied
                    suitable-construction: Any 
                    suitable-insulation: Any 
                    type: External 
                    thickness: 100 
                    capex: 0)
                
                ;;Set values after making the change
                (set temperature-after (house.living-area-temperature))
                (set permeability-after (house.permeability))
                (set heat-loss-after (house.heat-loss Total))
                (set double-glazed-after (house.double-glazed-proportion)) 
                
                ;;Send to report -hideem report
                ))
        ))