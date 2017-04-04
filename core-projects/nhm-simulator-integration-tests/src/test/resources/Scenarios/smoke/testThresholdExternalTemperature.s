(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                 continuous: false
                                 name: "Energy use should go down lots"
                                 group: (group.all)
                                 type: Decreasing
                   )

          (include href: ../common/all-weather.s)
          (policy name: "turn off everyone's heating, effectively"

                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "heating off"
                          action: (action.set-heating-temperatures threshold-external-temperature: 0)
                          group: (group.all)
                  )))