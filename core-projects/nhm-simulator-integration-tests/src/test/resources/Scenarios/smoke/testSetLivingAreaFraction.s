(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
          
           (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                 continuous: false
                                 name: "Energy use should go down"
                                 group: (group.all)
                                 type: Decreasing
                                 bound: 0.01
                   )

          (include href: ../common/all-weather.s)
          (policy name: "change living area fraction"

                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "set la fraction"
                          action: (action.set-living-area-fraction to: 0.1)
                          group: (group.all)
                  )))