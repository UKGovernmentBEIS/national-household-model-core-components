(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400
          
    (def-report report
                (column name: aacode value: (house.survey-code))
                (column name: floor-area value: (house.total-floor-area))
                (column name: health-impact-mortality value: (health-impact-of
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
                                           due-to: [WCaV, CP, CaV, LC, MI, WCV, WMI]
                                           
                                           double-glazed-before: 0.8
                                           double-glazed-after: 0.8
                                           )))
        
    (on.dates
        (scenario-start)
        (apply
            to: (filter
                    (all
                        (house.built-form-is MidTerrace)
                        (house.region-is SouthEast)
                        )
                    (all-houses))
            (measure.wall-insulation
                report: report 
                suitable-construction: Any 
                suitable-insulation: Any 
                type: External 
                thickness: 100 
                capex: 0)))
    )