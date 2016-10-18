(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                 continuous: false
                                 name: "Energy use should go up lots"
                                 group: (group.all)
                                 type: Increasing
                   )

         (include href: ../common/all-weather.s)
          
          (policy name: "turn off everyone's heating, effectively"

                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "heating off"
                          action: (action.set-heating-temperatures living-area-temperature: 25)
                          group: (group.all)
                  )))